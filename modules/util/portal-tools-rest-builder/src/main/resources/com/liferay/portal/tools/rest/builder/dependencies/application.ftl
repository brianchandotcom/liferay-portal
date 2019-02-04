package ${configYAML.apiPackagePath}.internal.jaxrs.application;

import javax.annotation.Generated;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(
	property = {
		"auth.verifier.auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes=/*",
		"auth.verifier.auth.verifier.OAuth2RestAuthVerifier.urls.includes=/*",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true",
		"oauth2.scope.checker.type=annotations",
		"osgi.jaxrs.application.base=${configYAML.application.baseURI}",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.OAuth2)",

		<#if javaTool.hasJavaParameterAcceptLanguage(openAPIYAML) || javaTool.hasJavaParameterPagination(openAPIYAML)>
			<#if javaTool.hasJavaParameterAcceptLanguage(openAPIYAML)>
				"osgi.jaxrs.extension.select=(osgi.jaxrs.name=vulcan.AcceptLanguageContextProvider)",
			</#if>

			"osgi.jaxrs.extension.select=(osgi.jaxrs.name=vulcan.JSONMessageBodyReader)",
			"osgi.jaxrs.extension.select=(osgi.jaxrs.name=vulcan.JSONMessageBodyWriter)",

			<#if javaTool.hasJavaParameterPagination(openAPIYAML)>
				"osgi.jaxrs.extension.select=(osgi.jaxrs.name=vulcan.PaginationContextProvider)",
			</#if>
		</#if>

		"osgi.jaxrs.name=${configYAML.application.name}.rest"
	},
	service = Application.class
)
@Generated("")
public class ${configYAML.application.className} extends Application {
}