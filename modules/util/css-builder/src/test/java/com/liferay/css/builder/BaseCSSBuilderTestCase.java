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
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Eduardo Garcia
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public abstract class BaseCSSBuilderTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		URL url = BaseCSSBuilderTestCase.class.getResource("dependencies");

		_dependenciesDirPath = Paths.get(url.toURI());

		Assert.assertTrue(Files.isDirectory(_dependenciesDirPath));

		_portalCommonDirPath = Paths.get("build/portal-common-css");

		Assert.assertTrue(Files.isDirectory(_portalCommonDirPath));

		_portalCommonJarPath = Paths.get(
			"build/portal-common-css-jar/com.liferay.frontend.css.common.jar");

		Assert.assertTrue(Files.isRegularFile(_portalCommonJarPath));
	}

	@Before
	public void setUp() throws Exception {
		File docrootDir = temporaryFolder.getRoot();

		_docrootDirPath = docrootDir.toPath();

		FileTestUtil.copyDir(
			_dependenciesDirPath.resolve("css"),
			_docrootDirPath.resolve("css"));
	}

	@Test
	public void testCSSBuilderOutputPath() throws Exception {
		Path absolutePath = _docrootDirPath.resolve(
			"absolute").toAbsolutePath();

		String absolutePathString = absolutePath.toString();

		if (!Files.exists(absolutePath)) {
			Files.createDirectory(absolutePath);
		}

		executeCSSBuilder(
			"/css", _docrootDirPath, false, absolutePathString,
			_portalCommonDirPath, 6, new String[0], "jni");

		File absoluteFile = absolutePath.toFile();

		int fileCount = absoluteFile.list().length;

		Assert.assertTrue(fileCount > 0);
	}

	@Test
	public void testCSSBuilderWithFragmentChange() throws Exception {
		Path fragmentChangePath = _docrootDirPath.resolve(
			"css/_import_change.scss");

		FileTestUtil.changeContentInPath(fragmentChangePath, "brown", "khaki");

		executeCSSBuilder(
			"/css", _docrootDirPath, false, ".sass-cache/",
			_portalCommonDirPath, 6, new String[0], "jni");

		Path cssPath = _docrootDirPath.resolve(
			"css/.sass-cache/test_import_change.css");

		String css = FileTestUtil.read(cssPath);

		FileTestUtil.changeContentInPath(fragmentChangePath, "khaki", "brown");

		executeCSSBuilder(
			"/css", _docrootDirPath, false, ".sass-cache/",
			_portalCommonDirPath, 6, new String[0], "jni");

		css = FileTestUtil.read(cssPath);

		Assert.assertTrue(css.contains("brown"));
	}

	@Test
	public void testCSSBuilderWithJni() throws Exception {
		_testCSSBuilder(_portalCommonDirPath, "jni");
	}

	@Test
	public void testCSSBuilderWithJniAndPortalCommonJar() throws Exception {
		_testCSSBuilder(_portalCommonJarPath, "jni");
	}

	@Test
	public void testCSSBuilderWithRuby() throws Exception {
		_testCSSBuilder(_portalCommonDirPath, "ruby");
	}

	@Test
	public void testCSSBuilderWithRubyAndPortalCommonJar() throws Exception {
		_testCSSBuilder(_portalCommonJarPath, "ruby");
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected abstract void executeCSSBuilder(
			String dirName, Path docrootDirPath, boolean generateSourceMap,
			String outputDirName, Path portalCommonPath, int precision,
			String[] rtlExcludedPathRegexps, String sassCompilerClassName)
		throws Exception;

	private static void _assertMatchesCount(
		Pattern pattern, String s, int expectedCount) {

		int count = 0;

		Matcher matcher = pattern.matcher(s);

		while (matcher.find()) {
			count++;
		}

		Assert.assertEquals(expectedCount, count);
	}

	private void _testCSSBuilder(
			Path portalCommonPath, String sassCompilerClassName)
		throws Exception {

		executeCSSBuilder(
			"/css", _docrootDirPath, false, ".sass-cache/", portalCommonPath, 6,
			new String[0], sassCompilerClassName);

		String expectedTestContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test.css"));
		String actualTestContent = FileTestUtil.read(
			_docrootDirPath.resolve("css/.sass-cache/test.css"));

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestImportContent = FileTestUtil.read(
			_docrootDirPath.resolve("css/.sass-cache/test_css_import.css"));

		_assertMatchesCount(_cssImportPattern, actualTestImportContent, 3);

		String actualTestImportRtlContent = FileTestUtil.read(
			_docrootDirPath.resolve("css/.sass-cache/test_css_import_rtl.css"));

		_assertMatchesCount(_cssImportPattern, actualTestImportRtlContent, 3);

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestPartialContent = FileTestUtil.read(
			_docrootDirPath.resolve("css/.sass-cache/test_partial.css"));

		Assert.assertEquals(expectedTestContent, actualTestPartialContent);

		Assert.assertFalse(
			Files.exists(
				_docrootDirPath.resolve("css/.sass-cache/_partial.css")));

		String expectedTestRtlContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test_rtl.css"));
		String actualTestRtlContent = FileTestUtil.read(
			_docrootDirPath.resolve("css/.sass-cache/test_rtl.css"));

		Assert.assertEquals(expectedTestRtlContent, actualTestRtlContent);

		String actualTestPartialRtlContent = FileTestUtil.read(
			_docrootDirPath.resolve("css/.sass-cache/test_partial_rtl.css"));

		Assert.assertEquals(
			expectedTestRtlContent, actualTestPartialRtlContent);

		String expectedUnicodeContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test_unicode.css"));
		String actualTestUnicodeContent = FileTestUtil.read(
			_docrootDirPath.resolve("css/.sass-cache/test_unicode.css"));

		Assert.assertEquals(expectedUnicodeContent, actualTestUnicodeContent);
	}

	private static final Pattern _cssImportPattern = Pattern.compile(
		"@import\\s+url\\s*\\(\\s*['\"]?(.+\\.css\\?t=\\d+)['\"]?\\s*\\)\\s*;");
	private static Path _dependenciesDirPath;
	private static Path _portalCommonDirPath;
	private static Path _portalCommonJarPath;

	private Path _docrootDirPath;

}