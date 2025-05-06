package ${configYAML.apiPackagePath}.internal.jaxrs.application;

<#assign javaEePrefix = freeMarkerTool.getJavaEePrefix(configYAML) />

import ${javaEePrefix}.annotation.Generated;
import ${javaEePrefix}.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(
	<#if configYAML.liferayEnterpriseApp>enabled = false,</#if>
	property = {
		"liferay.jackson=false",
		"osgi.jaxrs.application.base=${configYAML.application.baseURI}",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan)",
		"osgi.jaxrs.name=${configYAML.application.name}"
	},
	service = Application.class
)
@Generated("")
public class ${configYAML.application.className} extends Application {
}