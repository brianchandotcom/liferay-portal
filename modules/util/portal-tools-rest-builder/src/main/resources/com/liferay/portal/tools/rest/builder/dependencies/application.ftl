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
		<#compress>
			<#if !configYAML.application.security?? || !configYAML.application.security.basicAuth?? || stringUtil.equalsIgnoreCase(configYAML.application.security.basicAuth, "true")>
				"auth.verifier.auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes=/*",
			</#if>

			<#if !configYAML.application.security?? || !configYAML.application.security.guestAllowed?? || stringUtil.equalsIgnoreCase(configYAML.application.security.guestAllowed, "true")>
				"auth.verifier.guest.allowed=true",
			</#if>

			<#if !configYAML.application.security?? || !configYAML.application.security.OAuth2?? || stringUtil.equalsIgnoreCase(configYAML.application.security.OAuth2, "true")>
				"auth.verifier.auth.verifier.OAuth2RestAuthVerifier.urls.includes=/*",
				"oauth2.scope.checker.type=annotations",
				"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.OAuth2)",
			</#if>

			<#if javaTool.hasJavaParameterAcceptLanguage(openAPIYAML) || javaTool.hasJavaParameterPagination(openAPIYAML)>
				"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan.JSONMessageBodyReader)",
				"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan.JSONMessageBodyWriter)",

				<#if javaTool.hasJavaParameterAcceptLanguage(openAPIYAML)>
					"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan.AcceptLanguageContextProvider)",
				</#if>

				<#if javaTool.hasJavaParameterPagination(openAPIYAML)>
					"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan.PaginationContextProvider)",
				</#if>
			</#if>
		</#compress>

		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"osgi.jaxrs.application.base=${configYAML.application.baseURI}",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan.CompanyContextProvider)",
		"osgi.jaxrs.name=${configYAML.application.name}.rest"
	},
	service = Application.class
)
@Generated("")
public class ${configYAML.application.className} extends Application {
}