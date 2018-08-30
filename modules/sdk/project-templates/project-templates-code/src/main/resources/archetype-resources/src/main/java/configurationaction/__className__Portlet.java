package ${package}.${template};

import ${package}.${template}.constants.${className}PortletKeys;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

/**
 * @author ${author}
 */
@Component(
	configurationPid = "${package}.${template}.${className}DisplayConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=${portletName} Portlet",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ${className}PortletKeys.$className.toUpperCase()_PORTLET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ${className}Portlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		_log.log(LogService.LOG_INFO, "${className} Portlet render");

		renderRequest.setAttribute(
			${className}DisplayConfiguration.class.getName(),
			_messageDisplayConfiguration);

		super.doView(renderRequest, renderResponse);
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