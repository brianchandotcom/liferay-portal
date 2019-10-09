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

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
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
public abstract class BaseKnowledgeBaseArticleResourceImpl
	implements KnowledgeBaseArticleResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Operation(
		description = "Deletes the knowledge base article and returns a 204 if the operation succeeds."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void deleteKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(description = "Retrieves the knowledge base article.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@PATCH
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		KnowledgeBaseArticle existingKnowledgeBaseArticle =
			getKnowledgeBaseArticle(knowledgeBaseArticleId);

		if (knowledgeBaseArticle.getArticleBody() != null) {
			existingKnowledgeBaseArticle.setArticleBody(
				knowledgeBaseArticle.getArticleBody());
		}

		if (knowledgeBaseArticle.getDateCreated() != null) {
			existingKnowledgeBaseArticle.setDateCreated(
				knowledgeBaseArticle.getDateCreated());
		}

		if (knowledgeBaseArticle.getDateModified() != null) {
			existingKnowledgeBaseArticle.setDateModified(
				knowledgeBaseArticle.getDateModified());
		}

		if (knowledgeBaseArticle.getDescription() != null) {
			existingKnowledgeBaseArticle.setDescription(
				knowledgeBaseArticle.getDescription());
		}

		if (knowledgeBaseArticle.getEncodingFormat() != null) {
			existingKnowledgeBaseArticle.setEncodingFormat(
				knowledgeBaseArticle.getEncodingFormat());
		}

		if (knowledgeBaseArticle.getFriendlyUrlPath() != null) {
			existingKnowledgeBaseArticle.setFriendlyUrlPath(
				knowledgeBaseArticle.getFriendlyUrlPath());
		}

		if (knowledgeBaseArticle.getKeywords() != null) {
			existingKnowledgeBaseArticle.setKeywords(
				knowledgeBaseArticle.getKeywords());
		}

		if (knowledgeBaseArticle.getNumberOfAttachments() != null) {
			existingKnowledgeBaseArticle.setNumberOfAttachments(
				knowledgeBaseArticle.getNumberOfAttachments());
		}

		if (knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles() != null) {
			existingKnowledgeBaseArticle.setNumberOfKnowledgeBaseArticles(
				knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles());
		}

		if (knowledgeBaseArticle.getParentKnowledgeBaseFolderId() != null) {
			existingKnowledgeBaseArticle.setParentKnowledgeBaseFolderId(
				knowledgeBaseArticle.getParentKnowledgeBaseFolderId());
		}

		if (knowledgeBaseArticle.getSiteId() != null) {
			existingKnowledgeBaseArticle.setSiteId(
				knowledgeBaseArticle.getSiteId());
		}

		if (knowledgeBaseArticle.getSubscribed() != null) {
			existingKnowledgeBaseArticle.setSubscribed(
				knowledgeBaseArticle.getSubscribed());
		}

		if (knowledgeBaseArticle.getTaxonomyCategoryIds() != null) {
			existingKnowledgeBaseArticle.setTaxonomyCategoryIds(
				knowledgeBaseArticle.getTaxonomyCategoryIds());
		}

		if (knowledgeBaseArticle.getTitle() != null) {
			existingKnowledgeBaseArticle.setTitle(
				knowledgeBaseArticle.getTitle());
		}

		if (knowledgeBaseArticle.getViewableBy() != null) {
			existingKnowledgeBaseArticle.setViewableBy(
				knowledgeBaseArticle.getViewableBy());
		}

		preparePatch(knowledgeBaseArticle, existingKnowledgeBaseArticle);

		return putKnowledgeBaseArticle(
			knowledgeBaseArticleId, existingKnowledgeBaseArticle);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the knowledge base article with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Operation(
		description = "Deletes the knowledge base article's rating and returns a 204 if the operation succeeds."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void deleteKnowledgeBaseArticleMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(description = "Retrieves the knowledge base article's rating.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Rating getKnowledgeBaseArticleMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a rating for the knowledge base article.")
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Rating postKnowledgeBaseArticleMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Rating putKnowledgeBaseArticleMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/subscribe")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void putKnowledgeBaseArticleSubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/unsubscribe")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void putKnowledgeBaseArticleUnsubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId") Long knowledgeBaseArticleId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the parent knowledge base article's child knowledge base articles. Results can be paginated, filtered, searched, and sorted."
	)
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentKnowledgeBaseArticleId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("parentKnowledgeBaseArticleId") Long
					parentKnowledgeBaseArticleId,
				@Parameter(hidden = true) @QueryParam("search") String search,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a child knowledge base article of the knowledge base article identified by `parentKnowledgeBaseArticleId`."
	)
	@POST
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentKnowledgeBaseArticleId"
			)
		}
	)
	@Path(
		"/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentKnowledgeBaseArticleId") Long
				parentKnowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the folder's knowledge base articles. Results can be paginated, filtered, searched, flattened, and sorted."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
				@Parameter(hidden = true) @QueryParam("flatten") Boolean
					flatten,
				@Parameter(hidden = true) @QueryParam("search") String search,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a new knowledge base article in the folder."
	)
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path(
		"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId") Long knowledgeBaseFolderId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the Site's knowledge base articles. Results can be paginated, filtered, searched, flattened, and sorted."
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
	@Path("/sites/{siteId}/knowledge-base-articles")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle> getSiteKnowledgeBaseArticlesPage(
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new knowledge base article.")
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-articles")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postSiteKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-articles/subscribe")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void putSiteKnowledgeBaseArticleSubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@PUT
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-articles/unsubscribe")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void putSiteKnowledgeBaseArticleUnsubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId)
		throws Exception {
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
		KnowledgeBaseArticle knowledgeBaseArticle,
		KnowledgeBaseArticle existingKnowledgeBaseArticle) {
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