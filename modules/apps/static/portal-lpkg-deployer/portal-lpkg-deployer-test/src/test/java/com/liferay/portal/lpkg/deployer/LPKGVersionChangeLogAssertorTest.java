/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer;

import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class LPKGVersionChangeLogAssertorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUpgradeLog() throws IOException {
		String liferayHome = SystemProperties.get("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		Set<String> symbolicNames = new HashSet<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(liferayHome, "/osgi/marketplace"))) {

			for (Path lpkgPath : directoryStream) {
				String lpkgPathString = lpkgPath.toString();

				if (lpkgPathString.contains("override")) {
					continue;
				}

				File lpkgFile = lpkgPath.toFile();

				String name = lpkgFile.getName();

				symbolicNames.add(name.substring(0, name.length() - 5));
			}
		}

		Path logsPath = Paths.get(
			System.getProperty("liferay.log.dir", liferayHome.concat("/logs")));

		boolean hasLog = false;

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				logsPath, "liferay*.log")) {

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