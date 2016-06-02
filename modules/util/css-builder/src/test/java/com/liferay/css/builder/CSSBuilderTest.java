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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;

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
	public void testCssBuilderWithJni() throws Exception {
		_testCssBuilder("jni", _PORTAL_COMMON_CSS_DIR_NAME);
	}

	@Test
	public void testCssBuilderWithJniWithPortalCommonJar() throws Exception {
		_testCssBuilder(
			"jni",
			"build/portal-common-css-jar/" +
				"com.liferay.frontend.css.common-2.0.1.jar");
	}

	@Test
	public void testCssBuilderWithRuby() throws Exception {
		_testCssBuilder("ruby", _PORTAL_COMMON_CSS_DIR_NAME);
	}

	@Test
	public void testCssBuilderWithRubyWithPortalCommonJar() throws Exception {
		_testCssBuilder(
			"ruby",
			"build/portal-common-css-jar/" +
				"com.liferay.frontend.css.common-2.0.1.jar");
	}

	private String _read(String fileName) throws Exception {
		Path path = Paths.get(fileName);

		String s = new String(Files.readAllBytes(path), StringPool.UTF8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	private void _testCssBuilder(String compiler, String portalCommonCssPath)
		throws Exception {

		try (CSSBuilder cssBuilder = new CSSBuilder(
				_docrootDirName, false, ".sass-cache/", portalCommonCssPath, 6,
				new String[0], compiler)) {

			cssBuilder.execute(Arrays.asList(new String[] {"/css"}));
		}

		String expectedTestContent = _read(
			_docrootDirName + "/expected/test.css");

		String actualTestContent = _read(
			_docrootDirName + "/css/.sass-cache/test.css");

		Assert.assertEquals(expectedTestContent, actualTestContent);

		String actualTestPartialContent = _read(
			_docrootDirName + "/css/.sass-cache/test_partial.css");

		Assert.assertEquals(expectedTestContent, actualTestPartialContent);

		File partialCssFile = new File(
			Paths.get("/css/.sass-cache/_partial.css").toString());

		Assert.assertFalse(partialCssFile.exists());

		String expectedTestRtlContent = _read(
			_docrootDirName + "/expected/test_rtl.css");

		String actualTestRtlContent = _read(
			_docrootDirName + "/css/.sass-cache/test_rtl.css");

		Assert.assertEquals(expectedTestRtlContent, actualTestRtlContent);

		String actualTestPartialRtlContent = _read(
			_docrootDirName + "/css/.sass-cache/test_partial_rtl.css");

		Assert.assertEquals(
			expectedTestRtlContent, actualTestPartialRtlContent);

		String expectedUnicodeContent = _read(
			_docrootDirName + "/expected/test_unicode.css");

		String actualTestUnicodeContent = _read(
			_docrootDirName + "/css/.sass-cache/test_unicode.css");

		Assert.assertEquals(expectedUnicodeContent, actualTestUnicodeContent);
	}

	private static final String _PORTAL_COMMON_CSS_DIR_NAME =
		"build/portal-common-css";

	private static String _docrootDirName;

}