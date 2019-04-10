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

import com.liferay.headless.delivery.dto.v1_0.BlogPostingImage;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
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
public interface BlogPostingImageResource {

	@DELETE
	@Path("/blog-posting-images/{blogPostingImageId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPostingImage")})
	public void deleteBlogPostingImage(
			@NotNull @PathParam("blogPostingImageId") Long blogPostingImageId)
		throws Exception;

	@GET
	@Path("/blog-posting-images/{blogPostingImageId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPostingImage")})
	public BlogPostingImage getBlogPostingImage(
			@NotNull @PathParam("blogPostingImageId") Long blogPostingImageId)
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
	@Path("/sites/{siteId}/blog-posting-images")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPostingImage")})
	public Page<BlogPostingImage> getSiteBlogPostingImagesPage(
			@NotNull @PathParam("siteId") Long siteId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("multipart/form-data")
	@POST
	@Path("/sites/{siteId}/blog-posting-images")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPostingImage")})
	public BlogPostingImage postSiteBlogPostingImage(
			@NotNull @PathParam("siteId") Long siteId,
			MultipartBody multipartBody)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}