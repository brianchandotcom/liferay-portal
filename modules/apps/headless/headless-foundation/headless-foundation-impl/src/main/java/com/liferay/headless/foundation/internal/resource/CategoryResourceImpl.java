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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.foundation.dto.Category;
import com.liferay.headless.foundation.dto.CategoryCollection;
import com.liferay.headless.foundation.resource.CategoryResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
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
	public CategoryCollection<Category> getCategoryCollection(
			Pagination pagination, String size)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		Group group = company.getGroup();

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyService.getGroupVocabularies(group.getGroupId());

		if (assetVocabularies.isEmpty()) {
			return new CategoryCollection(Collections.emptyList(), 0);
		}

		AssetVocabulary assetVocabulary = assetVocabularies.get(0);

		List<AssetCategory> assetCategories =
			_assetCategoryService.getVocabularyRootCategories(
				assetVocabulary.getGroupId(), assetVocabulary.getVocabularyId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		Stream<AssetCategory> stream = assetCategories.stream();

		List<Category> categories = stream.map(
			assetCategory -> {
				Category category = new Category();

				category.setId(assetCategory.getCategoryId());

				return category;
			}
		).collect(
			Collectors.toList()
		);

		int count = _assetCategoryService.getVocabularyRootCategoriesCount(
			assetVocabulary.getGroupId(), assetVocabulary.getVocabularyId());

		return new CategoryCollection(categories, count);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private CompanyService _companyService;

}