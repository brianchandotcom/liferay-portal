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

import com.liferay.data.engine.rest.dto.v2_0.DataModelPermission;
import com.liferay.data.engine.rest.resource.v2_0.DataModelPermissionResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@Path("/v2.0")
public abstract class BaseDataModelPermissionResourceImpl
	implements DataModelPermissionResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-model-permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/data-definitions/{dataDefinitionId}/data-model-permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public Page<DataModelPermission> getDataDefinitionDataModelPermissionsPage(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId,
			@NotNull @Parameter(hidden = true) @QueryParam("roleNames") String
				roleNames)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-model-permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataDefinitionId")}
	)
	@Path("/data-definitions/{dataDefinitionId}/data-model-permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public void putDataDefinitionDataModelPermission(
			@NotNull @Parameter(hidden = true) @PathParam("dataDefinitionId")
				Long dataDefinitionId,
			DataModelPermission[] dataModelPermissions)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-layouts/{dataLayoutId}/data-model-permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataLayoutId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/data-layouts/{dataLayoutId}/data-model-permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public Page<DataModelPermission> getDataLayoutDataModelPermissionsPage(
			@NotNull @Parameter(hidden = true) @PathParam("dataLayoutId") Long
				dataLayoutId,
			@NotNull @Parameter(hidden = true) @QueryParam("roleNames") String
				roleNames)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-layouts/{dataLayoutId}/data-model-permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "dataLayoutId")}
	)
	@Path("/data-layouts/{dataLayoutId}/data-model-permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public void putDataLayoutDataModelPermission(
			@NotNull @Parameter(hidden = true) @PathParam("dataLayoutId") Long
				dataLayoutId,
			DataModelPermission[] dataModelPermissions)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-model-permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "roleNames")}
	)
	@Path("/data-model-permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public Page<DataModelPermission> getDataModelPermissionsPage(
			@NotNull @Parameter(hidden = true) @QueryParam("roleNames") String
				roleNames)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-model-permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@PUT
	@Path("/data-model-permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public void putDataModelPermission(
			DataModelPermission[] dataModelPermissions)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-model-permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path(
		"/data-record-collections/{dataRecordCollectionId}/data-model-permissions"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public Page<DataModelPermission>
			getDataRecordCollectionDataModelPermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("dataRecordCollectionId") Long
					dataRecordCollectionId,
				@NotNull @Parameter(hidden = true) @QueryParam("roleNames")
					String roleNames)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-model-permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId")
		}
	)
	@Path(
		"/data-record-collections/{dataRecordCollectionId}/data-model-permissions"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public void putDataRecordCollectionDataModelPermission(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId,
			DataModelPermission[] dataModelPermissions)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-model-permissions/by-current-user'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId")
		}
	)
	@Path(
		"/data-record-collections/{dataRecordCollectionId}/data-model-permissions/by-current-user"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataModelPermission")})
	public String getDataRecordCollectionDataModelPermissionByCurrentUser(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId)
		throws Exception {

		return StringPool.BLANK;
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

	protected void preparePatch(
		DataModelPermission dataModelPermission,
		DataModelPermission existingDataModelPermission) {
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
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected UriInfo contextUriInfo;

}