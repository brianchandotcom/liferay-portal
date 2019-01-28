package ${configYAML.apiPackagePath}.resource;

import ${configYAML.apiPackagePath}.dto.RESTCollection;
import ${configYAML.apiPackagePath}.dto.${schemaName};

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.rest.booster.apio.context.Pagination;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@Path("/${openAPIYAML.info.version}/${schemaPath}")
public interface ${schemaName}Resource {

	@GET
	@Produces("application/json")
	@RequiresScope("${configYAML.application.name}.read")
	public RESTCollection<${schemaName}> get${schemaName}s(
			@QueryParam("size") String size, @Context Pagination pagination)
		throws Exception;

}