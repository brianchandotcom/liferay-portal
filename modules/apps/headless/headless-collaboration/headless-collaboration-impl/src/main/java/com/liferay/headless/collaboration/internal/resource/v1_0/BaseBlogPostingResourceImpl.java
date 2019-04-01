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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseBlogPostingResourceImpl
	implements BlogPostingResource {

	@Override
	@DELETE
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public void deleteBlogPosting(
			@NotNull @PathParam("blog-posting-id") Long blogPostingId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting getBlogPosting(
			@NotNull @PathParam("blog-posting-id") Long blogPostingId)
		throws Exception {

		return new BlogPosting();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting patchBlogPosting(
			@NotNull @PathParam("blog-posting-id") Long blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		preparePatch(blogPosting);

		BlogPosting existingBlogPosting = getBlogPosting(blogPostingId);

		if (Validator.isNotNull(blogPosting.getAlternativeHeadline())) {
			existingBlogPosting.setAlternativeHeadline(
				blogPosting.getAlternativeHeadline());
		}

		if (Validator.isNotNull(blogPosting.getArticleBody())) {
			existingBlogPosting.setArticleBody(blogPosting.getArticleBody());
		}

		if (Validator.isNotNull(blogPosting.getContentSpaceId())) {
			existingBlogPosting.setContentSpaceId(
				blogPosting.getContentSpaceId());
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

		if (Validator.isNotNull(blogPosting.getHeadline())) {
			existingBlogPosting.setHeadline(blogPosting.getHeadline());
		}

		if (Validator.isNotNull(blogPosting.getKeywords())) {
			existingBlogPosting.setKeywords(blogPosting.getKeywords());
		}

		if (Validator.isNotNull(blogPosting.getNumberOfComments())) {
			existingBlogPosting.setNumberOfComments(
				blogPosting.getNumberOfComments());
		}

		if (Validator.isNotNull(blogPosting.getTaxonomyCategoryIds())) {
			existingBlogPosting.setTaxonomyCategoryIds(
				blogPosting.getTaxonomyCategoryIds());
		}

		if (Validator.isNotNull(blogPosting.getViewableBy())) {
			existingBlogPosting.setViewableBy(blogPosting.getViewableBy());
		}

		return putBlogPosting(blogPostingId, existingBlogPosting);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/blog-postings/{blog-posting-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting putBlogPosting(
			@NotNull @PathParam("blog-posting-id") Long blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/content-spaces/{content-space-id}/blog-postings")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Page<BlogPosting> getContentSpaceBlogPostingsPage(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/blog-postings")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting postContentSpaceBlogPosting(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(BlogPosting blogPosting) {
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