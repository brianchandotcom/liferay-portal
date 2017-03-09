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

package com.liferay.portal.tools.db.support.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

/**
 * @author Andrea Di Giorgi
 */
public class FileTestUtil {

	public static String getAbsolutePath(File file) {
		String absolutePath = file.getAbsolutePath();

		if (File.separatorChar != '/') {
			absolutePath = absolutePath.replace(File.separatorChar, '/');
		}

		return absolutePath;
	}

	public static String read(Class<?> clazz, String name) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (InputStream inputStream = clazz.getResourceAsStream(name)) {
			byte[] bytes = new byte[1024];
			int length = 0;

			while ((length = inputStream.read(bytes)) > 0) {
				byteArrayOutputStream.write(bytes, 0, length);
			}
		}

		return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
	}

}