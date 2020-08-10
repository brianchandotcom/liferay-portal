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

package com.liferay.headless.batch.engine.internal.resource.v1_0;

import com.liferay.headless.batch.engine.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.resource.v1_0.ImportTaskResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

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
import javax.ws.rs.core.UriInfo;

/**
 * @author Ivica Cardic
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseImportTaskResourceImpl implements ImportTaskResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-batch-engine/v1.0/import-task/{className}'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes(
		{
			"application/json", "application/x-ndjson", "application/xml",
			"text/csv"
		}
	)
	@DELETE
	@Operation(description = "Uploads a new file for deleting items in batch.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "className"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL"),
			@Parameter(in = ParameterIn.QUERY, name = "taskItemDelegateName")
		}
	)
	@Path("/import-task/{className}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ImportTask")})
	public ImportTask deleteImportTask(
			@NotNull @Parameter(hidden = true) @PathParam("className") String
				className,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			@Parameter(hidden = true) @QueryParam("taskItemDelegateName") String
				taskItemDelegateName,
			Object object)
		throws Exception {

		return new ImportTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-batch-engine/v1.0/import-task/{className}'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("multipart/form-data")
	@DELETE
	@Operation(description = "Uploads a new file for deleting items in batch.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "className"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL"),
			@Parameter(in = ParameterIn.QUERY, name = "taskItemDelegateName")
		}
	)
	@Path("/import-task/{className}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ImportTask")})
	public ImportTask deleteImportTask(
			@NotNull @Parameter(hidden = true) @PathParam("className") String
				className,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			@Parameter(hidden = true) @QueryParam("taskItemDelegateName") String
				taskItemDelegateName,
			MultipartBody multipartBody)
		throws Exception {

		return new ImportTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-batch-engine/v1.0/import-task/{className}'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes(
		{
			"application/json", "application/x-ndjson", "application/xml",
			"text/csv"
		}
	)
	@Operation(
		description = "Uploads a new file for creating new items in batch."
	)
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "className"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL"),
			@Parameter(in = ParameterIn.QUERY, name = "fieldNameMapping"),
			@Parameter(in = ParameterIn.QUERY, name = "taskItemDelegateName")
		}
	)
	@Path("/import-task/{className}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ImportTask")})
	public ImportTask postImportTask(
			@NotNull @Parameter(hidden = true) @PathParam("className") String
				className,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			@Parameter(hidden = true) @QueryParam("fieldNameMapping") String
				fieldNameMapping,
			@Parameter(hidden = true) @QueryParam("taskItemDelegateName") String
				taskItemDelegateName,
			Object object)
		throws Exception {

		return new ImportTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-batch-engine/v1.0/import-task/{className}'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("multipart/form-data")
	@Operation(
		description = "Uploads a new file for creating new items in batch."
	)
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "className"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL"),
			@Parameter(in = ParameterIn.QUERY, name = "fieldNameMapping"),
			@Parameter(in = ParameterIn.QUERY, name = "taskItemDelegateName")
		}
	)
	@Path("/import-task/{className}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ImportTask")})
	public ImportTask postImportTask(
			@NotNull @Parameter(hidden = true) @PathParam("className") String
				className,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			@Parameter(hidden = true) @QueryParam("fieldNameMapping") String
				fieldNameMapping,
			@Parameter(hidden = true) @QueryParam("taskItemDelegateName") String
				taskItemDelegateName,
			MultipartBody multipartBody)
		throws Exception {

		return new ImportTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-batch-engine/v1.0/import-task/{className}'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes(
		{
			"application/json", "application/x-ndjson", "application/xml",
			"text/csv"
		}
	)
	@Operation(description = "Uploads a new file for updating items in batch.")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "className"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL"),
			@Parameter(in = ParameterIn.QUERY, name = "taskItemDelegateName")
		}
	)
	@Path("/import-task/{className}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ImportTask")})
	public ImportTask putImportTask(
			@NotNull @Parameter(hidden = true) @PathParam("className") String
				className,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			@Parameter(hidden = true) @QueryParam("taskItemDelegateName") String
				taskItemDelegateName,
			Object object)
		throws Exception {

		return new ImportTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-batch-engine/v1.0/import-task/{className}'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes("multipart/form-data")
	@Operation(description = "Uploads a new file for updating items in batch.")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "className"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL"),
			@Parameter(in = ParameterIn.QUERY, name = "taskItemDelegateName")
		}
	)
	@Path("/import-task/{className}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ImportTask")})
	public ImportTask putImportTask(
			@NotNull @Parameter(hidden = true) @PathParam("className") String
				className,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			@Parameter(hidden = true) @QueryParam("taskItemDelegateName") String
				taskItemDelegateName,
			MultipartBody multipartBody)
		throws Exception {

		return new ImportTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-batch-engine/v1.0/import-task/{importTaskId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(description = "Retrieves the import task.")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "importTaskId")}
	)
	@Path("/import-task/{importTaskId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ImportTask")})
	public ImportTask getImportTask(
			@NotNull @Parameter(hidden = true) @PathParam("importTaskId") Long
				importTaskId)
		throws Exception {

		return new ImportTask();
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

}