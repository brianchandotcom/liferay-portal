/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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