package ${package}.portlet;

import ${package}.constants.${className}PortletKeys;
#if (${liferayVersion} == "7.1")
import ${package}.constants.${className}WebKeys;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
#end

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
#if (${liferayVersion} == "7.1")

import java.io.IOException;
#end

import javax.portlet.Portlet;
#if (${liferayVersion} == "7.1")
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
#end

import org.osgi.service.component.annotations.Component;
#if (${liferayVersion} == "7.1")
import org.osgi.service.component.annotations.Reference;
#end

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=${artifactId} Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ${className}PortletKeys.${className},
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ${className}Portlet extends MVCPortlet {
#if (${liferayVersion} == "7.1")

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		JSPackage jsPackage = _npmResolver.getJSPackage();

		renderRequest.setAttribute(
			${className}WebKeys.BOOTSTRAP_REQUIRE,
			jsPackage.getResolvedId() + " as bootstrapRequire");

		super.doView(renderRequest, renderResponse);
	}

	@Reference
	private NPMResolver _npmResolver;

#end
}