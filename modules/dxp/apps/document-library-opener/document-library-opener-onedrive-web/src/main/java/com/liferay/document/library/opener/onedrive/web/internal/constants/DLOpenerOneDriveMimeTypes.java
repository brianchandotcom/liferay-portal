/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal.constants;

import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

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
	 * Returns the OneDrive MIME type equivalent to the one received.
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

		return mimeType;
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
		return _office365MimeTypes.contains(mimeType);
	}

	private static final Set<String> _office365MimeTypes = SetUtil.fromArray(
		DLOpenerMimeTypes.APPLICATION_VND_DOCX,
		DLOpenerMimeTypes.APPLICATION_VND_PPTX,
		DLOpenerMimeTypes.APPLICATION_VND_XLSX);

}