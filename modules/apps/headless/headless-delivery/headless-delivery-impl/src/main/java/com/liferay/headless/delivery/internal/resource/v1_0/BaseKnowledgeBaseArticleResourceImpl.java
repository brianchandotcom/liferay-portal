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
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
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
public abstract class BaseKnowledgeBaseArticleResourceImpl
	implements KnowledgeBaseArticleResource {

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
	@Path("/content-spaces/{contentSpaceId}/knowledge-base-articles")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle> getContentSpaceKnowledgeBaseArticlesPage(
			@NotNull @PathParam("contentSpaceId") Long contentSpaceId,
			@QueryParam("flatten") Boolean flatten,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{contentSpaceId}/knowledge-base-articles")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postContentSpaceKnowledgeBaseArticle(
			@NotNull @PathParam("contentSpaceId") Long contentSpaceId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	@Override
	@DELETE
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void deleteKnowledgeBaseArticle(
			@NotNull @PathParam("knowledgeBaseArticleId") Long
				knowledgeBaseArticleId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			@NotNull @PathParam("knowledgeBaseArticleId") Long
				knowledgeBaseArticleId)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			@NotNull @PathParam("knowledgeBaseArticleId") Long
				knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		KnowledgeBaseArticle existingKnowledgeBaseArticle =
			getKnowledgeBaseArticle(knowledgeBaseArticleId);

		if (knowledgeBaseArticle.getArticleBody() != null) {
			existingKnowledgeBaseArticle.setArticleBody(
				knowledgeBaseArticle.getArticleBody());
		}

		if (knowledgeBaseArticle.getContentSpaceId() != null) {
			existingKnowledgeBaseArticle.setContentSpaceId(
				knowledgeBaseArticle.getContentSpaceId());
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

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			@NotNull @PathParam("knowledgeBaseArticleId") Long
				knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
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
		"/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-articles"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				@NotNull @PathParam("knowledgeBaseArticleId") Long
					knowledgeBaseArticleId,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-articles"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			@NotNull @PathParam("knowledgeBaseArticleId") Long
				knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
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
		"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@NotNull @PathParam("knowledgeBaseFolderId") Long
					knowledgeBaseFolderId,
				@QueryParam("flatten") Boolean flatten,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			@NotNull @PathParam("knowledgeBaseFolderId") Long
				knowledgeBaseFolderId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		KnowledgeBaseArticle knowledgeBaseArticle,
		KnowledgeBaseArticle existingKnowledgeBaseArticle) {
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