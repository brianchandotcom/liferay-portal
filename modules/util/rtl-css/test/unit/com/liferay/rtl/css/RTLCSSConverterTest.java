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

package com.liferay.rtl.css;

import com.helger.commons.charset.CCharset;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.reader.CSSReader;
import com.helger.css.writer.CSSWriterSettings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author David Truong
 */
public class RTLCSSConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAsterisk() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		String expected = "a{*margin-left:5px}";

		String css = "a{*margin-right: 5px}";

		String actual = rtlCssConverter.process(css);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testBackgroundPosition() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		String expected =
			"a{background-position:left top}b{background-position:right 10%}";

		String css =
			"a{background-position:right top}b{background-position:10%}";

		String actual = rtlCssConverter.process(css);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testPortalCss() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		String expected = formatCss(read("main_rtl.css"));

		String css = read("main.css");

		String actual = rtlCssConverter.process(css);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReplacement() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		String expected = "a{right:5px;left:15px}b{margin-right:5px}";

		String css = "a{left:5px;right:15px}b{margin-left:5px}";

		String actual = rtlCssConverter.process(css);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReverse() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		String expected = "a{text-align:right}";

		String css = "a{text-align:left}";

		String actual = rtlCssConverter.process(css);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testShorthand() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		String expected = "a{padding:1px 4px 3px 2px}";

		String css = "a{padding:1px 2px 3px 4px}";

		String actual = rtlCssConverter.process(css);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testShorthandRadius() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		String expected =
			"a{border-radius:2px 1px 4px 3px}" +
				"b{border-radius:10px 5px 10px 20px}";

		String css =
			"a{border-radius:1px 2px 3px 4px}b{border-radius:5px 10px 20px}";

		String actual = rtlCssConverter.process(css);

		Assert.assertEquals(expected, actual);
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

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		Path path =
			Paths.get(clazz.getResource("dependencies/" + fileName).toURI());

		return new String(Files.readAllBytes(path));
	}

}