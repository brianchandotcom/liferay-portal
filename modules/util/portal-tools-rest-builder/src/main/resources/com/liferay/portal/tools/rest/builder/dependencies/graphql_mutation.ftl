package ${configYAML.apiPackagePath}.internal.graphql.mutation.${escapedVersion};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
		import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
	</#list>
</#compress>

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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
			<#if stringUtil.equals(javaMethodSignature.returnType, "javax.ws.rs.core.Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#elseif javaMethodSignature.returnType?contains("Collection<")>
				${javaMethodSignature.schemaName}Resource ${javaMethodSignature.schemaName?uncap_first}Resource = _create${javaMethodSignature.schemaName}Resource();

				<#assign arguments = freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters) />

				Page paginationPage = ${javaMethodSignature.schemaName?uncap_first}Resource.${javaMethodSignature.methodName}(${arguments?replace("pageSize,page", "Pagination.of(pageSize, page)")});

				return paginationPage.getItems();
			<#else>
				${javaMethodSignature.schemaName}Resource ${javaMethodSignature.schemaName?uncap_first}Resource = _create${javaMethodSignature.schemaName}Resource();

				return ${javaMethodSignature.schemaName?uncap_first}Resource.${javaMethodSignature.methodName}(${freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters)});
			</#if>
		}
	</#list>

	<#assign schemaNames = freeMarkerTool.getGraphQLSchemaNames(javaMethodSignatures) />

	<#list schemaNames as schemaName>
		private static ${schemaName}Resource _create${schemaName}Resource() throws Exception {
			${schemaName}Resource ${schemaName?uncap_first}Resource = _${schemaName?uncap_first}ResourceServiceTracker.getService();

			${schemaName?uncap_first}Resource.setContextCompany(CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

			return ${schemaName?uncap_first}Resource;
		}

		private static final ServiceTracker<${schemaName}Resource, ${schemaName}Resource> _${schemaName?uncap_first}ResourceServiceTracker;
	</#list>

	<#if schemaNames?size != 0>
		static {
			Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

			<#list schemaNames as schemaName>
				ServiceTracker<${schemaName}Resource, ${schemaName}Resource> ${schemaName?uncap_first}ResourceServiceTracker = new ServiceTracker<>(bundle.getBundleContext(), ${schemaName}Resource.class, null);

				${schemaName?uncap_first}ResourceServiceTracker.open();

				_${schemaName?uncap_first}ResourceServiceTracker = ${schemaName?uncap_first}ResourceServiceTracker;
			</#list>
		}
	</#if>

}