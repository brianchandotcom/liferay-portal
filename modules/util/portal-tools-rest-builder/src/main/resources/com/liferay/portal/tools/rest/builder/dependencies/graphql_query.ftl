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
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

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
		public static void set${schemaName}ResourceComponentServiceObjects(ComponentServiceObjects<${schemaName}Resource> ${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects) {
			_${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects = ${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects;
		}
	</#list>

	<#list javaMethodSignatures as javaMethodSignature>

		<#assign
			parameters = freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, true)
		/>

		${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(
			DataFetchingEnvironment dataFetchingEnvironment
			<#if parameters?has_content>
				, ${parameters}
			</#if>
			) throws Exception {

			<#assign
				arguments = freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters)
				schemaVarName = freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)
			/>

			<#if javaMethodSignature.returnType?contains("Collection<")>
				return _applyComponentServiceObjects(
					_${schemaVarName}ResourceComponentServiceObjects,
					${schemaVarName}Resource -> _populateResourceContext(dataFetchingEnvironment, ${schemaVarName}Resource),
					${schemaVarName}Resource -> {
						Page paginationPage = ${schemaVarName}Resource.${javaMethodSignature.methodName}(${arguments?replace("pageSize,page", "Pagination.of(pageSize, page)")});

						return paginationPage.getItems();
					});
			<#else>
				return _applyComponentServiceObjects(_${schemaVarName}ResourceComponentServiceObjects, ${schemaVarName}Resource -> _populateResourceContext(dataFetchingEnvironment, ${schemaVarName}Resource), ${schemaVarName}Resource -> ${schemaVarName}Resource.${javaMethodSignature.methodName}(${arguments?replace("pageSize,page", "Pagination.of(pageSize, page)")}));
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
		private void _populateResourceContext(DataFetchingEnvironment dataFetchingEnvironment, ${schemaName}Resource ${freeMarkerTool.getSchemaVarName(schemaName)}Resource) throws Exception {

			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setContextAcceptLanguage(_acceptLanguageFunction.apply(dataFetchingEnvironment.getContext()));

			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setContextCompany(CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));
		}
	</#list>

	public static void setAcceptLanguageFunction(
		Function<Object, AcceptLanguage> acceptLanguageFunction) {
		_acceptLanguageFunction = acceptLanguageFunction;
	}

	private static Function<Object, AcceptLanguage> _acceptLanguageFunction;

	<#list schemaNames as schemaName>
		private static ComponentServiceObjects<${schemaName}Resource> _${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects;
	</#list>

}