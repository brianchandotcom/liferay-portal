package ${package}.portlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author ${author}
 */
@Controller
@RequestMapping("VIEW")
public class ${className}PortletViewController {

	/**
	 * Returns the normal view.
	 *
	 * @param  request the render request
	 * @param  response the render response
	 * @return the view result
	 */
	@RenderMapping
	public String view(RenderRequest request, RenderResponse response) {
		return "view";
	}

	/**
	 * Handles the action when the action key is <code>update</code>.
	 *
	 * @param  actionRequest the action request
	 * @param  response the action response
	 * @throws Exception if an exception occurred
	 */
	@ActionMapping(params = "action=update")
	public void update(ActionRequest actionRequest, ActionResponse response)
		throws Exception {
		// implement update logic
	}
}
