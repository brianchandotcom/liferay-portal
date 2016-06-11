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

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class LPKGUpgradeTest {

	@Test
	public void testUpgradeLPKG() throws IOException {
		String liferayHome = System.getProperty("liferay.home");

		Assert.assertNotNull(
			"Missing system property liferay.home", liferayHome);

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(liferayHome, "/osgi/marketplace"))) {

			for (Path lpkgPath : directoryStream) {
				try (FileSystem fileSystem = FileSystems.newFileSystem(
						lpkgPath, null)) {

					Path path = fileSystem.getPath(
						"liferay-marketplace.properties");

					try (InputStream inputStream = Files.newInputStream(path)) {
						String propertiesString = StringUtil.read(inputStream);

						Properties properties = new Properties();

						properties.load(new StringReader(propertiesString));

						String version = properties.getProperty("version");

						Matcher matcher = _pattern.matcher(version);

						if (matcher.matches()) {
							String newVersion = matcher.group(1);

							newVersion = newVersion.concat(
								String.valueOf(
									Integer.parseInt(matcher.group(2)) + 1));

							propertiesString = StringUtil.replace(
								propertiesString, "version=".concat(version),
								"version=".concat(newVersion));

							Files.write(
								path, propertiesString.getBytes(),
								StandardOpenOption.TRUNCATE_EXISTING,
								StandardOpenOption.WRITE);
						}
					}
				}
			}
		}
	}

	private static final Pattern _pattern = Pattern.compile("(.*\\.)(\\d)$");

}