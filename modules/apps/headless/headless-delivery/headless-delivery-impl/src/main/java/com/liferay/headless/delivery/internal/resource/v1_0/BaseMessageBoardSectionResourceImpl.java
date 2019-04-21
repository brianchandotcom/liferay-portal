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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardSectionResource;
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
public abstract class BaseMessageBoardSectionResourceImpl
	implements MessageBoardSectionResource {

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public void deleteMessageBoardSection(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection getMessageBoardSection(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {

		return new MessageBoardSection();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection patchMessageBoardSection(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		MessageBoardSection existingMessageBoardSection =
			getMessageBoardSection(messageBoardSectionId);

		if (messageBoardSection.getDateCreated() != null) {
			existingMessageBoardSection.setDateCreated(
				messageBoardSection.getDateCreated());
		}

		if (messageBoardSection.getDateModified() != null) {
			existingMessageBoardSection.setDateModified(
				messageBoardSection.getDateModified());
		}

		if (messageBoardSection.getDescription() != null) {
			existingMessageBoardSection.setDescription(
				messageBoardSection.getDescription());
		}

		if (messageBoardSection.getNumberOfMessageBoardSections() != null) {
			existingMessageBoardSection.setNumberOfMessageBoardSections(
				messageBoardSection.getNumberOfMessageBoardSections());
		}

		if (messageBoardSection.getNumberOfMessageBoardThreads() != null) {
			existingMessageBoardSection.setNumberOfMessageBoardThreads(
				messageBoardSection.getNumberOfMessageBoardThreads());
		}

		if (messageBoardSection.getSiteId() != null) {
			existingMessageBoardSection.setSiteId(
				messageBoardSection.getSiteId());
		}

		if (messageBoardSection.getTitle() != null) {
			existingMessageBoardSection.setTitle(
				messageBoardSection.getTitle());
		}

		if (messageBoardSection.getViewableBy() != null) {
			existingMessageBoardSection.setViewableBy(
				messageBoardSection.getViewableBy());
		}

		preparePatch(messageBoardSection, existingMessageBoardSection);

		return putMessageBoardSection(
			messageBoardSectionId, existingMessageBoardSection);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection putMessageBoardSection(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentMessageBoardSectionId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/message-board-sections/{parentMessageBoardSectionId}/message-board-sections"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public Page<MessageBoardSection>
			getMessageBoardSectionMessageBoardSectionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("parentMessageBoardSectionId") Long
					parentMessageBoardSectionId,
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
				in = ParameterIn.PATH, name = "parentMessageBoardSectionId"
			)
		}
	)
	@Path(
		"/message-board-sections/{parentMessageBoardSectionId}/message-board-sections"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection postMessageBoardSectionMessageBoardSection(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentMessageBoardSectionId") Long
				parentMessageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
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
	@Path("/sites/{siteId}/message-board-sections")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public Page<MessageBoardSection> getSiteMessageBoardSectionsPage(
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
	@Path("/sites/{siteId}/message-board-sections")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection postSiteMessageBoardSection(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		MessageBoardSection messageBoardSection,
		MessageBoardSection existingMessageBoardSection) {
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