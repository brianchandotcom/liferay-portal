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

import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
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
public abstract class BaseTaxonomyCategoryResourceImpl
	implements TaxonomyCategoryResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentTaxonomyCategoryId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyCategoryTaxonomyCategoriesPage(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentTaxonomyCategoryId") Long
				parentTaxonomyCategoryId,
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
			@Parameter(in = ParameterIn.PATH, name = "parentTaxonomyCategoryId")
		}
	)
	@Path("/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory postTaxonomyCategoryTaxonomyCategory(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentTaxonomyCategoryId") Long
				parentTaxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
	}

	@Override
	@DELETE
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public void deleteTaxonomyCategory(
			@NotNull @Parameter(hidden = true) @PathParam("taxonomyCategoryId")
				Long taxonomyCategoryId)
		throws Exception {
	}

	@Override
	@GET
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory getTaxonomyCategory(
			@NotNull @Parameter(hidden = true) @PathParam("taxonomyCategoryId")
				Long taxonomyCategoryId)
		throws Exception {

		return new TaxonomyCategory();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory patchTaxonomyCategory(
			@NotNull @Parameter(hidden = true) @PathParam("taxonomyCategoryId")
				Long taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		TaxonomyCategory existingTaxonomyCategory = getTaxonomyCategory(
			taxonomyCategoryId);

		if (taxonomyCategory.getAvailableLanguages() != null) {
			existingTaxonomyCategory.setAvailableLanguages(
				taxonomyCategory.getAvailableLanguages());
		}

		if (taxonomyCategory.getDateCreated() != null) {
			existingTaxonomyCategory.setDateCreated(
				taxonomyCategory.getDateCreated());
		}

		if (taxonomyCategory.getDateModified() != null) {
			existingTaxonomyCategory.setDateModified(
				taxonomyCategory.getDateModified());
		}

		if (taxonomyCategory.getDescription() != null) {
			existingTaxonomyCategory.setDescription(
				taxonomyCategory.getDescription());
		}

		if (taxonomyCategory.getName() != null) {
			existingTaxonomyCategory.setName(taxonomyCategory.getName());
		}

		if (taxonomyCategory.getNumberOfTaxonomyCategories() != null) {
			existingTaxonomyCategory.setNumberOfTaxonomyCategories(
				taxonomyCategory.getNumberOfTaxonomyCategories());
		}

		if (taxonomyCategory.getViewableBy() != null) {
			existingTaxonomyCategory.setViewableBy(
				taxonomyCategory.getViewableBy());
		}

		preparePatch(taxonomyCategory, existingTaxonomyCategory);

		return putTaxonomyCategory(
			taxonomyCategoryId, existingTaxonomyCategory);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory putTaxonomyCategory(
			@NotNull @Parameter(hidden = true) @PathParam("taxonomyCategoryId")
				Long taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyVocabularyTaxonomyCategoriesPage(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId") Long taxonomyVocabularyId,
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
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory postTaxonomyVocabularyTaxonomyCategory(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId") Long taxonomyVocabularyId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		TaxonomyCategory taxonomyCategory,
		TaxonomyCategory existingTaxonomyCategory) {
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