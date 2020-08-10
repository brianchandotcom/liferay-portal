package ${configYAML.apiPackagePath}.internal.graphql.mutation.${escapedVersion};

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
	import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
</#list>

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class Mutation {

	<#assign
		javaMethodSignatures = freeMarkerTool.getGraphQLJavaMethodSignatures(configYAML, "mutation", openAPIYAML)

		schemaNames = freeMarkerTool.getGraphQLSchemaNames(javaMethodSignatures)
	/>

	<#list schemaNames as schemaName>
		public static void set${schemaName}ResourceComponentServiceObjects(ComponentServiceObjects<${schemaName}Resource> ${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects) {
			_${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects = ${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects;
		}
	</#list>

	<#list javaMethodSignatures as javaMethodSignature>
		${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
		public

		<#if javaMethodSignature.returnType?contains("void")>
			boolean
		<#else>
			${javaMethodSignature.returnType}
		</#if>

		${freeMarkerTool.getGraphQLMutationName(javaMethodSignature.methodName)}(${freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, true)}) throws Exception {
			<#assign arguments = freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaMethodParameters, freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)) />

			<#if javaMethodSignature.returnType?contains("java.util.Collection<")>
				return _applyComponentServiceObjects(
					_${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}ResourceComponentServiceObjects, this::_populateResourceContext,
					${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource -> {

						Page paginationPage = ${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource.${javaMethodSignature.methodName}(${arguments});

						return paginationPage.getItems();
					});
			<#elseif javaMethodSignature.returnType?contains("void")>
				_applyVoidComponentServiceObjects(_${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}ResourceComponentServiceObjects, this::_populateResourceContext,${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource -> ${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource.${javaMethodSignature.methodName}(${arguments}));

				return true;
			<#else>
				return _applyComponentServiceObjects(_${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}ResourceComponentServiceObjects, this::_populateResourceContext,${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource -> ${freeMarkerTool.getSchemaVarName(javaMethodSignature.schemaName)}Resource.${javaMethodSignature.methodName}(${arguments}));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void _applyVoidComponentServiceObjects(ComponentServiceObjects<T> componentServiceObjects, UnsafeConsumer<T, E1> unsafeConsumer, UnsafeConsumer<T, E2> unsafeFunction) throws E1, E2 {
		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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
			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setGroupLocalService(groupLocalService);
			${freeMarkerTool.getSchemaVarName(schemaName)}Resource.setRoleLocalService(roleLocalService);
		}
	</#list>

	<#list schemaNames as schemaName>
		private static ComponentServiceObjects<${schemaName}Resource> _${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects;
	</#list>

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService groupLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private com.liferay.portal.kernel.model.User _user;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService roleLocalService;
	private UriInfo _uriInfo;

}