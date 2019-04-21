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

import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.resource.v1_0.CommentResource;
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
public abstract class BaseCommentResourceImpl implements CommentResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "blogPostingId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/blog-postings/{blogPostingId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Page<Comment> getBlogPostingCommentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "blogPostingId")}
	)
	@Path("/blog-postings/{blogPostingId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment postBlogPostingComment(
			@NotNull @Parameter(hidden = true) @PathParam("blogPostingId") Long
				blogPostingId,
			Comment comment)
		throws Exception {

		return new Comment();
	}

	@Override
	@DELETE
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "commentId")})
	@Path("/comments/{commentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public void deleteComment(
			@NotNull @Parameter(hidden = true) @PathParam("commentId") Long
				commentId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "commentId")})
	@Path("/comments/{commentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment getComment(
			@NotNull @Parameter(hidden = true) @PathParam("commentId") Long
				commentId)
		throws Exception {

		return new Comment();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "commentId")})
	@Path("/comments/{commentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment putComment(
			@NotNull @Parameter(hidden = true) @PathParam("commentId") Long
				commentId,
			Comment comment)
		throws Exception {

		return new Comment();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "parentCommentId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/comments/{parentCommentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Page<Comment> getCommentCommentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("parentCommentId")
				Long parentCommentId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "parentCommentId")}
	)
	@Path("/comments/{parentCommentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment postCommentComment(
			@NotNull @Parameter(hidden = true) @PathParam("parentCommentId")
				Long parentCommentId,
			Comment comment)
		throws Exception {

		return new Comment();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "documentId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/documents/{documentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Page<Comment> getDocumentCommentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "documentId")}
	)
	@Path("/documents/{documentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment postDocumentComment(
			@NotNull @Parameter(hidden = true) @PathParam("documentId") Long
				documentId,
			Comment comment)
		throws Exception {

		return new Comment();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/structured-contents/{structuredContentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Page<Comment> getStructuredContentCommentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment postStructuredContentComment(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			Comment comment)
		throws Exception {

		return new Comment();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(Comment comment, Comment existingComment) {
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