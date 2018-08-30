package ${package}.${template};

import ${package}.${template}.constants.${className}PortletKeys;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

/**
 * @author ${author}
 */
@Component(
	configurationPid = "${package}.${template}.${className}DisplayConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = "javax.portlet.name=" + ${className}PortletKeys.$className.toUpperCase()_PORTLET,
	service = ConfigurationAction.class
)
public class ${className}DisplayConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_log.log(
			LogService.LOG_INFO, "${className} Portlet configuration include");

		httpServletRequest.setAttribute(
			${className}DisplayConfiguration.class.getName(),
			_messageDisplayConfiguration);

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		_log.log(
			LogService.LOG_INFO, "${className} Portlet configuration action");

		String fontColor = ParamUtil.getString(actionRequest, "fontColor");
		String fontFamily = ParamUtil.getString(actionRequest, "fontFamily");
		String fontSize = ParamUtil.getString(actionRequest, "fontSize");

		_log.log(
			LogService.LOG_INFO,
			"Message Display Configuration: Font Family: " + fontFamily);
		_log.log(
			LogService.LOG_INFO,
			"Message Display Configuration: Font Size: " + fontSize);
		_log.log(
			LogService.LOG_INFO,
			"Message Display Configuration: Font Color: " + fontColor);

		setPreference(actionRequest, "fontColor", fontColor);
		setPreference(actionRequest, "fontFamily", fontFamily);
		setPreference(actionRequest, "fontSize", fontSize);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		_messageDisplayConfiguration = ConfigurableUtil.createConfigurable(
			${className}DisplayConfiguration.class, properties);
	}

	@Reference
	private LogService _log;

	private volatile ${className}DisplayConfiguration _messageDisplayConfiguration;

}