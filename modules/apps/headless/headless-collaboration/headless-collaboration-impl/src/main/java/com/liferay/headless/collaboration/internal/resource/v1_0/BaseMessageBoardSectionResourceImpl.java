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

import com.liferay.headless.collaboration.dto.v1_0.MessageBoardSection;
import com.liferay.headless.collaboration.resource.v1_0.MessageBoardSectionResource;
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
public abstract class BaseMessageBoardSectionResourceImpl
	implements MessageBoardSectionResource {

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
	@Path("/content-spaces/{content-space-id}/message-board-sections")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public Page<MessageBoardSection> getContentSpaceMessageBoardSectionsPage(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@QueryParam("flatten") Boolean flatten, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/message-board-sections")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection postContentSpaceMessageBoardSection(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
	}

	@Override
	@DELETE
	@Path("/message-board-sections/{message-board-section-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public void deleteMessageBoardSection(
			@NotNull @PathParam("message-board-section-id") Long
				messageBoardSectionId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/message-board-sections/{message-board-section-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection getMessageBoardSection(
			@NotNull @PathParam("message-board-section-id") Long
				messageBoardSectionId)
		throws Exception {

		return new MessageBoardSection();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/message-board-sections/{message-board-section-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection patchMessageBoardSection(
			@NotNull @PathParam("message-board-section-id") Long
				messageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		preparePatch(messageBoardSection);

		MessageBoardSection existingMessageBoardSection =
			getMessageBoardSection(messageBoardSectionId);

		if (Validator.isNotNull(messageBoardSection.getContentSpaceId())) {
			existingMessageBoardSection.setContentSpaceId(
				messageBoardSection.getContentSpaceId());
		}

		if (Validator.isNotNull(messageBoardSection.getDateCreated())) {
			existingMessageBoardSection.setDateCreated(
				messageBoardSection.getDateCreated());
		}

		if (Validator.isNotNull(messageBoardSection.getDateModified())) {
			existingMessageBoardSection.setDateModified(
				messageBoardSection.getDateModified());
		}

		if (Validator.isNotNull(messageBoardSection.getDescription())) {
			existingMessageBoardSection.setDescription(
				messageBoardSection.getDescription());
		}

		if (Validator.isNotNull(
				messageBoardSection.getNumberOfMessageBoardSections())) {

			existingMessageBoardSection.setNumberOfMessageBoardSections(
				messageBoardSection.getNumberOfMessageBoardSections());
		}

		if (Validator.isNotNull(
				messageBoardSection.getNumberOfMessageBoardThreads())) {

			existingMessageBoardSection.setNumberOfMessageBoardThreads(
				messageBoardSection.getNumberOfMessageBoardThreads());
		}

		if (Validator.isNotNull(messageBoardSection.getTitle())) {
			existingMessageBoardSection.setTitle(
				messageBoardSection.getTitle());
		}

		if (Validator.isNotNull(messageBoardSection.getViewableBy())) {
			existingMessageBoardSection.setViewableBy(
				messageBoardSection.getViewableBy());
		}

		return putMessageBoardSection(
			messageBoardSectionId, existingMessageBoardSection);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/message-board-sections/{message-board-section-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection putMessageBoardSection(
			@NotNull @PathParam("message-board-section-id") Long
				messageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
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
		"/message-board-sections/{message-board-section-id}/message-board-sections"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public Page<MessageBoardSection>
			getMessageBoardSectionMessageBoardSectionsPage(
				@NotNull @PathParam("message-board-section-id") Long
					messageBoardSectionId,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/message-board-sections/{message-board-section-id}/message-board-sections"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection postMessageBoardSectionMessageBoardSection(
			@NotNull @PathParam("message-board-section-id") Long
				messageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(MessageBoardSection messageBoardSection) {
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