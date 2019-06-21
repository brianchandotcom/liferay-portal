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

package com.liferay.document.library.web.internal.info.extractor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.info.extractor.InfoTextExtractor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.InputStream;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia García
 * @author Alejandro Tardín
 */
@Component(service = InfoTextExtractor.class)
public class DLFileEntryInfoTextExtractor
	implements InfoTextExtractor<DLFileEntry> {

	@Override
	public String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	public String extract(DLFileEntry dlFileEntry, Locale locale) {
		try {
			DLFileVersion fileVersion = dlFileEntry.getFileVersion();

			try (InputStream inputStream = fileVersion.getContentStream(
					false)) {

				return FileUtil.extractText(
					inputStream, fileVersion.getFileName());
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryInfoTextExtractor.class);

}