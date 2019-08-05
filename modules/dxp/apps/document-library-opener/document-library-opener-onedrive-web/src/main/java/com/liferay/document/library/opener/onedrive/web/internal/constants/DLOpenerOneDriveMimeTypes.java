/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.constants;

import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ContentTypes;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Cristina González
 */
public class DLOpenerOneDriveMimeTypes {

	/**
	 * The MIME type for Microsoft Office Open XML Format Document with Macros
	 * Enabled (docm).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_DOCM =
		"application/vnd.ms-word.document.macroEnabled.12";

	/**
	 * The MIME type for Microsoft Office Open XML Format Template with Macros
	 * Enabled (dotm).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_DOTM =
		"application/vnd.ms-word.template.macroEnabled.12";

	/**
	 * The MIME type for Microsoft Document Template (dotx).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_DOTX =
		"application/vnd.openxmlformats-officedocument.wordprocessingml." +
			"template";

	/**
	 * The MIME type for Microsoft Office Open XML Format Presentation Template
	 * with Macros Enabled (potm).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_POTM =
		"application/vnd.ms-powerpoint.template.macroEnabled.12";

	/**
	 * The MIME type for Microsoft Office Open XML Format Presentation Template
	 * (potx).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_POTX =
		"application/vnd.openxmlformats-officedocument.presentationml.template";

	/**
	 * The MIME type for Microsoft PowerPoint (ppsm) presentations.
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_PPSM =
		"application/vnd.ms-powerpoint.slideshow.macroEnabled.12";

	/**
	 * The MIME type for Microsoft Macro-Enabled Workbook (pptm).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_PPTM =
		"application/vnd.ms-powerpoint.presentation.macroEnabled.12";

	/**
	 * The MIME type for Microsoft Macro-Enabled Workbook (xlsm).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_XLSM =
		"application/vnd.ms-excel.sheet.macroEnabled.12";

	/**
	 * The MIME type for Microsoft Macro-Enabled Workbook (xltm).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_XLTM =
		"application/vnd.ms-excel.template.macroEnabled.12";

	/**
	 * The MIME type for Microsoft Macro-Enabled Workbook (xltx).
	 *
	 * @review
	 */
	public static final String APPLICATION_VND_XLTX =
		"application/vnd.openxmlformats-officedocument.spreadsheetml.template";

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
		String mimeTypeExtension = DLOpenerMimeTypes.getMimeTypeExtension(
			mimeType);

		if (Objects.equals(StringPool.BLANK, mimeTypeExtension)) {
			return _extensions.getOrDefault(mimeType, StringPool.BLANK);
		}

		return mimeTypeExtension;
	}

	/**
	 * Returns the OneDrive MIME type equivalent to the one received. For
	 * example, this method maps Open Office documents and plain text files to
	 * Word, CSV files to Excel, and and so on.
	 *
	 * <p>
	 * This method returns a valid Office 365 document type only for MIME types
	 * for which {@link #isOffice365MimeTypeSupported(String)} returns {@code
	 * true}. An exception is thrown for any others.
	 * </p>
	 *
	 * @param  mimeType the MIME type to map to OneDrive
	 * @return the equivalent OneDrive MIME type
	 * @review
	 */
	public static String getOffice365MimeType(String mimeType) {
		if (!isOffice365MimeTypeSupported(mimeType)) {
			throw new UnsupportedOperationException(
				"Office 365 does not support edition of documents of type " +
					mimeType);
		}

		return _office365MimeTypes.get(mimeType);
	}

	/**
	 * Returns {@code true} if a MIME type is supported.
	 *
	 * @param  mimeType the MIME type
	 * @return {@code true} if the MIME type is supported; {@code false}
	 *         otherwise
	 * @review
	 */
	public static boolean isOffice365MimeTypeSupported(String mimeType) {
		return _office365MimeTypes.containsKey(mimeType);
	}

	private static final Map<String, String> _extensions = Stream.of(
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_DOCM, ".docm"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_DOTM, ".dotm"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_DOTX, ".dotx"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_POTM, ".potm"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_POTX, ".potx"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_PPSM, ".ppsm"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_PPTM, ".pptm"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_XLSM, ".xlsm"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_XLTM, ".xltm"),
		new AbstractMap.SimpleEntry<>(APPLICATION_VND_XLTX, ".xltx"),
		new AbstractMap.SimpleEntry<>(ContentTypes.APPLICATION_MSWORD, ".doc"),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.APPLICATION_VND_MS_EXCEL, ".xls"),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.APPLICATION_VND_MS_POWERPOINT, ".ppt")
	).collect(
		Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
	);
	private static final Map<String, String> _office365MimeTypes = Stream.of(
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_DOCM, DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_DOTM, DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_DOTX, DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_POTM, DLOpenerMimeTypes.APPLICATION_VND_PPTX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_POTX, DLOpenerMimeTypes.APPLICATION_VND_PPTX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_PPSM, DLOpenerMimeTypes.APPLICATION_VND_PPTX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_PPTM, DLOpenerMimeTypes.APPLICATION_VND_PPTX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_XLSM, DLOpenerMimeTypes.APPLICATION_VND_XLSX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_XLTM, DLOpenerMimeTypes.APPLICATION_VND_XLSX),
		new AbstractMap.SimpleEntry<>(
			APPLICATION_VND_XLTX, DLOpenerMimeTypes.APPLICATION_VND_XLSX),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.APPLICATION_MSWORD,
			DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.APPLICATION_VND_MS_EXCEL,
			DLOpenerMimeTypes.APPLICATION_VND_XLSX),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.APPLICATION_VND_MS_POWERPOINT,
			DLOpenerMimeTypes.APPLICATION_VND_PPTX),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.TEXT, DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.TEXT_CSV, DLOpenerMimeTypes.APPLICATION_VND_XLSX),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.TEXT_HTML, DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			ContentTypes.TEXT_PLAIN, DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_RTF,
			DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_VND_DOCX,
			DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_VND_ODT,
			DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_VND_ODS,
			DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_VND_PPTX,
			DLOpenerMimeTypes.APPLICATION_VND_PPTX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_VND_XLSX,
			DLOpenerMimeTypes.APPLICATION_VND_XLSX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.TEXT_TAB_SEPARATED_VALUES,
			DLOpenerMimeTypes.APPLICATION_VND_XLSX)
	).collect(
		Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
	);

}