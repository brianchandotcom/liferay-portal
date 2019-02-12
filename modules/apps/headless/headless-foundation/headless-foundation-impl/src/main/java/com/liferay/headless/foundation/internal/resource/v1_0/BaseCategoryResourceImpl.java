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

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseCategoryResourceImpl implements CategoryResource {

	@Override
	public Response deleteCategories(Long categoryId) throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Category getCategories(Long categoryId) throws Exception {
		return new Category();
	}

	@Override
	public Page<Category> getCategoriesCategoriesPage(
			Long categoryId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Page<Category> getVocabulariesCategoriesPage(
			Long vocabularyId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Category postCategoriesCategories(Long categoryId, Category category)
		throws Exception {

		return new Category();
	}

	@Override
	public Category postCategoriesCategoriesBatchCreate(
			Long categoryId, Category category)
		throws Exception {

		return new Category();
	}

	@Override
	public Category postVocabulariesCategories(
			Long vocabularyId, Category category)
		throws Exception {

		return new Category();
	}

	@Override
	public Category postVocabulariesCategoriesBatchCreate(
			Long vocabularyId, Category category)
		throws Exception {

		return new Category();
	}

	@Override
	public Category putCategories(Long categoryId, Category category)
		throws Exception {

		return new Category();
	}

	protected Response buildNoContentResponse() {
		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}