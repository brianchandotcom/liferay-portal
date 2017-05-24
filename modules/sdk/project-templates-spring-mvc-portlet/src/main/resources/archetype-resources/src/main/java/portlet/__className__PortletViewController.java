package ${package}.portlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

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
}