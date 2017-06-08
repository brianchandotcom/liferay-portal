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

import com.helger.commons.charset.CCharset;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.reader.CSSReader;
import com.helger.css.writer.CSSWriterSettings;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class CSSBuilderTest {

	@Test
	public void testLibsassGeneratedCss() throws Exception {
		generateCSS("libsass");

		String expectedTestCss = getFile("expected/test.css");
		String generatedTestCss = getFile("/libsass/.sass-cache/test.css");

		Assert.assertEquals(expectedTestCss, generatedTestCss);

		String expectedTestRtlCss = getFile("/expected/test_rtl.css");
		String generatedTestRtlCss = getFile(
			"/libsass/.sass-cache/test_rtl.css");

		Assert.assertEquals(expectedTestRtlCss, generatedTestRtlCss);

		String expectedMainCss = getFile("/expected/main.css");
		String generatedMainCss = getFile("/libsass/.sass-cache/main.css");

		Assert.assertEquals(expectedMainCss, generatedMainCss);

		String expectedMainRtlCss = getFile("/expected/main_rtl.css");
		String generatedMainRtlCss = getFile(
			"/libsass/.sass-cache/main_rtl.css");

		Assert.assertEquals(expectedMainRtlCss, generatedMainRtlCss);

		File previousTestFile = new File("/libsass/.sass-cache/test.css");
		File previousTestRtlFile = new File(
			"/libsass/.sass-cache/test_rtl.css");
		File previousMainFile = new File("/libsass/.sass-cache/main.css");
		File previousMainRtlFile = new File(
			"/libsass/.sass-cache/main_rtl.css");

		long previousTestFileModifiedDate = previousTestFile.lastModified();
		long previousTestRtlFileModifiedDate =
			previousTestRtlFile.lastModified();
		long previousMainFileModifiedDate = previousMainFile.lastModified();
		long previousMainRtlFileModifiedDate =
			previousMainRtlFile.lastModified();

		generateCSS("libsass");

		File newTestFile = new File("/libsass/.sass-cache/test.css");
		File newTestRtlFile = new File("/libsass/.sass-cache/test_rtl.css");
		File newMainFile = new File("/libsass/.sass-cache/main.css");
		File newMainRtlFile = new File("/libsass/.sass-cache/main_rtl.css");

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

		String expectedTestCss = getFile("expected/test.css");
		String generatedTestCss = getFile("/ruby/.sass-cache/test.css");

		Assert.assertEquals(expectedTestCss, generatedTestCss);

		String expectedTestRtlCss = getFile("/expected/test_rtl.css");
		String generatedTestRtlCss = getFile("/ruby/.sass-cache/test_rtl.css");

		Assert.assertEquals(expectedTestRtlCss, generatedTestRtlCss);

		String expectedMainCss = getFile("/expected/main.css");
		String generatedMainCss = getFile("/ruby/.sass-cache/main.css");

		Assert.assertEquals(expectedMainCss, generatedMainCss);

		String expectedMainRtlCss = getFile("/expected/main_rtl.css");
		String generatedMainRtlCss = getFile("/ruby/.sass-cache/main_rtl.css");

		Assert.assertEquals(expectedMainRtlCss, generatedMainRtlCss);
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

	private void generateCSS(String sassImpl) throws Exception {
		String sassDir = "/" + sassImpl + "/";
		String docrootDirName = _WORKING_DIR;
		String portalCommonDirName = _WORKING_DIR + "common";

		String args[] = new String[] {
			"sass.dir=" + sassDir, "sass.docroot.dir=" + docrootDirName,
			"sass.portal.common.dir=" + portalCommonDirName,
			"sass.compiler.impl=" + sassImpl
		};

		CSSBuilder.main(args);
	}

	private String getFile(String fileName) throws Exception {
		Path path = Paths.get(_WORKING_DIR + fileName);

		String css = new String(Files.readAllBytes(path));

		return formatCss(css);
	}

	private static final String _WORKING_DIR = CSSBuilderTest.class.getResource(
		"dependencies/").getFile();

}