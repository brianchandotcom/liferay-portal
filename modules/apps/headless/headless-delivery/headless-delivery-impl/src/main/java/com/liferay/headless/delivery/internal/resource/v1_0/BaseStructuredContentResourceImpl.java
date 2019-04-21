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

import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
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
public abstract class BaseStructuredContentResourceImpl
	implements StructuredContentResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "contentStructureId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/content-structures/{contentStructureId}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("contentStructureId")
				Long contentStructureId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
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
	@Path("/sites/{siteId}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getSiteStructuredContentsPage(
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
	@Path("/sites/{siteId}/structured-contents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent postSiteStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "key")
		}
	)
	@Path("/sites/{siteId}/structured-contents/by-key/{key}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getSiteStructuredContentByKey(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @PathParam("key") String key)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "uuid")
		}
	)
	@Path("/sites/{siteId}/structured-contents/by-uuid/{uuid}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getSiteStructuredContentByUuid(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @PathParam("uuid") String uuid)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "structuredContentFolderId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/structured-content-folders/{structuredContentFolderId}/structured-contents"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("structuredContentFolderId") Long
					structuredContentFolderId,
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
				in = ParameterIn.PATH, name = "structuredContentFolderId"
			)
		}
	)
	@Path(
		"/structured-content-folders/{structuredContentFolderId}/structured-contents"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent postStructuredContentFolderStructuredContent(
			@NotNull @Parameter(hidden = true)
			@PathParam("structuredContentFolderId") Long
				structuredContentFolderId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void deleteStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent patchStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			StructuredContent structuredContent)
		throws Exception {

		StructuredContent existingStructuredContent = getStructuredContent(
			structuredContentId);

		if (structuredContent.getAvailableLanguages() != null) {
			existingStructuredContent.setAvailableLanguages(
				structuredContent.getAvailableLanguages());
		}

		if (structuredContent.getContentStructureId() != null) {
			existingStructuredContent.setContentStructureId(
				structuredContent.getContentStructureId());
		}

		if (structuredContent.getDateCreated() != null) {
			existingStructuredContent.setDateCreated(
				structuredContent.getDateCreated());
		}

		if (structuredContent.getDateModified() != null) {
			existingStructuredContent.setDateModified(
				structuredContent.getDateModified());
		}

		if (structuredContent.getDatePublished() != null) {
			existingStructuredContent.setDatePublished(
				structuredContent.getDatePublished());
		}

		if (structuredContent.getDescription() != null) {
			existingStructuredContent.setDescription(
				structuredContent.getDescription());
		}

		if (structuredContent.getFriendlyUrlPath() != null) {
			existingStructuredContent.setFriendlyUrlPath(
				structuredContent.getFriendlyUrlPath());
		}

		if (structuredContent.getKey() != null) {
			existingStructuredContent.setKey(structuredContent.getKey());
		}

		if (structuredContent.getKeywords() != null) {
			existingStructuredContent.setKeywords(
				structuredContent.getKeywords());
		}

		if (structuredContent.getNumberOfComments() != null) {
			existingStructuredContent.setNumberOfComments(
				structuredContent.getNumberOfComments());
		}

		if (structuredContent.getSiteId() != null) {
			existingStructuredContent.setSiteId(structuredContent.getSiteId());
		}

		if (structuredContent.getTaxonomyCategoryIds() != null) {
			existingStructuredContent.setTaxonomyCategoryIds(
				structuredContent.getTaxonomyCategoryIds());
		}

		if (structuredContent.getTitle() != null) {
			existingStructuredContent.setTitle(structuredContent.getTitle());
		}

		if (structuredContent.getUuid() != null) {
			existingStructuredContent.setUuid(structuredContent.getUuid());
		}

		if (structuredContent.getViewableBy() != null) {
			existingStructuredContent.setViewableBy(
				structuredContent.getViewableBy());
		}

		preparePatch(structuredContent, existingStructuredContent);

		return putStructuredContent(
			structuredContentId, existingStructuredContent);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent putStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void deleteStructuredContentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating getStructuredContentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {

		return new Rating();
	}

	@Override
	@Consumes("application/json")
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating postStructuredContentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating putStructuredContentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId"),
			@Parameter(in = ParameterIn.PATH, name = "templateId")
		}
	)
	@Path(
		"/structured-contents/{structuredContentId}/rendered-content/{templateId}"
	)
	@Produces("text/html")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public String getStructuredContentRenderedContentTemplate(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			@NotNull @Parameter(hidden = true) @PathParam("templateId") Long
				templateId)
		throws Exception {

		return StringPool.BLANK;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		StructuredContent structuredContent,
		StructuredContent existingStructuredContent) {
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