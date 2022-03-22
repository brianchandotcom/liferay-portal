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

package com.liferay.journal.web.internal.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;

/**
 * @author Jorge Ferrer
 */
public interface JournalArticleInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_authorName"
		).name(
			"authorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		authorProfileImageInfoField = InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_authorProfileImage"
		).name(
			"authorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "author-profile-image")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_createDate"
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_description"
		).name(
			"description"
		).attribute(
			TextInfoFieldType.HTML, true
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "description")
		).localizable(
			true
		).build();
	public static final InfoField<DateInfoFieldType> displayDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_displayDate"
		).name(
			"displayDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "display-date")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageURLInfoField =
		InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_displayPageURL"
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField<DateInfoFieldType> expirationDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_expirationDate"
		).name(
			"expirationDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "expiration-date")
		).build();
	public static final InfoField<TextInfoFieldType> lastEditorNameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_lastEditorName"
		).name(
			"lastEditorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "last-editor-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		lastEditorProfileImageInfoField = InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_lastEditorProfileImage"
		).name(
			"lastEditorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "last-editor-profile-image")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_modifiedDate"
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<DateInfoFieldType> publishDateInfoField =
		InfoField.builder(
		).infoFieldType(
			DateInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_publishDate"
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "publish-date")
		).build();
	public static final InfoField<ImageInfoFieldType> smallImageInfoField =
		InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_smallImage"
		).name(
			"smallImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "small-image")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			JournalArticle.class.getSimpleName() + "_title"
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "title")
		).localizable(
			true
		).build();

}