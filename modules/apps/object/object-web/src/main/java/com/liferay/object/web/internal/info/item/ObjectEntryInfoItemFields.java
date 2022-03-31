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

package com.liferay.object.web.internal.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.object.model.ObjectEntry;

/**
 * @author Jorge Ferrer
 */
public interface ObjectEntryInfoItemFields {

	public static final InfoField<DateInfoFieldType> createDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).namespace(
			ObjectEntry.class.getSimpleName()
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageURLInfoField =
		InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).namespace(
			ObjectEntry.class.getSimpleName()
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).namespace(
			ObjectEntry.class.getSimpleName()
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<DateInfoFieldType> publishDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).namespace(
			ObjectEntry.class.getSimpleName()
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "publish-date")
		).build();
	public static final InfoField<TextInfoFieldType> userNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			ObjectEntry.class.getSimpleName()
		).name(
			"userName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "user-name")
		).build();
	public static final InfoField userProfileImageInfoField = InfoField.builder(
	).infoFieldType(
		ImageInfoFieldType.INSTANCE
	).namespace(
		ObjectEntry.class.getSimpleName()
	).name(
		"userProfileImage"
	).labelInfoLocalizedValue(
		InfoLocalizedValue.localize(
			ObjectEntryInfoItemFields.class, "user-profile-image")
	).build();

}