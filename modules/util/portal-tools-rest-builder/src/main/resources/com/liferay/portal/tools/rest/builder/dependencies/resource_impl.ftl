package ${apiPackagePath}.internal.resource;

import ${apiPackagePath}.resource.${name}Resource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author ${author}
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=${applicationName})",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=${info.version}"
	},
	scope = ServiceScope.PROTOTYPE,
	service = ${name}Resource.class)
public class ${name}ResourceImpl implements ${name}Resource {
}