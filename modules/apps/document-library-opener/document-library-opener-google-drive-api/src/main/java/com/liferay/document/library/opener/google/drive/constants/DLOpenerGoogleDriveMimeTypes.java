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

package com.liferay.document.library.opener.google.drive.constants;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class DLOpenerGoogleDriveMimeTypes {

	public static final String APPLICATION_VND_DOCX =
		"application/vnd.openxmlformats-officedocument.wordprocessingml." +
			"document";

	public static final String APPLICATION_VND_GOOGLE_APPS_DOCUMENT =
		"application/vnd.google-apps.document";

	public static final String APPLICATION_VND_GOOGLE_APPS_PRESENTATION =
		"application/vnd.google-apps.presentation";

	public static final String APPLICATION_VND_GOOGLE_APPS_SPREADSHEET =
		"application/vnd.google-apps.spreadsheet";

	public static final String APPLICATION_VND_PPTX =
		"application/vnd.openxmlformats-officedocument.presentationml." +
			"presentation";

	public static final String APPLICATION_VND_XSLX =
		"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	public static String getGoogleDocsMimeType(String mimeType) {
		if (!isMimeTypeSupported(mimeType)) {
			throw new UnsupportedOperationException(
				StringBundler.concat(
					"Google Docs does not support edition of documents of ",
					"type ", mimeType));
		}

		return _mimeTypeMapping.get(mimeType);
	}

	public static String getMimeTypeExtension(String mimeType) {
		return _extensionMapping.getOrDefault(mimeType, StringPool.BLANK);
	}

	public static boolean isMimeTypeSupported(String mimeType) {
		return _mimeTypeMapping.containsKey(mimeType);
	}

	private static final Map<String, String> _extensionMapping =
		MapUtil.fromArray(
			APPLICATION_VND_DOCX, ".docx", APPLICATION_VND_PPTX, ".pptx",
			APPLICATION_VND_XSLX, ".xslx", ContentTypes.APPLICATION_TEXT,
			".txt", ContentTypes.TEXT, ".txt", ContentTypes.TEXT_PLAIN, ".txt");
	private static final Map<String, String> _mimeTypeMapping =
		MapUtil.fromArray(
			APPLICATION_VND_DOCX, APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			APPLICATION_VND_PPTX, APPLICATION_VND_GOOGLE_APPS_PRESENTATION,
			APPLICATION_VND_XSLX, APPLICATION_VND_GOOGLE_APPS_SPREADSHEET,
			ContentTypes.APPLICATION_TEXT, APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			ContentTypes.TEXT, APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			ContentTypes.TEXT_PLAIN, APPLICATION_VND_GOOGLE_APPS_DOCUMENT);

}