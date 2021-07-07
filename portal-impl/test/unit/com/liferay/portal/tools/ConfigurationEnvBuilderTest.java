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

		Assert.assertEquals(
			"Run \"ant generate-configuration-env\" to regenerate " +
				"modules/configuration-env.txt.",
			ConfigurationEnvBuilder.buildContent(
				configurationJavaFileNames.toArray(new String[0])),
			new String(
				Files.readAllBytes(
					modulesDirPath.resolve("configuration-env.txt"))));
	}

	private static final Pattern _pattern = Pattern.compile(
		StringBundler.concat(
			".*", File.separator, "apps", File.separator, ".*", File.separator,
			"configuration", File.separator, "[^", File.separator,
			"]+Configuration\\.java"));

}