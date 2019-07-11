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

import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.resource.v1_0.WikiPageResource;
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

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseWikiPageResourceImpl implements WikiPageResource {

	@Override
	@GET
	@Operation(
		description = "Retrieves the wiki page's of a node. Results can be paginated, filtered, searched, and sorted."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "wikiNodeId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/wiki-nodes/{wikiNodeId}/wiki-pages/")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public Page<WikiPage> getWikiNodeWikiPagesPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiNodeId") Long
				wikiNodeId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Parameter(hidden = true) @QueryParam("filter") Filter filter,
			@BeanParam Pagination pagination,
			@Parameter(hidden = true) @QueryParam("sort") List<Sort> sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new wiki page")
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiNodeId")}
	)
	@Path("/wiki-nodes/{wikiNodeId}/wiki-pages/")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage postWikiNodeWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiNodeId") Long
				wikiNodeId,
			WikiPage wikiPage)
		throws Exception {

		return new WikiPage();
	}

	@Override
	@DELETE
	@Operation(
		description = "Deletes the wiki page and returns a 204 if the operation succeeds."
	)
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WikiPage")})
	public void deleteWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId)
		throws Exception {
	}

	@Override
	@GET
	@Operation(description = "Retrieves the wiki page")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage getWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId)
		throws Exception {

		return new WikiPage();
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the wiki page with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "wikiPageId")}
	)
	@Path("/wiki-pages/{wikiPageId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WikiPage")})
	public WikiPage putWikiPage(
			@NotNull @Parameter(hidden = true) @PathParam("wikiPageId") Long
				wikiPageId,
			WikiPage wikiPage)
		throws Exception {

		return new WikiPage();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	public void setContextUser(User contextUser) {
		this.contextUser = contextUser;
	}

	protected void preparePatch(WikiPage wikiPage, WikiPage existingWikiPage) {
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