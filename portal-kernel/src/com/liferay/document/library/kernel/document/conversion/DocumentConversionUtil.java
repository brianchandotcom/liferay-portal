/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.document.conversion;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

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

		return _documentConversion.convert(
			id, inputStream, sourceExtension, targetExtension);
	}

	public static String[] getConversions(String extension) {
		return _documentConversion.getConversions(extension);
	}

	public static String getFilePath(String id, String targetExtension) {
		return _documentConversion.getFilePath(id, targetExtension);
	}

	public static boolean isComparableVersion(String extension) {
		return _documentConversion.isComparableVersion(extension);
	}

	public static boolean isConvertBeforeCompare(String extension) {
		return _documentConversion.isConvertBeforeCompare(extension);
	}

	public static boolean isEnabled() {
		return _documentConversion.isEnabled();
	}

	private static volatile DocumentConversion _documentConversion =
		ServiceProxyFactory.newServiceTrackedInstance(
			DocumentConversion.class, DocumentConversionUtil.class,
			"_documentConversion", false);

}