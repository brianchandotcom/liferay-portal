/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;

/**
 * @author Jorge Ferrer
 */
public class JournalArticleInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorNameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"authorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		authorProfileImageInfoField = BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"authorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "author-profile-image")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<HTMLInfoFieldType> descriptionInfoField =
		BuilderHolder._builder.infoFieldType(
			HTMLInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "description")
		).localizable(
			true
		).build();
	public static final InfoField<DateInfoFieldType> displayDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"displayDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "display-date")
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
	public static final InfoField<DateInfoFieldType> expirationDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"expirationDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "expiration-date")
		).build();
	public static final InfoField<TextInfoFieldType> lastEditorNameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"lastEditorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "last-editor-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		lastEditorProfileImageInfoField = BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"lastEditorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "last-editor-profile-image")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<ImageInfoFieldType> previewImageInfoField =
		BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"previewImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "preview-image")
		).build();
	public static final InfoField<DateInfoFieldType> publishDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "publish-date")
		).build();
	public static final InfoField<ImageInfoFieldType> smallImageInfoField =
		BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"smallImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "small-image")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "title")
		).localizable(
			true
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(JournalArticle.class.getSimpleName());

	}

}