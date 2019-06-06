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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingImageResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
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
public abstract class BaseBlogPostingImageResourceImpl
	implements BlogPostingImageResource {

	@Override
	@DELETE
	@Operation(description = "Deletes the blog post's image.")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingImageId")}
	)
	@Path("/blog-posting-images/{blogPostingImageId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPostingImage")})
	public void deleteBlogPostingImage(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingImageId")
				Long blogPostingImageId)
		throws Exception {
	}

	@Override
	@GET
	@Operation(
		description = "Retrieves the blog post's image. The binary image is returned as a relative URL to the image itself."
	)
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingImageId")}
	)
	@Path("/blog-posting-images/{blogPostingImageId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPostingImage")})
	public BlogPostingImage getBlogPostingImage(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingImageId")
				Long blogPostingImageId)
		throws Exception {

		return new BlogPostingImage();
	}

	@Override
	@GET
	@Operation(
		description = "Retrieves the Site's blog post images. Results can be paginated, filtered, searched, and sorted."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/blog-posting-images")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPostingImage")})
	public Page<BlogPostingImage> getSiteBlogPostingImagesPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("multipart/form-data")
	@Operation(
		description = "Creates a blog post image. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`blogPostingImage`) with the metadata."
	)
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/blog-posting-images")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPostingImage")})
	public BlogPostingImage postSiteBlogPostingImage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			MultipartBody multipartBody)
		throws Exception {

		return new BlogPostingImage();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		BlogPostingImage blogPostingImage,
		BlogPostingImage existingBlogPostingImage) {
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