/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.info.item;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jürgen Kappler
 */
public class AssetCategoryInfoItemFields {

	public static final InfoField<HTMLInfoFieldType> descriptionInfoField =
		BuilderHolder._builder.infoFieldType(
			HTMLInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetCategoryInfoItemFields.class, "description")
		).build();
	public static final InfoField<TextInfoFieldType> displayPageURLInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetCategoryInfoItemFields.class, "display-page-url")
		).build();
	public static final InfoField<TextInfoFieldType> nameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"name"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetCategoryInfoItemFields.class, "name")
		).build();
	public static final InfoField<TextInfoFieldType> vocabularyInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"vocabulary"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetCategoryInfoItemFields.class, "vocabulary")
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(AssetCategory.class.getSimpleName());

	}

}