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

package com.liferay.portal.lpkg;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;
import java.util.Properties;

import org.junit.Assert;

import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
public class LPKGVersionChangeTestCase {

	protected void doVersionChange(
			int majorDelta, int minorDelta, int microDelta)
		throws IOException {

		String liferayHome = System.getProperty("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(liferayHome, "/osgi/marketplace"))) {

			for (Path lpkgPath : directoryStream) {
				try (FileSystem fileSystem = FileSystems.newFileSystem(
						lpkgPath, null)) {

					Path path = fileSystem.getPath(
						"liferay-marketplace.properties");

					String propertiesString = new String(
						Files.readAllBytes(path), StandardCharsets.UTF_8);

					Properties properties = new Properties();

					properties.load(new UnsyncStringReader(propertiesString));

					String versionString = properties.getProperty("version");

					Version version = new Version(versionString);

					version = new Version(
						version.getMajor() + majorDelta,
						version.getMinor() + minorDelta,
						version.getMicro() + microDelta);

					propertiesString = StringUtil.replace(
						propertiesString, "version=".concat(versionString),
						"version=".concat(version.toString()));

					Files.write(
						path, Arrays.asList(propertiesString),
						StandardCharsets.UTF_8,
						StandardOpenOption.TRUNCATE_EXISTING,
						StandardOpenOption.WRITE);
				}
			}
		}
	}

}