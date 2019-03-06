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

package com.liferay.headless.document.library.internal.resource.v1_0;

import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.resource.v1_0.CommentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
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
public abstract class BaseCommentResourceImpl implements CommentResource {

	@DELETE
	@Override
	@Path("/comments/{comment-id}")
	@Produces("application/json")
	public boolean deleteComment(@PathParam("comment-id") Long commentId)
		throws Exception {

		return false;
	}

	@GET
	@Override
	@Path("/comments/{comment-id}")
	@Produces("application/json")
	public Comment getComment(@PathParam("comment-id") Long commentId)
		throws Exception {

		return new Comment();
	}

	@GET
	@Override
	@Path("/comments/{comment-id}/comments")
	@Produces("application/json")
	public Page<Comment> getCommentCommentsPage(
			@PathParam("comment-id") Long commentId, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@GET
	@Override
	@Path("/documents/{document-id}/comments")
	@Produces("application/json")
	public Page<Comment> getDocumentCommentsPage(
			@PathParam("document-id") Long documentId, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Consumes("application/json")
	@Override
	@Path("/comments/{comment-id}/comments")
	@POST
	@Produces("application/json")
	public Comment postCommentComment(
			@PathParam("comment-id") Long commentId, Comment comment)
		throws Exception {

		return new Comment();
	}

	@Consumes("application/json")
	@Override
	@Path("/documents/{document-id}/comments")
	@POST
	@Produces("application/json")
	public Comment postDocumentComment(
			@PathParam("document-id") Long documentId, Comment comment)
		throws Exception {

		return new Comment();
	}

	@Consumes("application/json")
	@Override
	@Path("/comments/{comment-id}")
	@Produces("application/json")
	@PUT
	public Comment putComment(
			@PathParam("comment-id") Long commentId, Comment comment)
		throws Exception {

		return new Comment();
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
			BaseCommentResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseCommentResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(Comment comment) {
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