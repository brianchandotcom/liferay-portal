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

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import javax.validation.constraints.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * @author Luis Miguel Barcos
 */
@Path("/v1.0")
public class BaseObjectRelationshipsResourceImpl {

	@GET
	@Parameters(
		{
			@Parameter(in = ParameterIn.PATH, name = "previousPath"),
			@Parameter(in = ParameterIn.PATH, name = "objectEntryId"),
			@Parameter(in = ParameterIn.PATH, name = "objectRelationshipName"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/{previousPath: [a-zA-Z0-9-]+}/{objectEntryId: \\d+}/{objectRelationshipName: [a-zA-Z0-9-]+}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags({@Tag(name = "ObjectEntry")})
	public Page<Object> getObjectRelatedObjectsPage(
			@NotNull @Parameter(hidden = true) @PathParam("previousPath") String
				previousPath,
			@NotNull @Parameter(hidden = true) @PathParam("objectEntryId") Long
				objectEntryId,
			@NotNull @Parameter(hidden = true)
			@PathParam("objectRelationshipName")
			String objectRelationshipName,
			@Context Pagination pagination)
		throws Exception {

		return null;
	}

}