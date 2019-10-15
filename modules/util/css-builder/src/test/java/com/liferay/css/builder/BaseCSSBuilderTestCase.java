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

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Eduardo García
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public abstract class BaseCSSBuilderTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		URL url = BaseCSSBuilderTestCase.class.getResource("dependencies");

		_dependenciesDirPath = Paths.get(url.toURI());

		Assert.assertTrue(Files.isDirectory(_dependenciesDirPath));

		importDirPath = Paths.get("build/portal-common-css");

		Assert.assertTrue(Files.isDirectory(importDirPath));

		importJarPath = Paths.get(
			"build/portal-common-css-jar/com.liferay.frontend.css.common.jar");

		Assert.assertTrue(Files.isRegularFile(importJarPath));
	}

	@Before
	public void setUp() throws Exception {
		File docrootDir = temporaryFolder.getRoot();

		baseDirPath = docrootDir.toPath();

		FileTestUtil.copyDir(
			_dependenciesDirPath.resolve("css"), baseDirPath.resolve("css"));
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected abstract String executeCSSBuilder(
			Path baseDirPath, String dirName, String[] excludes,
			boolean generateSourceMap, Path importDirPath, String outputDirName,
			int precision, String[] rtlExcludedPathRegexps,
			String sassCompilerClassName)
		throws Exception;

	protected String testCSSBuilder(
			Path importDirPath, String sassCompilerClassName)
		throws Exception {

		String output = executeCSSBuilder(
			baseDirPath, "/css", EXCLUDES, false, importDirPath, ".sass-cache/",
			6, new String[0], sassCompilerClassName);

		String expectedTestContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test.css"));
		String actualTestContent = FileTestUtil.read(
			baseDirPath.resolve("css/.sass-cache/test.css"));

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestImportContent = FileTestUtil.read(
			baseDirPath.resolve("css/.sass-cache/test_css_import.css"));

		_assertMatchesCount(_cssImportPattern, actualTestImportContent, 3);

		String actualTestImportRtlContent = FileTestUtil.read(
			baseDirPath.resolve("css/.sass-cache/test_css_import_rtl.css"));

		_assertMatchesCount(_cssImportPattern, actualTestImportRtlContent, 3);

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestPartialContent = FileTestUtil.read(
			baseDirPath.resolve("css/.sass-cache/test_partial.css"));

		Assert.assertEquals(expectedTestContent, actualTestPartialContent);

		Assert.assertFalse(
			Files.exists(baseDirPath.resolve("css/.sass-cache/_partial.css")));

		String expectedTestRtlContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test_rtl.css"));
		String actualTestRtlContent = FileTestUtil.read(
			baseDirPath.resolve("css/.sass-cache/test_rtl.css"));

		Assert.assertEquals(expectedTestRtlContent, actualTestRtlContent);

		String actualTestPartialRtlContent = FileTestUtil.read(
			baseDirPath.resolve("css/.sass-cache/test_partial_rtl.css"));

		Assert.assertEquals(
			expectedTestRtlContent, actualTestPartialRtlContent);

		String expectedUnicodeContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test_unicode.css"));
		String actualTestUnicodeContent = FileTestUtil.read(
			baseDirPath.resolve("css/.sass-cache/test_unicode.css"));

		Assert.assertEquals(expectedUnicodeContent, actualTestUnicodeContent);

		return output;
	}

	protected static final String[] EXCLUDES = CSSBuilderArgs.EXCLUDES;

	protected static Path importDirPath;
	protected static Path importJarPath;

	protected Path baseDirPath;

	private static void _assertMatchesCount(
		Pattern pattern, String s, int expectedCount) {

		int count = 0;

		Matcher matcher = pattern.matcher(s);

		while (matcher.find()) {
			count++;
		}

		Assert.assertEquals(expectedCount, count);
	}

	private static final Pattern _cssImportPattern = Pattern.compile(
		"@import\\s+url\\s*\\(\\s*['\"]?(.+\\.css\\?t=\\d+)['\"]?\\s*\\)\\s*;");
	private static Path _dependenciesDirPath;

}