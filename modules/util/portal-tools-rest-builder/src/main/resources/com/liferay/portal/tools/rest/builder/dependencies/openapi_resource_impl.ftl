package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

<#assign
	schemas = freeMarkerTool.getSchemas(openAPIYAML)
	allSchemas = freeMarkerTool.getAllSchemas(null, openAPIYAML, schemas)

	resourceSchemaNameClasses = {}
/>

<#list allSchemas?keys as schemaName>
	<#assign javaMethodSignatures = freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName) />

	<#if javaMethodSignatures?has_content>
		<#if schemas?keys?seq_contains(schemaName)>
			<#assign resourceSchemaNameClasses = resourceSchemaNameClasses + {schemaName + "ResourceImpl.class": schemaName + ".class"} />
			import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
		<#else>
			<#assign resourceSchemaNameClasses = resourceSchemaNameClasses + {schemaName + "ResourceImpl.class": "null"} />
		</#if>
	</#if>
</#list>

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashMap;
import java.util.Map;

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
	<#if configYAML.liferayEnterpriseApp>enabled = false,</#if>
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
<#if configYAML.application??>
	@Path("/${openAPIYAML.info.version}")
</#if>
public class OpenAPIResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type) throws Exception {
		try {
			Class<? extends OpenAPIResource> clazz = _openAPIResource.getClass();

			clazz.getMethod("getOpenAPI", long.class, Map.class, String.class, UriInfo.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return _openAPIResource.getOpenAPI(_resourceClasses.keySet(), type);
		}

		return _openAPIResource.getOpenAPI(_company.getCompanyId(), _resourceClasses, type, _uriInfo);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Context
	private UriInfo _uriInfo;

	private final Map<Class<?>, Class<?>> _resourceClasses = new HashMap<Class<?>, Class<?>>() {
		{
			<#list resourceSchemaNameClasses?keys?sort as resourceClass>
				put(${resourceClass}, ${resourceSchemaNameClasses[resourceClass]});
			</#list>

			put(OpenAPIResourceImpl.class, null);
		}
	};

	@Context
	private Company _company;

}