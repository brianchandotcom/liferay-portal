package ${configYAML.apiPackagePath}.internal.graphql.query.${escapedVersion};

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
	import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
</#list>

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
		/**
		 * ${freeMarkerTool.getGraphQLMethodJavadoc(javaMethodSignature, javaMethodSignatures, openAPIYAML)}
		 */
		${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
		public

		<#if javaMethodSignature.returnType?contains("Collection<")>
			${javaMethodSignature.schemaName}Page
		<#else>
			${javaMethodSignature.returnType}
		</#if>

		${freeMarkerTool.getGraphQLPropertyName(javaMethodSignature, javaMethodSignatures)}(${freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, true)}) throws Exception {
			<#assign arguments = freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters, freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)) />

			<#if javaMethodSignature.returnType?contains("Collection<")>
				return _applyComponentServiceObjects(
					_${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}ResourceComponentServiceObjects,
					this::_populateResourceContext,
					${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource ->
						new ${javaMethodSignature.schemaName}Page(
							${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource.${javaMethodSignature.methodName}(${arguments}))
					);
			<#else>
				return _applyComponentServiceObjects(_${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}ResourceComponentServiceObjects, this::_populateResourceContext, ${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource -> ${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource.${javaMethodSignature.methodName}(${arguments}));
			</#if>
		}
	</#list>

	<#list freeMarkerTool.getGraphQLRelationJavaMethodSignatures(configYAML, "query", openAPIYAML) as javaMethodSignature>
		@GraphQLTypeExtension(${javaMethodSignature.parentSchemaName}.class)
		public class ${javaMethodSignature.methodName?cap_first}TypeExtension {

			public ${javaMethodSignature.methodName?cap_first}TypeExtension(${javaMethodSignature.parentSchemaName} ${javaMethodSignature.parentSchemaName?uncap_first}) {
				_${javaMethodSignature.parentSchemaName?uncap_first} = ${javaMethodSignature.parentSchemaName?uncap_first};
			}

			${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
			public

			<#if javaMethodSignature.returnType?contains("Collection<")>
				${javaMethodSignature.schemaName}Page
			<#else>
				${javaMethodSignature.returnType}
			</#if>

			${freeMarkerTool.getGraphQLRelationName(javaMethodSignature, javaMethodSignatures)}(${freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaMethodParameters[1..*(javaMethodSignature.javaMethodParameters?size - 1)], javaMethodSignature.operation, true)}) throws Exception {
				<#assign arguments = freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters[1..*(javaMethodSignature.javaMethodParameters?size - 1)], freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)) />

				<#if javaMethodSignature.returnType?contains("Collection<")>
					return _applyComponentServiceObjects(
						_${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}ResourceComponentServiceObjects,
						Query.this::_populateResourceContext,
						${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource ->
							new ${javaMethodSignature.schemaName}Page(
								${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource.${javaMethodSignature.methodName}(_${javaMethodSignature.parentSchemaName?uncap_first}.get${freeMarkerTool.getGraphQLJavaParameterName(configYAML, openAPIYAML, javaMethodSignature.parentSchemaName, javaMethodSignature.javaMethodParameters[0])}()

								<#if arguments?has_content>
									, ${arguments}
								</#if>)));
				<#else>
					return _applyComponentServiceObjects(_${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}ResourceComponentServiceObjects, Query.this::_populateResourceContext, ${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource -> ${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource.${javaMethodSignature.methodName}(
						_${javaMethodSignature.parentSchemaName?uncap_first}.get${freeMarkerTool.getGraphQLJavaParameterName(configYAML, openAPIYAML, javaMethodSignature.parentSchemaName, javaMethodSignature.javaMethodParameters[0])}()

						<#if arguments?has_content>
							, ${arguments}
						</#if>));
				</#if>
			}

			private ${javaMethodSignature.parentSchemaName} _${javaMethodSignature.parentSchemaName?uncap_first};

		}
	</#list>

	<#list schemaNames as schemaName>
		@GraphQLName("${schemaName}Page")
		public class ${schemaName}Page {

			public ${schemaName}Page(Page ${freeMarkerTool.getSchemaVarName(schemaName)}Page) {
				items = ${freeMarkerTool.getSchemaVarName(schemaName)}Page.getItems();
				page = ${freeMarkerTool.getSchemaVarName(schemaName)}Page.getPage();
				pageSize = ${freeMarkerTool.getSchemaVarName(schemaName)}Page.getPageSize();
				totalCount = ${freeMarkerTool.getSchemaVarName(schemaName)}Page.getTotalCount();
			}

			@GraphQLField
			protected java.util.Collection<${schemaName}> items;

			@GraphQLField
			protected long page;

			@GraphQLField
			protected long pageSize;

			@GraphQLField
			protected long totalCount;

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
		private void _populateResourceContext(${schemaName}Resource ${freeMarkerTool.getSchemaVarName(schemaName)}Resource) throws Exception {
			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setContextAcceptLanguage(_acceptLanguage);
			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setContextCompany(_company);
			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setContextHttpServletRequest(_httpServletRequest);
			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setContextHttpServletResponse(_httpServletResponse);
			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setContextUriInfo(_uriInfo);
			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setContextUser(_user);
		}
	</#list>

	<#list schemaNames as schemaName>
		private static ComponentServiceObjects<${schemaName}Resource> _${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects;
	</#list>

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;
	private User _user;

}