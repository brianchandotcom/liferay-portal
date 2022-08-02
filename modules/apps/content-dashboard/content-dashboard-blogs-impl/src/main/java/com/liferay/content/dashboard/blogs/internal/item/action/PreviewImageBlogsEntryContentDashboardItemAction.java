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

package com.liferay.content.dashboard.blogs.internal.item.action;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;
import java.util.Optional;

/**
 * @author Cristina González
 */
public class PreviewImageBlogsEntryContentDashboardItemAction
	implements ContentDashboardItemAction {

	public PreviewImageBlogsEntryContentDashboardItemAction(
		BlogsEntry blogsEntry,
		InfoItemFieldValuesProvider<BlogsEntry> infoItemFieldValuesProvider,
		Language language) {

		_blogsEntry = blogsEntry;
		_infoItemFieldValuesProvider = infoItemFieldValuesProvider;
		_language = language;
	}

	@Override
	public String getIcon() {
		return "preview-image";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "preview-image");
	}

	@Override
	public String getName() {
		return "preview-image";
	}

	@Override
	public Type getType() {
		return Type.PREVIEW_IMAGE;
	}

	@Override
	public String getURL() {
		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(_blogsEntry);

		return Optional.ofNullable(
			infoItemFieldValues.getInfoFieldValue("previewImage")
		).map(
			InfoFieldValue::getValue
		).orElse(
			StringPool.BLANK
		).toString();
	}

	@Override
	public String getURL(Locale locale) {
		return getURL();
	}

	private final BlogsEntry _blogsEntry;
	private final InfoItemFieldValuesProvider<BlogsEntry>
		_infoItemFieldValuesProvider;
	private final Language _language;

}