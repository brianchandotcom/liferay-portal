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

package com.liferay.portlet.documentlibrary.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author     Bruno Farache
 * @author     Alexander Chow
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil}
 */
@Deprecated
public class DocumentConversionUtil {

	public static File convert(
			String id, InputStream inputStream, String sourceExtension,
			String targetExtension)
		throws IOException {

		return com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.convert(
				id, inputStream, sourceExtension, targetExtension);
	}

	public static void disconnect() {
		com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.disconnect();
	}

	public static String[] getConversions(String extension) {
		return com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.getConversions(extension);
	}

	public static String getFilePath(String id, String targetExtension) {
		return com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.getFilePath(id, targetExtension);
	}

	public static boolean isComparableVersion(String extension) {
		return com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.isComparableVersion(extension);
	}

	public static boolean isConvertBeforeCompare(String extension) {
		return com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.isConvertBeforeCompare(extension);
	}

	public static boolean isEnabled() {
		return com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.isEnabled();
	}

}