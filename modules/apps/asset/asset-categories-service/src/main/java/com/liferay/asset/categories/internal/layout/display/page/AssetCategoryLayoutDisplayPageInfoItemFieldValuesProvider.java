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

package com.liferay.asset.categories.internal.layout.display.page;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.display.page.LayoutDisplayPageInfoItemFieldValuesProvider;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	service = LayoutDisplayPageInfoItemFieldValuesProvider.class
)
public class AssetCategoryLayoutDisplayPageInfoItemFieldValuesProvider
	implements LayoutDisplayPageInfoItemFieldValuesProvider<AssetCategory> {

	@Override
	public String getClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		AssetCategory assetCategory) {

		if (assetCategory == null) {
			return InfoItemFieldValues.builder(
			).build();
		}

		List<InfoFieldValue<Object>> assetCategoryInfoFieldValues =
			new ArrayList<>();

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchAssetVocabulary(
				assetCategory.getVocabularyId());

		if (assetVocabulary != null) {
			assetCategoryInfoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).name(
						"vocabulary"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							AssetCategoryLayoutDisplayPageInfoItemFieldValuesProvider.class,
							"vocabulary")
					).build(),
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							assetVocabulary.getDefaultLanguageId())
					).values(
						assetVocabulary.getTitleMap()
					).build()));
		}

		Group group = _groupLocalService.fetchGroup(assetCategory.getGroupId());

		if (group != null) {
			assetCategoryInfoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).name(
						"group"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							AssetCategoryLayoutDisplayPageInfoItemFieldValuesProvider.class,
							"site")
					).build(),
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(group.getDefaultLanguageId())
					).values(
						HashMapBuilder.put(
							LanguageUtil.getAvailableLocales(),
							locale -> group.getDescriptiveName(locale)
						).build()
					).build()));
		}

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			assetCategoryInfoFieldValues
		).infoItemReference(
			new InfoItemReference(
				AssetCategory.class.getName(), assetCategory.getCategoryId())
		).build();
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(long categoryId) {
		return getInfoItemFieldValues(
			_assetCategoryLocalService.fetchAssetCategory(categoryId));
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}