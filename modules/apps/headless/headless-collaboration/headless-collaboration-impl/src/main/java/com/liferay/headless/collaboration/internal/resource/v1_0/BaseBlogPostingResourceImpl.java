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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
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
public abstract class BaseBlogPostingResourceImpl
	implements BlogPostingResource {

	@DELETE
	@Override
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public boolean deleteBlogPosting(
			@PathParam("blog-posting-id") Long blogPostingId)
		throws Exception {

		return false;
	}

	@GET
	@Override
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public BlogPosting getBlogPosting(
			@PathParam("blog-posting-id") Long blogPostingId)
		throws Exception {

		return new BlogPostingImpl();
	}

	@GET
	@Override
	@Path("/content-spaces/{content-space-id}/blog-postings")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<BlogPosting> getContentSpaceBlogPostingsPage(
			@PathParam("content-space-id") Long contentSpaceId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Consumes("application/json")
	@Override
	@PATCH
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public BlogPosting patchBlogPosting(
			@PathParam("blog-posting-id") Long blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		BlogPosting existingBlogPosting = getBlogPosting(blogPostingId);

		if (Validator.isNotNull(blogPosting.getAlternativeHeadline())) {
			existingBlogPosting.setAlternativeHeadline(
				blogPosting.getAlternativeHeadline());
		}

		if (Validator.isNotNull(blogPosting.getArticleBody())) {
			existingBlogPosting.setArticleBody(blogPosting.getArticleBody());
		}

		if (Validator.isNotNull(blogPosting.getCaption())) {
			existingBlogPosting.setCaption(blogPosting.getCaption());
		}

		if (Validator.isNotNull(blogPosting.getCategoryIds())) {
			existingBlogPosting.setCategoryIds(blogPosting.getCategoryIds());
		}

		if (Validator.isNotNull(blogPosting.getContentSpace())) {
			existingBlogPosting.setContentSpace(blogPosting.getContentSpace());
		}

		if (Validator.isNotNull(blogPosting.getDateCreated())) {
			existingBlogPosting.setDateCreated(blogPosting.getDateCreated());
		}

		if (Validator.isNotNull(blogPosting.getDateModified())) {
			existingBlogPosting.setDateModified(blogPosting.getDateModified());
		}

		if (Validator.isNotNull(blogPosting.getDatePublished())) {
			existingBlogPosting.setDatePublished(
				blogPosting.getDatePublished());
		}

		if (Validator.isNotNull(blogPosting.getDescription())) {
			existingBlogPosting.setDescription(blogPosting.getDescription());
		}

		if (Validator.isNotNull(blogPosting.getEncodingFormat())) {
			existingBlogPosting.setEncodingFormat(
				blogPosting.getEncodingFormat());
		}

		if (Validator.isNotNull(blogPosting.getFriendlyUrlPath())) {
			existingBlogPosting.setFriendlyUrlPath(
				blogPosting.getFriendlyUrlPath());
		}

		if (Validator.isNotNull(blogPosting.getHasComments())) {
			existingBlogPosting.setHasComments(blogPosting.getHasComments());
		}

		if (Validator.isNotNull(blogPosting.getHeadline())) {
			existingBlogPosting.setHeadline(blogPosting.getHeadline());
		}

		if (Validator.isNotNull(blogPosting.getId())) {
			existingBlogPosting.setId(blogPosting.getId());
		}

		if (Validator.isNotNull(blogPosting.getImageId())) {
			existingBlogPosting.setImageId(blogPosting.getImageId());
		}

		if (Validator.isNotNull(blogPosting.getKeywords())) {
			existingBlogPosting.setKeywords(blogPosting.getKeywords());
		}

		return putBlogPosting(blogPostingId, existingBlogPosting);
	}

	@Consumes("application/json")
	@Override
	@Path("/content-spaces/{content-space-id}/blog-postings")
	@POST
	@Produces("application/json")
	@RequiresScope("everything.read")
	public BlogPosting postContentSpaceBlogPosting(
			@PathParam("content-space-id") Long contentSpaceId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPostingImpl();
	}

	@Consumes("application/json")
	@Override
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@PUT
	@RequiresScope("everything.read")
	public BlogPosting putBlogPosting(
			@PathParam("blog-posting-id") Long blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPostingImpl();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected String getJAXRSLink(String methodName, Object... values) {
		URI baseURI = contextUriInfo.getBaseUri();

		URI resourceURI = UriBuilder.fromResource(
			BaseBlogPostingResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseBlogPostingResourceImpl.class, methodName
		).build(
			values
		);

		return baseURI.toString() + resourceURI.toString() +
			methodURI.toString();
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}