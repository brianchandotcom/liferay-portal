/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.document.conversion;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Bruno Farache
 * @author Alexander Chow
 */
public class DocumentConversionUtil {

	public static File convert(
			String id, InputStream inputStream, String sourceExtension,
			String targetExtension)
		throws IOException {

		DocumentConversion documentConversion =
			_documentConversionSnapshot.get();

		return documentConversion.convert(
			id, inputStream, sourceExtension, targetExtension);
	}

	public static String[] getConversions(String extension) {
		DocumentConversion documentConversion =
			_documentConversionSnapshot.get();

		return documentConversion.getConversions(extension);
	}

	public static String getFilePath(String id, String targetExtension) {
		DocumentConversion documentConversion =
			_documentConversionSnapshot.get();

		return documentConversion.getFilePath(id, targetExtension);
	}

	public static boolean isComparableVersion(String extension) {
		DocumentConversion documentConversion =
			_documentConversionSnapshot.get();

		return documentConversion.isComparableVersion(extension);
	}

	public static boolean isConvertBeforeCompare(String extension) {
		DocumentConversion documentConversion =
			_documentConversionSnapshot.get();

		return documentConversion.isConvertBeforeCompare(extension);
	}

	public static boolean isEnabled() {
		DocumentConversion documentConversion =
			_documentConversionSnapshot.get();

		return documentConversion.isEnabled();
	}

	private static final Snapshot<DocumentConversion>
		_documentConversionSnapshot = new Snapshot<>(
			DocumentConversionUtil.class, DocumentConversion.class);

}