/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.asset.auto.tagger.text.extractor;

import com.liferay.asset.auto.tagger.text.extractor.TextExtractor;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 * @author Alejandro Tardín
 */
@Component(service = TextExtractor.class)
public class FileEntryTextExtractor implements TextExtractor<FileEntry> {

	@Override
	public String extract(FileEntry fileEntry, Locale locale) {
		try {
			FileVersion fileVersion = fileEntry.getFileVersion();

			try (InputStream inputStream = fileVersion.getContentStream(
					false)) {

				return _textExtractor.extractText(inputStream, -1);
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
	}

	@Override
	public String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryTextExtractor.class);

	@Reference
	private com.liferay.portal.kernel.util.TextExtractor _textExtractor;

}