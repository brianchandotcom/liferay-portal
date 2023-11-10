/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.info.item;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jorge Ferrer
 */
public class BlogsEntryInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorNameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"authorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		authorProfileImageInfoField = BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"authorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "author-profile-image")
		).build();
	public static final InfoField<HTMLInfoFieldType> contentInfoField =
		BuilderHolder._builder.infoFieldType(
			HTMLInfoFieldType.INSTANCE
		).name(
			"content"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "content")
		).build();
	public static final InfoField<TextInfoFieldType>
		coverImageCaptionInfoField = BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"coverImageCaption"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "cover-image-caption")
		).build();
	public static final InfoField<ImageInfoFieldType> coverImageInfoField =
		BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"coverImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "cover-image")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "description")
		).build();
	public static final InfoField<DateInfoFieldType> displayDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"displayDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "display-date")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageURLInfoField =
		BuilderHolder._builder.infoFieldType(
			URLInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<ImageInfoFieldType> previewImageInfoField =
		BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"previewImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "preview-image")
		).build();
	public static final InfoField<DateInfoFieldType> publishDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "publish-date")
		).build();
	public static final InfoField<ImageInfoFieldType> smallImageInfoField =
		BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"smallImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "small-image")
		).build();
	public static final InfoField<TextInfoFieldType> subtitleInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"subtitle"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				BlogsEntryInfoItemFields.class, "subtitle")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(BlogsEntryInfoItemFields.class, "title")
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(BlogsEntry.class.getSimpleName());

	}

}