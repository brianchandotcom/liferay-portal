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

package com.liferay.portal.tools.css.builder;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class CSSBuilderTest {

	@Test
	public void testGeneratedCss() throws Exception {
		generateCSS();

		String expectedTestCss = readFile(_WORKING_DIR + "expected/test.css");
		String generatedTestCss = readFile(
			_WORKING_DIR + "/tests/.sass-cache/test.css");

		Assert.assertEquals(expectedTestCss, generatedTestCss);

		String expectedTestRtlCss = readFile(
			_WORKING_DIR + "/expected/test_rtl.css");
		String generatedTestRtlCss = readFile(
			_WORKING_DIR + "/tests/.sass-cache/test_rtl.css");

		Assert.assertEquals(expectedTestRtlCss, generatedTestRtlCss);

		String expectedMainCss = readFile(_WORKING_DIR + "/expected/main.css");
		String generatedMainCss = readFile(
			_WORKING_DIR + "/tests/.sass-cache/main.css");

		Assert.assertEquals(expectedMainCss, generatedMainCss);

		String expectedMainRtlCss = readFile(
			_WORKING_DIR + "/expected/main_rtl.css");
		String generatedMainRtlCss = readFile(
			_WORKING_DIR + "/tests/.sass-cache/main_rtl.css");

		Assert.assertEquals(expectedMainRtlCss, generatedMainRtlCss);
	}

	@Test
	public void testSkipUnmodifiedCss() throws Exception {
		File previousTestFile = new File(
			_WORKING_DIR + "/tests/.sass-cache/test.css");
		File previousTestRtlFile = new File(
			_WORKING_DIR + "/tests/.sass-cache/test_rtl.css");
		File previousMainFile = new File(
			_WORKING_DIR + "/tests/.sass-cache/main.css");
		File previousMainRtlFile = new File(
			_WORKING_DIR + "/tests/.sass-cache/main_rtl.css");

		long previousTestFileModifiedDate = previousTestFile.lastModified();
		long previousTestRtlFileModifiedDate =
			previousTestRtlFile.lastModified();
		long previousMainFileModifiedDate = previousMainFile.lastModified();
		long previousMainRtlFileModifiedDate =
			previousMainRtlFile.lastModified();

		generateCSS();

		File newTestFile = new File(
			_WORKING_DIR + "/tests/.sass-cache/test.css");
		File newTestRtlFile = new File(
			_WORKING_DIR + "/tests/.sass-cache/test_rtl.css");
		File newMainFile = new File(
			_WORKING_DIR + "/tests/.sass-cache/main.css");
		File newMainRtlFile = new File(
			_WORKING_DIR + "/tests/.sass-cache/main_rtl.css");

		long newTestFileModifiedDate = newTestFile.lastModified();
		long newTestRtlFileModifiedDate = newTestRtlFile.lastModified();
		long newMainFileModifiedDate = newMainFile.lastModified();
		long newMainRtlFileModifiedDate = newMainRtlFile.lastModified();

		Assert.assertEquals(
			previousTestFileModifiedDate, newTestFileModifiedDate);
		Assert.assertEquals(
			previousTestRtlFileModifiedDate, newTestRtlFileModifiedDate);
		Assert.assertEquals(
			previousMainFileModifiedDate, newMainFileModifiedDate);
		Assert.assertEquals(
			previousMainRtlFileModifiedDate, newMainRtlFileModifiedDate);
	}

	private void generateCSS() throws Exception {
		String[] args = {
			"sass.dir=/tests/", "sass.docroot.dir=" + _WORKING_DIR,
			"sass.portal.common.dir=" +
				"../../../portal-web/docroot/html/css/common/"
		};

		new CSSBuilder(args);
	}

	private String readFile(String fileName) throws Exception {
		Path path = Paths.get(fileName);

		return new String(Files.readAllBytes(path));
	}

	private static final String _WORKING_DIR =
		System.getProperty("user.dir") + "/test-classes/unit/" +
		"com/liferay/portal/tools/css/builder/dependencies/";

}