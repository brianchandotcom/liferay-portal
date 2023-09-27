/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.info.internal.item;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jorge Ferrer
 */
public class AssetEntryInfoItemFields {

	public static final InfoField<DateInfoFieldType> createDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "description")
		).build();
	public static final InfoField<TextInfoFieldType> displayPageURLInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "display-page-url")
		).build();
	public static final InfoField<DateInfoFieldType> expirationDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"expirationDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "expiration-date")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<TextInfoFieldType> summaryInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"summary"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "summary")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(AssetEntryInfoItemFields.class, "title")
		).build();
	public static final InfoField<TextInfoFieldType> urlInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"url"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(AssetEntryInfoItemFields.class, "url")
		).build();
	public static final InfoField<TextInfoFieldType> userNameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"userName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "user-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		userProfileImageInfoField = BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"userProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "user-profile-image")
		).build();
	public static final InfoField<TextInfoFieldType> viewCountInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"viewName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				AssetEntryInfoItemFields.class, "view-count")
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(AssetEntry.class.getSimpleName());

	}

}