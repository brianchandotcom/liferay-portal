package ${package}.controller;

import ${package}.dto.User;

#if (${framework.equals("portletmvc4spring")})
import com.liferay.portletmvc4spring.bind.annotation.ActionMapping;
import com.liferay.portletmvc4spring.bind.annotation.RenderMapping;
#elseif (${framework.equals("springportletmvc")})
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
#end

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;

import javax.portlet.ActionResponse;
#if (${framework.equals("portletmvc4spring")})
import javax.portlet.MutableRenderParameters;
#end
#if (${viewType.equals("thymeleaf")})
import javax.portlet.RenderResponse;
#end

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author ${author}
 */
@Controller
@RequestMapping("VIEW")
public class UserController {

	@ModelAttribute("user")
	public User getUserModelAttribute() {
		return new User();
	}

#if (${viewType.equals("jsp")})
	@RenderMapping
	public String prepareView() {
		return "user";
	}
#end
#if (${viewType.equals("thymeleaf")})
	@RenderMapping
	public String prepareView(ModelMap modelMap, RenderResponse renderResponse) {

		modelMap.put("mainFormActionURL", renderResponse.createActionURL());
		modelMap.put("namespace", renderResponse.getNamespace());

		return "user";
	}
#end

	@RenderMapping(params = "javax.portlet.action=success")
	public String showGreeting(ModelMap modelMap) {

		DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy G");

		Calendar todayCalendar = Calendar.getInstance();

		modelMap.put("todaysDate", dateFormat.format(todayCalendar.getTime()));

		return "greeting";
	}

	@ActionMapping
	public void submitApplicant(
		@ModelAttribute("user") User user, BindingResult bindingResult,
		ModelMap modelMap, Locale locale, ActionResponse actionResponse,
		SessionStatus sessionStatus) {

		_localValidatorFactoryBean.validate(user, bindingResult);

		if (!bindingResult.hasErrors()) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("firstName=" + user.getFirstName());
				_logger.debug("lastName=" + user.getLastName());
			}

#if (${framework.equals("portletmvc4spring")})
			MutableRenderParameters mutableRenderParameters =
				actionResponse.getRenderParameters();

			mutableRenderParameters.setValue("javax.portlet.action", "success");
#elseif (${framework.equals("springportletmvc")})
			actionResponse.setRenderParameter("javax.portlet.action", "success");
#end

			sessionStatus.setComplete();
		}
		else {
			bindingResult.addError(
				new ObjectError(
					"user",
					_messageSource.getMessage(
						"please-correct-the-following-errors", null, locale)));
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		UserController.class);

	@Autowired
	private LocalValidatorFactoryBean _localValidatorFactoryBean;

	@Autowired
	private MessageSource _messageSource;

}