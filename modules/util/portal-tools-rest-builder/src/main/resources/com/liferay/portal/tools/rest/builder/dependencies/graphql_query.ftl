package ${configYAML.apiPackagePath}.internal.graphql.query.${escapedVersion};

<#list openAPIYAML.components.schemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
	import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
</#list>

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
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

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class Query {

	<#assign
		javaMethodSignatures = freeMarkerTool.getGraphQLJavaMethodSignatures(configYAML, "query", openAPIYAML)

		schemaNames = freeMarkerTool.getGraphQLSchemaNames(javaMethodSignatures)
	/>

	<#list schemaNames as schemaName>
		public static void set${schemaName}ResourceComponentServiceObjects(ComponentServiceObjects<${schemaName}Resource> ${schemaName?uncap_first}ResourceComponentServiceObjects) {
			_${schemaName?uncap_first}ResourceComponentServiceObjects = ${schemaName?uncap_first}ResourceComponentServiceObjects;
		}
	</#list>

	<#list javaMethodSignatures as javaMethodSignature>
		${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(${freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, true)}) throws Exception {
			<#if stringUtil.equals(javaMethodSignature.returnType, "javax.ws.rs.core.Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#elseif javaMethodSignature.returnType?contains("Collection<")>
				return _applyComponentServiceObjects(
					_${javaMethodSignature.schemaName?uncap_first}ResourceComponentServiceObjects,
					this::_populateResourceContext,
					${javaMethodSignature.schemaName?uncap_first}Resource -> {
						<#assign arguments = freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters) />

						Page paginationPage = ${javaMethodSignature.schemaName?uncap_first}Resource.${javaMethodSignature.methodName}(${arguments?replace("pageSize,page", "Pagination.of(pageSize, page)")});

						return paginationPage.getItems();
					});
			<#else>
				return _applyComponentServiceObjects(_${javaMethodSignature.schemaName?uncap_first}ResourceComponentServiceObjects, this::_populateResourceContext, ${javaMethodSignature.schemaName?uncap_first}Resource -> ${javaMethodSignature.schemaName?uncap_first}Resource.${javaMethodSignature.methodName}(${freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters)}));
			</#if>
		}
	</#list>

	private <T, R, E1 extends Throwable, E2 extends Throwable> R _applyComponentServiceObjects(ComponentServiceObjects<T> componentServiceObjects, UnsafeConsumer<T, E1> unsafeConsumer, UnsafeFunction<T, R, E2> unsafeFunction) throws E1, E2 {
		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	<#list schemaNames as schemaName>
		private void _populateResourceContext(${schemaName}Resource ${schemaName?uncap_first}Resource) throws Exception {
			${schemaName?uncap_first}Resource.setContextCompany(CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));
		}
	</#list>

	<#list schemaNames as schemaName>
		private static ComponentServiceObjects<${schemaName}Resource> _${schemaName?uncap_first}ResourceComponentServiceObjects;
	</#list>

}