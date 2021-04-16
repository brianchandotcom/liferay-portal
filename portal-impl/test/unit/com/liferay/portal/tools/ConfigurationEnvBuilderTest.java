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

package com.liferay.portal.tools;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ConfigurationEnvBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testScanConfigurationEnvFile() throws IOException {
		Path modulesDirPath = Paths.get("modules");

		List<String> configFiles = new ArrayList<>();

		Matcher matcher = _configPattern.matcher(StringPool.BLANK);

		Files.walkFileTree(
			modulesDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes attrs)
					throws IOException {

					String pathString = path.toString();

					matcher.reset(pathString);

					if (matcher.matches()) {
						configFiles.add(pathString);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		String expectedContent =
			ConfigurationEnvBuilder.generateConfigOverrideContent(
				configFiles.toArray(new String[0]));

		String actualContent = new String(
			Files.readAllBytes(modulesDirPath.resolve("config-env.text")));

		Assert.assertEquals(
			"Please run \"ant generate-config-env-vars\" to regenerate " +
				"config-env.text file",
			expectedContent, actualContent);
	}

	private static final Pattern _configPattern = Pattern.compile(
		StringBundler.concat(
			".*", File.separator, "apps", File.separator, ".*", File.separator,
			"configuration", File.separator, "[^", File.separator,
			"]+Configuration\\.java"));

}