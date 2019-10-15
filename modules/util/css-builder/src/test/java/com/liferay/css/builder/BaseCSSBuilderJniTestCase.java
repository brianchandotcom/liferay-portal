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

package com.liferay.css.builder;

import com.liferay.css.builder.util.FileTestUtil;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eduardo García
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public abstract class BaseCSSBuilderJniTestCase extends BaseCSSBuilderTestCase {

	@Test
	public void testCSSBuilderExcludes() throws Exception {
		Path outputDirPath = baseDirPath.resolve("absolute");

		Files.createDirectories(outputDirPath);

		String[] excludes = Arrays.copyOf(EXCLUDES, EXCLUDES.length + 1);

		excludes[excludes.length - 1] = "**/test*.scss";

		executeCSSBuilder(
			baseDirPath, "/css", excludes, false, importDirPath,
			String.valueOf(outputDirPath.toAbsolutePath()), 6, new String[0],
			"jni");

		File outputDir = new File(baseDirPath.toString() + "/css/.sass-cache");

		if (outputDir.exists()) {
			for (File file : outputDir.listFiles()) {
				String fileName = file.getName();

				System.out.println(fileName);

				Assert.assertFalse(fileName.startsWith("test"));
			}
		}
	}

	@Test
	public void testCSSBuilderOutputPath() throws Exception {
		Path outputDirPath = baseDirPath.resolve("absolute");

		Files.createDirectories(outputDirPath);

		executeCSSBuilder(
			baseDirPath, "/css", EXCLUDES, false, importDirPath,
			String.valueOf(outputDirPath.toAbsolutePath()), 6, new String[0],
			"jni");

		File outputDir = outputDirPath.toFile();

		File[] files = outputDir.listFiles();

		Assert.assertTrue(files.length > 0);
	}

	@Test
	public void testCSSBuilderWithFragmentChange() throws Exception {
		Path fragmentChangePath = baseDirPath.resolve(
			"css/_import_change.scss");

		FileTestUtil.changeContentInPath(fragmentChangePath, "brown", "khaki");

		executeCSSBuilder(
			baseDirPath, "/css", EXCLUDES, false, importDirPath, ".sass-cache/",
			6, new String[0], "jni");

		Path cssPath = baseDirPath.resolve(
			"css/.sass-cache/test_import_change.css");

		String css = FileTestUtil.read(cssPath);

		FileTestUtil.changeContentInPath(fragmentChangePath, "khaki", "brown");

		executeCSSBuilder(
			baseDirPath, "/css", EXCLUDES, false, importDirPath, ".sass-cache/",
			6, new String[0], "jni");

		css = FileTestUtil.read(cssPath);

		Assert.assertTrue(css, css.contains("brown"));
	}

	@Test
	public void testCSSBuilderWithJni() throws Exception {
		String output = testCSSBuilder(importDirPath, "jni");

		Assert.assertTrue(
			output, output.contains("Using native Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithJniAndPortalCommonJar() throws Exception {
		String output = testCSSBuilder(importJarPath, "jni");

		Assert.assertTrue(
			output, output.contains("Using native Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithRuby() throws Exception {
		String output = testCSSBuilder(importDirPath, "ruby");

		Assert.assertTrue(output, output.contains("Using Ruby Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithRubyAndPortalCommonJar() throws Exception {
		String output = testCSSBuilder(importJarPath, "ruby");

		Assert.assertTrue(output, output.contains("Using Ruby Sass compiler"));
	}

}