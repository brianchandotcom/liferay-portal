/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * Provides a set of constants and methods for working with the MIME types
 * supported by Liferay.
 *
 * See https://developers.google.com/drive/api/v3/manage-downloads.
 *
 * @author Adolfo Pérez
 * @author Alicia García
 * @review
 */
public class DLOpenerMimeTypes {

	/**
	 * The MIME type for Rich Text files.
	 *
	 * @review
	 */
	public static final String APPLICATION_RTF = "application/rtf";

	/**
	 * The MIME type for Microsoft Word (docx) documents.
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_DOCX =
		"application/vnd.openxmlformats-officedocument.wordprocessingml." +
			"document";

	/**
	 * The MIME type for Open Document (odp) presentations.
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_ODP =
		"application/vnd.oasis.opendocument.presentation";

	/**
	 * The MIME type for Open Document (ods) spreadsheets.
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_ODS =
		"application/vnd.oasis.opendocument.spreadsheet";

	/**
	 * The MIME type for Open Document (odt) documents.
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_ODT =
		"application/vnd.oasis.opendocument.text";

	/**
	 * The MIME type for Microsoft PowerPoint (pptx) presentations.
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_PPTX =
		"application/vnd.openxmlformats-officedocument.presentationml." +
			"presentation";

	/**
	 * The MIME type for Microsoft Excel (xlsx) spreadsheets.
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_XLSX =
		"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/**
	 * Misspelled MIME type for Microsoft Excel (xlsx) spreadsheets.
	 *
	 * @deprecated As of Mueller (7.2.x), replace by APPLICATION_VND_XLSX
	 * @review
	 */
	@Deprecated
	public static final String APPLICATION_VND_XSLX = APPLICATION_VND_XLSX;

	/**
	 * The MIME type for Tab Separated Values files.
	 *
	 * @review
	 */
	public static final String TEXT_TAB_SEPARATED_VALUES =
		"text/tab-separated-values";

	public static final Map<String, String> extensions = HashMapBuilder.put(
		APPLICATION_RTF, ".rtf"
	).put(
		APPLICATION_VND_DOCX, ".docx"
	).put(
		APPLICATION_VND_ODP, ".odp"
	).put(
		APPLICATION_VND_ODS, ".ods"
	).put(
		APPLICATION_VND_ODT, ".odt"
	).put(
		APPLICATION_VND_PPTX, ".pptx"
	).put(
		APPLICATION_VND_XLSX, ".xlsx"
	).put(
		ContentTypes.APPLICATION_PDF, ".pdf"
	).put(
		ContentTypes.APPLICATION_TEXT, ".txt"
	).put(
		ContentTypes.IMAGE_PNG, ".png"
	).put(
		ContentTypes.TEXT, ".txt"
	).put(
		ContentTypes.TEXT_CSV, ".csv"
	).put(
		ContentTypes.TEXT_HTML, ".html"
	).put(
		ContentTypes.TEXT_PLAIN, ".txt"
	).put(
		TEXT_TAB_SEPARATED_VALUES, ".tsv"
	).build();

	/**
	 * Returns the canonical file extension associated with the MIME type. The
	 * canonical file extension is the most common one used for documents of
	 * this type (e.g., if the MIME type indicates this is a Microsoft docx
	 * document, the return value is {@code ".docx"}.
	 *
	 * <p>
	 * The return value always includes a dot ({@code .}) as the extension's
	 * first character.
	 * </p>
	 *
	 * @param  mimeType the MIME type
	 * @return the canonical extension, or an empty string if none could be
	 *         determined
	 * @review
	 */
	public static String getMimeTypeExtension(String mimeType) {
		return extensions.getOrDefault(mimeType, StringPool.BLANK);
	}

}