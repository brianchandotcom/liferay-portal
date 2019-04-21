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

import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
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
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public void deleteBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting getBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId)
		throws Exception {

		return new BlogPosting();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting patchBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		BlogPosting existingBlogPosting = getBlogPosting(blogPostingId);

		if (blogPosting.getAlternativeHeadline() != null) {
			existingBlogPosting.setAlternativeHeadline(
				blogPosting.getAlternativeHeadline());
		}

		if (blogPosting.getArticleBody() != null) {
			existingBlogPosting.setArticleBody(blogPosting.getArticleBody());
		}

		if (blogPosting.getDateCreated() != null) {
			existingBlogPosting.setDateCreated(blogPosting.getDateCreated());
		}

		if (blogPosting.getDateModified() != null) {
			existingBlogPosting.setDateModified(blogPosting.getDateModified());
		}

		if (blogPosting.getDatePublished() != null) {
			existingBlogPosting.setDatePublished(
				blogPosting.getDatePublished());
		}

		if (blogPosting.getDescription() != null) {
			existingBlogPosting.setDescription(blogPosting.getDescription());
		}

		if (blogPosting.getEncodingFormat() != null) {
			existingBlogPosting.setEncodingFormat(
				blogPosting.getEncodingFormat());
		}

		if (blogPosting.getFriendlyUrlPath() != null) {
			existingBlogPosting.setFriendlyUrlPath(
				blogPosting.getFriendlyUrlPath());
		}

		if (blogPosting.getHeadline() != null) {
			existingBlogPosting.setHeadline(blogPosting.getHeadline());
		}

		if (blogPosting.getKeywords() != null) {
			existingBlogPosting.setKeywords(blogPosting.getKeywords());
		}

		if (blogPosting.getNumberOfComments() != null) {
			existingBlogPosting.setNumberOfComments(
				blogPosting.getNumberOfComments());
		}

		if (blogPosting.getSiteId() != null) {
			existingBlogPosting.setSiteId(blogPosting.getSiteId());
		}

		if (blogPosting.getTaxonomyCategoryIds() != null) {
			existingBlogPosting.setTaxonomyCategoryIds(
				blogPosting.getTaxonomyCategoryIds());
		}

		if (blogPosting.getViewableBy() != null) {
			existingBlogPosting.setViewableBy(blogPosting.getViewableBy());
		}

		preparePatch(blogPosting, existingBlogPosting);

		return putBlogPosting(blogPostingId, existingBlogPosting);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting putBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	@Override
	@DELETE
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public void deleteBlogPostingMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Rating getBlogPostingMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId)
		throws Exception {

		return new Rating();
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Rating postBlogPostingMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Rating putBlogPostingMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	@Override
	@GET
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
	@Path("/sites/{siteId}/blog-postings")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Page<BlogPosting> getSiteBlogPostingsPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/blog-postings")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting postSiteBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		BlogPosting blogPosting, BlogPosting existingBlogPosting) {
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