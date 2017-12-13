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

package com.liferay.osgi.bundle.builder.internal.util;

import com.liferay.osgi.bundle.builder.OSGiBundleBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.Properties;

/**
 * @author David Truong
 */
public class FileUtil {

	public static void createDirectories(File dir) throws IOException {
		if (dir.isDirectory()) {
			return;
		}

		boolean created = dir.mkdirs();

		if (!created) {
			throw new IOException("Unable to create " + dir);
		}
	}

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			OSGiBundleBuilder.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static Properties readProperties(File file) throws IOException {
		Properties properties = new Properties();

		try (InputStream inputStream = new FileInputStream(file)) {
			properties.load(inputStream);
		}

		return properties;
	}

}