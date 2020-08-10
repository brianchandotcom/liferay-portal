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

package com.liferay.portal.search.internal.spi.model.index.contributor;

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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = DocumentContributor.class)
public class AssetPublicCategoryDocumentContributor
	implements DocumentContributor<AssetCategory> {

	@Override
	public void contribute(
		Document document, BaseModel<AssetCategory> baseModel) {

		List<AssetCategory> publicAssetCategories = new ArrayList<>();

		String className = document.get(Field.ENTRY_CLASS_NAME);
		long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

		List<AssetCategory> assetCategories =
			assetCategoryLocalService.getCategories(className, classPK);

		Map<Long, AssetVocabulary> assetVocabulariesMap = new HashMap<>();

		for (AssetCategory assetCategory : assetCategories) {
			AssetVocabulary assetVocabulary =
				assetVocabulariesMap.computeIfAbsent(
					assetCategory.getVocabularyId(),
					vocabularyId ->
						assetVocabularyLocalService.fetchAssetVocabulary(
							vocabularyId));

			if ((assetVocabulary != null) &&
				(assetVocabulary.getVisibilityType() ==
					AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC)) {

				publicAssetCategories.add(assetCategory);
			}
		}

		long[] publicAssetCategoryIds = ListUtil.toLongArray(
			publicAssetCategories, AssetCategory.CATEGORY_ID_ACCESSOR);

		document.addKeyword(
			Field.ASSET_PUBLIC_CATEGORY_IDS, publicAssetCategoryIds);

		addAssetCategoryTitles(
			document, Field.ASSET_PUBLIC_CATEGORY_TITLES,
			publicAssetCategories);
	}

	protected void addAssetCategoryTitles(
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
				StringBundler.concat(
					field, StringPool.UNDERLINE, locale.toString()),
				titlesArray);
		}
	}

	@Reference
	protected AssetCategoryLocalService assetCategoryLocalService;

	@Reference
	protected AssetVocabularyLocalService assetVocabularyLocalService;

}