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

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class LPKGVersionChangeLogAssertorTest {

	@Test
	public void testUpgradeLog() throws IOException {
		String liferayHome = System.getProperty("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		final Set<String> symbolicNames = new HashSet<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(liferayHome, "/osgi/marketplace"))) {

			for (Path lpkgPath : directoryStream) {
				String lpkgPathString = lpkgPath.toString();

				if (lpkgPathString.contains("override")) {
					continue;
				}

				try (ZipFile zipFile = new ZipFile(lpkgPath.toFile());
					InputStream inputStream = zipFile.getInputStream(
						new ZipEntry("liferay-marketplace.properties"))) {

					Properties properties = new Properties();

					properties.load(inputStream);

					symbolicNames.add(properties.getProperty("title"));
				}
			}
		}

		Path logsPath = Paths.get(
			System.getProperty("liferay.log.dir", liferayHome.concat("/logs")));

		boolean hasLog = false;

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				logsPath, "*.log")) {

			for (Path logPath : directoryStream) {
				hasLog = true;

				assertUpgrade(logPath, symbolicNames);
			}
		}

		Assert.assertTrue(
			"Unable to find any log file under " + logsPath, hasLog);
	}

	protected void assertUpgrade(Path path, Set<String> symbolicNames)
		throws IOException {

		String logContent = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		for (String symbolicName : symbolicNames) {
			Assert.assertTrue(
				symbolicName.concat(" was not uninstalled for upgrade"),
				logContent.contains(
					"Uninstalled older LPKG bundle ".concat(symbolicName)));
			Assert.assertTrue(
				symbolicName.concat(" did not start refreshing for upgrade"),
				logContent.contains(
					"Start refreshing references to point to the new " +
						"bundle ".concat(symbolicName)));
			Assert.assertTrue(
				symbolicName.concat(" did not finish refreshing for upgrade"),
				logContent.contains(
					"Finished refreshing references to point to the new " +
						"bundle ".concat(symbolicName)));
		}
	}

}