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

package com.liferay.app.builder.rest.internal.resource.v1_0;

import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
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
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseAppResourceImpl
	implements AppResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<App> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/app-builder/v1.0/apps'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "active"),
			@Parameter(in = ParameterIn.QUERY, name = "deploymentTypes"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "scope"),
			@Parameter(in = ParameterIn.QUERY, name = "userIds"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/apps")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "App")})
	public Page<App> getAppsPage(
			@Parameter(hidden = true) @QueryParam("active") Boolean active,
			@Parameter(hidden = true) @QueryParam("deploymentTypes") String[]
				deploymentTypes,
			@Parameter(hidden = true) @QueryParam("keywords") String keywords,
			@Parameter(hidden = true) @QueryParam("scope") String scope,
			@Parameter(hidden = true) @QueryParam("userIds") Long[] userIds,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/app-builder/v1.0/apps/{appId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "appId")})
	@Path("/apps/{appId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "App")})
	public void deleteApp(
			@NotNull @Parameter(hidden = true) @PathParam("appId") Long appId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/app-builder/v1.0/apps/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/apps/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "App")})
	public Response deleteAppBatch(
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
				App.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/app-builder/v1.0/apps/{appId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "appId")})
	@Path("/apps/{appId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "App")})
	public App getApp(
			@NotNull @Parameter(hidden = true) @PathParam("appId") Long appId)
		throws Exception {

		return new App();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/app-builder/v1.0/apps/{appId}' -d $'{"active": ___, "appDeployments": ___, "dataDefinitionId": ___, "dataLayoutId": ___, "dataListViewId": ___, "dataRecordCollectionId": ___, "dateCreated": ___, "dateModified": ___, "id": ___, "name": ___, "scope": ___, "siteId": ___, "userId": ___, "version": ___, "workflowDefinitionName": ___, "workflowDefinitionVersion": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "appId")})
	@Path("/apps/{appId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "App")})
	public App putApp(
			@NotNull @Parameter(hidden = true) @PathParam("appId") Long appId,
			App app)
		throws Exception {

		return new App();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/app-builder/v1.0/apps/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/apps/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "App")})
	public Response putAppBatch(
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
				App.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/app-builder/v1.0/apps/{appId}/deploy'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "appId")})
	@Path("/apps/{appId}/deploy")
	@PUT
	@Tags(value = {@Tag(name = "App")})
	public Response putAppDeploy(
			@NotNull @Parameter(hidden = true) @PathParam("appId") Long appId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/app-builder/v1.0/apps/{appId}/undeploy'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "appId")})
	@Path("/apps/{appId}/undeploy")
	@PUT
	@Tags(value = {@Tag(name = "App")})
	public Response putAppUndeploy(
			@NotNull @Parameter(hidden = true) @PathParam("appId") Long appId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/app-builder/v1.0/data-definitions/{dataDefinitionId}/apps'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "scope"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/data-definitions/{dataDefinitionId}/apps")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "App")})
	public Page<App> getDataDefinitionAppsPage(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId,
			@Parameter(hidden = true) @QueryParam("keywords") String keywords,
			@Parameter(hidden = true) @QueryParam("scope") String scope,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/app-builder/v1.0/data-definitions/{dataDefinitionId}/apps' -d $'{"active": ___, "appDeployments": ___, "dataDefinitionId": ___, "dataLayoutId": ___, "dataListViewId": ___, "dataRecordCollectionId": ___, "dateCreated": ___, "dateModified": ___, "id": ___, "name": ___, "scope": ___, "siteId": ___, "userId": ___, "version": ___, "workflowDefinitionName": ___, "workflowDefinitionVersion": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@Path("/data-definitions/{dataDefinitionId}/apps")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "App")})
	public App postDataDefinitionApp(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId,
			App app)
		throws Exception {

		return new App();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/app-builder/v1.0/sites/{siteId}/apps'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "scope"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/apps")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "App")})
	public Page<App> getSiteAppsPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("keywords") String keywords,
			@Parameter(hidden = true) @QueryParam("scope") String scope,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<App> apps,
			Map<String, Serializable> parameters)
		throws Exception {
	}

	@Override
	public void delete(
			java.util.Collection<App> apps,
			Map<String, Serializable> parameters)
		throws Exception {

		for (App app : apps) {
			deleteApp(app.getId());
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
	public Page<App> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return getSiteAppsPage(
			Long.parseLong((String)parameters.get("siteId")),
			(String)parameters.get("keywords"), (String)parameters.get("scope"),
			pagination, sorts);
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
			java.util.Collection<App> apps,
			Map<String, Serializable> parameters)
		throws Exception {

		for (App app : apps) {
			putApp(
				app.getId() != null ? app.getId() :
					Long.parseLong((String)parameters.get("appId")),
				app);
		}
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