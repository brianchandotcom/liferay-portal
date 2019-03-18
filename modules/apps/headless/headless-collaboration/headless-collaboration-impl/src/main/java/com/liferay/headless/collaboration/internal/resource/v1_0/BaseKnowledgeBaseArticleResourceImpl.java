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

import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
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

import java.net.URI;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
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
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/content-spaces/{content-space-id}/knowledge-base-articles")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle> getContentSpaceKnowledgeBaseArticlesPage(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/knowledge-base-articles")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postContentSpaceKnowledgeBaseArticle(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	@Override
	@DELETE
	@Path("/knowledge-base-articles/{knowledge-base-article-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public boolean deleteKnowledgeBaseArticle(
			@NotNull @PathParam("knowledge-base-article-id") Long
				knowledgeBaseArticleId)
		throws Exception {

		return false;
	}

	@Override
	@GET
	@Path("/knowledge-base-articles/{knowledge-base-article-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			@NotNull @PathParam("knowledge-base-article-id") Long
				knowledgeBaseArticleId)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/knowledge-base-articles/{knowledge-base-article-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			@NotNull @PathParam("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		preparePatch(knowledgeBaseArticle);

		KnowledgeBaseArticle existingKnowledgeBaseArticle =
			getKnowledgeBaseArticle(knowledgeBaseArticleId);

		if (Validator.isNotNull(knowledgeBaseArticle.getArticleBody())) {
			existingKnowledgeBaseArticle.setArticleBody(
				knowledgeBaseArticle.getArticleBody());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getCategoryIds())) {
			existingKnowledgeBaseArticle.setCategoryIds(
				knowledgeBaseArticle.getCategoryIds());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getContentSpaceId())) {
			existingKnowledgeBaseArticle.setContentSpaceId(
				knowledgeBaseArticle.getContentSpaceId());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getDateCreated())) {
			existingKnowledgeBaseArticle.setDateCreated(
				knowledgeBaseArticle.getDateCreated());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getDateModified())) {
			existingKnowledgeBaseArticle.setDateModified(
				knowledgeBaseArticle.getDateModified());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getDescription())) {
			existingKnowledgeBaseArticle.setDescription(
				knowledgeBaseArticle.getDescription());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getFriendlyUrlPath())) {
			existingKnowledgeBaseArticle.setFriendlyUrlPath(
				knowledgeBaseArticle.getFriendlyUrlPath());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getHasAttachments())) {
			existingKnowledgeBaseArticle.setHasAttachments(
				knowledgeBaseArticle.getHasAttachments());
		}

		if (Validator.isNotNull(
				knowledgeBaseArticle.getHasKnowledgeBaseArticles())) {

			existingKnowledgeBaseArticle.setHasKnowledgeBaseArticles(
				knowledgeBaseArticle.getHasKnowledgeBaseArticles());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getKeywords())) {
			existingKnowledgeBaseArticle.setKeywords(
				knowledgeBaseArticle.getKeywords());
		}

		if (Validator.isNotNull(
				knowledgeBaseArticle.getParentKnowledgeBaseFolderId())) {

			existingKnowledgeBaseArticle.setParentKnowledgeBaseFolderId(
				knowledgeBaseArticle.getParentKnowledgeBaseFolderId());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getTitle())) {
			existingKnowledgeBaseArticle.setTitle(
				knowledgeBaseArticle.getTitle());
		}

		if (Validator.isNotNull(knowledgeBaseArticle.getViewableBy())) {
			existingKnowledgeBaseArticle.setViewableBy(
				knowledgeBaseArticle.getViewableBy());
		}

		return putKnowledgeBaseArticle(
			knowledgeBaseArticleId, existingKnowledgeBaseArticle);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/knowledge-base-articles/{knowledge-base-article-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			@NotNull @PathParam("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/knowledge-base-articles/{knowledge-base-article-id}/knowledge-base-articles"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				@NotNull @PathParam("knowledge-base-article-id") Long
					knowledgeBaseArticleId,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/knowledge-base-articles/{knowledge-base-article-id}/knowledge-base-articles"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			@NotNull @PathParam("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/knowledge-base-folders/{knowledge-base-folder-id}/knowledge-base-articles"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@NotNull @PathParam("knowledge-base-folder-id") Long
					knowledgeBaseFolderId,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/knowledge-base-folders/{knowledge-base-folder-id}/knowledge-base-articles"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			@NotNull @PathParam("knowledge-base-folder-id") Long
				knowledgeBaseFolderId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected String getJAXRSLink(String methodName, Object... values) {
		String baseURIString = String.valueOf(contextUriInfo.getBaseUri());

		if (baseURIString.endsWith(StringPool.FORWARD_SLASH)) {
			baseURIString = baseURIString.substring(
				0, baseURIString.length() - 1);
		}

		URI resourceURI = UriBuilder.fromResource(
			BaseKnowledgeBaseArticleResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseKnowledgeBaseArticleResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(KnowledgeBaseArticle knowledgeBaseArticle) {
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transformToArray(list, unsafeFunction, clazz);
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