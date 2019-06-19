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

package com.liferay.asset.auto.tagger.opennlp.internal.extractor.external;

import com.liferay.asset.auto.tagger.opennlp.internal.extractor.TextExtractor;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia García
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = TextExtractor.class
)
public class DLFileEntryTextExtractor implements TextExtractor<DLFileEntry> {

	@Override
	public String getText(DLFileEntry dlFileEntry) {
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
		DLFileEntryTextExtractor.class);

}