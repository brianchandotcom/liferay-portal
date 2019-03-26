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

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionPermission;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
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

import java.net.URI;

import java.util.Collection;
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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseDataDefinitionResourceImpl
	implements DataDefinitionResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/content-spaces/{content-space-id}/data-definitions")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataDefinition")})
	public Page<DataDefinition> getContentSpaceDataDefinitionsPage(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@QueryParam("keywords") String keywords,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/data-definitions")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition postContentSpaceDataDefinition(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			DataDefinition dataDefinition)
		throws Exception {

		return new DataDefinition();
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/data-definitions/permissions")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataDefinition")})
	public void postContentSpaceDataDefinitionPermission(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@NotNull @QueryParam("operation") String operation,
			DataDefinitionPermission dataDefinitionPermission)
		throws Exception {
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/data-definitions/{data-definition-id}/permissions")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataDefinition")})
	public void postDataDefinitionPermission(
			@NotNull @PathParam("data-definition-id") Long dataDefinitionId,
			@NotNull @QueryParam("operation") String operation,
			DataDefinitionPermission dataDefinitionPermission)
		throws Exception {
	}

	@Override
	@DELETE
	@Path("/data-definitions/{data-definition-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataDefinition")})
	public void deleteDataDefinition(
			@NotNull @PathParam("data-definition-id") Long dataDefinitionId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/data-definitions/{data-definition-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition getDataDefinition(
			@NotNull @PathParam("data-definition-id") Long dataDefinitionId)
		throws Exception {

		return new DataDefinition();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/data-definitions/{data-definition-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataDefinition")})
	public DataDefinition putDataDefinition(
			@NotNull @PathParam("data-definition-id") Long dataDefinitionId,
			DataDefinition dataDefinition)
		throws Exception {

		return new DataDefinition();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected String getJAXRSLink(String methodName, Object... values) {
		String baseURIString = String.valueOf(contextUriInfo.getBaseUri());

		if (baseURIString.endsWith(StringPool.FORWARD_SLASH)) {
			baseURIString = baseURIString.substring(
				0, baseURIString.length() - 1);
		}

		URI resourceURI = UriBuilder.fromResource(
			BaseDataDefinitionResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseDataDefinitionResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(DataDefinition dataDefinition) {
	}

	protected <T, R> List<R> transform(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		Collection<T> collection,
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