/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.rest.internal.resource.v2_0;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.PermissionUtil;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

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
import javax.ws.rs.GET;
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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@Path("/v2.0")
public abstract class BaseDataDefinitionResourceImpl
	implements DataDefinitionResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<DataDefinition> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/by-content-type/{contentType}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "contentType"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/data-definitions/by-content-type/{contentType}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public Page<DataDefinition> getDataDefinitionByContentTypeContentTypePage(
			@NotNull @Parameter(hidden = true) @PathParam("contentType") String
				contentType,
			@Parameter(hidden = true) @QueryParam("keywords") String keywords,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/by-content-type/{contentType}' -d $'{"availableLanguageIds": ___, "contentType": ___, "dataDefinitionFields": ___, "dataDefinitionKey": ___, "dataRules": ___, "dateCreated": ___, "dateModified": ___, "defaultDataLayout": ___, "defaultLanguageId": ___, "description": ___, "id": ___, "name": ___, "siteId": ___, "storageType": ___, "userId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "contentType")}
	)
	@Path("/data-definitions/by-content-type/{contentType}")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition postDataDefinitionByContentType(
			@NotNull @Parameter(hidden = true) @PathParam("contentType") String
				contentType,
			DataDefinition dataDefinition)
		throws Exception {

		return new DataDefinition();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/data-definition-fields/field-types'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Path("/data-definitions/data-definition-fields/field-types")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public String getDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception {

		return StringPool.BLANK;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@Path("/data-definitions/{dataDefinitionId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public void deleteDataDefinition(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/data-definitions/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataDefinition")})
	public Response deleteDataDefinitionBatch(
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.deleteImportTask(
				DataDefinition.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@Path("/data-definitions/{dataDefinitionId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition getDataDefinition(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId)
		throws Exception {

		return new DataDefinition();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}' -d $'{"availableLanguageIds": ___, "contentType": ___, "dataDefinitionFields": ___, "dataDefinitionKey": ___, "dataRules": ___, "dateCreated": ___, "dateModified": ___, "defaultDataLayout": ___, "defaultLanguageId": ___, "description": ___, "id": ___, "name": ___, "siteId": ___, "storageType": ___, "userId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@PATCH
	@Path("/data-definitions/{dataDefinitionId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition patchDataDefinition(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId,
			DataDefinition dataDefinition)
		throws Exception {

		DataDefinition existingDataDefinition = getDataDefinition(
			dataDefinitionId);

		if (dataDefinition.getAvailableLanguageIds() != null) {
			existingDataDefinition.setAvailableLanguageIds(
				dataDefinition.getAvailableLanguageIds());
		}

		if (dataDefinition.getContentType() != null) {
			existingDataDefinition.setContentType(
				dataDefinition.getContentType());
		}

		if (dataDefinition.getDataDefinitionKey() != null) {
			existingDataDefinition.setDataDefinitionKey(
				dataDefinition.getDataDefinitionKey());
		}

		if (dataDefinition.getDateCreated() != null) {
			existingDataDefinition.setDateCreated(
				dataDefinition.getDateCreated());
		}

		if (dataDefinition.getDateModified() != null) {
			existingDataDefinition.setDateModified(
				dataDefinition.getDateModified());
		}

		if (dataDefinition.getDefaultLanguageId() != null) {
			existingDataDefinition.setDefaultLanguageId(
				dataDefinition.getDefaultLanguageId());
		}

		if (dataDefinition.getDescription() != null) {
			existingDataDefinition.setDescription(
				dataDefinition.getDescription());
		}

		if (dataDefinition.getName() != null) {
			existingDataDefinition.setName(dataDefinition.getName());
		}

		if (dataDefinition.getSiteId() != null) {
			existingDataDefinition.setSiteId(dataDefinition.getSiteId());
		}

		if (dataDefinition.getStorageType() != null) {
			existingDataDefinition.setStorageType(
				dataDefinition.getStorageType());
		}

		if (dataDefinition.getUserId() != null) {
			existingDataDefinition.setUserId(dataDefinition.getUserId());
		}

		preparePatch(dataDefinition, existingDataDefinition);

		return putDataDefinition(dataDefinitionId, existingDataDefinition);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}' -d $'{"availableLanguageIds": ___, "contentType": ___, "dataDefinitionFields": ___, "dataDefinitionKey": ___, "dataRules": ___, "dateCreated": ___, "dateModified": ___, "defaultDataLayout": ___, "defaultLanguageId": ___, "description": ___, "id": ___, "name": ___, "siteId": ___, "storageType": ___, "userId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@Path("/data-definitions/{dataDefinitionId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition putDataDefinition(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId,
			DataDefinition dataDefinition)
		throws Exception {

		return new DataDefinition();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/data-definitions/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "DataDefinition")})
	public Response putDataDefinitionBatch(
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.putImportTask(
				DataDefinition.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/data-definitions/{dataDefinitionId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getDataDefinitionPermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("dataDefinitionId")
				Long dataDefinitionId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			dataDefinitionId);
		Long resourceId = getPermissionCheckerResourceId(dataDefinitionId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(dataDefinitionId));

		return toPermissionPage(resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@Path("/data-definitions/{dataDefinitionId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "DataDefinition")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putDataDefinitionPermission(
				@NotNull @Parameter(hidden = true)
				@PathParam("dataDefinitionId")
				Long dataDefinitionId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			dataDefinitionId);
		Long resourceId = getPermissionCheckerResourceId(dataDefinitionId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(dataDefinitionId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(dataDefinitionId), resourceName,
			String.valueOf(resourceId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, resourceId,
				resourceName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));

		return toPermissionPage(resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "contentType"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/data-definitions/by-content-type/{contentType}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public Page<DataDefinition>
			getSiteDataDefinitionByContentTypeContentTypePage(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@NotNull @Parameter(hidden = true) @PathParam("contentType")
					String contentType,
				@Parameter(hidden = true) @QueryParam("keywords") String
					keywords,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}' -d $'{"availableLanguageIds": ___, "contentType": ___, "dataDefinitionFields": ___, "dataDefinitionKey": ___, "dataRules": ___, "dateCreated": ___, "dateModified": ___, "defaultDataLayout": ___, "defaultLanguageId": ___, "description": ___, "id": ___, "name": ___, "siteId": ___, "storageType": ___, "userId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "contentType")
		}
	)
	@Path("/sites/{siteId}/data-definitions/by-content-type/{contentType}")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition postSiteDataDefinitionByContentType(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @PathParam("contentType") String
				contentType,
			DataDefinition dataDefinition)
		throws Exception {

		return new DataDefinition();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}/by-data-definition-key/{dataDefinitionKey}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "contentType"),
			@Parameter(in = ParameterIn.PATH, name = "dataDefinitionKey")
		}
	)
	@Path(
		"/sites/{siteId}/data-definitions/by-content-type/{contentType}/by-data-definition-key/{dataDefinitionKey}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition getSiteDataDefinitionByContentTypeByDataDefinitionKey(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @PathParam("contentType") String
				contentType,
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionKey")
				String dataDefinitionKey)
		throws Exception {

		return new DataDefinition();
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<DataDefinition> dataDefinitions,
			Map<String, Serializable> parameters)
		throws Exception {
	}

	@Override
	public void delete(
			java.util.Collection<DataDefinition> dataDefinitions,
			Map<String, Serializable> parameters)
		throws Exception {

		for (DataDefinition dataDefinition : dataDefinitions) {
			deleteDataDefinition(dataDefinition.getId());
		}
	}

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return getEntityModel(
			new MultivaluedHashMap<String, Object>(multivaluedMap));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return null;
	}

	@Override
	public Page<DataDefinition> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return null;
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
	public void update(
			java.util.Collection<DataDefinition> dataDefinitions,
			Map<String, Serializable> parameters)
		throws Exception {

		for (DataDefinition dataDefinition : dataDefinitions) {
			putDataDefinition(
				dataDefinition.getId() != null ? dataDefinition.getId() :
					Long.parseLong((String)parameters.get("dataDefinitionId")),
				dataDefinition);
		}
	}

	protected String getPermissionCheckerActionsResourceName(Object id)
		throws Exception {

		return getPermissionCheckerResourceName(id);
	}

	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String getPermissionCheckerPortletName(Object id)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long getPermissionCheckerResourceId(Object id) throws Exception {
		return GetterUtil.getLong(id);
	}

	protected String getPermissionCheckerResourceName(Object id)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<com.liferay.portal.vulcan.permission.Permission>
			toPermissionPage(long id, String resourceName, String roleNames)
		throws Exception {

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(resourceName);

		if (Validator.isNotNull(roleNames)) {
			return Page.of(
				transform(
					PermissionUtil.getRoles(
						contextCompany, roleLocalService,
						StringUtil.split(roleNames)),
					role -> PermissionUtil.toPermission(
						contextCompany.getCompanyId(), id, resourceActions,
						resourceName, resourcePermissionLocalService, role)));
		}

		return Page.of(
			transform(
				resourcePermissionLocalService.getResourcePermissions(
					contextCompany.getCompanyId(), resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(id)),
				resourcePermission -> PermissionUtil.toPermission(
					resourceActions, resourcePermission,
					roleLocalService.getRole(resourcePermission.getRoleId()))));
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

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

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected void preparePatch(
		DataDefinition dataDefinition, DataDefinition existingDataDefinition) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

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
	protected VulcanBatchEngineImportTaskResource
		vulcanBatchEngineImportTaskResource;

}