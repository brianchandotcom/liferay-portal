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

import com.liferay.headless.collaboration.dto.v1_0.DiscussionForumPosting;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionForumPostingResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
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

import java.net.URI;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseDiscussionForumPostingResourceImpl
	implements DiscussionForumPostingResource {

	@Override
	@DELETE
	@Path("/discussion-forum-postings/{discussion-forum-posting-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionForumPosting")})
	public void deleteDiscussionForumPosting(
			@NotNull @PathParam("discussion-forum-posting-id") Long
				discussionForumPostingId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/discussion-forum-postings/{discussion-forum-posting-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionForumPosting")})
	public DiscussionForumPosting getDiscussionForumPosting(
			@NotNull @PathParam("discussion-forum-posting-id") Long
				discussionForumPostingId)
		throws Exception {

		return new DiscussionForumPosting();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/discussion-forum-postings/{discussion-forum-posting-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionForumPosting")})
	public DiscussionForumPosting patchDiscussionForumPosting(
			@NotNull @PathParam("discussion-forum-posting-id") Long
				discussionForumPostingId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		preparePatch(discussionForumPosting);

		DiscussionForumPosting existingDiscussionForumPosting =
			getDiscussionForumPosting(discussionForumPostingId);

		if (Validator.isNotNull(discussionForumPosting.getArticleBody())) {
			existingDiscussionForumPosting.setArticleBody(
				discussionForumPosting.getArticleBody());
		}

		if (Validator.isNotNull(discussionForumPosting.getContentSpaceId())) {
			existingDiscussionForumPosting.setContentSpaceId(
				discussionForumPosting.getContentSpaceId());
		}

		if (Validator.isNotNull(discussionForumPosting.getDateCreated())) {
			existingDiscussionForumPosting.setDateCreated(
				discussionForumPosting.getDateCreated());
		}

		if (Validator.isNotNull(discussionForumPosting.getDateModified())) {
			existingDiscussionForumPosting.setDateModified(
				discussionForumPosting.getDateModified());
		}

		if (Validator.isNotNull(discussionForumPosting.getHeadline())) {
			existingDiscussionForumPosting.setHeadline(
				discussionForumPosting.getHeadline());
		}

		if (Validator.isNotNull(discussionForumPosting.getKeywords())) {
			existingDiscussionForumPosting.setKeywords(
				discussionForumPosting.getKeywords());
		}

		if (Validator.isNotNull(
				discussionForumPosting.getNumberOfDiscussionAttachments())) {

			existingDiscussionForumPosting.setNumberOfDiscussionAttachments(
				discussionForumPosting.getNumberOfDiscussionAttachments());
		}

		if (Validator.isNotNull(
				discussionForumPosting.getNumberOfDiscussionForumPostings())) {

			existingDiscussionForumPosting.setNumberOfDiscussionForumPostings(
				discussionForumPosting.getNumberOfDiscussionForumPostings());
		}

		if (Validator.isNotNull(discussionForumPosting.getShowAsAnswer())) {
			existingDiscussionForumPosting.setShowAsAnswer(
				discussionForumPosting.getShowAsAnswer());
		}

		if (Validator.isNotNull(
				discussionForumPosting.getTaxonomyCategoryIds())) {

			existingDiscussionForumPosting.setTaxonomyCategoryIds(
				discussionForumPosting.getTaxonomyCategoryIds());
		}

		if (Validator.isNotNull(discussionForumPosting.getViewableBy())) {
			existingDiscussionForumPosting.setViewableBy(
				discussionForumPosting.getViewableBy());
		}

		return putDiscussionForumPosting(
			discussionForumPostingId, existingDiscussionForumPosting);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/discussion-forum-postings/{discussion-forum-posting-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionForumPosting")})
	public DiscussionForumPosting putDiscussionForumPosting(
			@NotNull @PathParam("discussion-forum-posting-id") Long
				discussionForumPostingId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		return new DiscussionForumPosting();
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
		"/discussion-forum-postings/{discussion-forum-posting-id}/discussion-forum-postings"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionForumPosting")})
	public Page<DiscussionForumPosting>
			getDiscussionForumPostingDiscussionForumPostingsPage(
				@NotNull @PathParam("discussion-forum-posting-id") Long
					discussionForumPostingId,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/discussion-forum-postings/{discussion-forum-posting-id}/discussion-forum-postings"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionForumPosting")})
	public DiscussionForumPosting
			postDiscussionForumPostingDiscussionForumPosting(
				@NotNull @PathParam("discussion-forum-posting-id") Long
					discussionForumPostingId,
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		return new DiscussionForumPosting();
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
		"/discussion-threads/{discussion-thread-id}/discussion-forum-postings"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionForumPosting")})
	public Page<DiscussionForumPosting>
			getDiscussionThreadDiscussionForumPostingsPage(
				@NotNull @PathParam("discussion-thread-id") Long
					discussionThreadId,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path(
		"/discussion-threads/{discussion-thread-id}/discussion-forum-postings"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DiscussionForumPosting")})
	public DiscussionForumPosting postDiscussionThreadDiscussionForumPosting(
			@NotNull @PathParam("discussion-thread-id") Long discussionThreadId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		return new DiscussionForumPosting();
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
			BaseDiscussionForumPostingResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseDiscussionForumPostingResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(DiscussionForumPosting discussionForumPosting) {
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