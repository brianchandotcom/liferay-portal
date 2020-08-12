package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.PermissionUtil;

<#if configYAML.generateBatch>
	import com.liferay.portal.vulcan.resource.EntityModelResource;
</#if>

import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@Path("/${openAPIYAML.info.version}")
public abstract class Base${schemaName}ResourceImpl
	implements ${schemaName}Resource

	<#assign
		generateBatch = configYAML.generateBatch && freeMarkerTool.getJavaDataType(configYAML, openAPIYAML, schemaName)??
	/>

	<#if generateBatch>
		, EntityModelResource, VulcanBatchEngineTaskItemDelegate<${schemaName}>
	</#if>

	{

	<#assign
		generateGetPermissionCheckerMethods = false
		generatePatchMethods = false
		javaMethodSignatures = freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName)
	/>

	<#list javaMethodSignatures as javaMethodSignature>
		<#assign
			parentSchemaName = javaMethodSignature.parentSchemaName!
		/>

		<#if stringUtil.equals(javaMethodSignature.methodName, "delete" + schemaName)>
			<#assign deleteBatchJavaMethodSignature = javaMethodSignature />
		<#elseif stringUtil.equals(javaMethodSignature.methodName, "get" + parentSchemaName + schemaName + "sPage")>
			<#if !getBatchJavaMethodSignature??>
				<#assign getBatchJavaMethodSignature = javaMethodSignature />
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "getSite" + schemaName + "sPage")>
				<#assign getBatchJavaMethodSignature = javaMethodSignature />
			</#if>
		<#elseif stringUtil.equals(javaMethodSignature.methodName, "post" + parentSchemaName + schemaName)>
			<#if !postBatchJavaMethodSignature??>
				<#assign postBatchJavaMethodSignature = javaMethodSignature />
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "postSite" + schemaName)>
				<#assign postBatchJavaMethodSignature = javaMethodSignature />
			</#if>
		<#elseif stringUtil.equals(javaMethodSignature.methodName, "put" + schemaName)>
			<#assign putBatchJavaMethodSignature = javaMethodSignature />
		</#if>

		/**
		* ${freeMarkerTool.getRESTMethodJavadoc(configYAML, javaMethodSignature, openAPIYAML)}
		*/
		@Override
		${freeMarkerTool.getResourceMethodAnnotations(javaMethodSignature)}
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(${freeMarkerTool.getResourceParameters(javaMethodSignature.javaMethodParameters, openAPIYAML, javaMethodSignature.operation, true)}) throws Exception {
			<#if stringUtil.equals(javaMethodSignature.returnType, "boolean")>
				return false;
			<#elseif generateBatch && stringUtil.equals(javaMethodSignature.methodName, "delete" + schemaName + "Batch")>
				vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(contextAcceptLanguage);
				vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
				vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(contextHttpServletRequest);
				vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
				vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

				Response.ResponseBuilder responseBuilder = Response.accepted();

				return responseBuilder.entity(
					vulcanBatchEngineImportTaskResource.deleteImportTask(${schemaName}.class.getName(), callbackURL, object)
				).build();
			<#elseif generateBatch && (stringUtil.equals(javaMethodSignature.methodName, "post" + parentSchemaName + schemaName + "Batch") || stringUtil.equals(javaMethodSignature.methodName, "post" + parentSchemaName + "Id" + schemaName + "Batch"))>
				vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(contextAcceptLanguage);
				vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
				vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(contextHttpServletRequest);
				vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
				vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

				Response.ResponseBuilder responseBuilder = Response.accepted();

				return responseBuilder.entity(
					vulcanBatchEngineImportTaskResource.postImportTask(${schemaName}.class.getName(), callbackURL, null, object)
				).build();
			<#elseif generateBatch && stringUtil.equals(javaMethodSignature.methodName, "put" + schemaName + "Batch")>
				vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(contextAcceptLanguage);
				vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
				vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(contextHttpServletRequest);
				vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
				vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

				Response.ResponseBuilder responseBuilder = Response.accepted();

				return responseBuilder.entity(
					vulcanBatchEngineImportTaskResource.putImportTask(${schemaName}.class.getName(), callbackURL, object)
				).build();
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "get" + schemaName + "PermissionsPage")>
				<#assign generateGetPermissionCheckerMethods = true />

				String resourceName = getPermissionCheckerResourceName(${schemaVarName}Id);

				PermissionUtil.checkPermission(ActionKeys.PERMISSIONS, groupLocalService, resourceName, ${schemaVarName}Id, getPermissionCheckerGroupId(${schemaVarName}Id));

				return Page.of(transform(PermissionUtil.getRoles(contextCompany, roleLocalService, StringUtil.split(roleNames)), role -> PermissionUtil.toPermission(contextCompany.getCompanyId(), ${schemaVarName}Id, resourceActionLocalService.getResourceActions(resourceName), resourceName, resourcePermissionLocalService, role)));
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "getSite" + schemaName + "PermissionsPage")>
				<#assign generateGetPermissionCheckerMethods = true />

				String portletName = getPermissionCheckerPortletName(siteId);

				PermissionUtil.checkPermission(ActionKeys.PERMISSIONS, groupLocalService, portletName, siteId, siteId);

				return Page.of(transform(PermissionUtil.getRoles(contextCompany, roleLocalService, StringUtil.split(roleNames)), role -> PermissionUtil.toPermission(contextCompany.getCompanyId(), siteId, resourceActionLocalService.getResourceActions(portletName), portletName, resourcePermissionLocalService, role)));
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "put" + schemaName + "Permission")>
				<#assign generateGetPermissionCheckerMethods = true />

				String resourceName = getPermissionCheckerResourceName(${schemaVarName}Id);

				PermissionUtil.checkPermission(ActionKeys.PERMISSIONS, groupLocalService, resourceName, ${schemaVarName}Id, getPermissionCheckerGroupId(${schemaVarName}Id));

				resourcePermissionLocalService.updateResourcePermissions(contextCompany.getCompanyId(), 0, resourceName, String.valueOf(${schemaVarName}Id), ModelPermissionsUtil.toModelPermissions(contextCompany.getCompanyId(), permissions, ${schemaVarName}Id, resourceName, resourceActionLocalService, resourcePermissionLocalService, roleLocalService));
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "putSite" + schemaName + "Permission")>
				<#assign generateGetPermissionCheckerMethods = true />

				String portletName = getPermissionCheckerPortletName(siteId);

				PermissionUtil.checkPermission(ActionKeys.PERMISSIONS, groupLocalService, portletName, siteId, siteId);

				resourcePermissionLocalService.updateResourcePermissions(contextCompany.getCompanyId(), siteId, portletName, String.valueOf(siteId), ModelPermissionsUtil.toModelPermissions(contextCompany.getCompanyId(), permissions, siteId, portletName, resourceActionLocalService, resourcePermissionLocalService, roleLocalService));
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.Boolean")>
				return false;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.Double") ||
					 stringUtil.equals(javaMethodSignature.returnType, "java.lang.Number")>

				return 0.0;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.Float")>
				return 0f;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.Integer")>
				return 0;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.Long")>
				return 0L;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.Object")>
				return null;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.String")>
				return StringPool.BLANK;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.math.BigDecimal")>
				return java.math.BigDecimal.ZERO;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.util.Date")>
				return new java.util.Date();
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "javax.ws.rs.core.Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "void")>
			<#elseif javaMethodSignature.returnType?contains("Page<")>
				return Page.of(Collections.emptyList());
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch") && freeMarkerTool.hasJavaMethodSignature(javaMethodSignatures, "get" + javaMethodSignature.methodName?remove_beginning("patch")) && freeMarkerTool.hasJavaMethodSignature(javaMethodSignatures, "put" + javaMethodSignature.methodName?remove_beginning("patch")) && !javaMethodSignature.operation.requestBody.content?keys?seq_contains("multipart/form-data")>
				<#assign
					generatePatchMethods = true
					firstJavaMethodParameter = javaMethodSignature.javaMethodParameters[0]
				/>

				<#if javaMethodSignature.methodName?contains("ByExternalReferenceCode")>
					${schemaName} existing${schemaName} = get${schemaName}ByExternalReferenceCode(${firstJavaMethodParameter.parameterName});
				<#else>
					${schemaName} existing${schemaName} = get${schemaName}(${firstJavaMethodParameter.parameterName});
				</#if>

				<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

				<#list properties?keys as propertyName>
					<#if !freeMarkerTool.isDTOSchemaProperty(openAPIYAML, propertyName, schema) && !stringUtil.equals(propertyName, "id")>
						if (${schemaVarName}.get${propertyName?cap_first}() != null) {
							existing${schemaName}.set${propertyName?cap_first}(${schemaVarName}.get${propertyName?cap_first}());
						}
					</#if>
				</#list>

				preparePatch(${schemaVarName}, existing${schemaName});

				<#if javaMethodSignature.methodName?contains("ByExternalReferenceCode")>
					return put${schemaName}ByExternalReferenceCode(${firstJavaMethodParameter.parameterName}, existing${schemaName});
				<#else>
					return put${schemaName}(${firstJavaMethodParameter.parameterName}, existing${schemaName});
				</#if>
			<#else>
				return new ${javaMethodSignature.returnType}();
			</#if>
		}
	</#list>

	<#if generateBatch>
		@Override
		@SuppressWarnings("PMD.UnusedLocalVariable")
		public void create(java.util.Collection<${schemaName}> ${schemaVarNames}, Map<String, Serializable> parameters) throws Exception {
			<#if postBatchJavaMethodSignature??>
				for (${schemaName} ${schemaVarName} : ${schemaVarNames}) {
					post${postBatchJavaMethodSignature.parentSchemaName!}${schemaName}(
						<#list postBatchJavaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if stringUtil.equals(javaMethodParameter.parameterName, schemaVarName)>
								${schemaVarName}
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, postBatchJavaMethodSignature.parentSchemaName!?uncap_first + "Id")>
								<#if stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
									String.valueOf(
								<#else>
									Long.valueOf((String)
								</#if>
								parameters.get("${postBatchJavaMethodSignature.parentSchemaName!?uncap_first}Id"))
							<#else>
								null
							</#if>
							<#sep>, </#sep>
						</#list>
					);
				}
			</#if>
		}

		@Override
		public void delete(java.util.Collection<${schemaName}> ${schemaVarNames}, Map<String, Serializable> parameters) throws Exception {
			<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

			<#if deleteBatchJavaMethodSignature?? && properties?keys?seq_contains('id')??>
				for (${schemaName} ${schemaVarName} : ${schemaVarNames}) {
					delete${schemaName}(${schemaVarName}.getId());
				}
			</#if>
		}

		@Override
		public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap) throws Exception {
			return getEntityModel(new MultivaluedHashMap<String, Object>(multivaluedMap));
		}

		@Override
		public EntityModel getEntityModel(MultivaluedMap multivaluedMap) throws Exception {
			return null;
		}

		@Override
		public Page<${schemaName}> read(Filter filter, Pagination pagination, Sort[] sorts, Map<String, Serializable> parameters, String search) throws Exception {
			<#if getBatchJavaMethodSignature??>
				return get${getBatchJavaMethodSignature.parentSchemaName!}${schemaName}sPage(
					<#list getBatchJavaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if stringUtil.equals(javaMethodParameter.parameterName, "aggregation")>
							null
						<#elseif stringUtil.equals(javaMethodParameter.parameterName, "filter") || stringUtil.equals(javaMethodParameter.parameterName, "pagination") || stringUtil.equals(javaMethodParameter.parameterName, "search") || stringUtil.equals(javaMethodParameter.parameterName, "sorts") || stringUtil.equals(javaMethodParameter.parameterName, "user")>
							${javaMethodParameter.parameterName}
						<#else>
							<#if javaMethodParameter.parameterType?contains("java.lang.Boolean")>
								(Boolean
							<#elseif javaMethodParameter.parameterType?contains("java.lang.Integer")>
								(Integer
							<#elseif javaMethodParameter.parameterType?contains("java.lang.Long")>
								(Long
							<#elseif javaMethodParameter.parameterType?contains("java.util.Date")>
								(java.util.Date
							<#else>
								(String
							</#if>
							<#if stringUtil.startsWith(javaMethodParameter.parameterType, "[L")>
								[]
							</#if>
							) parameters.get("${javaMethodParameter.parameterName}")
						</#if>
						<#sep>, </#sep>
					</#list>
					);
			<#else>
				return null;
			</#if>
		}

		@Override
		public void setLanguageId(String languageId) {
			this.contextAcceptLanguage = new AcceptLanguage() {

				@Override
				public List<Locale> getLocales() {
					return null;
				}

				@Override
				public String getPreferredLanguageId() {
					return languageId;
				}

				@Override
				public Locale getPreferredLocale() {
					return LocaleUtil.fromLanguageId(languageId);
				}

			};
		}

		@Override
		public void update(java.util.Collection<${schemaName}> ${schemaVarNames}, Map<String, Serializable> parameters) throws Exception {
			<#if putBatchJavaMethodSignature??>
				for (${schemaName} ${schemaVarName} : ${schemaVarNames}) {
					put${schemaName}(
						<#list putBatchJavaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if stringUtil.equals(javaMethodParameter.parameterName, "flatten")>
								(Boolean)parameters.get("flatten")
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, schemaVarName)>
								${schemaVarName}
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id") || stringUtil.equals(javaMethodParameter.parameterName, "id")>
								${schemaVarName}.getId() != null ? ${schemaVarName}.getId() :
								<#if stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
									(String)
								<#else>
									(Long)
								</#if>
								parameters.get("${schemaVarName}Id")
							<#elseif putBatchJavaMethodSignature.parentSchemaName?? && stringUtil.equals(javaMethodParameter.parameterName, putBatchJavaMethodSignature.parentSchemaName?uncap_first + "Id")>
								<#if stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
									(String)
								<#else>
									(Long)
								</#if>
								parameters.get("${javaMethodSignature.parentSchemaName?uncap_first}Id")
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, "multipartBody")>
								null
							<#else>
								${javaMethodParameter.parameterName}
							</#if>
							<#sep>, </#sep>
						</#list>
					);
				}
			</#if>
		}
	</#if>

	<#if generateGetPermissionCheckerMethods>
		protected String getPermissionCheckerActionsResourceName(Object id) throws Exception {
			return getPermissionCheckerResourceName(id);
		}

		protected Long getPermissionCheckerGroupId(Object id) throws Exception {
			throw new UnsupportedOperationException("This method needs to be implemented");
		}

		protected String getPermissionCheckerPortletName(Object id) throws Exception {
			throw new UnsupportedOperationException("This method needs to be implemented");
		}

		protected String getPermissionCheckerResourceName(Object id) throws Exception {
			throw new UnsupportedOperationException("This method needs to be implemented");
		}
	</#if>

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(com.liferay.portal.kernel.model.Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {
		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {
		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(com.liferay.portal.kernel.model.User contextUser) {
		this.contextUser = contextUser;
	}

	protected Map<String, String> addAction(String actionName, GroupedModel groupedModel, String methodName) {
		return ActionUtil.addAction(actionName, getClass(), groupedModel, methodName, contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(String actionName, Long id, String methodName, Long ownerId, String permissionName, Long siteId) {
		return ActionUtil.addAction(actionName, getClass(), id, methodName, contextScopeChecker, ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(String actionName, Long id, String methodName, ModelResourcePermission modelResourcePermission) {
		return ActionUtil.addAction(actionName, getClass(), id, methodName, contextScopeChecker, modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(String actionName, String methodName, String permissionName, Long siteId) {
		return addAction(actionName, siteId, methodName, null, permissionName, siteId);
	}

	<#if generatePatchMethods>
		protected void preparePatch(${schemaName} ${schemaVarName}, ${schemaName} existing${schemaVarName?cap_first}) {
		}
	</#if>

	protected <T, R> List<R> transform(java.util.Collection<T> collection, UnsafeFunction<T, R, Exception> unsafeFunction) {
		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(T[] array, UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {
		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(java.util.Collection<T> collection, UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {
		return TransformUtil.transformToArray(collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {
		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;

	<#if generateBatch>
		protected VulcanBatchEngineImportTaskResource vulcanBatchEngineImportTaskResource;
	</#if>

}