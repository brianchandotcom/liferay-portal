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

package com.liferay.portal.kernel.util;

import java.io.File;
import java.io.InputStream;

/**
 * @author     Bruno Farache
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil}
 */
@Deprecated
public class DocumentConversionUtil {

	public static File convert(
			String id, InputStream inputStream, String sourceExtension,
			String targetExtension)
		throws Exception {

		return com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.convert(
				id, inputStream, sourceExtension, targetExtension);
	}

	public static String[] getConversions(String extension) throws Exception {
		return com.liferay.document.library.kernel.document.conversion.
			DocumentConversionUtil.getConversions(extension);
	}

}