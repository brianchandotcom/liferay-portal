package ${configYAML.apiPackagePath}.internal.application;

import javax.annotation.Generated;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=${configYAML.application.baseURI}",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION_SELECT + "=(osgi.jaxrs.name=Liferay.OAuth2)",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=${configYAML.application.name}.rest",
		"auth.verifier.auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes=/*",
		"auth.verifier.auth.verifier.OAuth2RestAuthVerifier.urls.includes=/*",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true",
		"oauth2.scopechecker.type=annotations"
	},
	service = Application.class
)
@Generated("")
public class ${configYAML.application.className} extends Application {
}