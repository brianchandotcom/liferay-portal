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

import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordCollectionResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.PermissionUtil;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@Path("/v2.0")
public abstract class BaseDataRecordCollectionResourceImpl
	implements DataRecordCollectionResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collection'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@Path("/data-definitions/{dataDefinitionId}/data-record-collection")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection getDataDefinitionDataRecordCollection(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId)
		throws Exception {

		return new DataRecordCollection();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/data-definitions/{dataDefinitionId}/data-record-collections")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("dataDefinitionId") Long dataDefinitionId,
				@Parameter(hidden = true) @QueryParam("keywords") String
					keywords,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections' -d $'{"dataDefinitionId": ___, "dataRecordCollectionKey": ___, "description": ___, "id": ___, "name": ___, "siteId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@Path("/data-definitions/{dataDefinitionId}/data-record-collections")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		return new DataRecordCollection();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId")
		}
	)
	@Path("/data-record-collections/{dataRecordCollectionId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public void deleteDataRecordCollection(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId")
		}
	)
	@Path("/data-record-collections/{dataRecordCollectionId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection getDataRecordCollection(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId)
		throws Exception {

		return new DataRecordCollection();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}' -d $'{"dataDefinitionId": ___, "dataRecordCollectionKey": ___, "description": ___, "id": ___, "name": ___, "siteId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId")
		}
	)
	@Path("/data-record-collections/{dataRecordCollectionId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection putDataRecordCollection(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		return new DataRecordCollection();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/data-record-collections/{dataRecordCollectionId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getDataRecordCollectionPermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("dataRecordCollectionId") Long
					dataRecordCollectionId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService,
			getPermissionCheckerResourceName(), dataRecordCollectionId,
			getPermissionCheckerGroupId(dataRecordCollectionId));

		return Page.of(
			transform(
				PermissionUtil.getRoles(
					contextCompany, roleLocalService,
					StringUtil.split(roleNames)),
				role -> PermissionUtil.toPermission(
					contextCompany.getCompanyId(), dataRecordCollectionId,
					resourceActionLocalService.getResourceActions(
						getPermissionCheckerActionsResourceName()),
					getPermissionCheckerResourceName(),
					resourcePermissionLocalService, role)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId")
		}
	)
	@Path("/data-record-collections/{dataRecordCollectionId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public void putDataRecordCollectionPermission(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId,
			com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String resourceName = getPermissionCheckerResourceName();

		if (!permissionChecker.hasPermission(
				0, resourceName, 0, ActionKeys.PERMISSIONS)) {

			return;
		}

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), 0, resourceName,
			String.valueOf(dataRecordCollectionId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions,
				dataRecordCollectionId, resourceName,
				resourceActionLocalService, resourcePermissionLocalService,
				roleLocalService));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions/by-current-user'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId")
		}
	)
	@Path(
		"/data-record-collections/{dataRecordCollectionId}/permissions/by-current-user"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public String getDataRecordCollectionPermissionByCurrentUser(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId)
		throws Exception {

		return StringPool.BLANK;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/sites/{siteId}/data-record-collections/by-data-record-collection-key/{dataRecordCollectionKey}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionKey")
		}
	)
	@Path(
		"/sites/{siteId}/data-record-collections/by-data-record-collection-key/{dataRecordCollectionKey}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection
			getSiteDataRecordCollectionByDataRecordCollectionKey(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@NotNull @Parameter(hidden = true)
				@PathParam("dataRecordCollectionKey") String
					dataRecordCollectionKey)
		throws Exception {

		return new DataRecordCollection();
	}

	protected String getPermissionCheckerActionsResourceName() {
		return getPermissionCheckerResourceName();
	}

	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String getPermissionCheckerPortletName() {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String getPermissionCheckerResourceName() {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, String permissionName,
		Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, permissionName,
			contextScopeChecker, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, permissionName, siteId);
	}

	protected void preparePatch(
		DataRecordCollection dataRecordCollection,
		DataRecordCollection existingDataRecordCollection) {
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
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;

}