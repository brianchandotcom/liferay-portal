/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class DownloadFileVersionContentDashboardItemVersionAction
	implements ContentDashboardItemVersionAction {

	public DownloadFileVersionContentDashboardItemVersionAction(
		FileEntry fileEntry,
		InfoItemFieldValuesProvider<FileEntry> infoItemFieldValuesProvider,
		Language language) {

		_fileEntry = fileEntry;
		_infoItemFieldValuesProvider = infoItemFieldValuesProvider;
		_language = language;
	}

	@Override
	public String getIcon() {
		return "download";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "download");
	}

	@Override
	public String getName() {
		return "download";
	}

	@Override
	public String getURL() {
		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(_fileEntry);

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue("downloadURL");

		if (infoFieldValue == null) {
			return StringPool.BLANK;
		}

		Object value = infoFieldValue.getValue();

		if (value == null) {
			return StringPool.BLANK;
		}

		return value.toString();
	}

	private final FileEntry _fileEntry;
	private final InfoItemFieldValuesProvider<FileEntry>
		_infoItemFieldValuesProvider;
	private final Language _language;

}