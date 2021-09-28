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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Category;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = {CategoryDTOConverter.class, DTOConverter.class}
)
public class CategoryDTOConverter
	implements DTOConverter<AssetCategory, Category> {

	@Override
	public String getContentType() {
		return Category.class.getSimpleName();
	}

	@Override
	public Category toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			(Long)dtoConverterContext.getId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.getAssetVocabulary(
				assetCategory.getVocabularyId());

		return new Category() {
			{
				externalReferenceCode =
					assetCategory.getExternalReferenceCode();
				id = assetCategory.getCategoryId();
				name = assetCategory.getName();
				siteId = assetCategory.getGroupId();
				vocabulary = assetVocabulary.getName();
			}
		};
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}