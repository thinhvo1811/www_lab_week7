package vn.edu.iuh.fit.week07_lab_voquocthinh.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.week07_lab_voquocthinh.backend.enums.UserType;
import vn.edu.iuh.fit.week07_lab_voquocthinh.backend.models.Customer;
import vn.edu.iuh.fit.week07_lab_voquocthinh.backend.models.User;
import vn.edu.iuh.fit.week07_lab_voquocthinh.backend.repositories.CustomerRepository;
import vn.edu.iuh.fit.week07_lab_voquocthinh.backend.repositories.UserRepository;

import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(
            @ModelAttribute("user") User user,
            @ModelAttribute("customer") Customer customer
            ){
        user.setType(UserType.CUSTOMER);
        customer.setUser(user);
        customerRepository.save(customer);

        return "redirect:/home";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("user") User user,
            HttpSession session
    ){
        Optional<User> user1 = userRepository.findById(user.getUsername());
        if (user1.isPresent()){
            if(user1.get().getPassword().equals(user.getPassword()) && user1.get().getType().equals(UserType.CUSTOMER)){
                session.setAttribute("customerSession",user1.get());
                return "redirect:/home";
            }
            else {
                return "redirect:/home";
            }
        }
        else {
            return "redirect:/home";
        }
    }

    @GetMapping("/logout")
    public String logout(
            HttpSession session
    ){
        session.removeAttribute("customerSession");
        return "redirect:/home";
    }
}
