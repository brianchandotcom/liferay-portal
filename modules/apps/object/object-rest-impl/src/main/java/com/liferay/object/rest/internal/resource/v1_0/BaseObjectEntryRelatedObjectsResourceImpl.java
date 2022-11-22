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

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.validation.constraints.NotNull;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Carlos Correa
 */
public abstract class BaseObjectEntryRelatedObjectsResourceImpl {

	@DELETE
	@Parameters(
		{
			@Parameter(in = ParameterIn.PATH, name = "currentObjectEntryId"),
			@Parameter(in = ParameterIn.PATH, name = "objectRelationshipName"),
			@Parameter(in = ParameterIn.PATH, name = "relatedObjectEntryId")
		}
	)
	@Path(
		"/{currentObjectEntryId}/{objectRelationshipName}/{relatedObjectEntryId}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(@Tag(name = "ObjectEntry"))
	public abstract void deleteCurrentObjectEntry(
			@NotNull @Parameter(hidden = true)
			@PathParam("currentObjectEntryId")
			Long currentObjectEntryId,
			@NotNull @Parameter(hidden = true)
			@PathParam("objectRelationshipName")
			String objectRelationshipName,
			@NotNull @Parameter(hidden = true)
			@PathParam("relatedObjectEntryId")
			Long relatedObjectEntryId)
		throws Exception;

	@GET
	@Parameters(
		{
			@Parameter(in = ParameterIn.PATH, name = "currentObjectEntryId"),
			@Parameter(in = ParameterIn.PATH, name = "objectRelationshipName"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/{currentObjectEntryId}/{objectRelationshipName}")
	@Produces({"application/json", "application/xml"})
	@Tags(@Tag(name = "ObjectEntry"))
	public abstract Page<Object>
			getCurrentObjectEntriesObjectRelationshipNamePage(
				@NotNull @Parameter(hidden = true)
				@PathParam("currentObjectEntryId")
				Long currentObjectEntryId,
				@NotNull @Parameter(hidden = true)
				@PathParam("objectRelationshipName")
				String objectRelationshipName,
				@Context Pagination pagination)
		throws Exception;

	@Parameters(
		{
			@Parameter(in = ParameterIn.PATH, name = "currentObjectEntryId"),
			@Parameter(in = ParameterIn.PATH, name = "objectRelationshipName"),
			@Parameter(in = ParameterIn.PATH, name = "relatedObjectEntryId")
		}
	)
	@Path(
		"/{currentObjectEntryId}/{objectRelationshipName}/{relatedObjectEntryId}"
	)
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(@Tag(name = "ObjectEntry"))
	public abstract Object putCurrentObjectEntry(
			@NotNull @Parameter(hidden = true)
			@PathParam("currentObjectEntryId")
			Long currentObjectEntryId,
			@NotNull @Parameter(hidden = true)
			@PathParam("objectRelationshipName")
			String objectRelationshipName,
			@NotNull @Parameter(hidden = true)
			@PathParam("relatedObjectEntryId")
			Long relatedObjectEntryId)
		throws Exception;

	protected <T, R, E extends Throwable> List<R> transform(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected HttpServletRequest contextHttpServletRequest;
	protected UriInfo contextUriInfo;
	protected User contextUser;

}