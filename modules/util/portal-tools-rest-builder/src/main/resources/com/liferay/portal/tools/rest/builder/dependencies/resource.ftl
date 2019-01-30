package ${configYAML.apiPackagePath}.resource;

import ${configYAML.apiPackagePath}.dto.${schemaName};
import ${configYAML.apiPackagePath}.dto.${schemaName}Collection;

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}
 *
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@Path("/${openAPIYAML.info.version}")
public interface ${schemaName}Resource {

	@GET
	@Produces("application/json")
	@RequiresScope("${configYAML.application.name}.read")
	public ${schemaName}Collection<${schemaName}> get${schemaName}Collection(
			@Context Pagination pagination, @QueryParam("size") String size)
		throws Exception;

}