package ${configYAML.apiPackagePath}.internal.jaxrs.application;

<#if freeMarkerTool.isUseJavax(configYAML)>
	import javax.annotation.Generated;

	import javax.ws.rs.core.Application;

<#else>
	import jakarta.annotation.Generated;

	import jakarta.ws.rs.core.Application;
</#if>

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