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

package com.liferay.document.library.web.internal.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
public class FileEntryInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"authorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		authorProfileImageInfoField = InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"authorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.journal.lang", "author-profile-image")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "description")
		).build();
	public static final InfoField<URLInfoFieldType> downloadURL =
		InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).name(
			"downloadURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "download-url")
		).build();
	public static final InfoField<TextInfoFieldType> fileName =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"fileName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "file-name")
		).build();
	public static final InfoField<TextInfoFieldType> fileURL =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"fileURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "file-url")
		).build();
	public static final InfoField<TextInfoFieldType> mimeType =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"mimeType"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "content-type")
		).build();
	public static final InfoField<ImageInfoFieldType> previewImage =
		InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"previewImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "preview-image")
		).build();
	public static final InfoField<DateInfoFieldType> publishDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "publish-date")
		).build();
	public static final InfoField<TextInfoFieldType> size = InfoField.builder(
	).infoFieldType(
		TextInfoFieldType.INSTANCE
	).name(
		"size"
	).labelInfoLocalizedValue(
		InfoLocalizedValue.localize(FileEntryInfoItemFields.class, "size")
	).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(FileEntryInfoItemFields.class, "title")
		).build();
	public static final InfoField<TextInfoFieldType> versionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"version"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "version")
		).build();

}