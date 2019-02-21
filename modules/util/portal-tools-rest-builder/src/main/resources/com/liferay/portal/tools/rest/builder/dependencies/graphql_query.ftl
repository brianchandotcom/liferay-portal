package ${configYAML.apiPackagePath}.internal.graphql.query.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.internal.dto.${versionDirName}.${schemaName}Impl;
		import ${configYAML.apiPackagePath}.internal.resource.${versionDirName}.${schemaName}ResourceImpl;
	</#list>
</#compress>

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
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
public class Query {

	<#assign javaMethodSignatures = freeMarkerTool.getGraphQLJavaMethodSignatures(configYAML, openAPIYAML, "query", false) />

	<#list javaMethodSignatures as javaMethodSignature>
		<#assign
			returnType = javaMethodSignature.returnType
			schemaName = javaMethodSignature.schemaName
		/>

		<#if returnType?starts_with("Collection<")>
			<#assign simpleClassName = returnType[11..(returnType?length - 2)] />

			<#if allSchemas?keys?seq_contains(simpleClassName)>
				<#assign returnType = "Collection<" + simpleClassName + "Impl>" />
			</#if>
		<#elseif allSchemas?keys?seq_contains(returnType)>
			<#assign returnType = returnType + "Impl" />
		</#if>

		${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
		public ${returnType} ${javaMethodSignature.methodName}(
				${freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaParameters, true)})
			throws Exception {

			<#if stringUtil.equals(returnType, "Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#elseif returnType?contains("Collection<")>
				${schemaName}ResourceImpl ${schemaName?uncap_first}ResourceImpl = _get${schemaName}ResourceImpl();

				${schemaName?uncap_first}ResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				<#assign arguments = freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaParameters) />

				Page paginationPage = ${schemaName?uncap_first}ResourceImpl.${javaMethodSignature.methodName}(
					${arguments?replace("pageSize,page", "Pagination.of(pageSize, page)")});

				return paginationPage.getItems();
			<#else>
				${schemaName}ResourceImpl ${schemaName?uncap_first}ResourceImpl = _get${schemaName}ResourceImpl();

				${schemaName?uncap_first}ResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return ${schemaName?uncap_first}ResourceImpl.${javaMethodSignature.methodName}(
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