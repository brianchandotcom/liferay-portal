package _package_.portlet;

import _package_.constants._CLASS_PortletKeys;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=_NAME_ Portlet",
		"javax.portlet.name=" + _CLASS_PortletKeys._CLASS_,
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class _CLASS_Portlet extends GenericPortlet {

	@Override
	protected void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PrintWriter printWriter = renderResponse.getWriter();

		printWriter.print("_NAME_ Add Portlet Provider");
	}

}