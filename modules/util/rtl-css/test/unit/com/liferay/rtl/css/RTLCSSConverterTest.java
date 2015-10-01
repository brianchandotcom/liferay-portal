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

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

		Assert.assertEquals(
			"p{*right:50%}",
			rtlCssConverter.process("p{*left:50%}"));
		Assert.assertEquals(
			"p{*text-align:left}",
			rtlCssConverter.process("p{*text-align:right}"));
	}

	@Test
	public void testBackground() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{background:url(../left/right/test_right.png) right 30%}",
			rtlCssConverter.process(
				"p{background:url(../left/right/test_left.png) left 30%}"));
		Assert.assertEquals(
			"p{background:url(../left/right/test_right.png) 80% 10%}",
			rtlCssConverter.process(
				"p{background:url(../left/right/test_left.png) 20% 10%}"));
		Assert.assertEquals(
			"p{background:color url(../left/right/test_right.png) repeat " +
				"left 20%}",
			rtlCssConverter.process(
				"p{background:color url(../left/right/test_left.png) repeat " +
					"right 20%}"));
		Assert.assertEquals(
			"p{background-image:url(../atleft/right/test_right.png)}",
			rtlCssConverter.process(
				"p{background-image:url(../atleft/right/test_left.png)}"));
	}

	@Test
	public void testBackgroundPosition() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{background-position:right top}",
			rtlCssConverter.process("p{background-position:left top}"));
		Assert.assertEquals(
			"p{background-position:right 20px}",
			rtlCssConverter.process("p{background-position:20px}"));
		Assert.assertEquals(
			"p{background-position:80% top}",
			rtlCssConverter.process("p{background-position:20% top}"));
	}

	@Test
	public void testBorder() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{border-right:1px}",
			rtlCssConverter.process("p{border-left:1px}"));
		Assert.assertEquals(
			"p{border-left:1px}",
			rtlCssConverter.process("p{border-right:1px}"));
		Assert.assertEquals(
			"p{border-left:1px solid #000}",
			rtlCssConverter.process("p{border-right:1px solid #000}"));
		Assert.assertEquals(
			"p{border-style:solid}",
			rtlCssConverter.process("p{border-style:solid}"));
		Assert.assertEquals(
			"p{border-style:none solid}",
			rtlCssConverter.process("p{border-style:none solid}"));
		Assert.assertEquals(
			"p{border-style:none solid dashed}",
			rtlCssConverter.process("p{border-style:none solid dashed}"));
		Assert.assertEquals(
			"p{border-style:none double dashed solid}",
			rtlCssConverter.process(
				"p{border-style:none solid dashed double}"));
		Assert.assertEquals(
			"p{border-color:#fff}",
			rtlCssConverter.process("p{border-color:#fff}"));
		Assert.assertEquals(
			"p{border-color:#fff #000}",
			rtlCssConverter.process("p{border-color:#fff #000}"));
		Assert.assertEquals(
			"p{border-color:#000 #111 #222}",
			rtlCssConverter.process("p{border-color:#000 #111 #222}"));
		Assert.assertEquals(
			"p{border-color:#000 #333 #222 #111}",
			rtlCssConverter.process("p{border-color:#000 #111 #222 #333}"));
		Assert.assertEquals(
			"p{border-right-color:#fff}",
			rtlCssConverter.process("p{border-left-color:#fff}"));
		Assert.assertEquals(
			"p{border-left-color:#fff}",
			rtlCssConverter.process("p{border-right-color:#fff}"));
		Assert.assertEquals(
			"p{border-width:0}", rtlCssConverter.process("p{border-width:0}"));
		Assert.assertEquals(
			"p{border-width:0 1px}",
			rtlCssConverter.process("p{border-width:0 1px}"));
		Assert.assertEquals(
			"p{border-width:0 1px 2px}",
			rtlCssConverter.process("p{border-width:0 1px 2px}"));
		Assert.assertEquals(
			"p{border-width:0 3px 2px 1px}",
			rtlCssConverter.process("p{border-width:0 1px 2px 3px}"));
	}

	@Test
	public void testBorderRadius() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{border-radius:0}",
			rtlCssConverter.process("p{border-radius:0}"));
		Assert.assertEquals(
			"p{-moz-border-radius:0}",
			rtlCssConverter.process("p{-moz-border-radius:0}"));
		Assert.assertEquals(
			"p{-webkit-border-radius:0}",
			rtlCssConverter.process("p{-webkit-border-radius:0}"));
		Assert.assertEquals(
			"p{border-radius:1px 0 1px 2px}",
			rtlCssConverter.process("p{border-radius:0 1px 2px}"));
		Assert.assertEquals(
			"p{-moz-border-radius:1px 0 1px 2px}",
			rtlCssConverter.process("p{-moz-border-radius:0 1px 2px}"));
		Assert.assertEquals(
			"p{-webkit-border-radius:1px 0 1px 2px}",
			rtlCssConverter.process("p{-webkit-border-radius:0 1px 2px}"));
		Assert.assertEquals(
			"p{border-radius:1px 0 3px 2px}",
			rtlCssConverter.process("p{border-radius:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{-moz-border-radius:1px 0 3px 2px}",
			rtlCssConverter.process("p{-moz-border-radius:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{-webkit-border-radius:1px 0 3px 2px}",
			rtlCssConverter.process("p{-webkit-border-radius:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{border-top-right-radius:5px}",
			rtlCssConverter.process("p{border-top-left-radius:5px}"));
		Assert.assertEquals(
			"p{-moz-border-radius-topright:5px}",
			rtlCssConverter.process("p{-moz-border-radius-topleft:5px}"));
		Assert.assertEquals(
			"p{-webkit-border-top-right-radius:5px}",
			rtlCssConverter.process("p{-webkit-border-top-left-radius:5px}"));
		Assert.assertEquals(
			"p{border-top-left-radius:5px}",
			rtlCssConverter.process("p{border-top-right-radius:5px}"));
		Assert.assertEquals(
			"p{-moz-border-radius-topleft:5px}",
			rtlCssConverter.process("p{-moz-border-radius-topright:5px}"));
		Assert.assertEquals(
			"p{-webkit-border-top-left-radius:5px}",
			rtlCssConverter.process("p{-webkit-border-top-right-radius:5px}"));
		Assert.assertEquals(
			"p{border-bottom-right-radius:5px}",
			rtlCssConverter.process("p{border-bottom-left-radius:5px}"));
		Assert.assertEquals(
			"p{-moz-border-radius-bottomright:5px}",
			rtlCssConverter.process("p{-moz-border-radius-bottomleft:5px}"));
		Assert.assertEquals(
			"p{-webkit-border-bottom-right-radius:5px}",
			rtlCssConverter.process(
				"p{-webkit-border-bottom-left-radius:5px}"));
		Assert.assertEquals(
			"p{border-bottom-left-radius:5px}",
			rtlCssConverter.process("p{border-bottom-right-radius:5px}"));
		Assert.assertEquals(
			"p{-moz-border-radius-bottomleft:5px}",
			rtlCssConverter.process("p{-moz-border-radius-bottomright:5px}"));
		Assert.assertEquals(
			"p{-webkit-border-bottom-left-radius:5px}",
			rtlCssConverter.process(
				"p{-webkit-border-bottom-right-radius:5px}"));
	}

	@Test
	public void testClear() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{clear:left}", rtlCssConverter.process("p{clear:right}"));
		Assert.assertEquals(
			"p{clear:right}", rtlCssConverter.process("p{clear:left}"));
	}

	@Test
	public void testComments() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{margin-right:5px}",
			rtlCssConverter.process("/*le comment*/ p { margin-left: 5px}"));
		Assert.assertEquals(
			"p{margin-right:5px}",
			rtlCssConverter.process("p { /*le comment*/\nmargin-left: 5px}"));
	}

	@Test
	public void testCommentsInPropertyNamesOrValues() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"hello{padding:1px 2px}",
			rtlCssConverter.process("hello { padding/*hello*/: 1px 2px}"));
		Assert.assertEquals(
			"hello{padding:1px 2px}",
			rtlCssConverter.process(
				"hello { padding: 1px/* some comment*/ 2px/*another*/}"));
		Assert.assertEquals(
			"hello{padding:1px 2px}",
			rtlCssConverter.process(
				"hello { padding/*I*//*comment*/: 1px/* every*/ " +
					"/*single*/2px/*space*/}"));
	}

	@Test
	public void testDirection() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{direction:ltr}", rtlCssConverter.process("p{direction:rtl}"));
		Assert.assertEquals(
			"p{direction:rtl}", rtlCssConverter.process("p{direction:ltr}"));
		Assert.assertEquals(
			"p{direction:foo}", rtlCssConverter.process("p{direction:foo}"));
	}

	@Test
	public void testEmptyInput() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals("", rtlCssConverter.process(""));
	}

	@Test
	public void testEmptyRuleDefinitions() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"b:hover{right:10px}h2{top:2px}",
			rtlCssConverter.process(
				"a {}\nb:hover{ left: 10px; }\nh1{  }\nh2 { top: 2px; }"));
	}

	@Test
	public void testFloat() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{float:left}", rtlCssConverter.process("p{float:right}"));
		Assert.assertEquals(
			"p{float:right}", rtlCssConverter.process("p{float:left}"));
	}

	@Test
	public void testImportant() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{float:right !important}",
			rtlCssConverter.process("p{float:left!important}"));
	}

	@Test
	public void testMargin() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{margin:0}", rtlCssConverter.process("p{margin:0}"));
		Assert.assertEquals(
			"p{margin:0 1px}", rtlCssConverter.process("p{margin:0 1px}"));
		Assert.assertEquals(
			"p{margin:0 1px 2px}",
			rtlCssConverter.process("p{margin:0 1px 2px}"));
		Assert.assertEquals(
			"p{margin:0 3px 2px 1px}",
			rtlCssConverter.process("p{margin:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{margin-right:0}", rtlCssConverter.process("p{margin-left:0}"));
		Assert.assertEquals(
			"p{margin-left:0}", rtlCssConverter.process("p{margin-right:0}"));
	}

	@Test
	public void testMediaExpressions() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"@media (max-width:320px){#myid{margin-left:1px}" +
				".cls{padding-right:3px}}td{float:right}",
			rtlCssConverter.process(
				"@media (max-width: 320px) { #myid { margin-right: 1px; } " +
					".cls { padding-left: 3px; } } td { float: left; }"));
	}

	@Test
	public void testNoCompress() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter(false);

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"/* some comment*/\n\np {\n  margin-right: 5px;\n}",
			rtlCssConverter.process(
				"/* some comment*/\n\np {\n  margin-left: 5px;\n}"));
	}

	@Test
	public void testNoFlip() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{margin-left:5px}",
			rtlCssConverter.process("/* @noflip */ p {margin-left: 5px}"));
		Assert.assertEquals(
			"p{margin-left:5px}",
			rtlCssConverter.process("/*@noflip*/p {margin-left: 5px}"));
		Assert.assertEquals(
			"p{margin-right:5px}",
			rtlCssConverter.process("p {margin-left: 5px;/*@noflip*/}"));
		Assert.assertEquals(
			"p{margin-right:5px}",
			rtlCssConverter.process("p{margin-left: 5px}\n/*@noflip*/"));
	}

	@Test
	public void testPadding() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{padding:0}", rtlCssConverter.process("p{padding:0}"));
		Assert.assertEquals(
			"p{padding:0 1px}", rtlCssConverter.process("p{padding:0 1px}"));
		Assert.assertEquals(
			"p{padding:0 1px 2px}",
			rtlCssConverter.process("p{padding:0 1px 2px}"));
		Assert.assertEquals(
			"p{padding:0 3px 2px 1px}",
			rtlCssConverter.process("p{padding:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{padding-right:0}", rtlCssConverter.process("p{padding-left:0}"));
		Assert.assertEquals(
			"p{padding-left:0}", rtlCssConverter.process("p{padding-right:0}"));
	}

	@Test
	public void testPortalCss() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			formatCss(read("main_rtl.css")),
			rtlCssConverter.process(read("main.css")));
	}

	@Test
	public void testPosition() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{right:50%}", rtlCssConverter.process("p{left:50%}"));
		Assert.assertEquals(
			"p{left:50%}", rtlCssConverter.process("p{right:50%}"));
	}

	@Test
	public void testSemicolonInContent() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"b.broke:before{content:\"&darr;\"}",
			rtlCssConverter.process("b.broke:before { content:\"&darr;\"}"));
	}

	@Test
	public void testTextAlign() throws Exception {
		RTLCSSConverter rtlCssConverter = new RTLCSSConverter();

		Assert.assertNotNull(rtlCssConverter);

		Assert.assertEquals(
			"p{text-align:left}",
			rtlCssConverter.process("p{text-align:right}"));
		Assert.assertEquals(
			"p{text-align:right}",
			rtlCssConverter.process("p{text-align:left}"));
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

		URL url = clazz.getResource("dependencies/" + fileName);

		Path path = Paths.get(url.toURI());

		return new String(Files.readAllBytes(path));
	}

}