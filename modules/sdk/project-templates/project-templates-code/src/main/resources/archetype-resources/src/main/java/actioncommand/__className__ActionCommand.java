package ${package}.${template};

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
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
		"javax.portlet.name=${portletName} Portlet",
		"mvc.command.name=$className.toLowerCase()"
	},
	service = MVCActionCommand.class
)
public class ${className}ActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		_handleActionCommand(actionRequest);

		return true;
	}

	private void _handleActionCommand(ActionRequest actionRequest) {
		String name = ParamUtil.get(actionRequest, "name", StringPool.BLANK);

		_log.log(LogService.LOG_INFO, "Hello " + name);

		String $className.toUpperCase()Message = "Hello " + name";

		actionRequest.setAttribute("$className.toUpperCase()_MESSAGE", $className.toLowerCase()Message);

		SessionMessages.add(actionRequest, "$className.toLowerCase()Message", $className.toLowerCase()Message);
	}

	@Reference
	private LogService _log;

}