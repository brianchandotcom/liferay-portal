/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal.constants;

import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

/**
 * Provides a set of constants and methods for working with the MIME types
 * supported by Liferay and Google Drive.
 *
 * See https://developers.google.com/drive/api/v3/manage-downloads.
 *
 * @author Adolfo Pérez
 * @review
 */
public class DLOpenerGoogleDriveMimeTypes {

	/**
	 * The MIME type for Google Docs.
	 */
	public static final String APPLICATION_VND_GOOGLE_APPS_DOCUMENT =
		"application/vnd.google-apps.document";

	/**
	 * The MIME type for Google Slides.
	 */
	public static final String APPLICATION_VND_GOOGLE_APPS_PRESENTATION =
		"application/vnd.google-apps.presentation";

	/**
	 * The MIME type for Google Spreadsheets.
	 */
	public static final String APPLICATION_VND_GOOGLE_APPS_SPREADSHEET =
		"application/vnd.google-apps.spreadsheet";

	/**
	 * Returns the Google Drive MIME type equivalent to the one received. For
	 * example, this method maps Microsoft Word documents and plain text files
	 * to Google Docs, Microsoft PowerPoint presentations to Google Slides, and
	 * and so on.
	 *
	 * <p>
	 * This method returns a valid Google document type only for MIME types for
	 * which {@link #isGoogleMimeTypeSupported(String)} returns {@code true}. An
	 * exception is thrown for any others.
	 * </p>
	 *
	 * @param  mimeType the MIME type to map to Google Drive
	 * @return the equivalent Google Drive MIME type
	 * @review
	 */
	public static String getGoogleDocsMimeType(String mimeType) {
		if (!isGoogleMimeTypeSupported(mimeType)) {
			throw new UnsupportedOperationException(
				StringBundler.concat(
					"Google Docs does not support edition of documents of ",
					"type ", mimeType));
		}

		return _googleDocsMimeTypes.get(mimeType);
	}

	/**
	 * Returns {@code true} if a MIME type is supported.
	 *
	 * @param  mimeType the MIME type
	 * @return {@code true} if the MIME type is supported; {@code false}
	 *         otherwise
	 * @review
	 */
	public static boolean isGoogleMimeTypeSupported(String mimeType) {
		return _googleDocsMimeTypes.containsKey(mimeType);
	}

	private static final Map<String, String> _googleDocsMimeTypes =
		MapUtil.fromArray(
			ContentTypes.APPLICATION_PDF, APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			DLOpenerMimeTypes.APPLICATION_RTF,
			APPLICATION_VND_GOOGLE_APPS_DOCUMENT, ContentTypes.APPLICATION_TEXT,
			APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			DLOpenerMimeTypes.APPLICATION_VND_DOCX,
			APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			DLOpenerMimeTypes.APPLICATION_VND_ODP,
			APPLICATION_VND_GOOGLE_APPS_PRESENTATION,
			DLOpenerMimeTypes.APPLICATION_VND_ODS,
			APPLICATION_VND_GOOGLE_APPS_SPREADSHEET,
			DLOpenerMimeTypes.APPLICATION_VND_ODT,
			APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			DLOpenerMimeTypes.APPLICATION_VND_PPTX,
			APPLICATION_VND_GOOGLE_APPS_PRESENTATION,
			DLOpenerMimeTypes.APPLICATION_VND_XLSX,
			APPLICATION_VND_GOOGLE_APPS_SPREADSHEET, ContentTypes.TEXT,
			APPLICATION_VND_GOOGLE_APPS_DOCUMENT, ContentTypes.TEXT_CSV,
			APPLICATION_VND_GOOGLE_APPS_SPREADSHEET, ContentTypes.TEXT_HTML,
			APPLICATION_VND_GOOGLE_APPS_DOCUMENT, ContentTypes.TEXT_PLAIN,
			APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			DLOpenerMimeTypes.TEXT_TAB_SEPARATED_VALUES,
			APPLICATION_VND_GOOGLE_APPS_SPREADSHEET);

}