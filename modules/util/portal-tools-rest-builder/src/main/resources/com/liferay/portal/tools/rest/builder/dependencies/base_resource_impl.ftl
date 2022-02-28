package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ResourceAction;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

<#if configYAML.generateBatch>
	import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
	import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
	import com.liferay.portal.vulcan.batch.engine.strategy.BatchStrategy;
</#if>

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

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
<#if configYAML.application??>
	@javax.ws.rs.Path("/${openAPIYAML.info.version}")
</#if>
public abstract class Base${schemaName}ResourceImpl
	implements ${schemaName}Resource

	<#assign
		javaDataType = freeMarkerTool.getJavaDataType(configYAML, openAPIYAML, schemaName)!""

		generateBatch = configYAML.generateBatch && javaDataType?has_content
	/>

	<#if generateBatch>
		, EntityModelResource, VulcanBatchEngineTaskItemDelegate<${javaDataType}>
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
			<#if stringUtil.equals(javaMethodSignature.methodName, "getAssetLibrary" + schemaName + "sPage")>
				<#assign getAssetLibraryBatchJavaMethodSignature = javaMethodSignature />
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "getSite" + schemaName + "sPage")>
				<#assign getSiteBatchJavaMethodSignature = javaMethodSignature />
			<#elseif !getBatchJavaMethodSignature??>
				<#assign getBatchJavaMethodSignature = javaMethodSignature />
			</#if>
		<#elseif stringUtil.equals(javaMethodSignature.methodName, "post" + parentSchemaName + schemaName)>
			<#if stringUtil.equals(javaMethodSignature.methodName, "postAssetLibrary" + schemaName)>
				<#assign postAssetLibraryBatchJavaMethodSignature = javaMethodSignature />
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "postSite" + schemaName)>
				<#assign postSiteBatchJavaMethodSignature = javaMethodSignature />
			<#elseif !postBatchJavaMethodSignature??>
				<#assign postBatchJavaMethodSignature = javaMethodSignature />
			</#if>
		<#elseif stringUtil.equals(javaMethodSignature.methodName, "put" + schemaName)>
			<#assign putBatchJavaMethodSignature = javaMethodSignature />
		</#if>

		<#if configYAML.application??>
			/**
			* ${freeMarkerTool.getRESTMethodJavadoc(configYAML, javaMethodSignature, openAPIYAML)}
			*/
		</#if>
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

				javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.accepted();

				return responseBuilder.entity(
					vulcanBatchEngineImportTaskResource.deleteImportTask(${javaDataType}.class.getName(), callbackURL, object)
				).build();
			<#elseif generateBatch && (stringUtil.equals(javaMethodSignature.methodName, "post" + parentSchemaName + schemaName + "Batch") || stringUtil.equals(javaMethodSignature.methodName, "post" + parentSchemaName + "Id" + schemaName + "Batch"))>
				vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(contextAcceptLanguage);
				vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
				vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(contextHttpServletRequest);
				vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
				vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

				javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.accepted();

				return responseBuilder.entity(
					vulcanBatchEngineImportTaskResource.postImportTask(${javaDataType}.class.getName(), callbackURL, null, object)
				).build();
			<#elseif generateBatch && stringUtil.equals(javaMethodSignature.methodName, "put" + schemaName + "Batch")>
				vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(contextAcceptLanguage);
				vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
				vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(contextHttpServletRequest);
				vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
				vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

				javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.accepted();

				return responseBuilder.entity(
					vulcanBatchEngineImportTaskResource.putImportTask(${javaDataType}.class.getName(), callbackURL, object)
				).build();
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "get" + schemaName + "PermissionsPage")>
				<#if freeMarkerTool.hasParameter(javaMethodSignature, schemaVarName + "Id")>
					<#assign generateGetPermissionCheckerMethods = true />

					String resourceName = getPermissionCheckerResourceName(${schemaVarName}Id);
					Long resourceId = getPermissionCheckerResourceId(${schemaVarName}Id);

					PermissionUtil.checkPermission(ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId, getPermissionCheckerGroupId(${schemaVarName}Id));

					return toPermissionPage(
						<@getActions
							resourceId="resourceId"
							resourceName="resourceName"
							source=schemaName
						/>,
						resourceId, resourceName, roleNames);
				<#else>
					throw new UnsupportedOperationException("This method needs to be implemented");
				</#if>
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "getAssetLibrary" + schemaName + "PermissionsPage")>
				<#assign generateGetPermissionCheckerMethods = true />

				String portletName = getPermissionCheckerPortletName(assetLibraryId);

				PermissionUtil.checkPermission(ActionKeys.PERMISSIONS, groupLocalService, portletName, assetLibraryId, assetLibraryId);

				return toPermissionPage(
					<@getActions
						resourceId="assetLibraryId"
						resourceName="portletName"
						source="AssetLibrary" + schemaName
					/>,
					assetLibraryId, portletName, roleNames);
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "getSite" + schemaName + "PermissionsPage")>
				<#assign generateGetPermissionCheckerMethods = true />

				String portletName = getPermissionCheckerPortletName(siteId);

				PermissionUtil.checkPermission(ActionKeys.PERMISSIONS, groupLocalService, portletName, siteId, siteId);

				return toPermissionPage(
					<@getActions
						resourceId="siteId"
						resourceName="portletName"
						source="Site" + schemaName
					/>,
					siteId, portletName, roleNames);
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "put" + schemaName + "PermissionsPage")>
				<#if freeMarkerTool.hasParameter(javaMethodSignature, schemaVarName + "Id")>
					<#assign generateGetPermissionCheckerMethods = true />

					String resourceName = getPermissionCheckerResourceName(${schemaVarName}Id);
					Long resourceId = getPermissionCheckerResourceId(${schemaVarName}Id);

					<@updateResourcePermissions
						groupId="getPermissionCheckerGroupId(${schemaVarName}Id)"
						resourceId="resourceId"
						resourceName="resourceName"
					>
						<@getActions
							resourceId="resourceId"
							resourceName="resourceName"
							source=schemaName
						/>
					</@updateResourcePermissions>
				<#else>
					throw new UnsupportedOperationException("This method needs to be implemented");
				</#if>
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "putAssetLibrary" + schemaName + "PermissionsPage")>
				<#assign generateGetPermissionCheckerMethods = true />

				String portletName = getPermissionCheckerPortletName(assetLibraryId);

				<@updateResourcePermissions
					groupId="assetLibraryId"
					resourceId="assetLibraryId"
					resourceName="portletName"
				>
					<@getActions
						resourceId="assetLibraryId"
						resourceName="portletName"
						source="AssetLibrary" + schemaName
					/>
				</@updateResourcePermissions>
			<#elseif stringUtil.equals(javaMethodSignature.methodName, "putSite" + schemaName + "PermissionsPage")>
				<#assign generateGetPermissionCheckerMethods = true />

				String portletName = getPermissionCheckerPortletName(siteId);

				<@updateResourcePermissions
					groupId="siteId"
					resourceId="siteId"
					resourceName="portletName"
				>
					<@getActions
						resourceId="siteId"
						resourceName="portletName"
						source="Site" + schemaName
					/>
				</@updateResourcePermissions>
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
				javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();

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
					${javaDataType} existing${schemaName} = get${schemaName}ByExternalReferenceCode(${firstJavaMethodParameter.parameterName});
				<#else>
					${javaDataType} existing${schemaName} = get${schemaName}(${firstJavaMethodParameter.parameterName});
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
		public void create(java.util.Collection<${javaDataType}> ${schemaVarNames}, Map<String, Serializable> parameters) throws Exception {
			<#if postAssetLibraryBatchJavaMethodSignature?? || postSiteBatchJavaMethodSignature?? || postBatchJavaMethodSignature??>
				UnsafeConsumer<${javaDataType}, Exception> ${schemaVarName}UnsafeConsumer =
				<#if postBatchJavaMethodSignature??>
					${schemaVarName} -> ${postBatchJavaMethodSignature.methodName}(
						<@getPOSTBatchJavaMethodParameters
							javaMethodParameters=postBatchJavaMethodSignature.javaMethodParameters
							schemaVarName=schemaVarName
						/>
					);
				<#else>
					${schemaVarName} -> {};
				</#if>

				<#if postAssetLibraryBatchJavaMethodSignature??>
					if (parameters.containsKey("assetLibraryId")) {
						${schemaVarName}UnsafeConsumer = ${schemaVarName} -> ${postAssetLibraryBatchJavaMethodSignature.methodName}(
							<@getPOSTBatchJavaMethodParameters
								javaMethodParameters=postAssetLibraryBatchJavaMethodSignature.javaMethodParameters
								schemaVarName=schemaVarName
							/>
						);
					}
				</#if>

				<#if postSiteBatchJavaMethodSignature??>
					<#if postAssetLibraryBatchJavaMethodSignature??>
						else
					</#if>
					if (parameters.containsKey("siteId")) {
						${schemaVarName}UnsafeConsumer = ${schemaVarName} -> ${postSiteBatchJavaMethodSignature.methodName}(
							<@getPOSTBatchJavaMethodParameters
								javaMethodParameters=postSiteBatchJavaMethodSignature.javaMethodParameters
								schemaVarName=schemaVarName
							/>
						);
					}
				</#if>

				contextBatchStrategy.apply(${schemaVarNames}, ${schemaVarName}UnsafeConsumer);
			</#if>
		}

		@Override
		public void delete(java.util.Collection<${javaDataType}> ${schemaVarNames}, Map<String, Serializable> parameters) throws Exception {
			<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

			<#if deleteBatchJavaMethodSignature?? && properties?keys?seq_contains("id")>
				for (${javaDataType} ${schemaVarName} : ${schemaVarNames}) {
					delete${schemaName}(${schemaVarName}.getId());
				}
			<#elseif deleteBatchJavaMethodSignature?? && properties?keys?seq_contains(schemaVarName + "Id")>
				for (${javaDataType} ${schemaVarName} : ${schemaVarNames}) {
					delete${schemaName}(${schemaVarName}.get${schemaName}Id());
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
		public Page<${javaDataType}> read(Filter filter, Pagination pagination, Sort[] sorts, Map<String, Serializable> parameters, String search) throws Exception {
			<#if getAssetLibraryBatchJavaMethodSignature?? || getBatchJavaMethodSignature?? || getSiteBatchJavaMethodSignature??>
				<#if getAssetLibraryBatchJavaMethodSignature??>
					if (parameters.containsKey("assetLibraryId")) {
						return ${getAssetLibraryBatchJavaMethodSignature.methodName}(
							<@getGETBatchJavaMethodParameters javaMethodParameters=getAssetLibraryBatchJavaMethodSignature.javaMethodParameters />
						);
					}
					else
				</#if>

				<#if getSiteBatchJavaMethodSignature??>
					if (parameters.containsKey("siteId")) {
						return ${getSiteBatchJavaMethodSignature.methodName}(
							<@getGETBatchJavaMethodParameters javaMethodParameters=getSiteBatchJavaMethodSignature.javaMethodParameters />
						);
					}
					else
				</#if>

				<#if getBatchJavaMethodSignature??>
					<#if getAssetLibraryBatchJavaMethodSignature?? || getSiteBatchJavaMethodSignature??>
						{
					</#if>

					return ${getBatchJavaMethodSignature.methodName}(
						<@getGETBatchJavaMethodParameters javaMethodParameters=getBatchJavaMethodSignature.javaMethodParameters />
					);

					<#if getAssetLibraryBatchJavaMethodSignature?? || getSiteBatchJavaMethodSignature??>
						}
					</#if>
				<#else>
					{
						return null;
					}
				</#if>
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
		public void update(java.util.Collection<${javaDataType}> ${schemaVarNames}, Map<String, Serializable> parameters) throws Exception {
			<#if putBatchJavaMethodSignature??>
				for (${javaDataType} ${schemaVarName} : ${schemaVarNames}) {
					put${schemaName}(
						<#list putBatchJavaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if stringUtil.equals(javaMethodParameter.parameterName, "flatten")>
								(Boolean)parameters.get("flatten")
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, schemaVarName)>
								${schemaVarName}
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id") || stringUtil.equals(javaMethodParameter.parameterName, "id")>
								<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

								<#if properties?keys?seq_contains("id")>
									${schemaVarName}.getId() != null ? ${schemaVarName}.getId() :
								<#elseif properties?keys?seq_contains(schemaVarName + "Id")>
									(${schemaVarName}.get${schemaName}Id() != null) ? ${schemaVarName}.get${schemaName}Id() :
								</#if>

								<@castParameters
									type=javaMethodParameter.parameterType
									value="${schemaVarName}Id"
								/>
							<#elseif putBatchJavaMethodSignature.parentSchemaName?? && stringUtil.equals(javaMethodParameter.parameterName, putBatchJavaMethodSignature.parentSchemaName?uncap_first + "Id")>
								<@castParameters
									type=javaMethodParameter.parameterType
									value="${javaMethodSignature.parentSchemaName?uncap_first}Id"
								/>
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

		protected Long getPermissionCheckerResourceId(Object id) throws Exception {
			return GetterUtil.getLong(id);
		}

		protected String getPermissionCheckerResourceName(Object id) throws Exception {
			throw new UnsupportedOperationException("This method needs to be implemented");
		}

		protected Page<com.liferay.portal.vulcan.permission.Permission> toPermissionPage(Map<String, Map<String, String>> actions, long id, String resourceName, String roleNames) throws Exception {
			List<ResourceAction> resourceActions = resourceActionLocalService.getResourceActions(resourceName);

			if (Validator.isNotNull(roleNames)) {
				return Page.of(
					actions,
					transform(
						PermissionUtil.getRoles(contextCompany, roleLocalService, StringUtil.split(roleNames)),
						role -> PermissionUtil.toPermission(contextCompany.getCompanyId(), id, resourceActions, resourceName, resourcePermissionLocalService, role)));
			}

			return Page.of(
				actions,
				transform(
					PermissionUtil.getResourcePermissions(contextCompany.getCompanyId(), id, resourceName, resourcePermissionLocalService),
					resourcePermission -> PermissionUtil.toPermission(resourceActions, resourcePermission, roleLocalService.getRole(resourcePermission.getRoleId()))));
		}
	</#if>

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	<#if generateBatch>
		public void setContextBatchStrategy(BatchStrategy contextBatchStrategy) {
			this.contextBatchStrategy = contextBatchStrategy;
		}
	</#if>

	public void setContextCompany(com.liferay.portal.kernel.model.Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(HttpServletRequest contextHttpServletRequest) {
		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(HttpServletResponse contextHttpServletResponse) {
		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(com.liferay.portal.kernel.model.User contextUser) {
		this.contextUser = contextUser;
	}

	public void setExpressionConvert(ExpressionConvert<Filter> expressionConvert) {
		this.expressionConvert = expressionConvert;
	}

	public void setFilterParserProvider(FilterParserProvider filterParserProvider) {
		this.filterParserProvider = filterParserProvider;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setResourceActionLocalService(ResourceActionLocalService resourceActionLocalService) {
		this.resourceActionLocalService = resourceActionLocalService;
	}

	public void setResourcePermissionLocalService(ResourcePermissionLocalService resourcePermissionLocalService) {
		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	<#if generateBatch>
		@Override
		public Filter toFilter(String filterString, Map<String, List<String>> multivaluedMap) {
			try {
				EntityModel entityModel = getEntityModel(multivaluedMap);

				FilterParser filterParser = filterParserProvider.provide(entityModel);

				com.liferay.portal.odata.filter.Filter oDataFilter = new com.liferay.portal.odata.filter.Filter(filterParser.parse(filterString));

				return expressionConvert.convert(oDataFilter.getExpression(), contextAcceptLanguage.getPreferredLocale(), entityModel);
			}
			catch (Exception exception) {
				_log.error("Invalid filter " + filterString, exception);
			}

			return null;
		}
	</#if>

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
		protected void preparePatch(${javaDataType} ${schemaVarName}, ${javaDataType} existing${schemaVarName?cap_first}) {
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
	<#if generateBatch>
		protected BatchStrategy contextBatchStrategy;
	</#if>
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected ExpressionConvert<Filter> expressionConvert;
	protected FilterParserProvider filterParserProvider;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;

	<#if generateBatch>
		protected VulcanBatchEngineImportTaskResource vulcanBatchEngineImportTaskResource;
	</#if>

	private static final com.liferay.portal.kernel.log.Log _log = LogFactoryUtil.getLog(Base${schemaName}ResourceImpl.class);

}

<#macro castParameters
	type
	value
>
	<#if stringUtil.equals(value, "assetLibraryId") || stringUtil.equals(value, "siteId")>
		(Long)parameters.get("${value}")
	<#elseif stringUtil.startsWith(type, "[L")>
		(

		<#if type?contains("java.lang.Boolean")>
			Boolean[]
		<#elseif type?contains("java.util.Date")>
			java.util.Date[]
		<#elseif type?contains("java.lang.Double")>
			Double[]
		<#elseif type?contains("java.lang.Integer")>
			Integer[]
		<#elseif type?contains("java.lang.Long")>
			Long[]
		<#else>
			String[]
		</#if>

		)parameters.get("${value}")
	<#elseif !stringUtil.startsWith(type, "java")>
		(${type})parameters.get("${value}")
	<#else>
		<#if type?contains("java.lang.Boolean")>
			Boolean.parseBoolean(
		<#elseif type?contains("java.util.Date")>
			new java.util.Date(
		<#elseif type?contains("java.lang.Double")>
			Double.parseDouble(
		<#elseif type?contains("java.lang.Integer")>
			Integer.parseInt(
		<#elseif type?contains("java.lang.Long")>
			Long.parseLong(
		</#if>

		(String)parameters.get("${value}")

		<#if !type?contains("java.lang.String")>
			)
		</#if>
	</#if>
</#macro>

<#macro getActions
	resourceId
	resourceName
	source
>
	HashMapBuilder.put(
		"get", addAction(ActionKeys.PERMISSIONS, "get${source}PermissionsPage", ${resourceName}, ${resourceId})
	).put(
		"replace", addAction(ActionKeys.PERMISSIONS, "put${source}PermissionsPage", ${resourceName}, ${resourceId})
	).build()
</#macro>

<#macro getGETBatchJavaMethodParameters
	javaMethodParameters
>
	<#list javaMethodParameters as javaMethodParameter>
		<#if stringUtil.equals(javaMethodParameter.parameterName, "aggregation")>
			null
		<#elseif stringUtil.equals(javaMethodParameter.parameterName, "filter") || stringUtil.equals(javaMethodParameter.parameterName, "pagination") || stringUtil.equals(javaMethodParameter.parameterName, "search") || stringUtil.equals(javaMethodParameter.parameterName, "sorts") || stringUtil.equals(javaMethodParameter.parameterName, "user")>
			${javaMethodParameter.parameterName}
		<#else>
			<@castParameters
				type=javaMethodParameter.parameterType
				value=javaMethodParameter.parameterName
			/>
		</#if>

		<#sep>, </#sep>
	</#list>
</#macro>

<#macro getPOSTBatchJavaMethodParameters
	javaMethodParameters
	schemaVarName
>
	<#list javaMethodParameters as javaMethodParameter>
		<#if stringUtil.equals(javaMethodParameter.parameterName, schemaVarName)>
			${schemaVarName}
		<#else>
			<@castParameters
				type=javaMethodParameter.parameterType
				value=javaMethodParameter.parameterName
			/>
		</#if>

		<#sep>, </#sep>
	</#list>
</#macro>

<#macro updateResourcePermissions
	groupId
	resourceId
	resourceName
>
	PermissionUtil.checkPermission(ActionKeys.PERMISSIONS, groupLocalService, ${resourceName}, ${resourceId}, ${groupId});

	resourcePermissionLocalService.updateResourcePermissions(contextCompany.getCompanyId(), ${groupId}, ${resourceName}, String.valueOf(${resourceId}), ModelPermissionsUtil.toModelPermissions(contextCompany.getCompanyId(), permissions, ${resourceId}, ${resourceName}, resourceActionLocalService, resourcePermissionLocalService, roleLocalService));

	return toPermissionPage(<#nested>, ${resourceId}, ${resourceName}, null);
</#macro>