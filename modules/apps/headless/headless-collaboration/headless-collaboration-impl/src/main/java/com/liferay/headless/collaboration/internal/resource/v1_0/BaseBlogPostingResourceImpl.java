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
import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImpl;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseBlogPostingResourceImpl implements BlogPostingResource {

	@Override
	@DELETE
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public boolean deleteBlogPosting(
	@PathParam("blog-posting-id") Long blogPostingId)
			throws Exception {

				return false;
	}
	@Override
	@GET
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public BlogPosting getBlogPosting(
	@PathParam("blog-posting-id") Long blogPostingId)
			throws Exception {

				return new BlogPostingImpl();
	}
	@Override
	@Consumes("application/json")
	@PUT
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public BlogPosting putBlogPosting(
	@PathParam("blog-posting-id") Long blogPostingId,BlogPosting blogPosting)
			throws Exception {

				return new BlogPostingImpl();
	}
	@Override
	@GET
	@Path("/content-spaces/{content-space-id}/blog-postings")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<BlogPosting> getContentSpaceBlogPostingsPage(
	@PathParam("content-space-id") Long contentSpaceId,@Context Pagination pagination)
			throws Exception {

				return Page.of(Collections.emptyList());
	}
	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/blog-postings")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public BlogPosting postContentSpaceBlogPosting(
	@PathParam("content-space-id") Long contentSpaceId,BlogPosting blogPosting)
			throws Exception {

				return new BlogPostingImpl();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected <T, R> List<R> transform(List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {
		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

}