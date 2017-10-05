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
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 * @author David Truong
 */
public class CSSBuilderTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		URL url = CSSBuilderTest.class.getResource("dependencies");

		Path path = Paths.get(url.toURI());

		_docrootDirName = path.toString();
	}

	@After
	public void tearDown() throws Exception {
		Files.walkFileTree(
			Paths.get(_docrootDirName + "/css/.sass-cache"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path path, IOException ioe)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Test
	public void testCssBuilderWithFragmentChange() throws Exception {
		Path fragmentFileToChange = Paths.get(
			_docrootDirName, "css", "_import_change.scss");

		FileTestUtil.changeContentInPath(fragmentFileToChange, "brown", "khaki");

		executeCSSBuilder(
			"/css", _docrootDirName, false, ".sass-cache/",
			_PORTAL_COMMON_CSS_DIR_NAME, 6, new String[0], "jni");

		String outputCssFilePath =
			_docrootDirName + "/css/.sass-cache/test_import_change.css";

		String outputCssFileContent = FileTestUtil.read(outputCssFilePath);

		FileTestUtil.changeContentInPath(fragmentFileToChange, "khaki", "brown");

		executeCSSBuilder(
			"/css", _docrootDirName, false, ".sass-cache/",
			_PORTAL_COMMON_CSS_DIR_NAME, 6, new String[0], "jni");

		outputCssFileContent = FileTestUtil.read(outputCssFilePath);

		Assert.assertTrue(outputCssFileContent.contains("brown"));
	}

	@Test
	public void testCssBuilderWithJni() throws Exception {
		_testCssBuilder(_PORTAL_COMMON_CSS_DIR_NAME, "jni");
	}

	@Test
	public void testCssBuilderWithJniAndPortalCommonJar() throws Exception {
		_testCssBuilder(_PORTAL_COMMON_CSS_JAR_FILE_NAME, "jni");
	}

	@Test
	public void testCssBuilderWithRuby() throws Exception {
		_testCssBuilder(_PORTAL_COMMON_CSS_DIR_NAME, "ruby");
	}

	@Test
	public void testCssBuilderWithRubyAndPortalCommonJar() throws Exception {
		_testCssBuilder(_PORTAL_COMMON_CSS_DIR_NAME, "ruby");
	}

	protected void executeCSSBuilder(
			String dirName, String docrootDirName, boolean generateSourceMap,
			String outputDirName, String portalCommonPath, int precision,
			String[] rtlExcludedPathRegexps, String sassCompilerClassName)
		throws Exception {

		try (CSSBuilder cssBuilder = new CSSBuilder(
				docrootDirName, generateSourceMap, outputDirName,
				portalCommonPath, precision, rtlExcludedPathRegexps,
				sassCompilerClassName)) {

			cssBuilder.execute(Collections.singletonList(dirName));
		}
	}

	private static void _assertMatchesCount(
		Pattern pattern, String s, int expectedCount) {

		int count = 0;

		Matcher matcher = pattern.matcher(s);

		while (matcher.find()) {
			count++;
		}

		Assert.assertEquals(expectedCount, count);
	}

	private void _testCssBuilder(
			String portalCommonCssPath, String sassCompilerClassName)
		throws Exception {

		executeCSSBuilder(
			"/css", _docrootDirName, false, ".sass-cache/", portalCommonCssPath,
			6, new String[0], sassCompilerClassName);

		String expectedTestContent = FileTestUtil.read(
			_docrootDirName + "/expected/test.css");

		String actualTestContent = FileTestUtil.read(
			_docrootDirName + "/css/.sass-cache/test.css");

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestCssImportContent = FileTestUtil.read(
			_docrootDirName + "/css/.sass-cache/test_css_import.css");

		_assertMatchesCount(_cssImportPattern, actualTestCssImportContent, 3);

		String actualTestCssImportRtlContent = FileTestUtil.read(
			_docrootDirName + "/css/.sass-cache/test_css_import_rtl.css");

		_assertMatchesCount(
			_cssImportPattern, actualTestCssImportRtlContent, 3);

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestPartialContent = FileTestUtil.read(
			_docrootDirName + "/css/.sass-cache/test_partial.css");

		Assert.assertEquals(expectedTestContent, actualTestPartialContent);

		File partialCssFile = new File(
			Paths.get("/css/.sass-cache/_partial.css").toString());

		Assert.assertFalse(partialCssFile.exists());

		String expectedTestRtlContent = FileTestUtil.read(
			_docrootDirName + "/expected/test_rtl.css");

		String actualTestRtlContent = FileTestUtil.read(
			_docrootDirName + "/css/.sass-cache/test_rtl.css");

		Assert.assertEquals(expectedTestRtlContent, actualTestRtlContent);

		String actualTestPartialRtlContent = FileTestUtil.read(
			_docrootDirName + "/css/.sass-cache/test_partial_rtl.css");

		Assert.assertEquals(
			expectedTestRtlContent, actualTestPartialRtlContent);

		String expectedUnicodeContent = FileTestUtil.read(
			_docrootDirName + "/expected/test_unicode.css");

		String actualTestUnicodeContent = FileTestUtil.read(
			_docrootDirName + "/css/.sass-cache/test_unicode.css");

		Assert.assertEquals(expectedUnicodeContent, actualTestUnicodeContent);
	}

	private static final String _PORTAL_COMMON_CSS_DIR_NAME =
		"build/portal-common-css";

	private static final String _PORTAL_COMMON_CSS_JAR_FILE_NAME =
		"build/portal-common-css-jar/com.liferay.frontend.css.common.jar";

	private static final Pattern _cssImportPattern = Pattern.compile(
		"@import\\s+url\\s*\\(\\s*['\"]?(.+\\.css\\?t=\\d+)['\"]?\\s*\\)\\s*;");
	private static String _docrootDirName;

}