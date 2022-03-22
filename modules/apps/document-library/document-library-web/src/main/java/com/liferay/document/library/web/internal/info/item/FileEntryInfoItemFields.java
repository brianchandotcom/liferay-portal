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
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
public class FileEntryInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_authorName"
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
		).uniqueId(
			FileEntry.class.getSimpleName() + "_authorProfileImage"
		).name(
			"authorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "author-profile-image")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_createDate"
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_description"
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "description")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageURLInfoField =
		InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_displayPageURL"
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField<URLInfoFieldType> downloadURLInfoField =
		InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_downloadURL"
		).name(
			"downloadURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "download-url")
		).build();
	public static final InfoField<TextInfoFieldType> fileNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_fileName"
		).name(
			"fileName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "file-name")
		).build();
	public static final InfoField<ImageInfoFieldType> fileURLInfoField =
		InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_fileURL"
		).name(
			"fileURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "file-url")
		).build();
	public static final InfoField<TextInfoFieldType> mimeTypeInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_mimeType"
		).name(
			"mimeType"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "mime-type")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_modifiedDate"
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<ImageInfoFieldType> previewImageInfoField =
		InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_previewImage"
		).name(
			"previewImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "preview-image")
		).build();
	public static final InfoField<URLInfoFieldType> previewURLInfoField =
		InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_previewURL"
		).name(
			"previewURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "preview-url")
		).build();
	public static final InfoField<DateInfoFieldType> publishDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_publishDate"
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "publish-date")
		).build();
	public static final InfoField<TextInfoFieldType> sizeInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_size"
		).name(
			"size"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(FileEntryInfoItemFields.class, "size")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_title"
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(FileEntryInfoItemFields.class, "title")
		).build();
	public static final InfoField<TextInfoFieldType> versionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			FileEntry.class.getSimpleName() + "_version"
		).name(
			"version"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				FileEntryInfoItemFields.class, "version")
		).build();

}