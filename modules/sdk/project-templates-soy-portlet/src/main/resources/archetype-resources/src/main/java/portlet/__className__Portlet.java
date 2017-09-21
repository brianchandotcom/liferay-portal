package ${package}.portlet;

import ${package}.constants.${className}PortletKeys;

import com.liferay.portal.portlet.bridge.soy.SoyPortlet;

import java.io.IOException;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.single-page-application=false",
		"javax.portlet.display-name=${artifactId} Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=View",
		"javax.portlet.name=" + ${className}PortletKeys.${className},
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ${className}Portlet extends SoyPortlet {
}