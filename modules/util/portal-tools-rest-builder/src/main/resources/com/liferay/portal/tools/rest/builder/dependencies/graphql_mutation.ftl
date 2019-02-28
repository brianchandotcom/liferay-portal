package ${configYAML.apiPackagePath}.internal.graphql.mutation.${escapedVersion};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
		import ${configYAML.apiPackagePath}.internal.resource.${escapedVersion}.${schemaName}ResourceImpl;
		import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
	</#list>
</#compress>

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class Mutation {

	<#assign javaMethodSignatures = freeMarkerTool.getGraphQLJavaMethodSignatures(configYAML, "mutation", openAPIYAML) />

	<#list javaMethodSignatures as javaMethodSignature>
		${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(${freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, true)}) throws Exception {
			<#if stringUtil.equals(javaMethodSignature.returnType, "Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#else>
				<#assign schemaName = javaMethodSignature.schemaName />

				${schemaName}Resource ${schemaName?uncap_first}Resource = _create${schemaName}Resource();

				return ${schemaName?uncap_first}Resource.${javaMethodSignature.methodName}(${freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters)});
			</#if>
		}
	</#list>

	<#assign schemaNames = freeMarkerTool.getGraphQLSchemaNames(javaMethodSignatures) />

	<#list schemaNames as schemaName>
		private static ${schemaName}Resource _create${schemaName}Resource() {
			return new ${schemaName}ResourceImpl();
		}
	</#list>

}