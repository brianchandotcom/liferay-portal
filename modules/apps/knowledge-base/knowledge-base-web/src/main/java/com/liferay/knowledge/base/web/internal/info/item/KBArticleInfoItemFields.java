/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.knowledge.base.model.KBArticle;

/**
 * @author Alicia García
 */
public class KBArticleInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorNameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"authorName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				KBArticleInfoItemFields.class, "author-name")
		).build();
	public static final InfoField<ImageInfoFieldType>
		authorProfileImageInfoField = BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"authorProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				KBArticleInfoItemFields.class, "author-profile-image")
		).build();
	public static final InfoField<HTMLInfoFieldType> contentInfoField =
		BuilderHolder._builder.infoFieldType(
			HTMLInfoFieldType.INSTANCE
		).name(
			"content"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				KBArticleInfoItemFields.class, "content")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				KBArticleInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				KBArticleInfoItemFields.class, "description")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				KBArticleInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(KBArticleInfoItemFields.class, "title")
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(KBArticle.class.getSimpleName());

	}

}