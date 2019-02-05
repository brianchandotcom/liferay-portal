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

package com.liferay.headless.foundation.internal.resource;

import com.liferay.headless.foundation.dto.Category;
import com.liferay.headless.foundation.resource.CategoryResource;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Collections;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseCategoryResourceImpl implements CategoryResource {

	@Override
	public Category getCategories(Long id) throws Exception {
		return new Category();
	}

	@Override
	public Page<Category> getCategoriesCategoriesPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Page<Category> getVocabulariesCategoriesPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Category postCategoriesCategories(Long parentId) throws Exception {
		return new Category();
	}

	@Override
	public Category postCategoriesCategoriesBatchCreate(Long parentId)
		throws Exception {

		return new Category();
	}

	@Override
	public Category postVocabulariesCategories(Long parentId) throws Exception {
		return new Category();
	}

	@Override
	public Category postVocabulariesCategoriesBatchCreate(Long parentId)
		throws Exception {

		return new Category();
	}

	@Override
	public Category putCategories(Long id) throws Exception {
		return new Category();
	}

}