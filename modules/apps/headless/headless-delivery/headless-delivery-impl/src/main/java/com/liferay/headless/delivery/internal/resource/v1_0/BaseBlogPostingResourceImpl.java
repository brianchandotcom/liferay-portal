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
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Operation(
		description = "Deletes the blog post and returns a 204 if the operation succeeds."
	)
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public void deleteBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(description = "Retrieves the blog post.")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting getBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId)
		throws Exception {

		return new BlogPosting();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}' -d $'{"alternativeHeadline": ___, "articleBody": ___, "customFields": ___, "datePublished": ___, "description": ___, "friendlyUrlPath": ___, "headline": ___, "image": ___, "keywords": ___, "taxonomyCategoryIds": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the blog post using only the fields received in the request body. Any other fields are left untouched. Returns the updated blog post."
	)
	@PATCH
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting patchBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		BlogPosting existingBlogPosting = getBlogPosting(blogPostingId);

		if (blogPosting.getActions() != null) {
			existingBlogPosting.setActions(blogPosting.getActions());
		}

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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}' -d $'{"alternativeHeadline": ___, "articleBody": ___, "customFields": ___, "datePublished": ___, "description": ___, "friendlyUrlPath": ___, "headline": ___, "image": ___, "keywords": ___, "taxonomyCategoryIds": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the blog post with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting putBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Operation(
		description = "Deletes the blog post rating of the user who authenticated the request."
	)
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public void deleteBlogPostingMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the blog post rating of the user who authenticated the request."
	)
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Rating getBlogPostingMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a new blog post rating by the user who authenticated the request."
	)
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Rating postBlogPostingMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/blog-postings/{blogPostingId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces an existing blog post rating by the user who authenticated the request."
	)
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Rating putBlogPostingMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the site's blog postings. Results can be paginated, filtered, searched, and sorted."
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
	@Path("/sites/{siteId}/blog-postings")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public Page<BlogPosting> getSiteBlogPostingsPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings' -d $'{"alternativeHeadline": ___, "articleBody": ___, "customFields": ___, "datePublished": ___, "description": ___, "friendlyUrlPath": ___, "headline": ___, "image": ___, "keywords": ___, "taxonomyCategoryIds": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new blog post.")
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/blog-postings")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public BlogPosting postSiteBlogPosting(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			BlogPosting blogPosting)
		throws Exception {

		return new BlogPosting();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/blog-postings/subscribe")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public void putSiteBlogPostingSubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-postings/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/blog-postings/unsubscribe")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BlogPosting")})
	public void putSiteBlogPostingUnsubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId)
		throws Exception {
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, String permissionName,
		Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, permissionName,
			contextScopeChecker, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, permissionName, siteId);
	}

	protected void preparePatch(
		BlogPosting blogPosting, BlogPosting existingBlogPosting) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;

}