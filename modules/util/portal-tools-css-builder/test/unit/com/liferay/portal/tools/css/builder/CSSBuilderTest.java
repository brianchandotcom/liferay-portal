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
import java.util.List;

import com.helger.commons.charset.CCharset;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.reader.CSSReader;
import com.helger.css.writer.CSSWriterSettings;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class CSSBuilderTest {

	@Test
	public void testLibsassGeneratedCss() throws Exception {
		generateCSS("libsass");

		String expectedTestCss = readFile(_WORKING_DIR + "expected/test.css");
		String generatedTestCss = readFile(
			_WORKING_DIR + "/libsass/.sass-cache/test.css");

		Assert.assertEquals(formatCss(expectedTestCss), formatCss(generatedTestCss));

		String expectedTestRtlCss = readFile(
			_WORKING_DIR + "/expected/test_rtl.css");
		String generatedTestRtlCss = readFile(
			_WORKING_DIR + "/libsass/.sass-cache/test_rtl.css");

		Assert.assertEquals(formatCss(expectedTestRtlCss), formatCss(generatedTestRtlCss));

		String expectedMainCss = readFile(_WORKING_DIR + "/expected/main.css");
		String generatedMainCss = readFile(
			_WORKING_DIR + "/libsass/.sass-cache/main.css");

		Assert.assertEquals(formatCss(expectedMainCss), formatCss(generatedMainCss));

		String expectedMainRtlCss = readFile(
			_WORKING_DIR + "/expected/main_rtl.css");
		String generatedMainRtlCss = readFile(
			_WORKING_DIR + "/libsass/.sass-cache/main_rtl.css");

		Assert.assertEquals(formatCss(expectedMainRtlCss), formatCss(generatedMainRtlCss));

		File previousTestFile = new File(
			_WORKING_DIR + "/libsass/.sass-cache/test.css");
		File previousTestRtlFile = new File(
			_WORKING_DIR + "/libsass/.sass-cache/test_rtl.css");
		File previousMainFile = new File(
			_WORKING_DIR + "/libsass/.sass-cache/main.css");
		File previousMainRtlFile = new File(
			_WORKING_DIR + "/libsass/.sass-cache/main_rtl.css");

		long previousTestFileModifiedDate = previousTestFile.lastModified();
		long previousTestRtlFileModifiedDate =
			previousTestRtlFile.lastModified();
		long previousMainFileModifiedDate = previousMainFile.lastModified();
		long previousMainRtlFileModifiedDate =
			previousMainRtlFile.lastModified();

		generateCSS("libsass");

		File newTestFile = new File(
			_WORKING_DIR + "/libsass/.sass-cache/test.css");
		File newTestRtlFile = new File(
			_WORKING_DIR + "/libsass/.sass-cache/test_rtl.css");
		File newMainFile = new File(
			_WORKING_DIR + "/libsass/.sass-cache/main.css");
		File newMainRtlFile = new File(
			_WORKING_DIR + "/libsass/.sass-cache/main_rtl.css");

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

	@Test
	public void testRubyGeneratedCss() throws Exception {
		generateCSS("ruby");

		String expectedTestCss = readFile(_WORKING_DIR + "expected/test.css");
		String generatedTestCss = readFile(
			_WORKING_DIR + "/ruby/.sass-cache/test.css");

		Assert.assertEquals(formatCss(expectedTestCss), formatCss(generatedTestCss));

		String expectedTestRtlCss = readFile(
			_WORKING_DIR + "/expected/test_rtl.css");
		String generatedTestRtlCss = readFile(
			_WORKING_DIR + "/ruby/.sass-cache/test_rtl.css");

		Assert.assertEquals(formatCss(expectedTestRtlCss), formatCss(generatedTestRtlCss));

		String expectedMainCss = readFile(_WORKING_DIR + "/expected/main.css");
		String generatedMainCss = readFile(
			_WORKING_DIR + "/ruby/.sass-cache/main.css");

		Assert.assertEquals(formatCss(expectedMainCss), formatCss(generatedMainCss));

		String expectedMainRtlCss = readFile(
			_WORKING_DIR + "/expected/main_rtl.css");
		String generatedMainRtlCss = readFile(
			_WORKING_DIR + "/ruby/.sass-cache/main_rtl.css");

		Assert.assertEquals(formatCss(expectedMainRtlCss), formatCss(generatedMainRtlCss));
	}

	private void generateCSS(String sassImpl) throws Exception {
		String sassDir = "/" + sassImpl + "/";
		String docrootDirName = _WORKING_DIR;
		String portalCommonDirName =
			_WORKING_DIR + "common";

		new CSSBuilder(sassDir, docrootDirName, portalCommonDirName, sassImpl);
	}

	protected String formatCss(String css) {
		CascadingStyleSheet cascadingStyleSheet = CSSReader.readFromString(
			css, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);

		List<CSSStyleRule> cssStyleRules =
			cascadingStyleSheet.getAllStyleRules();

		StringBuilder sb = new StringBuilder(cssStyleRules.size());

		CSSWriterSettings cssWriterSettings = new CSSWriterSettings(
			ECSSVersion.CSS30, true);

		for (CSSStyleRule cssStyleRule : cssStyleRules) {
			sb.append(cssStyleRule.getAsCSSString(cssWriterSettings, 1));
		}

		return sb.toString();
	}

	private String readFile(String fileName) throws Exception {
		Path path = Paths.get(fileName);

		return new String(Files.readAllBytes(path));
	}

	private static final String _WORKING_DIR =
		System.getProperty("user.dir") + "/test-classes/unit/" +
		"com/liferay/portal/tools/css/builder/dependencies/";

}