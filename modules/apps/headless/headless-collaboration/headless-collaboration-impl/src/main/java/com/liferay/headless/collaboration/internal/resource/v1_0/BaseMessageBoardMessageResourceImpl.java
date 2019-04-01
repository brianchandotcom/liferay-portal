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

import com.liferay.headless.collaboration.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.collaboration.resource.v1_0.MessageBoardMessageResource;
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
public abstract class BaseMessageBoardMessageResourceImpl
	implements MessageBoardMessageResource {

	@Override
	@DELETE
	@Path("/message-board-messages/{message-board-message-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public void deleteMessageBoardMessage(
			@NotNull @PathParam("message-board-message-id") Long
				messageBoardMessageId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/message-board-messages/{message-board-message-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage getMessageBoardMessage(
			@NotNull @PathParam("message-board-message-id") Long
				messageBoardMessageId)
		throws Exception {

		return new MessageBoardMessage();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/message-board-messages/{message-board-message-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage patchMessageBoardMessage(
			@NotNull @PathParam("message-board-message-id") Long
				messageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		preparePatch(messageBoardMessage);

		MessageBoardMessage existingMessageBoardMessage =
			getMessageBoardMessage(messageBoardMessageId);

		if (Validator.isNotNull(messageBoardMessage.getAnonymous())) {
			existingMessageBoardMessage.setAnonymous(
				messageBoardMessage.getAnonymous());
		}

		if (Validator.isNotNull(messageBoardMessage.getArticleBody())) {
			existingMessageBoardMessage.setArticleBody(
				messageBoardMessage.getArticleBody());
		}

		if (Validator.isNotNull(messageBoardMessage.getContentSpaceId())) {
			existingMessageBoardMessage.setContentSpaceId(
				messageBoardMessage.getContentSpaceId());
		}

		if (Validator.isNotNull(messageBoardMessage.getDateCreated())) {
			existingMessageBoardMessage.setDateCreated(
				messageBoardMessage.getDateCreated());
		}

		if (Validator.isNotNull(messageBoardMessage.getDateModified())) {
			existingMessageBoardMessage.setDateModified(
				messageBoardMessage.getDateModified());
		}

		if (Validator.isNotNull(messageBoardMessage.getEncodingFormat())) {
			existingMessageBoardMessage.setEncodingFormat(
				messageBoardMessage.getEncodingFormat());
		}

		if (Validator.isNotNull(messageBoardMessage.getHeadline())) {
			existingMessageBoardMessage.setHeadline(
				messageBoardMessage.getHeadline());
		}

		if (Validator.isNotNull(messageBoardMessage.getKeywords())) {
			existingMessageBoardMessage.setKeywords(
				messageBoardMessage.getKeywords());
		}

		if (Validator.isNotNull(
				messageBoardMessage.getNumberOfMessageBoardAttachments())) {

			existingMessageBoardMessage.setNumberOfMessageBoardAttachments(
				messageBoardMessage.getNumberOfMessageBoardAttachments());
		}

		if (Validator.isNotNull(
				messageBoardMessage.getNumberOfMessageBoardMessages())) {

			existingMessageBoardMessage.setNumberOfMessageBoardMessages(
				messageBoardMessage.getNumberOfMessageBoardMessages());
		}

		if (Validator.isNotNull(messageBoardMessage.getShowAsAnswer())) {
			existingMessageBoardMessage.setShowAsAnswer(
				messageBoardMessage.getShowAsAnswer());
		}

		if (Validator.isNotNull(messageBoardMessage.getTaxonomyCategoryIds())) {
			existingMessageBoardMessage.setTaxonomyCategoryIds(
				messageBoardMessage.getTaxonomyCategoryIds());
		}

		if (Validator.isNotNull(messageBoardMessage.getViewableBy())) {
			existingMessageBoardMessage.setViewableBy(
				messageBoardMessage.getViewableBy());
		}

		return putMessageBoardMessage(
			messageBoardMessageId, existingMessageBoardMessage);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/message-board-messages/{message-board-message-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage putMessageBoardMessage(
			@NotNull @PathParam("message-board-message-id") Long
				messageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
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
		"/message-board-messages/{message-board-message-id}/message-board-messages"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				@NotNull @PathParam("message-board-message-id") Long
					messageBoardMessageId,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/message-board-messages/{message-board-message-id}/message-board-messages"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			@NotNull @PathParam("message-board-message-id") Long
				messageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
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
		"/message-board-threads/{message-board-thread-id}/message-board-messages"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				@NotNull @PathParam("message-board-thread-id") Long
					messageBoardThreadId,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/message-board-threads/{message-board-thread-id}/message-board-messages"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage postMessageBoardThreadMessageBoardMessage(
			@NotNull @PathParam("message-board-thread-id") Long
				messageBoardThreadId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(MessageBoardMessage messageBoardMessage) {
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