/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Category;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
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
	public AssetCategory getObject(String externalReferenceCode)
		throws Exception {

		return _assetCategoryLocalService.fetchCategory(
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public Category toDTO(
			DTOConverterContext dtoConverterContext,
			AssetCategory assetCategory)
		throws Exception {

		if (assetCategory == null) {
			return null;
		}

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
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}