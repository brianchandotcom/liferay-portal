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

package com.liferay.portal.vulcan.internal.util.resource;

import com.liferay.portal.vulcan.internal.util.dto.ProductOption;
import com.liferay.portal.vulcan.internal.util.dto.Sku;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * @author Ivica Cardic
 */
public class BaseProductResourceImpl implements ProductResource {

	@GET
	@Path("/{id}/productOption")
	@Produces("application/*")
	public List<ProductOption> getProductOptions(
		@PathParam("id") Long id, @QueryParam("name") String name,
		@QueryParam("createDate") Date createDate,
		@Context Pagination pagination) {

		return Collections.emptyList();
	}

	@GET
	@Path("/{id}/sku")
	@Produces("application/*")
	public Page<Sku> getSkus(
		@PathParam("id") String id, @Context Pagination pagination) {

		return Page.of(Collections.emptyList());
	}

}