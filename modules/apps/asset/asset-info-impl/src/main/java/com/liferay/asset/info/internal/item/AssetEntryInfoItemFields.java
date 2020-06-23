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

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jorge Ferrer
 */
public interface AssetEntryInfoItemFields {

	public static final InfoField createDateInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			AssetEntryInfoItemFields.class, "create-date"),
		"createDate");
	public static final InfoField descriptionInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			AssetEntryInfoItemFields.class, "description"),
		"description");
	public static final InfoField expirationDateInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			AssetEntryInfoItemFields.class, "expiration-date"),
		"expirationDate");
	public static final InfoField modifiedDateInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			AssetEntryInfoItemFields.class, "modified-date"),
		"modifiedDate");
	public static final InfoField summaryInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(AssetEntryInfoItemFields.class, "summary"),
		"summary");
	public static final InfoField titleInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(AssetEntryInfoItemFields.class, "title"),
		"title");
	public static final InfoField urlInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(AssetEntryInfoItemFields.class, "url"),
		"url");
	public static final InfoField userNameInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			AssetEntryInfoItemFields.class, "user-name"),
		"userName");
	public static final InfoField userProfileImage = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			AssetEntryInfoItemFields.class, "user-profile-image"),
		"userProfileImage");
	public static final InfoField viewCountInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			AssetEntryInfoItemFields.class, "view-count"),
		"viewName");

}