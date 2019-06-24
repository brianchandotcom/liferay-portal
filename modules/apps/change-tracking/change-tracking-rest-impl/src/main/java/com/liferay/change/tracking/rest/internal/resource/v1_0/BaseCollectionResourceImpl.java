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

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.dto.v1_0.CollectionUpdate;
import com.liferay.change.tracking.rest.resource.v1_0.CollectionResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseCollectionResourceImpl implements CollectionResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "companyId"),
			@Parameter(in = ParameterIn.QUERY, name = "type"),
			@Parameter(in = ParameterIn.QUERY, name = "userId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/collections")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Collection")})
	public Page<Collection> getCollectionsPage(
			@Parameter(hidden = true) @QueryParam("companyId") Long companyId,
			@DefaultValue("all") @Parameter(hidden = true) @QueryParam("type")
				com.liferay.change.tracking.rest.constant.v1_0.CollectionType
					type,
			@Parameter(hidden = true) @QueryParam("userId") Long userId,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "companyId"),
			@Parameter(in = ParameterIn.QUERY, name = "userId")
		}
	)
	@Path("/collections")
	@Produces({"application/json", "application/xml", "text/plain"})
	@Tags(value = {@Tag(name = "Collection")})
	public Collection postCollection(
			@NotNull @Parameter(hidden = true) @QueryParam("companyId") Long
				companyId,
			@NotNull @Parameter(hidden = true) @QueryParam("userId") Long
				userId,
			CollectionUpdate collectionUpdate)
		throws Exception {

		return new Collection();
	}

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "companyId"),
			@Parameter(in = ParameterIn.PATH, name = "collectionId")
		}
	)
	@Path("/collections/{collectionId}")
	@Produces("text/plain")
	@Tags(value = {@Tag(name = "Collection")})
	public Response deleteCollection(
			@NotNull @Parameter(hidden = true) @PathParam("companyId") Long
				companyId,
			@NotNull @Parameter(hidden = true) @PathParam("collectionId") Long
				collectionId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "companyId"),
			@Parameter(in = ParameterIn.PATH, name = "collectionId")
		}
	)
	@Path("/collections/{collectionId}")
	@Produces({"application/json", "application/xml", "text/plain"})
	@Tags(value = {@Tag(name = "Collection")})
	public Collection getCollection(
			@NotNull @Parameter(hidden = true) @PathParam("companyId") Long
				companyId,
			@NotNull @Parameter(hidden = true) @PathParam("collectionId") Long
				collectionId)
		throws Exception {

		return new Collection();
	}

	@Override
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "collectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "userId")
		}
	)
	@Path("/collections/{collectionId}/checkout")
	@Tags(value = {@Tag(name = "Collection")})
	public Response postCollectionCheckout(
			@NotNull @Parameter(hidden = true) @PathParam("collectionId") Long
				collectionId,
			@NotNull @Parameter(hidden = true) @QueryParam("userId") Long
				userId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "collectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "ignoreCollision"),
			@Parameter(in = ParameterIn.QUERY, name = "userId")
		}
	)
	@Path("/collections/{collectionId}/publish")
	@Produces("text/plain")
	@Tags(value = {@Tag(name = "Collection")})
	public Response postCollectionPublish(
			@NotNull @Parameter(hidden = true) @PathParam("collectionId") Long
				collectionId,
			@NotNull @Parameter(hidden = true) @QueryParam("ignoreCollision")
				Boolean ignoreCollision,
			@NotNull @Parameter(hidden = true) @QueryParam("userId") Long
				userId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		Collection collection, Collection existingCollection) {
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