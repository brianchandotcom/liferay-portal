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
public class JournalArticleInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorNameInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"authorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		authorProfileImageInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				ImageInfoFieldType.INSTANCE
			).name(
				"authorProfileImage"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					JournalArticleInfoItemFields.class, "author-profile-image")
			).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
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
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"displayDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "display-date")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageURLInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			URLInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField<DateInfoFieldType> expirationDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"expirationDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "expiration-date")
		).build();
	public static final InfoField<TextInfoFieldType> lastEditorNameInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"lastEditorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "last-editor-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		lastEditorProfileImageInfoField =
			BuilderStaticHolder._builder.infoFieldType(
				ImageInfoFieldType.INSTANCE
			).name(
				"lastEditorProfileImage"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					JournalArticleInfoItemFields.class,
					"last-editor-profile-image")
			).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<DateInfoFieldType> publishDateInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "publish-date")
		).build();
	public static final InfoField<ImageInfoFieldType> smallImageInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"smallImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "small-image")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		BuilderStaticHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class, "title")
		).localizable(
			true
		).build();

	private static class BuilderStaticHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(JournalArticle.class.getSimpleName());

	}

}