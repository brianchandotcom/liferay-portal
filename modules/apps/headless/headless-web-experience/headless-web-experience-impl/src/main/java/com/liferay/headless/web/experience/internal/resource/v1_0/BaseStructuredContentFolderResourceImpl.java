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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentFolderResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

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
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseStructuredContentFolderResourceImpl
	implements StructuredContentFolderResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/content-spaces/{content-space-id}/structured-content-folders")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContentFolder")})
	public Page<StructuredContentFolder>
			getContentSpaceStructuredContentFoldersPage(
				@NotNull @PathParam("content-space-id") Long contentSpaceId,
				@QueryParam("flatten") Boolean flatten,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/structured-content-folders")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContentFolder")})
	public StructuredContentFolder postContentSpaceStructuredContentFolder(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		return new StructuredContentFolder();
	}

	@Override
	@DELETE
	@Path("/structured-content-folders/{structured-content-folder-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContentFolder")})
	public void deleteStructuredContentFolder(
			@NotNull @PathParam("structured-content-folder-id") Long
				structuredContentFolderId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/structured-content-folders/{structured-content-folder-id}")
	@Produces("application/json")
	@Tags(value = {})
	public StructuredContentFolder getStructuredContentFolder(
			@NotNull @PathParam("structured-content-folder-id") Long
				structuredContentFolderId)
		throws Exception {

		return new StructuredContentFolder();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/structured-content-folders/{structured-content-folder-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContentFolder")})
	public StructuredContentFolder putStructuredContentFolder(
			@NotNull @PathParam("structured-content-folder-id") Long
				structuredContentFolderId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		return new StructuredContentFolder();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path(
		"/structured-content-folders/{structured-content-folder-id}/structured-content-folders"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContentFolder")})
	public Page<StructuredContentFolder>
			getStructuredContentFolderStructuredContentFoldersPage(
				@NotNull @PathParam("structured-content-folder-id") Long
					structuredContentFolderId,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/structured-content-folders/{structured-content-folder-id}/structured-content-folders"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContentFolder")})
	public StructuredContentFolder
			postStructuredContentFolderStructuredContentFolder(
				@NotNull @PathParam("structured-content-folder-id") Long
					structuredContentFolderId,
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return new StructuredContentFolder();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		StructuredContentFolder structuredContentFolder) {
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