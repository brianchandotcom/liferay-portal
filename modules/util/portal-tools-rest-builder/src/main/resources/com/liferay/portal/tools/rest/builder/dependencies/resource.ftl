package ${configYAML.apiPackagePath}.resource;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${schemaName};
	</#list>
</#compress>

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
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

	<#list openAPIYAML.pathItems?keys as path>
		<#assign pathItem = openAPIYAML.pathItems[path] />

		<#list javaTool.getOperations(pathItem) as operation>
			<#assign javaSignature = javaTool.getJavaSignature(configYAML, openAPIYAML, operation, path, pathItem, schemaName) />

			<#if !stringUtil.equals(javaSignature.returnType, schemaName) && !stringUtil.equals(javaSignature.returnType, "Page<${schemaName}>") && !stringUtil.endsWith(javaSignature.methodName, schemaName)>
				<#continue>
			</#if>

			<#compress>
				<#list javaSignature.methodAnnotations as methodAnnotation>
					${methodAnnotation}
				</#list>

				<@compress single_line=true>
					public ${javaSignature.returnType} ${javaSignature.methodName}(
						<#list javaSignature.javaParameters as javaParameter>
							<#list javaParameter.parameterAnnotations as parameterAnnotation>
								${parameterAnnotation}
							</#list>

							${javaParameter.parameterType} ${javaParameter.parameterName}

							<#if javaParameter_has_next>
								,
							</#if>
						</#list>
					) throws Exception;
				</@compress>
			</#compress>
		</#list>
	</#list>

}