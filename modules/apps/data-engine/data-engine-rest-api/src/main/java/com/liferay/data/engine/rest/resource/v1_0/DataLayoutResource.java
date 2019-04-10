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

package com.liferay.data.engine.rest.resource.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPermission;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

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
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/data-engine/v1.0
 *
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface DataLayoutResource {

	@Consumes("application/json")
	@POST
	@Path("/data-definitions/{dataDefinitionId}/data-layouts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataLayout")})
	public DataLayout postDataDefinitionDataLayout(
			@NotNull @PathParam("dataDefinitionId") Long dataDefinitionId,
			DataLayout dataLayout)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/data-layout/{dataLayoutId}/data-layout-permissions")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataLayout")})
	public void postDataLayoutDataLayoutPermission(
			@NotNull @PathParam("dataLayoutId") Long dataLayoutId,
			@NotNull @QueryParam("operation") String operation,
			DataLayoutPermission dataLayoutPermission)
		throws Exception;

	@DELETE
	@Path("/data-layouts/{dataLayoutId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataLayout")})
	public void deleteDataLayout(
			@NotNull @PathParam("dataLayoutId") Long dataLayoutId)
		throws Exception;

	@GET
	@Path("/data-layouts/{dataLayoutId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataLayout")})
	public DataLayout getDataLayout(
			@NotNull @PathParam("dataLayoutId") Long dataLayoutId)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/data-layouts/{dataLayoutId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataLayout")})
	public DataLayout putDataLayout(
			@NotNull @PathParam("dataLayoutId") Long dataLayoutId,
			DataLayout dataLayout)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/sites/{siteId}/data-layout")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataLayout")})
	public Page<DataLayout> getSiteDataLayoutPage(
			@NotNull @PathParam("siteId") Long siteId,
			@Context Pagination pagination)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/sites/{siteId}/data-layout-permissions")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataLayout")})
	public void postSiteDataLayoutPermission(
			@NotNull @PathParam("siteId") Long siteId,
			@NotNull @QueryParam("operation") String operation,
			DataLayoutPermission dataLayoutPermission)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}