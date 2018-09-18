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

package com.liferay.portal.bootstrap;

import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class BundleRevisionTest {

	@Test
	public void testBundleRevisions() throws IOException {
		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_STATE_DIR, "org.eclipse.osgi");

		Assert.assertTrue(path + " not found", Files.exists(path));

		File osgiStateDir = path.toFile();

		for (File file : osgiStateDir.listFiles()) {
			if (!file.isDirectory() || ".manager".equals(file.getName())) {
				continue;
			}

			for (File innerFile : file.listFiles()) {
				Assert.assertTrue(
					_generateMessage(innerFile),
					_expectedNames.contains(innerFile.getName()));
			}
		}
	}

	private String _generateMessage(File file) throws IOException {
		File bundleFile = new File(file, "bundleFile");

		if (!bundleFile.exists()) {
			return "Unexpected directory " + file;
		}

		try (ZipFile zipFile = new ZipFile(bundleFile);
			InputStream inputStream = zipFile.getInputStream(
				zipFile.getEntry("META-INF/MANIFEST.MF"))) {

			Properties properties = new Properties();

			properties.load(inputStream);

			String symbolicName = properties.getProperty("Bundle-SymbolicName");

			return symbolicName + " contains unexpected directory " + file;
		}
	}

	private static final List<String> _expectedNames = Arrays.asList(
		".cp", "0", "data");

}