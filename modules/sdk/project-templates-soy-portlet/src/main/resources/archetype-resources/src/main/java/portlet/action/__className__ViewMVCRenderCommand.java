package ${package}.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=${className}", "mvc.command.name=View",
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ${className}ViewMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		Template template = (Template)renderRequest.getAttribute(
			WebKeys.TEMPLATE);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		template.put("layouts", themeDisplay.getLayouts());

		PortletURL navigationURL = renderResponse.createRenderURL();

		navigationURL.setParameter("mvcRenderCommandName", "Navigation");

		template.put("navigationURL", navigationURL.toString());

		template.put("releaseInfo", ReleaseInfo.getReleaseInfo());

		return "View";
	}

}