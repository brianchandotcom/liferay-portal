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

	public static void disconnect() {
		_documentConversion.disconnect();
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