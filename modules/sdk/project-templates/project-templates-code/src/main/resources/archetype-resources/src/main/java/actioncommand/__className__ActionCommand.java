package ${package}.${template};

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

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
		"mvc.command.name=${className.toLowerCase()}"
	},
	service = MVCActionCommand.class
)
public class ${className}ActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		String nameParam = ParamUtil.get(actionRequest, "name", "");

		String message = "Hello " + nameParam;

		_log.log(LogService.LOG_INFO, message);

		actionRequest.setAttribute("${className.toUpperCase()}_MESSAGE", message);

		return true;
	}

	@Reference
	private LogService _log;

}