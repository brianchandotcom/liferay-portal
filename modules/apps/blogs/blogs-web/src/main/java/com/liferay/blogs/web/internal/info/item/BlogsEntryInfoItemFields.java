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
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jorge Ferrer
 */
public interface BlogsEntryInfoItemFields {

	public static final InfoField authorNameInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			BlogsEntryInfoItemFields.class, "author-name"),
		"authorName");
	public static final InfoField authorProfileImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			BlogsEntryInfoItemFields.class, "author-profile-image"),
		"authorProfileImage");
	public static final InfoField contentInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(BlogsEntryInfoItemFields.class, "content"),
		"content");
	public static final InfoField coverImageCaptionInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			BlogsEntryInfoItemFields.class, "cover-image-caption"),
		"coverImageCaption");
	public static final InfoField coverImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			BlogsEntryInfoItemFields.class, "cover-image"),
		"coverImage");
	public static final InfoField descriptionInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			BlogsEntryInfoItemFields.class, "description"),
		"description");
	public static final InfoField displayPageUrlInfoField = new InfoField(
		URLInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.asset.info.display.impl", "display-page-url"),
		"displayPageURL");
	public static final InfoField publishDateInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			BlogsEntryInfoItemFields.class, "publish-date"),
		"publishDate");
	public static final InfoField smallImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			BlogsEntryInfoItemFields.class, "small-image"),
		"smallImage");
	public static final InfoField subtitleInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(BlogsEntryInfoItemFields.class, "subtitle"),
		"subtitle");
	public static final InfoField titleInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(BlogsEntryInfoItemFields.class, "title"),
		"title");

}