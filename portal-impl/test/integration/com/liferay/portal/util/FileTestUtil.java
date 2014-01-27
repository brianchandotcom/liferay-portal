/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.InputStream;

/**
 * @author Cristina González
 */
public class FileTestUtil {

	public static byte[] getBytes(Class<?> clazz, String fileName)
		throws Exception {

		return FileUtil.getBytes(getFileInputStream(clazz, fileName));
	}

	public static InputStream getFileInputStream(
			Class<?> clazz, String fileName)
		throws Exception {

		if (fileName.startsWith(_DEPENDENCIES)) {
			String packageName = clazz.getPackage().getName();

			fileName = packageName.replace(
					StringPool.PERIOD, StringPool.SLASH) +
				StringPool.SLASH  + fileName;
		}

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		return inputStream;
	}

	private static final String _DEPENDENCIES = "dependencies";

}