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

package com.liferay.document.library.opener.constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ContentTypes;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public static final Map<String, String> extensions = Stream.of(
		new AbstractMap.SimpleEntry<>(ContentTypes.APPLICATION_PDF, ".pdf"),
		new AbstractMap.SimpleEntry<>(APPLICATION_RTF, ".rtf"),
		new AbstractMap.SimpleEntry<>(ContentTypes.APPLICATION_TEXT, ".txt"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_DOCX, ".docx"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_ODP, ".odp"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_ODS, ".ods"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_ODT, ".odt"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_PPTX, ".pptx"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_XLSX, ".xlsx"),
		new AbstractMap.SimpleEntry<>(ContentTypes.IMAGE_PNG, ".png"),
		new AbstractMap.SimpleEntry<>(ContentTypes.TEXT, ".txt"),
		new AbstractMap.SimpleEntry<>(ContentTypes.TEXT_CSV, ".csv"),
		new AbstractMap.SimpleEntry<>(ContentTypes.TEXT_PLAIN, ".txt"),
		new AbstractMap.SimpleEntry<>(ContentTypes.TEXT_HTML, ".html"),
		new AbstractMap.SimpleEntry<>(TEXT_TAB_SEPARATED_VALUES, ".tsv")
	).collect(
		Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
	);

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