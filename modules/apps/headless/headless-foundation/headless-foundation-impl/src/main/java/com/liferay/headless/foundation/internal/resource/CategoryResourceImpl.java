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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-foundation-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = CategoryResource.class
)
@Generated("")
public class CategoryResourceImpl implements CategoryResource {

	@Override
	public Category getCategories(Integer id) throws Exception {
		return new Category();
	}

	@Override
	public Page<Category> getCategoriesCategoriesPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Category> getVocabulariesCategoriesPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Category postCategoriesCategories(Integer parentId)
		throws Exception {

		return new Category();
	}

	@Override
	public Category postCategoriesCategoriesBatchCreate(Integer parentId)
		throws Exception {

		return new Category();
	}

	@Override
	public Category postVocabulariesCategories(Integer parentId)
		throws Exception {

		return new Category();
	}

	@Override
	public Category postVocabulariesCategoriesBatchCreate(Integer parentId)
		throws Exception {

		return new Category();
	}

	@Override
	public Category putCategories(Integer id) throws Exception {
		return new Category();
	}

}