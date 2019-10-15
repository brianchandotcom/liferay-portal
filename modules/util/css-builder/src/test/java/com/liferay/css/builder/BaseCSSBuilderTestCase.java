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

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
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

		_importDirPath = Paths.get("build/portal-common-css");

		Assert.assertTrue(Files.isDirectory(_importDirPath));

		_importJarPath = Paths.get(
			"build/portal-common-css-jar/com.liferay.frontend.css.common.jar");

		Assert.assertTrue(Files.isRegularFile(_importJarPath));
	}

	@Before
	public void setUp() throws Exception {
		File docrootDir = temporaryFolder.getRoot();

		_baseDirPath = docrootDir.toPath();

		FileTestUtil.copyDir(
			_dependenciesDirPath.resolve("css"), _baseDirPath.resolve("css"));
	}

	@Test
	public void testCSSBuilderExcludes() throws Exception {
		Path outputDirPath = _baseDirPath.resolve("absolute");

		Files.createDirectories(outputDirPath);

		String[] excludes = Arrays.copyOf(_EXCLUDES, _EXCLUDES.length + 1);

		excludes[excludes.length - 1] = "**/test*.scss";

		executeCSSBuilder(
			_baseDirPath, "/css", excludes, false, _importDirPath,
			String.valueOf(outputDirPath.toAbsolutePath()), 6, new String[0],
			"jni");

		File outputDir = new File(_baseDirPath.toString() + "/css/.sass-cache");

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
		Path outputDirPath = _baseDirPath.resolve("absolute");

		Files.createDirectories(outputDirPath);

		executeCSSBuilder(
			_baseDirPath, "/css", _EXCLUDES, false, _importDirPath,
			String.valueOf(outputDirPath.toAbsolutePath()), 6, new String[0],
			"jni");

		File outputDir = outputDirPath.toFile();

		File[] files = outputDir.listFiles();

		Assert.assertTrue(files.length > 0);
	}

	@Test
	public void testCSSBuilderWithFragmentChange() throws Exception {
		Path fragmentChangePath = _baseDirPath.resolve(
			"css/_import_change.scss");

		FileTestUtil.changeContentInPath(fragmentChangePath, "brown", "khaki");

		executeCSSBuilder(
			_baseDirPath, "/css", _EXCLUDES, false, _importDirPath,
			".sass-cache/", 6, new String[0], "jni");

		Path cssPath = _baseDirPath.resolve(
			"css/.sass-cache/test_import_change.css");

		String css = FileTestUtil.read(cssPath);

		FileTestUtil.changeContentInPath(fragmentChangePath, "khaki", "brown");

		executeCSSBuilder(
			_baseDirPath, "/css", _EXCLUDES, false, _importDirPath,
			".sass-cache/", 6, new String[0], "jni");

		css = FileTestUtil.read(cssPath);

		Assert.assertTrue(css, css.contains("brown"));
	}

	@Test
	public void testCSSBuilderWithJni() throws Exception {
		String output = _testCSSBuilder(_importDirPath, "jni");

		Assert.assertTrue(
			output, output.contains("Using native Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithJni32() throws Exception {
		String output = _testCSSBuilder(_importDirPath, "jni32");

		Assert.assertTrue(
			output, output.contains("Using native 32-bit Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithJni32AndPortalCommonJar() throws Exception {
		String output = _testCSSBuilder(_importJarPath, "jni32");

		Assert.assertTrue(
			output, output.contains("Using native 32-bit Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithJniAndPortalCommonJar() throws Exception {
		String output = _testCSSBuilder(_importJarPath, "jni");

		Assert.assertTrue(
			output, output.contains("Using native Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithRuby() throws Exception {
		String output = _testCSSBuilder(_importDirPath, "ruby");

		Assert.assertTrue(output, output.contains("Using Ruby Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithRubyAndPortalCommonJar() throws Exception {
		String output = _testCSSBuilder(_importJarPath, "ruby");

		Assert.assertTrue(output, output.contains("Using Ruby Sass compiler"));
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected abstract String executeCSSBuilder(
			Path baseDirPath, String dirName, String[] excludes,
			boolean generateSourceMap, Path importDirPath, String outputDirName,
			int precision, String[] rtlExcludedPathRegexps,
			String sassCompilerClassName)
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

	private String _testCSSBuilder(
			Path importDirPath, String sassCompilerClassName)
		throws Exception {

		String output = executeCSSBuilder(
			_baseDirPath, "/css", _EXCLUDES, false, importDirPath,
			".sass-cache/", 6, new String[0], sassCompilerClassName);

		String expectedTestContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test.css"));
		String actualTestContent = FileTestUtil.read(
			_baseDirPath.resolve("css/.sass-cache/test.css"));

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestImportContent = FileTestUtil.read(
			_baseDirPath.resolve("css/.sass-cache/test_css_import.css"));

		_assertMatchesCount(_cssImportPattern, actualTestImportContent, 3);

		String actualTestImportRtlContent = FileTestUtil.read(
			_baseDirPath.resolve("css/.sass-cache/test_css_import_rtl.css"));

		_assertMatchesCount(_cssImportPattern, actualTestImportRtlContent, 3);

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestPartialContent = FileTestUtil.read(
			_baseDirPath.resolve("css/.sass-cache/test_partial.css"));

		Assert.assertEquals(expectedTestContent, actualTestPartialContent);

		Assert.assertFalse(
			Files.exists(_baseDirPath.resolve("css/.sass-cache/_partial.css")));

		String expectedTestRtlContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test_rtl.css"));
		String actualTestRtlContent = FileTestUtil.read(
			_baseDirPath.resolve("css/.sass-cache/test_rtl.css"));

		Assert.assertEquals(expectedTestRtlContent, actualTestRtlContent);

		String actualTestPartialRtlContent = FileTestUtil.read(
			_baseDirPath.resolve("css/.sass-cache/test_partial_rtl.css"));

		Assert.assertEquals(
			expectedTestRtlContent, actualTestPartialRtlContent);

		String expectedUnicodeContent = FileTestUtil.read(
			_dependenciesDirPath.resolve("expected/test_unicode.css"));
		String actualTestUnicodeContent = FileTestUtil.read(
			_baseDirPath.resolve("css/.sass-cache/test_unicode.css"));

		Assert.assertEquals(expectedUnicodeContent, actualTestUnicodeContent);

		return output;
	}

	private static final String[] _EXCLUDES = CSSBuilderArgs.EXCLUDES;

	private static final Pattern _cssImportPattern = Pattern.compile(
		"@import\\s+url\\s*\\(\\s*['\"]?(.+\\.css\\?t=\\d+)['\"]?\\s*\\)\\s*;");
	private static Path _dependenciesDirPath;
	private static Path _importDirPath;
	private static Path _importJarPath;

	private Path _baseDirPath;

}