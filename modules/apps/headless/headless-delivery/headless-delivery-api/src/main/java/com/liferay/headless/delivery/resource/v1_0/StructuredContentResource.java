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

package com.liferay.headless.delivery.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
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
import javax.ws.rs.PATCH;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-delivery/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface StructuredContentResource {

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/content-structures/{contentStructureId}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			@NotNull @PathParam("contentStructureId") Long contentStructureId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/sites/{siteId}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getSiteStructuredContentsPage(
			@NotNull @PathParam("siteId") Long siteId,
			@QueryParam("flatten") Boolean flatten,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/sites/{siteId}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent postSiteStructuredContent(
			@NotNull @PathParam("siteId") Long siteId,
			StructuredContent structuredContent)
		throws Exception;

	@GET
	@Path("/sites/{siteId}/structured-contents/by-key/{key}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getSiteStructuredContentByKey(
			@NotNull @PathParam("siteId") Long siteId,
			@NotNull @PathParam("key") String key)
		throws Exception;

	@GET
	@Path("/sites/{siteId}/structured-contents/by-uuid/{uuid}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getSiteStructuredContentByUuid(
			@NotNull @PathParam("siteId") Long siteId,
			@NotNull @PathParam("uuid") String uuid)
		throws Exception;

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
		"/structured-content-folders/{structuredContentFolderId}/structured-contents"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				@NotNull @PathParam("structuredContentFolderId") Long
					structuredContentFolderId,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path(
		"/structured-content-folders/{structuredContentFolderId}/structured-contents"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent postStructuredContentFolderStructuredContent(
			@NotNull @PathParam("structuredContentFolderId") Long
				structuredContentFolderId,
			StructuredContent structuredContent)
		throws Exception;

	@DELETE
	@Path("/structured-contents/{structuredContentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void deleteStructuredContent(
			@NotNull @PathParam("structuredContentId") Long structuredContentId)
		throws Exception;

	@GET
	@Path("/structured-contents/{structuredContentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getStructuredContent(
			@NotNull @PathParam("structuredContentId") Long structuredContentId)
		throws Exception;

	@Consumes("application/json")
	@PATCH
	@Path("/structured-contents/{structuredContentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent patchStructuredContent(
			@NotNull @PathParam("structuredContentId") Long structuredContentId,
			StructuredContent structuredContent)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/structured-contents/{structuredContentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent putStructuredContent(
			@NotNull @PathParam("structuredContentId") Long structuredContentId,
			StructuredContent structuredContent)
		throws Exception;

	@DELETE
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void deleteStructuredContentMyRating(
			@NotNull @PathParam("structuredContentId") Long structuredContentId)
		throws Exception;

	@GET
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating getStructuredContentMyRating(
			@NotNull @PathParam("structuredContentId") Long structuredContentId)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating postStructuredContentMyRating(
			@NotNull @PathParam("structuredContentId") Long structuredContentId,
			Rating rating)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating putStructuredContentMyRating(
			@NotNull @PathParam("structuredContentId") Long structuredContentId,
			Rating rating)
		throws Exception;

	@GET
	@Path(
		"/structured-contents/{structuredContentId}/rendered-content/{templateId}"
	)
	@Produces("text/html")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public String getStructuredContentRenderedContentTemplate(
			@NotNull @PathParam("structuredContentId") Long structuredContentId,
			@NotNull @PathParam("templateId") Long templateId)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}