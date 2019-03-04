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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
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

import java.net.URI;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

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
public abstract class BaseStructuredContentResourceImpl
	implements StructuredContentResource {

	@DELETE
	@Override
	@Path("/structured-contents/{structured-content-id}")
	@Produces("application/json")
	public boolean deleteStructuredContent(
			@PathParam("structured-content-id") Long structuredContentId)
		throws Exception {

		return false;
	}

	@GET
	@Override
	@Path("/content-spaces/{content-space-id}/structured-contents")
	@Produces("application/json")
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			@PathParam("content-space-id") Long contentSpaceId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@GET
	@Override
	@Path("/content-structures/{content-structure-id}/structured-contents")
	@Produces("application/json")
	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			@PathParam("content-structure-id") Long contentStructureId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@GET
	@Override
	@Path("/structured-contents/{structured-content-id}")
	@Produces("application/json")
	public StructuredContent getStructuredContent(
			@PathParam("structured-content-id") Long structuredContentId)
		throws Exception {

		return new StructuredContent();
	}

	@GET
	@Override
	@Path(
		"/structured-contents/{structured-content-id}/rendered-content/{template-id}"
	)
	@Produces("text/html")
	public String getStructuredContentTemplate(
			@PathParam("structured-content-id") Long structuredContentId,
			@PathParam("template-id") Long templateId)
		throws Exception {

		return StringPool.BLANK;
	}

	@Consumes("application/json")
	@Override
	@PATCH
	@Path("/structured-contents/{structured-content-id}")
	@Produces("application/json")
	public StructuredContent patchStructuredContent(
			@PathParam("structured-content-id") Long structuredContentId,
			StructuredContent structuredContent)
		throws Exception {

		preparePatch(structuredContent);

		StructuredContent existingStructuredContent = getStructuredContent(
			structuredContentId);

		if (Validator.isNotNull(structuredContent.getAvailableLanguages())) {
			existingStructuredContent.setAvailableLanguages(
				structuredContent.getAvailableLanguages());
		}

		if (Validator.isNotNull(structuredContent.getCategoryIds())) {
			existingStructuredContent.setCategoryIds(
				structuredContent.getCategoryIds());
		}

		if (Validator.isNotNull(structuredContent.getContentSpace())) {
			existingStructuredContent.setContentSpace(
				structuredContent.getContentSpace());
		}

		if (Validator.isNotNull(structuredContent.getContentStructureId())) {
			existingStructuredContent.setContentStructureId(
				structuredContent.getContentStructureId());
		}

		if (Validator.isNotNull(structuredContent.getDateCreated())) {
			existingStructuredContent.setDateCreated(
				structuredContent.getDateCreated());
		}

		if (Validator.isNotNull(structuredContent.getDateModified())) {
			existingStructuredContent.setDateModified(
				structuredContent.getDateModified());
		}

		if (Validator.isNotNull(structuredContent.getDatePublished())) {
			existingStructuredContent.setDatePublished(
				structuredContent.getDatePublished());
		}

		if (Validator.isNotNull(structuredContent.getDescription())) {
			existingStructuredContent.setDescription(
				structuredContent.getDescription());
		}

		if (Validator.isNotNull(structuredContent.getHasComments())) {
			existingStructuredContent.setHasComments(
				structuredContent.getHasComments());
		}

		if (Validator.isNotNull(structuredContent.getKeywords())) {
			existingStructuredContent.setKeywords(
				structuredContent.getKeywords());
		}

		if (Validator.isNotNull(structuredContent.getLastReviewed())) {
			existingStructuredContent.setLastReviewed(
				structuredContent.getLastReviewed());
		}

		if (Validator.isNotNull(structuredContent.getTitle())) {
			existingStructuredContent.setTitle(structuredContent.getTitle());
		}

		if (Validator.isNotNull(structuredContent.getViewableBy())) {
			existingStructuredContent.setViewableBy(
				structuredContent.getViewableBy());
		}

		return putStructuredContent(
			structuredContentId, existingStructuredContent);
	}

	@Consumes("application/json")
	@Override
	@Path("/content-spaces/{content-space-id}/structured-contents")
	@POST
	@Produces("application/json")
	public StructuredContent postContentSpaceStructuredContent(
			@PathParam("content-space-id") Long contentSpaceId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Consumes("application/json")
	@Override
	@Path("/structured-contents/{structured-content-id}")
	@Produces("application/json")
	@PUT
	public StructuredContent putStructuredContent(
			@PathParam("structured-content-id") Long structuredContentId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
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
			BaseStructuredContentResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseStructuredContentResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(StructuredContent structuredContent) {
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