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

import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
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
import javax.ws.rs.PUT;
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
public interface CommentResource {

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/blog-postings/{blogPostingId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Page<Comment> getBlogPostingCommentsPage(
			@NotNull @PathParam("blogPostingId") Long blogPostingId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/blog-postings/{blogPostingId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment postBlogPostingComment(
			@NotNull @PathParam("blogPostingId") Long blogPostingId,
			Comment comment)
		throws Exception;

	@DELETE
	@Path("/comments/{commentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public void deleteComment(@NotNull @PathParam("commentId") Long commentId)
		throws Exception;

	@GET
	@Path("/comments/{commentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment getComment(@NotNull @PathParam("commentId") Long commentId)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/comments/{commentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment putComment(
			@NotNull @PathParam("commentId") Long commentId, Comment comment)
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
	@Path("/comments/{parentCommentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Page<Comment> getCommentCommentsPage(
			@NotNull @PathParam("parentCommentId") Long parentCommentId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/comments/{parentCommentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment postCommentComment(
			@NotNull @PathParam("parentCommentId") Long parentCommentId,
			Comment comment)
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
	@Path("/documents/{documentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Page<Comment> getDocumentCommentsPage(
			@NotNull @PathParam("documentId") Long documentId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/documents/{documentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment postDocumentComment(
			@NotNull @PathParam("documentId") Long documentId, Comment comment)
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
	@Path("/structured-contents/{structuredContentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Page<Comment> getStructuredContentCommentsPage(
			@NotNull @PathParam("structuredContentId") Long structuredContentId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/structured-contents/{structuredContentId}/comments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Comment")})
	public Comment postStructuredContentComment(
			@NotNull @PathParam("structuredContentId") Long structuredContentId,
			Comment comment)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}