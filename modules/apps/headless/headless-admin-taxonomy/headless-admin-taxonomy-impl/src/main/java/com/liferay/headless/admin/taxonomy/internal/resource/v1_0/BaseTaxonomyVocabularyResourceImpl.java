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

package com.liferay.headless.admin.taxonomy.internal.resource.v1_0;

import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
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
public abstract class BaseTaxonomyVocabularyResourceImpl
	implements TaxonomyVocabularyResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/taxonomy-vocabularies")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyVocabulary")})
	public Page<TaxonomyVocabulary> getSiteTaxonomyVocabulariesPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
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
	@Path("/sites/{siteId}/taxonomy-vocabularies")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyVocabulary")})
	public TaxonomyVocabulary postSiteTaxonomyVocabulary(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return new TaxonomyVocabulary();
	}

	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyVocabulary")})
	public void deleteTaxonomyVocabulary(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId") Long taxonomyVocabularyId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyVocabulary")})
	public TaxonomyVocabulary getTaxonomyVocabulary(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId") Long taxonomyVocabularyId)
		throws Exception {

		return new TaxonomyVocabulary();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyVocabulary")})
	public TaxonomyVocabulary patchTaxonomyVocabulary(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId") Long taxonomyVocabularyId,
			TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		TaxonomyVocabulary existingTaxonomyVocabulary = getTaxonomyVocabulary(
			taxonomyVocabularyId);

		if (taxonomyVocabulary.getAvailableLanguages() != null) {
			existingTaxonomyVocabulary.setAvailableLanguages(
				taxonomyVocabulary.getAvailableLanguages());
		}

		if (taxonomyVocabulary.getDateCreated() != null) {
			existingTaxonomyVocabulary.setDateCreated(
				taxonomyVocabulary.getDateCreated());
		}

		if (taxonomyVocabulary.getDateModified() != null) {
			existingTaxonomyVocabulary.setDateModified(
				taxonomyVocabulary.getDateModified());
		}

		if (taxonomyVocabulary.getDescription() != null) {
			existingTaxonomyVocabulary.setDescription(
				taxonomyVocabulary.getDescription());
		}

		if (taxonomyVocabulary.getName() != null) {
			existingTaxonomyVocabulary.setName(taxonomyVocabulary.getName());
		}

		if (taxonomyVocabulary.getNumberOfTaxonomyCategories() != null) {
			existingTaxonomyVocabulary.setNumberOfTaxonomyCategories(
				taxonomyVocabulary.getNumberOfTaxonomyCategories());
		}

		if (taxonomyVocabulary.getSiteId() != null) {
			existingTaxonomyVocabulary.setSiteId(
				taxonomyVocabulary.getSiteId());
		}

		if (taxonomyVocabulary.getViewableBy() != null) {
			existingTaxonomyVocabulary.setViewableBy(
				taxonomyVocabulary.getViewableBy());
		}

		preparePatch(taxonomyVocabulary, existingTaxonomyVocabulary);

		return putTaxonomyVocabulary(
			taxonomyVocabularyId, existingTaxonomyVocabulary);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyVocabulary")})
	public TaxonomyVocabulary putTaxonomyVocabulary(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId") Long taxonomyVocabularyId,
			TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return new TaxonomyVocabulary();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		TaxonomyVocabulary taxonomyVocabulary,
		TaxonomyVocabulary existingTaxonomyVocabulary) {
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