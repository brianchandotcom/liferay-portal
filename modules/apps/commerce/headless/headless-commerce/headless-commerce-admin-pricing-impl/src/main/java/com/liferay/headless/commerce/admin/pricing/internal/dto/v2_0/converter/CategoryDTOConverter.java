/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Category;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = DTOConverter.class
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
				id = assetCategory.getCategoryId();
				name = assetCategory.getName();
				path = assetCategory.getPath(dtoConverterContext.getLocale());
				vocabulary = assetVocabulary.getName();
			}
		};
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}