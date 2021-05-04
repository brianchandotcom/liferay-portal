package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(
	properties = "OSGI-INF/liferay/rest/${escapedVersion}/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(
		<#if openAPIYAML.info?? && openAPIYAML.info.description??>
			description = "${openAPIYAML.info.description}",
		</#if>
		<#if configYAML.licenseName?? && configYAML.licenseURL??>
			license = @License(name = "${configYAML.licenseName}", url = "${configYAML.licenseURL}"),
		</#if>

		title = "${openAPIYAML.info.title}",
		version = "${openAPIYAML.info.version}"
	)
)
@Path("/${openAPIYAML.info.version}")
public class OpenAPIResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type) throws Exception {
		try {
			Class<? extends OpenAPIResource> clazz = _openAPIResource.getClass();

			clazz.getMethod("getOpenAPI", Set.class, String.class, UriInfo.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return _openAPIResource.getOpenAPI(_resourceClasses, type);
		}

		return _openAPIResource.getOpenAPI(_resourceClasses, type, _uriInfo);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Context
	private UriInfo _uriInfo;

	private final Set<Class<?>> _resourceClasses = new HashSet<Class<?>>() {
		{
			<#list freeMarkerTool.getAllSchemas(null, openAPIYAML, freeMarkerTool.getSchemas(openAPIYAML))?keys as schemaName>
				<#assign javaMethodSignatures = freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName) />

				<#if javaMethodSignatures?has_content>
					add(${schemaName}ResourceImpl.class);
				</#if>
			</#list>

			add(OpenAPIResourceImpl.class);
		}
	};

}