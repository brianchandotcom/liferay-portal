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

package com.liferay.asset.info.internal.item;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jorge Ferrer
 */
public class AssetEntryInfoItemFields {

	public static final InfoField<TextInfoFieldType> createDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "description")
		).build();
	public static final InfoField<TextInfoFieldType> displayPageURLInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "display-page-url")
		).build();
	public static final InfoField<TextInfoFieldType> expirationDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"expirationDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "expiration-date")
		).build();
	public static final InfoField<TextInfoFieldType> modifiedDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<TextInfoFieldType> summaryInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"summary"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "summary")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(AssetEntryInfoItemFields.class, "title")
		).build();
	public static final InfoField<TextInfoFieldType> urlInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"url"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(AssetEntryInfoItemFields.class, "url")
		).build();
	public static final InfoField<TextInfoFieldType> userNameInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"userName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "user-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		userProfileImageInfoField = BuilderStaticHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"userProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "user-profile-image")
		).build();
	public static final InfoField<TextInfoFieldType> viewCountInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"viewName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "view-count")
		).build();

	private static class BuilderStaticHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(AssetEntry.class.getSimpleName());

	}

}