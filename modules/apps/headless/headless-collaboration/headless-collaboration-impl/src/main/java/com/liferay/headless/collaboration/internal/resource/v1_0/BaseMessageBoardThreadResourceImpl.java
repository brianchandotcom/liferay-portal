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

import com.liferay.headless.collaboration.dto.v1_0.MessageBoardThread;
import com.liferay.headless.collaboration.resource.v1_0.MessageBoardThreadResource;
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
public abstract class BaseMessageBoardThreadResourceImpl
	implements MessageBoardThreadResource {

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
	@Path("/content-spaces/{content-space-id}/message-board-threads")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<MessageBoardThread> getContentSpaceMessageBoardThreadsPage(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@QueryParam("flatten") Boolean flatten,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/message-board-threads")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread postContentSpaceMessageBoardThread(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
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
	@Path(
		"/message-board-sections/{message-board-section-id}/message-board-threads"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<MessageBoardThread>
			getMessageBoardSectionMessageBoardThreadsPage(
				@NotNull @PathParam("message-board-section-id") Long
					messageBoardSectionId,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/message-board-sections/{message-board-section-id}/message-board-threads"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread postMessageBoardSectionMessageBoardThread(
			@NotNull @PathParam("message-board-section-id") Long
				messageBoardSectionId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
	}

	@Override
	@DELETE
	@Path("/message-board-threads/{message-board-thread-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public void deleteMessageBoardThread(
			@NotNull @PathParam("message-board-thread-id") Long
				messageBoardThreadId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/message-board-threads/{message-board-thread-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread getMessageBoardThread(
			@NotNull @PathParam("message-board-thread-id") Long
				messageBoardThreadId)
		throws Exception {

		return new MessageBoardThread();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/message-board-threads/{message-board-thread-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread patchMessageBoardThread(
			@NotNull @PathParam("message-board-thread-id") Long
				messageBoardThreadId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		preparePatch(messageBoardThread);

		MessageBoardThread existingMessageBoardThread = getMessageBoardThread(
			messageBoardThreadId);

		if (Validator.isNotNull(messageBoardThread.getArticleBody())) {
			existingMessageBoardThread.setArticleBody(
				messageBoardThread.getArticleBody());
		}

		if (Validator.isNotNull(messageBoardThread.getContentSpaceId())) {
			existingMessageBoardThread.setContentSpaceId(
				messageBoardThread.getContentSpaceId());
		}

		if (Validator.isNotNull(messageBoardThread.getDateCreated())) {
			existingMessageBoardThread.setDateCreated(
				messageBoardThread.getDateCreated());
		}

		if (Validator.isNotNull(messageBoardThread.getDateModified())) {
			existingMessageBoardThread.setDateModified(
				messageBoardThread.getDateModified());
		}

		if (Validator.isNotNull(messageBoardThread.getEncodingFormat())) {
			existingMessageBoardThread.setEncodingFormat(
				messageBoardThread.getEncodingFormat());
		}

		if (Validator.isNotNull(messageBoardThread.getHeadline())) {
			existingMessageBoardThread.setHeadline(
				messageBoardThread.getHeadline());
		}

		if (Validator.isNotNull(messageBoardThread.getKeywords())) {
			existingMessageBoardThread.setKeywords(
				messageBoardThread.getKeywords());
		}

		if (Validator.isNotNull(
				messageBoardThread.getNumberOfMessageBoardAttachments())) {

			existingMessageBoardThread.setNumberOfMessageBoardAttachments(
				messageBoardThread.getNumberOfMessageBoardAttachments());
		}

		if (Validator.isNotNull(
				messageBoardThread.getNumberOfMessageBoardMessages())) {

			existingMessageBoardThread.setNumberOfMessageBoardMessages(
				messageBoardThread.getNumberOfMessageBoardMessages());
		}

		if (Validator.isNotNull(messageBoardThread.getShowAsQuestion())) {
			existingMessageBoardThread.setShowAsQuestion(
				messageBoardThread.getShowAsQuestion());
		}

		if (Validator.isNotNull(messageBoardThread.getTaxonomyCategoryIds())) {
			existingMessageBoardThread.setTaxonomyCategoryIds(
				messageBoardThread.getTaxonomyCategoryIds());
		}

		if (Validator.isNotNull(messageBoardThread.getThreadType())) {
			existingMessageBoardThread.setThreadType(
				messageBoardThread.getThreadType());
		}

		if (Validator.isNotNull(messageBoardThread.getViewableBy())) {
			existingMessageBoardThread.setViewableBy(
				messageBoardThread.getViewableBy());
		}

		return putMessageBoardThread(
			messageBoardThreadId, existingMessageBoardThread);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/message-board-threads/{message-board-thread-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread putMessageBoardThread(
			@NotNull @PathParam("message-board-thread-id") Long
				messageBoardThreadId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(MessageBoardThread messageBoardThread) {
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