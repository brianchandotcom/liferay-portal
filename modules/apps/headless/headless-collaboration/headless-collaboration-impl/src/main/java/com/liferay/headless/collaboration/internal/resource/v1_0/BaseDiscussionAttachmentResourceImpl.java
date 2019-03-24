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

import com.liferay.headless.collaboration.dto.v1_0.DiscussionAttachment;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionAttachmentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.net.URI;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
public abstract class BaseDiscussionAttachmentResourceImpl
	implements DiscussionAttachmentResource {

	@Override
	@DELETE
	@Path("/discussion-attachments/{discussion-attachment-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionAttachment")})
	public void deleteDiscussionAttachment(
			@NotNull @PathParam("discussion-attachment-id") Long
				discussionAttachmentId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/discussion-attachments/{discussion-attachment-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionAttachment")})
	public DiscussionAttachment getDiscussionAttachment(
			@NotNull @PathParam("discussion-attachment-id") Long
				discussionAttachmentId)
		throws Exception {

		return new DiscussionAttachment();
	}

	@Override
	@GET
	@Path(
		"/discussion-forum-postings/{discussion-forum-posting-id}/discussion-attachments"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionAttachment")})
	public Page<DiscussionAttachment>
			getDiscussionForumPostingDiscussionAttachmentsPage(
				@NotNull @PathParam("discussion-forum-posting-id") Long
					discussionForumPostingId)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("multipart/form-data")
	@POST
	@Path(
		"/discussion-forum-postings/{discussion-forum-posting-id}/discussion-attachments"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionAttachment")})
	public DiscussionAttachment postDiscussionForumPostingDiscussionAttachment(
			@NotNull @PathParam("discussion-forum-posting-id") Long
				discussionForumPostingId,
			MultipartBody multipartBody)
		throws Exception {

		return new DiscussionAttachment();
	}

	@Override
	@GET
	@Path("/discussion-threads/{discussion-thread-id}/discussion-attachments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionAttachment")})
	public Page<DiscussionAttachment>
			getDiscussionThreadDiscussionAttachmentsPage(
				@NotNull @PathParam("discussion-thread-id") Long
					discussionThreadId)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("multipart/form-data")
	@POST
	@Path("/discussion-threads/{discussion-thread-id}/discussion-attachments")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionAttachment")})
	public DiscussionAttachment postDiscussionThreadDiscussionAttachment(
			@NotNull @PathParam("discussion-thread-id") Long discussionThreadId,
			MultipartBody multipartBody)
		throws Exception {

		return new DiscussionAttachment();
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
			BaseDiscussionAttachmentResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseDiscussionAttachmentResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(DiscussionAttachment discussionAttachment) {
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