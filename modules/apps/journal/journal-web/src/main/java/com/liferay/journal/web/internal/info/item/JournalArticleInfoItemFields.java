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
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jorge Ferrer
 */
public interface JournalArticleInfoItemFields {

	public static final InfoField authorNameInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			JournalArticleInfoItemFields.class, "author-name"),
		"authorName");
	public static final InfoField authorProfileImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.journal.lang", "author-profile-image"),
		"authorProfileImage");
	public static final InfoField descriptionInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			JournalArticleInfoItemFields.class, "description"),
		true, "description");
	public static final InfoField displayPageUrlInfoField = new InfoField(
		URLInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.asset.info.display.impl", "display-page-url"),
		"displayPageURL");
	public static final InfoField lastEditorNameInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.journal.lang", "last-editor-name"),
		"lastEditorName");
	public static final InfoField lastEditorProfileImageInfoField =
		new InfoField(
			ImageInfoFieldType.INSTANCE,
			InfoLocalizedValue.localize(
				JournalArticleInfoItemFields.class,
				"last-editor-profile-image"),
			"lastEditorProfileImage");
	public static final InfoField publishDateInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			JournalArticleInfoItemFields.class, "publish-date"),
		"publishDate");
	public static final InfoField smallImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize("com.liferay.journal.lang", "small-image"),
		"smallImage");
	public static final InfoField titleInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			JournalArticleInfoItemFields.class, "title"),
		true, "title");

}