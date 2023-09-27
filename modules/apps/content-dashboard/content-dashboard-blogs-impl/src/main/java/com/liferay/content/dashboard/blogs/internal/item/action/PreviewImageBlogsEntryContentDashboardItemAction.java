/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue("previewImage");

		if (infoFieldValue == null) {
			return StringPool.BLANK;
		}

		Object value = infoFieldValue.getValue();

		if (value == null) {
			return StringPool.BLANK;
		}

		return value.toString();
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