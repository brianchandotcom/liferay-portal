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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.net.URI;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseBlogPostingImageResourceImpl
	implements BlogPostingImageResource {

	@DELETE
	@Override
	@Path("/blog-posting-images/{blog-posting-image-id}")
	@Produces("application/json")
	public boolean deleteBlogPostingImage(
			@PathParam("blog-posting-image-id") Long blogPostingImageId)
		throws Exception {

		return false;
	}

	@GET
	@Override
	@Path("/blog-posting-images/{blog-posting-image-id}")
	@Produces("application/json")
	public BlogPostingImage getBlogPostingImage(
			@PathParam("blog-posting-image-id") Long blogPostingImageId)
		throws Exception {

		return new BlogPostingImage();
	}

	@GET
	@Override
	@Path("/content-spaces/{content-space-id}/blog-posting-images")
	@Produces("application/json")
	public Page<BlogPostingImage> getContentSpaceBlogPostingImagesPage(
			@PathParam("content-space-id") Long contentSpaceId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Consumes("multipart/form-data")
	@Override
	@PATCH
	@Path("/blog-posting-images/{blog-posting-image-id}")
	@Produces("application/json")
	public BlogPostingImage patchBlogPostingImage(
			@PathParam("blog-posting-image-id") Long blogPostingImageId,
			MultipartBody multipartBody)
		throws Exception {

		return new BlogPostingImage();
	}

	@Consumes("multipart/form-data")
	@Override
	@Path("/content-spaces/{content-space-id}/blog-posting-images")
	@POST
	@Produces("application/json")
	public BlogPostingImage postContentSpaceBlogPostingImage(
			@PathParam("content-space-id") Long contentSpaceId,
			MultipartBody multipartBody)
		throws Exception {

		return new BlogPostingImage();
	}

	@Consumes("multipart/form-data")
	@Override
	@Path("/blog-posting-images/{blog-posting-image-id}")
	@Produces("application/json")
	@PUT
	public BlogPostingImage putBlogPostingImage(
			@PathParam("blog-posting-image-id") Long blogPostingImageId,
			MultipartBody multipartBody)
		throws Exception {

		return new BlogPostingImage();
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
			BaseBlogPostingImageResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseBlogPostingImageResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(BlogPostingImage blogPostingImage) {
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transformToArray(list, unsafeFunction, clazz);
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