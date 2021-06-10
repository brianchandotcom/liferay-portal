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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
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
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
@Ignore
public class ConfigurationEnvBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuildContent() throws IOException {
		List<String> configurationJavaFileNames = new ArrayList<>();

		Path modulesDirPath = Paths.get("modules");

		Matcher matcher = _pattern.matcher(StringPool.BLANK);

		Files.walkFileTree(
			modulesDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String pathString = path.toString();

					matcher.reset(pathString);

					if (matcher.matches()) {
						configurationJavaFileNames.add(pathString);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		List<String> expectedList = StringUtil.split(
			ConfigurationEnvBuilder.buildContent(
				configurationJavaFileNames.toArray(new String[0])),
			CharPool.NEW_LINE);

		List<String> actualList = _readPortalProperties();

		Collections.sort(actualList);

		Collections.sort(expectedList);

		actualList = _formatList(actualList);

		expectedList = _formatList(expectedList);

		List<String> leftover = new ArrayList<>();

		for (String line : actualList) {
			if (!expectedList.remove(line)) {
				leftover.add(line);
			}
		}

		Assert.assertTrue(
			StringBundler.concat(_MESSAGE, " Leftover configs: ", leftover),
			leftover.isEmpty());

		Assert.assertTrue(
			StringBundler.concat(_MESSAGE, " Missing configs: ", expectedList),
			expectedList.isEmpty());
	}

	private List<String> _formatList(List<String> lines) {
		List<String> result = new ArrayList<>();

		for (String line : lines) {
			if (line.contains("configuration.override")) {
				line = line.replace(StringPool.POUND, StringPool.BLANK);

				result.add(line.trim());
			}
		}

		return result;
	}

	private List<String> _readPortalProperties() throws IOException {
		Path path = Paths.get("portal-impl/src/portal.properties");

		List<String> lines = Files.readAllLines(path);

		boolean skip = true;

		List<String> result = new ArrayList<>();

		result.add("##");

		for (String line : lines) {
			if (skip) {
				if (line.equals("## Configuration Overrides")) {
					skip = false;

					result.add(line);
				}
			}
			else if (!line.isEmpty()) {
				result.add(line);
			}
		}

		return result;
	}

	private static final String _MESSAGE =
		"Run \"ant generate-configuration-override-properties\" to " +
			"regenerate portal.properties.";

	private static final Pattern _pattern = Pattern.compile(
		StringBundler.concat(
			".*", File.separator, "apps", File.separator, ".*", File.separator,
			"configuration", File.separator, "[^", File.separator,
			"]+Configuration\\.java"));

}