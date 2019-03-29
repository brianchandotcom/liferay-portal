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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.foundation.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.petra.function.UnsafeFunction;
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
public abstract class BaseTaxonomyCategoryResourceImpl
	implements TaxonomyCategoryResource {

	@Override
	@DELETE
	@Path("/taxonomy-categories/{taxonomy-category-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public void deleteTaxonomyCategory(
			@NotNull @PathParam("taxonomy-category-id") Long taxonomyCategoryId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/taxonomy-categories/{taxonomy-category-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory getTaxonomyCategory(
			@NotNull @PathParam("taxonomy-category-id") Long taxonomyCategoryId)
		throws Exception {

		return new TaxonomyCategory();
	}

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/taxonomy-categories/{taxonomy-category-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory patchTaxonomyCategory(
			@NotNull @PathParam("taxonomy-category-id") Long taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		preparePatch(taxonomyCategory);

		TaxonomyCategory existingTaxonomyCategory = getTaxonomyCategory(
			taxonomyCategoryId);

		if (Validator.isNotNull(taxonomyCategory.getAvailableLanguages())) {
			existingTaxonomyCategory.setAvailableLanguages(
				taxonomyCategory.getAvailableLanguages());
		}

		if (Validator.isNotNull(taxonomyCategory.getDateCreated())) {
			existingTaxonomyCategory.setDateCreated(
				taxonomyCategory.getDateCreated());
		}

		if (Validator.isNotNull(taxonomyCategory.getDateModified())) {
			existingTaxonomyCategory.setDateModified(
				taxonomyCategory.getDateModified());
		}

		if (Validator.isNotNull(taxonomyCategory.getDescription())) {
			existingTaxonomyCategory.setDescription(
				taxonomyCategory.getDescription());
		}

		if (Validator.isNotNull(taxonomyCategory.getName())) {
			existingTaxonomyCategory.setName(taxonomyCategory.getName());
		}

		if (Validator.isNotNull(
				taxonomyCategory.getNumberOfTaxonomyCategories())) {

			existingTaxonomyCategory.setNumberOfTaxonomyCategories(
				taxonomyCategory.getNumberOfTaxonomyCategories());
		}

		if (Validator.isNotNull(taxonomyCategory.getParentVocabularyId())) {
			existingTaxonomyCategory.setParentVocabularyId(
				taxonomyCategory.getParentVocabularyId());
		}

		if (Validator.isNotNull(taxonomyCategory.getViewableBy())) {
			existingTaxonomyCategory.setViewableBy(
				taxonomyCategory.getViewableBy());
		}

		return putTaxonomyCategory(
			taxonomyCategoryId, existingTaxonomyCategory);
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/taxonomy-categories/{taxonomy-category-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory putTaxonomyCategory(
			@NotNull @PathParam("taxonomy-category-id") Long taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
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
	@Path("/taxonomy-categories/{taxonomy-category-id}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyCategoryTaxonomyCategoriesPage(
			@NotNull @PathParam("taxonomy-category-id") Long taxonomyCategoryId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/taxonomy-categories/{taxonomy-category-id}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory postTaxonomyCategoryTaxonomyCategory(
			@NotNull @PathParam("taxonomy-category-id") Long taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
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
	@Path("/taxonomy-vocabularies/{taxonomy-vocabulary-id}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyVocabularyTaxonomyCategoriesPage(
			@NotNull @PathParam("taxonomy-vocabulary-id") Long
				taxonomyVocabularyId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/taxonomy-vocabularies/{taxonomy-vocabulary-id}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory postTaxonomyVocabularyTaxonomyCategory(
			@NotNull @PathParam("taxonomy-vocabulary-id") Long
				taxonomyVocabularyId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(TaxonomyCategory taxonomyCategory) {
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