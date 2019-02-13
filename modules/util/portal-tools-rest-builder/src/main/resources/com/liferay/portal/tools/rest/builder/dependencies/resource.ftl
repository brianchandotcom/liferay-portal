package ${configYAML.apiPackagePath}.resource.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;

import javax.annotation.Generated;

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

	<#list javaTool.getJavaSignatures(openAPIYAML, schemaName) as javaSignature>
		<#compress>
			<#list javaTool.getMethodAnnotations(javaSignature) as methodAnnotation>
				${methodAnnotation}
			</#list>

			<@compress single_line=true>
				public ${javaSignature.returnType} ${javaSignature.methodName}(
					<#list javaSignature.javaParameters as javaParameter>
						${javaTool.getParameterAnnotation(javaParameter)} ${javaParameter.parameterType} ${javaParameter.parameterName}

						<#if javaParameter_has_next>
							,
						</#if>
					</#list>
				) throws Exception;
			</@compress>
		</#compress>

		${"\n"}<#lt>
	</#list>

}