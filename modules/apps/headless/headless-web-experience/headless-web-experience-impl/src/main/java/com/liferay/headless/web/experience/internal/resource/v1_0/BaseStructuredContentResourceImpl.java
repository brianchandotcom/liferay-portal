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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseStructuredContentResourceImpl
	implements StructuredContentResource {

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
	@Path("/content-spaces/{content-space-id}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent postContentSpaceStructuredContent(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
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
	@Path("/content-structures/{content-structure-id}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			@NotNull @PathParam("content-structure-id") Long contentStructureId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Path("/content-spaces/{content-space-id}/structured-contents/by-key/{key}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getContentSpaceStructuredContentByKey(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@NotNull @PathParam("key") String key)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@GET
	@Path(
		"/content-spaces/{content-space-id}/structured-contents/by-uuid/{uuid}"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getContentSpaceStructuredContentByUuid(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@NotNull @PathParam("uuid") String uuid)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@DELETE
	@Path("/structured-contents/{structured-content-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void deleteStructuredContent(
			@NotNull @PathParam("structured-content-id") Long
				structuredContentId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/structured-contents/{structured-content-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getStructuredContent(
			@NotNull @PathParam("structured-content-id") Long
				structuredContentId)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/structured-contents/{structured-content-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent patchStructuredContent(
			@NotNull @PathParam("structured-content-id") Long
				structuredContentId,
			StructuredContent structuredContent)
		throws Exception {

		preparePatch(structuredContent);

		StructuredContent existingStructuredContent = getStructuredContent(
			structuredContentId);

		if (Validator.isNotNull(structuredContent.getAvailableLanguages())) {
			existingStructuredContent.setAvailableLanguages(
				structuredContent.getAvailableLanguages());
		}

		if (Validator.isNotNull(structuredContent.getContentSpaceId())) {
			existingStructuredContent.setContentSpaceId(
				structuredContent.getContentSpaceId());
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

		if (Validator.isNotNull(structuredContent.getKey())) {
			existingStructuredContent.setKey(structuredContent.getKey());
		}

		if (Validator.isNotNull(structuredContent.getKeywords())) {
			existingStructuredContent.setKeywords(
				structuredContent.getKeywords());
		}

		if (Validator.isNotNull(structuredContent.getLastReviewed())) {
			existingStructuredContent.setLastReviewed(
				structuredContent.getLastReviewed());
		}

		if (Validator.isNotNull(structuredContent.getNumberOfComments())) {
			existingStructuredContent.setNumberOfComments(
				structuredContent.getNumberOfComments());
		}

		if (Validator.isNotNull(structuredContent.getTaxonomyCategoryIds())) {
			existingStructuredContent.setTaxonomyCategoryIds(
				structuredContent.getTaxonomyCategoryIds());
		}

		if (Validator.isNotNull(structuredContent.getTitle())) {
			existingStructuredContent.setTitle(structuredContent.getTitle());
		}

		if (Validator.isNotNull(structuredContent.getUuid())) {
			existingStructuredContent.setUuid(structuredContent.getUuid());
		}

		if (Validator.isNotNull(structuredContent.getViewableBy())) {
			existingStructuredContent.setViewableBy(
				structuredContent.getViewableBy());
		}

		return putStructuredContent(
			structuredContentId, existingStructuredContent);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/structured-contents/{structured-content-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent putStructuredContent(
			@NotNull @PathParam("structured-content-id") Long
				structuredContentId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@GET
	@Path(
		"/structured-contents/{structured-content-id}/rendered-content/{template-id}"
	)
	@Produces("text/html")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public String getStructuredContentRenderedContentTemplate(
			@NotNull @PathParam("structured-content-id") Long
				structuredContentId,
			@NotNull @PathParam("template-id") Long templateId)
		throws Exception {

		return StringPool.BLANK;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(StructuredContent structuredContent) {
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