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

package com.liferay.asset.categories.admin.web.internal.info.item;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jürgen Kappler
 */
public class AssetCategoryInfoItemFields {

	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).attribute(
			TextInfoFieldType.HTML, true
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