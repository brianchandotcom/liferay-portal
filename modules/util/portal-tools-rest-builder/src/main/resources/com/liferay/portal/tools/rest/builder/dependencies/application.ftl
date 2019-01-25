package ${apiPackagePath}.internal.application;

import javax.annotation.Generated;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author ${author}
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=${applicationBaseURI}",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=${applicationName}"
	},
	service = Application.class
)
public class ${applicationClassName} extends Application {
}