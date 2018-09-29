package ${package}.${template};

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=${portletName}",
		"mvc.command.name=/${portletName.toLowerCase()}/${className.toLowerCase()}"
	},
	service = MVCResourceCommand.class
)
public class ${className}MVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		_log.log(LogService.LOG_INFO, "${className} serviceResource");

		try {
			CaptchaUtil.serveImage(resourceRequest, resourceResponse);

			return false;
		}
		catch (Exception e) {
			_log.log(LogService.LOG_ERROR, e.getMessage(), e);

			return true;
		}
	}

	@Reference
	private LogService _log;

}