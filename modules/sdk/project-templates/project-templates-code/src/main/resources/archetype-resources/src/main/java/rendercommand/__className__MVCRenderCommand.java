package ${package}.${template};

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=${portletName}",
		"mvc.command.name=/${portletName.toLowerCase()}/${className.toLowerCase()}"
	},
	service = MVCRenderCommand.class
)
public class ${className}MVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		return "/render.jsp";
	}

}