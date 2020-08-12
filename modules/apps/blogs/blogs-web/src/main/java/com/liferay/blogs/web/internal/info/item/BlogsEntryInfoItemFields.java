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

package com.liferay.blogs.web.internal.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jorge Ferrer
 */
public interface BlogsEntryInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"authorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		authorProfileImageInfoField = InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"authorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "author-profile-image")
		).build();
	public static final InfoField<TextInfoFieldType> contentInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"content"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "content")
		).build();
	public static final InfoField<TextInfoFieldType>
		coverImageCaptionInfoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"coverImageCaption"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "cover-image-caption")
		).build();
	public static final InfoField<ImageInfoFieldType> coverImageInfoField =
		InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"coverImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "cover-image")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "description")
		).build();
	public static final InfoField<DateInfoFieldType> displayDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "display-date")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageUrlInfoField =
		InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField<ImageInfoFieldType> smallImageInfoField =
		InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"smallImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "small-image")
		).build();
	public static final InfoField<TextInfoFieldType> subtitleInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"subtitle"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "subtitle")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(BlogsEntryInfoItemFields.class, "title")
		).build();

}