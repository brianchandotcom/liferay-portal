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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource;
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
public abstract class BaseMessageBoardMessageResourceImpl
	implements MessageBoardMessageResource {

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public void deleteMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage getMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		return new MessageBoardMessage();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage patchMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId") Long messageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		MessageBoardMessage existingMessageBoardMessage =
			getMessageBoardMessage(messageBoardMessageId);

		if (messageBoardMessage.getAnonymous() != null) {
			existingMessageBoardMessage.setAnonymous(
				messageBoardMessage.getAnonymous());
		}

		if (messageBoardMessage.getArticleBody() != null) {
			existingMessageBoardMessage.setArticleBody(
				messageBoardMessage.getArticleBody());
		}

		if (messageBoardMessage.getDateCreated() != null) {
			existingMessageBoardMessage.setDateCreated(
				messageBoardMessage.getDateCreated());
		}

		if (messageBoardMessage.getDateModified() != null) {
			existingMessageBoardMessage.setDateModified(
				messageBoardMessage.getDateModified());
		}

		if (messageBoardMessage.getEncodingFormat() != null) {
			existingMessageBoardMessage.setEncodingFormat(
				messageBoardMessage.getEncodingFormat());
		}

		if (messageBoardMessage.getHeadline() != null) {
			existingMessageBoardMessage.setHeadline(
				messageBoardMessage.getHeadline());
		}

		if (messageBoardMessage.getKeywords() != null) {
			existingMessageBoardMessage.setKeywords(
				messageBoardMessage.getKeywords());
		}

		if (messageBoardMessage.getNumberOfMessageBoardAttachments() != null) {
			existingMessageBoardMessage.setNumberOfMessageBoardAttachments(
				messageBoardMessage.getNumberOfMessageBoardAttachments());
		}

		if (messageBoardMessage.getNumberOfMessageBoardMessages() != null) {
			existingMessageBoardMessage.setNumberOfMessageBoardMessages(
				messageBoardMessage.getNumberOfMessageBoardMessages());
		}

		if (messageBoardMessage.getShowAsAnswer() != null) {
			existingMessageBoardMessage.setShowAsAnswer(
				messageBoardMessage.getShowAsAnswer());
		}

		if (messageBoardMessage.getSiteId() != null) {
			existingMessageBoardMessage.setSiteId(
				messageBoardMessage.getSiteId());
		}

		if (messageBoardMessage.getViewableBy() != null) {
			existingMessageBoardMessage.setViewableBy(
				messageBoardMessage.getViewableBy());
		}

		preparePatch(messageBoardMessage, existingMessageBoardMessage);

		return putMessageBoardMessage(
			messageBoardMessageId, existingMessageBoardMessage);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage putMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId") Long messageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
	}

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public void deleteMessageBoardMessageMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Rating getMessageBoardMessageMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId") Long messageBoardMessageId)
		throws Exception {

		return new Rating();
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Rating postMessageBoardMessageMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId") Long messageBoardMessageId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Rating putMessageBoardMessageMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId") Long messageBoardMessageId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentMessageBoardMessageId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/message-board-messages/{parentMessageBoardMessageId}/message-board-messages"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("parentMessageBoardMessageId") Long
					parentMessageBoardMessageId,
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
			@Parameter(
				in = ParameterIn.PATH, name = "parentMessageBoardMessageId"
			)
		}
	)
	@Path(
		"/message-board-messages/{parentMessageBoardMessageId}/message-board-messages"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentMessageBoardMessageId") Long
				parentMessageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/message-board-threads/{messageBoardThreadId}/message-board-messages"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("messageBoardThreadId") Long messageBoardThreadId,
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
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path(
		"/message-board-threads/{messageBoardThreadId}/message-board-messages"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage postMessageBoardThreadMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		MessageBoardMessage messageBoardMessage,
		MessageBoardMessage existingMessageBoardMessage) {
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