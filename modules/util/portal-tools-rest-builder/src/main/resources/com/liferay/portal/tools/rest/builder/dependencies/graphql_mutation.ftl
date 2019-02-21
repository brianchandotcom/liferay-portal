package ${configYAML.apiPackagePath}.internal.graphql.mutation.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
		import ${configYAML.apiPackagePath}.internal.dto.${versionDirName}.${schemaName}Impl;
		import ${configYAML.apiPackagePath}.internal.resource.${versionDirName}.${schemaName}ResourceImpl;
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

	<#assign javaMethodSignatures = freeMarkerTool.getGraphQLJavaMethodSignatures(configYAML, openAPIYAML, "mutation", false) />

	<#list javaMethodSignatures as javaMethodSignature>
		<#assign returnType = javaMethodSignature.returnType />

		<#if allSchemas?keys?seq_contains(returnType)>
			<#assign returnType = returnType + "Impl" />
		</#if>

		${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
		public ${returnType} ${javaMethodSignature.methodName}(
				${freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaParameters, true)})
			throws Exception {

			<#if stringUtil.equals(javaMethodSignature.returnType, "Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#else>
				return _get${javaMethodSignature.schemaName}ResourceImpl().${javaMethodSignature.methodName}(
					${freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaParameters)});
			</#if>
		}
	</#list>

	<#assign schemaNames = freeMarkerTool.getGraphQLSchemaNames(javaMethodSignatures) />

	<#list schemaNames as schemaName>
		private static ${schemaName}ResourceImpl _get${schemaName}ResourceImpl() {
			return new ${schemaName}ResourceImpl();
		}
	</#list>

}