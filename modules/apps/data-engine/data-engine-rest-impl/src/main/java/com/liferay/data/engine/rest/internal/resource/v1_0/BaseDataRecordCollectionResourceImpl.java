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

package com.liferay.data.engine.rest.internal.resource.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollectionPermission;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

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
@Path("/v1.0")
public abstract class BaseDataRecordCollectionResourceImpl
	implements DataRecordCollectionResource {

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

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId")
		}
	)
	@Path("/data-record-collections/{dataRecordCollectionId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public void deleteDataRecordCollection(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId)
		throws Exception {
	}

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

	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "operation")
		}
	)
	@Path(
		"/data-record-collections/{dataRecordCollectionId}/data-record-collection-permissions"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public void postDataRecordCollectionDataRecordCollectionPermission(
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionId") Long dataRecordCollectionId,
			@NotNull @Parameter(hidden = true) @QueryParam("operation") String
				operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "operation")
		}
	)
	@Path("/sites/{siteId}/data-record-collection-permissions")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public void postSiteDataRecordCollectionPermission(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @QueryParam("operation") String
				operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/sites/{siteId}/data-record-collections")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public Page<DataRecordCollection> getSiteDataRecordCollectionsPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("keywords") String keywords,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "dataRecordCollectionKey")
		}
	)
	@Path("/sites/{siteId}/data-record-collections/{dataRecordCollectionKey}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection getSiteDataRecordCollection(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true)
			@PathParam("dataRecordCollectionKey") String
				dataRecordCollectionKey)
		throws Exception {

		return new DataRecordCollection();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
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

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}