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

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Cristina González
 */
public class DLOpenerOneDriveMimeTypes {

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
		return DLOpenerMimeTypes.getMimeTypeExtension(mimeType);
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

	private static final Map<String, String> _office365MimeTypes = Stream.of(
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_VND_DOCX,
			DLOpenerMimeTypes.APPLICATION_VND_DOCX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_VND_PPTX,
			DLOpenerMimeTypes.APPLICATION_VND_PPTX),
		new AbstractMap.SimpleEntry<>(
			DLOpenerMimeTypes.APPLICATION_VND_XLSX,
			DLOpenerMimeTypes.APPLICATION_VND_XLSX)
	).collect(
		Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
	);

}