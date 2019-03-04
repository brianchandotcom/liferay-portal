package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

import com.liferay.portal.vulcan.resource.DocumentationResource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author ${configYAML.author}
 */
@Component(
	properties = "OSGI-INF/liferay/rest/${escapedVersion}/documentation.properties",
	scope = ServiceScope.PROTOTYPE, service = DocumentationResourceImpl.class
)
@Path("/${openAPIYAML.info.version}")
public class DocumentationResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenApi(
		@Context HttpHeaders headers, @Context UriInfo uriInfo,
		@PathParam("type") String type)
	throws Exception {

		Set<Class<?>> resourceClasses = new HashSet<>();

		<#list filteredSchemas as schemaName>
			resourceClasses.add(${schemaName}ResourceImpl.class);
		</#list>

		return _documentationResource.getOpenApi(
			resourceClasses, headers, _servletConfig, _application, uriInfo,
			type);
	}

	@Context
	private Application _application;

	@Reference
	private DocumentationResource _documentationResource;

	@Context
	private ServletConfig _servletConfig;
}