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

package com.liferay.portal.tools.theme.builder.internal.util;

import com.liferay.portal.tools.theme.builder.ThemeBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static String getExtension(String fileName) {
		int pos = fileName.lastIndexOf('.');

		if (pos == -1) {
			return "";
		}

		return fileName.substring(pos + 1);
	}

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			ThemeBuilder.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static String read(String name) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		ClassLoader classLoader = ThemeBuilder.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(name)) {
			byte[] bytes = new byte[1024];
			int length = 0;

			while ((length = inputStream.read(bytes)) > 0) {
				byteArrayOutputStream.write(bytes, 0, length);
			}
		}

		return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
	}

}