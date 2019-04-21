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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardThreadResource;
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
public abstract class BaseMessageBoardThreadResourceImpl
	implements MessageBoardThreadResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/message-board-sections/{messageBoardSectionId}/message-board-threads"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<MessageBoardThread>
			getMessageBoardSectionMessageBoardThreadsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("messageBoardSectionId") Long messageBoardSectionId,
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
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path(
		"/message-board-sections/{messageBoardSectionId}/message-board-threads"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread postMessageBoardSectionMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
	}

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public void deleteMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread getMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		return new MessageBoardThread();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread patchMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		MessageBoardThread existingMessageBoardThread = getMessageBoardThread(
			messageBoardThreadId);

		if (messageBoardThread.getArticleBody() != null) {
			existingMessageBoardThread.setArticleBody(
				messageBoardThread.getArticleBody());
		}

		if (messageBoardThread.getDateCreated() != null) {
			existingMessageBoardThread.setDateCreated(
				messageBoardThread.getDateCreated());
		}

		if (messageBoardThread.getDateModified() != null) {
			existingMessageBoardThread.setDateModified(
				messageBoardThread.getDateModified());
		}

		if (messageBoardThread.getEncodingFormat() != null) {
			existingMessageBoardThread.setEncodingFormat(
				messageBoardThread.getEncodingFormat());
		}

		if (messageBoardThread.getHeadline() != null) {
			existingMessageBoardThread.setHeadline(
				messageBoardThread.getHeadline());
		}

		if (messageBoardThread.getKeywords() != null) {
			existingMessageBoardThread.setKeywords(
				messageBoardThread.getKeywords());
		}

		if (messageBoardThread.getNumberOfMessageBoardAttachments() != null) {
			existingMessageBoardThread.setNumberOfMessageBoardAttachments(
				messageBoardThread.getNumberOfMessageBoardAttachments());
		}

		if (messageBoardThread.getNumberOfMessageBoardMessages() != null) {
			existingMessageBoardThread.setNumberOfMessageBoardMessages(
				messageBoardThread.getNumberOfMessageBoardMessages());
		}

		if (messageBoardThread.getShowAsQuestion() != null) {
			existingMessageBoardThread.setShowAsQuestion(
				messageBoardThread.getShowAsQuestion());
		}

		if (messageBoardThread.getSiteId() != null) {
			existingMessageBoardThread.setSiteId(
				messageBoardThread.getSiteId());
		}

		if (messageBoardThread.getThreadType() != null) {
			existingMessageBoardThread.setThreadType(
				messageBoardThread.getThreadType());
		}

		if (messageBoardThread.getViewableBy() != null) {
			existingMessageBoardThread.setViewableBy(
				messageBoardThread.getViewableBy());
		}

		preparePatch(messageBoardThread, existingMessageBoardThread);

		return putMessageBoardThread(
			messageBoardThreadId, existingMessageBoardThread);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread putMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
	}

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public void deleteMessageBoardThreadMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Rating getMessageBoardThreadMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId)
		throws Exception {

		return new Rating();
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Rating postMessageBoardThreadMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Rating putMessageBoardThreadMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId") Long messageBoardThreadId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/message-board-threads")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<MessageBoardThread> getSiteMessageBoardThreadsPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
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
	@Path("/sites/{siteId}/message-board-threads")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread postSiteMessageBoardThread(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		MessageBoardThread messageBoardThread,
		MessageBoardThread existingMessageBoardThread) {
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