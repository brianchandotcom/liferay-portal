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

package com.liferay.asset.categories.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = DocumentContributor.class)
public class AssetCategoryDocumentContributor
	implements DocumentContributor<AssetCategory> {

	@Override
	public void contribute(
		Document document, BaseModel<AssetCategory> baseModel) {

		_addAssetCategoriesFields(
			document, Field.ASSET_CATEGORY_IDS, Field.ASSET_CATEGORY_TITLES,
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);
		_addAssetCategoriesFields(
			document, Field.ASSET_INTERNAL_CATEGORY_IDS,
			Field.ASSET_INTERNAL_CATEGORY_TITLES,
			AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);
		_addAssetVocabularyCategoriesFields(
			document, "assetVocabularyCategoryIds",
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);
	}

	private void _addAssetCategoriesFields(
		Document document, String assetCategoryIdsFieldName,
		String assetCategoryTitlesFieldName, int visibilityType) {

		List<AssetCategory> filteredAssetCategories = new ArrayList<>();

		_populate(
			document, filteredAssetCategories, visibilityType,
			assetCategory -> assetCategory);

		long[] filteredAssetCategoryIds = ListUtil.toLongArray(
			filteredAssetCategories, AssetCategory.CATEGORY_ID_ACCESSOR);

		document.addKeyword(
			assetCategoryIdsFieldName, filteredAssetCategoryIds);

		_addAssetCategoryTitles(
			document, assetCategoryTitlesFieldName, filteredAssetCategories);
	}

	private void _addAssetCategoryTitles(
		Document document, String field, List<AssetCategory> assetCategories) {

		Map<Locale, List<String>> assetCategoryTitles = new HashMap<>();

		for (AssetCategory assetCategory : assetCategories) {
			Map<Locale, String> titleMap = assetCategory.getTitleMap();

			for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
				String title = entry.getValue();

				if (Validator.isNull(title)) {
					continue;
				}

				Locale locale = entry.getKey();

				List<String> titles = assetCategoryTitles.computeIfAbsent(
					locale, k -> new ArrayList<>());

				titles.add(StringUtil.toLowerCase(title));
			}
		}

		for (Map.Entry<Locale, List<String>> entry :
				assetCategoryTitles.entrySet()) {

			Locale locale = entry.getKey();

			List<String> titles = entry.getValue();

			String[] titlesArray = titles.toArray(new String[0]);

			document.addText(
				StringBundler.concat(field, StringPool.UNDERLINE, locale),
				titlesArray);
		}
	}

	private void _addAssetVocabularyCategoriesFields(
		Document document, String assetVocabularyCategoryIdsFieldName,
		int visibilityType) {

		List<String> filteredAssetVocabularyCategoryStrings = new ArrayList<>();

		_populate(
			document, filteredAssetVocabularyCategoryStrings, visibilityType,
			assetCategory ->
				assetCategory.getVocabularyId() + StringPool.DASH +
					assetCategory.getCategoryId());

		document.addKeyword(
			assetVocabularyCategoryIdsFieldName,
			filteredAssetVocabularyCategoryStrings.toArray(new String[0]));
	}

	private <T> void _populate(
		Document document, List<T> list, int visibilityType,
		Function<AssetCategory, T> function) {

		Map<Long, AssetVocabulary> assetVocabulariesMap = new HashMap<>();

		String className = document.get(Field.ENTRY_CLASS_NAME);
		long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(className, classPK);

		for (AssetCategory assetCategory : assetCategories) {
			AssetVocabulary assetVocabulary =
				assetVocabulariesMap.computeIfAbsent(
					assetCategory.getVocabularyId(),
					vocabularyId ->
						_assetVocabularyLocalService.fetchAssetVocabulary(
							vocabularyId));

			if ((assetVocabulary != null) &&
				(assetVocabulary.getVisibilityType() == visibilityType)) {

				list.add((T)function.apply(assetCategory));
			}
		}
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}