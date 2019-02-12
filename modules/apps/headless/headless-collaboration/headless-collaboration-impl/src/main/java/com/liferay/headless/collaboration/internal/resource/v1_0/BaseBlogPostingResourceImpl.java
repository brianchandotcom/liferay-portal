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

import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseBlogPostingResourceImpl
	implements BlogPostingResource {

	@Override
	public Response deleteBlogPostings(Long blogPostingId) throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public BlogPosting getBlogPostings(Long blogPostingId) throws Exception {
		return new BlogPosting();
	}

	@Override
	public Page<Long> getBlogPostingsCategoriesPage(
			Long blogPostingId, Pagination pagination)
		throws Exception {

		return new Page<>();
	}

	@Override
	public Page<BlogPosting> getContentSpacesBlogPostingsPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Response postBlogPostingsCategories(
			Long blogPostingId, Long referenceId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response postBlogPostingsCategoriesBatchCreate(
			Long blogPostingId, Long referenceId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public BlogPosting postContentSpacesBlogPostings(
			Long contentSpaceId, BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	@Override
	public BlogPosting postContentSpacesBlogPostingsBatchCreate(
			Long contentSpaceId, BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	@Override
	public BlogPosting putBlogPostings(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	protected Response buildNoContentResponse() {
		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}