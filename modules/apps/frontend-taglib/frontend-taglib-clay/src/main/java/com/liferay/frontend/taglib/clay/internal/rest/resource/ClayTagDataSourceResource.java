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

package com.liferay.frontend.taglib.clay.internal.rest.resource;

import com.liferay.frontend.taglib.clay.internal.ClayTagDataSourceProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSource;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.Pagination;
import com.liferay.portal.kernel.json.JSONFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodolfo Roza Miranda
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=taglib-clay-application)",
		"osgi.jaxrs.resource=true"
	},
	service = ClayTagDataSourceResource.class
)
public class ClayTagDataSourceResource {

	@GET
	@Path("/clay-tag-data-source/{dataSourceKey}/items")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClayTagDataSourceItems(
		@PathParam("dataSourceKey") String dataSourceKey,
		@QueryParam("page") int page, @QueryParam("pageSize") int pageSize,
		@Context HttpServletRequest request) {

		ClayTagDataSource<?> clayTagDataSource =
			ClayTagDataSourceProvider.getClayTagDataSource(dataSourceKey);

		if (clayTagDataSource == null) {
			return Response.status(
				Response.Status.NOT_FOUND
			).build();
		}

		Pagination pagination = new Pagination(pageSize, page);

		List<?> items = clayTagDataSource.getItems(request, pagination);

		Map<String, Object> map = new HashMap<>();

		map.put("items", items);

		return Response.ok(
			_jsonFactory.looseSerializeDeep(map)
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}