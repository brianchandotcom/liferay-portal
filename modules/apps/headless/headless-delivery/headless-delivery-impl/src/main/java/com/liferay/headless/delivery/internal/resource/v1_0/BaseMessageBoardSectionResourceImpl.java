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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Operation(
		description = "Deletes the message board section and returns a 204 if the operation succeeds."
	)
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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(description = "Retrieves the message board section.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection getMessageBoardSection(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {

		return new MessageBoardSection();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}' -d $'{"customFields": ___, "description": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@PATCH
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}")
	@Produces({"application/json", "application/xml"})
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

		if (messageBoardSection.getSubscribed() != null) {
			existingMessageBoardSection.setSubscribed(
				messageBoardSection.getSubscribed());
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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}' -d $'{"customFields": ___, "description": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the message board section with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection putMessageBoardSection(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}/subscribe")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public void putMessageBoardSectionSubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path("/message-board-sections/{messageBoardSectionId}/unsubscribe")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public void putMessageBoardSectionUnsubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId") Long messageBoardSectionId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{parentMessageBoardSectionId}/message-board-sections'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the parent message board section's subsections. Results can be paginated, filtered, searched, and sorted."
	)
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
	@Produces({"application/json", "application/xml"})
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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{parentMessageBoardSectionId}/message-board-sections' -d $'{"customFields": ___, "description": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a new message board section in the parent section."
	)
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
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection postMessageBoardSectionMessageBoardSection(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentMessageBoardSectionId") Long
				parentMessageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-sections'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the Site's message board sections. Results can be paginated, filtered, searched, flattened, and sorted."
	)
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
	@Produces({"application/json", "application/xml"})
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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-sections' -d $'{"customFields": ___, "description": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new message board section.")
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/message-board-sections")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardSection")})
	public MessageBoardSection postSiteMessageBoardSection(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return new MessageBoardSection();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(User contextUser) {
		this.contextUser = contextUser;
	}

	protected void preparePatch(
		MessageBoardSection messageBoardSection,
		MessageBoardSection existingMessageBoardSection) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected UriInfo contextUriInfo;
	protected User contextUser;

}