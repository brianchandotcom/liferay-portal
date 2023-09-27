/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.language.Language;
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
@Component(service = LayoutDisplayPageInfoItemFieldValuesProvider.class)
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
					).namespace(
						AssetCategory.class.getSimpleName()
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
					).namespace(
						AssetCategory.class.getSimpleName()
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
							_language.getAvailableLocales(),
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

	@Reference
	private Language _language;

}