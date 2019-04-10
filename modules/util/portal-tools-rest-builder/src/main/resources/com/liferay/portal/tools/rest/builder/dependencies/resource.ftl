package ${configYAML.apiPackagePath}.resource.${escapedVersion};

<#list openAPIYAML.components.schemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
</#list>

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Date;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

	<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName) as javaMethodSignature>
		${freeMarkerTool.getResourceMethodAnnotations(javaMethodSignature)}
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(${freeMarkerTool.getResourceParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, true)}) throws Exception;
	</#list>

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}