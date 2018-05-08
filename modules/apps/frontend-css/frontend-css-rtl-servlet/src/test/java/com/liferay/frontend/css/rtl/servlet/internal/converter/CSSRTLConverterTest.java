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

package com.liferay.frontend.css.rtl.servlet.internal.converter;

import com.helger.commons.charset.CCharset;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.reader.CSSReader;
import com.helger.css.reader.errorhandler.DoNothingCSSParseErrorHandler;
import com.helger.css.writer.CSSWriter;
import com.helger.css.writer.CSSWriterSettings;

import java.io.File;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author David Truong
 */
public class CSSRTLConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAsterisk() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{*right:50%}", cssRTLConverter.process("p{*left:50%}"));
		Assert.assertEquals(
			"p{*text-align:left}",
			cssRTLConverter.process("p{*text-align:right}"));
	}

	@Test
	public void testBackground() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{background:url(../left/right/test_right.png) right 30%}",
			cssRTLConverter.process(
				"p{background:url(../left/right/test_left.png) left 30%}"));
		Assert.assertEquals(
			"p{background:url(../left/right/test_right.png) 80% 10%}",
			cssRTLConverter.process(
				"p{background:url(../left/right/test_left.png) 20% 10%}"));
		Assert.assertEquals(
			"p{background:color url(../left/right/test_right.png) repeat " +
				"left 20%}",
			cssRTLConverter.process(
				"p{background:color url(../left/right/test_left.png) repeat " +
					"right 20%}"));
		Assert.assertEquals(
			"p{background-image:url(../atleft/right/test_right.png)}",
			cssRTLConverter.process(
				"p{background-image:url(../atleft/right/test_left.png)}"));
	}

	@Test
	public void testBackgroundPosition() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{background-position:right top}",
			cssRTLConverter.process("p{background-position:left top}"));
		Assert.assertEquals(
			"p{background-position:right 20px}",
			cssRTLConverter.process("p{background-position:20px}"));
		Assert.assertEquals(
			"p{background-position:80% top}",
			cssRTLConverter.process("p{background-position:20% top}"));
	}

	@Test
	public void testBorder() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{border-right:1px}",
			cssRTLConverter.process("p{border-left:1px}"));
		Assert.assertEquals(
			"p{border-left:1px}",
			cssRTLConverter.process("p{border-right:1px}"));
		Assert.assertEquals(
			"p{border-left:1px solid #000}",
			cssRTLConverter.process("p{border-right:1px solid #000}"));
		Assert.assertEquals(
			"p{border-style:solid}",
			cssRTLConverter.process("p{border-style:solid}"));
		Assert.assertEquals(
			"p{border-style:none solid}",
			cssRTLConverter.process("p{border-style:none solid}"));
		Assert.assertEquals(
			"p{border-style:none solid dashed}",
			cssRTLConverter.process("p{border-style:none solid dashed}"));
		Assert.assertEquals(
			"p{border-style:none double dashed solid}",
			cssRTLConverter.process(
				"p{border-style:none solid dashed double}"));
		Assert.assertEquals(
			"p{border-color:#fff}",
			cssRTLConverter.process("p{border-color:#fff}"));
		Assert.assertEquals(
			"p{border-color:#fff #000}",
			cssRTLConverter.process("p{border-color:#fff #000}"));
		Assert.assertEquals(
			"p{border-color:#000 #111 #222}",
			cssRTLConverter.process("p{border-color:#000 #111 #222}"));
		Assert.assertEquals(
			"p{border-color:#000 #333 #222 #111}",
			cssRTLConverter.process("p{border-color:#000 #111 #222 #333}"));
		Assert.assertEquals(
			"p{border-right-color:#fff}",
			cssRTLConverter.process("p{border-left-color:#fff}"));
		Assert.assertEquals(
			"p{border-left-color:#fff}",
			cssRTLConverter.process("p{border-right-color:#fff}"));
		Assert.assertEquals(
			"p{border-width:0}", cssRTLConverter.process("p{border-width:0}"));
		Assert.assertEquals(
			"p{border-width:0 1px}",
			cssRTLConverter.process("p{border-width:0 1px}"));
		Assert.assertEquals(
			"p{border-width:0 1px 2px}",
			cssRTLConverter.process("p{border-width:0 1px 2px}"));
		Assert.assertEquals(
			"p{border-width:0 3px 2px 1px}",
			cssRTLConverter.process("p{border-width:0 1px 2px 3px}"));
	}

	@Test
	public void testBorderRadius() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{border-radius:0}",
			cssRTLConverter.process("p{border-radius:0}"));
		Assert.assertEquals(
			"p{-moz-border-radius:0}",
			cssRTLConverter.process("p{-moz-border-radius:0}"));
		Assert.assertEquals(
			"p{-webkit-border-radius:0}",
			cssRTLConverter.process("p{-webkit-border-radius:0}"));
		Assert.assertEquals(
			"p{border-radius:1px 0 1px 2px}",
			cssRTLConverter.process("p{border-radius:0 1px 2px}"));
		Assert.assertEquals(
			"p{-moz-border-radius:1px 0 1px 2px}",
			cssRTLConverter.process("p{-moz-border-radius:0 1px 2px}"));
		Assert.assertEquals(
			"p{-webkit-border-radius:1px 0 1px 2px}",
			cssRTLConverter.process("p{-webkit-border-radius:0 1px 2px}"));
		Assert.assertEquals(
			"p{border-radius:1px 0 3px 2px}",
			cssRTLConverter.process("p{border-radius:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{-moz-border-radius:1px 0 3px 2px}",
			cssRTLConverter.process("p{-moz-border-radius:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{-webkit-border-radius:1px 0 3px 2px}",
			cssRTLConverter.process("p{-webkit-border-radius:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{border-top-right-radius:5px}",
			cssRTLConverter.process("p{border-top-left-radius:5px}"));
		Assert.assertEquals(
			"p{-moz-border-radius-topright:5px}",
			cssRTLConverter.process("p{-moz-border-radius-topleft:5px}"));
		Assert.assertEquals(
			"p{-webkit-border-top-right-radius:5px}",
			cssRTLConverter.process("p{-webkit-border-top-left-radius:5px}"));
		Assert.assertEquals(
			"p{border-top-left-radius:5px}",
			cssRTLConverter.process("p{border-top-right-radius:5px}"));
		Assert.assertEquals(
			"p{-moz-border-radius-topleft:5px}",
			cssRTLConverter.process("p{-moz-border-radius-topright:5px}"));
		Assert.assertEquals(
			"p{-webkit-border-top-left-radius:5px}",
			cssRTLConverter.process("p{-webkit-border-top-right-radius:5px}"));
		Assert.assertEquals(
			"p{border-bottom-right-radius:5px}",
			cssRTLConverter.process("p{border-bottom-left-radius:5px}"));
		Assert.assertEquals(
			"p{-moz-border-radius-bottomright:5px}",
			cssRTLConverter.process("p{-moz-border-radius-bottomleft:5px}"));
		Assert.assertEquals(
			"p{-webkit-border-bottom-right-radius:5px}",
			cssRTLConverter.process(
				"p{-webkit-border-bottom-left-radius:5px}"));
		Assert.assertEquals(
			"p{border-bottom-left-radius:5px}",
			cssRTLConverter.process("p{border-bottom-right-radius:5px}"));
		Assert.assertEquals(
			"p{-moz-border-radius-bottomleft:5px}",
			cssRTLConverter.process("p{-moz-border-radius-bottomright:5px}"));
		Assert.assertEquals(
			"p{-webkit-border-bottom-left-radius:5px}",
			cssRTLConverter.process(
				"p{-webkit-border-bottom-right-radius:5px}"));
	}

	@Test
	public void testCalc() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		cssRTLConverter.process(
			".foo { margin-top: calc(((1em * 1.428571) - 1em) / 2); }");
	}

	@Test
	public void testClear() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{clear:left}", cssRTLConverter.process("p{clear:right}"));
		Assert.assertEquals(
			"p{clear:right}", cssRTLConverter.process("p{clear:left}"));
	}

	@Test
	public void testComments() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{margin-right:5px}",
			cssRTLConverter.process("/*le comment*/ p { margin-left: 5px}"));
		Assert.assertEquals(
			"p{margin-right:5px}",
			cssRTLConverter.process("p { /*le comment*/\nmargin-left: 5px}"));
	}

	@Test
	public void testCommentsInPropertyNamesOrValues() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"hello{padding:1px 2px}",
			cssRTLConverter.process("hello { padding/*hello*/: 1px 2px}"));
		Assert.assertEquals(
			"hello{padding:1px 2px}",
			cssRTLConverter.process(
				"hello { padding: 1px/* some comment*/ 2px/*another*/}"));
		Assert.assertEquals(
			"hello{padding:1px 2px}",
			cssRTLConverter.process(
				"hello { padding/*I*//*comment*/: 1px/* every*/ /*single*/2px" +
					"/*space*/}"));
	}

	@Test
	public void testDirection() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{direction:ltr}", cssRTLConverter.process("p{direction:rtl}"));
		Assert.assertEquals(
			"p{direction:rtl}", cssRTLConverter.process("p{direction:ltr}"));
		Assert.assertEquals(
			"p{direction:foo}", cssRTLConverter.process("p{direction:foo}"));
	}

	@Test
	public void testEmptyInput() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals("", cssRTLConverter.process(""));
	}

	@Test
	public void testEmptyRuleDefinitions() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"b:hover{right:10px}h2{top:2px}",
			cssRTLConverter.process(
				"a {}\nb:hover{ left: 10px; }\nh1{  }\nh2 { top: 2px; }"));
	}

	@Test
	public void testFloat() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{float:left}", cssRTLConverter.process("p{float:right}"));
		Assert.assertEquals(
			"p{float:right}", cssRTLConverter.process("p{float:left}"));
	}

	@Test
	public void testImportant() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{float:right !important}",
			cssRTLConverter.process("p{float:left!important}"));
	}

	@Test
	public void testMargin() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{margin:0}", cssRTLConverter.process("p{margin:0}"));
		Assert.assertEquals(
			"p{margin:0 1px}", cssRTLConverter.process("p{margin:0 1px}"));
		Assert.assertEquals(
			"p{margin:0 1px 2px}",
			cssRTLConverter.process("p{margin:0 1px 2px}"));
		Assert.assertEquals(
			"p{margin:0 3px 2px 1px}",
			cssRTLConverter.process("p{margin:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{margin-right:0}", cssRTLConverter.process("p{margin-left:0}"));
		Assert.assertEquals(
			"p{margin-left:0}", cssRTLConverter.process("p{margin-right:0}"));
	}

	@Test
	public void testMediaExpressions() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"@media (max-width:320px){#myid{margin-left:1px}." +
				"cls{padding-right:3px}}td{float:right}",
			cssRTLConverter.process(
				"@media (max-width: 320px) { #myid { margin-right: 1px; } " +
					".cls { padding-left: 3px; } } td { float: left; }"));
	}

	@Test
	public void testMultipleDeclarations() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{text-align:left;text-align:start}",
			cssRTLConverter.process("p{text-align: right; text-align: start}"));
	}

	@Test
	public void testNoCompress() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter(false);

		Assert.assertEquals(
			"p { margin-right:5px; }\n",
			cssRTLConverter.process(
				"/* some comment*/\n\np {\n  margin-left: 5px;\n}"));
	}

	@Test
	public void testPadding() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{padding:0}", cssRTLConverter.process("p{padding:0}"));
		Assert.assertEquals(
			"p{padding:0 1px}", cssRTLConverter.process("p{padding:0 1px}"));
		Assert.assertEquals(
			"p{padding:0 1px 2px}",
			cssRTLConverter.process("p{padding:0 1px 2px}"));
		Assert.assertEquals(
			"p{padding:0 3px 2px 1px}",
			cssRTLConverter.process("p{padding:0 1px 2px 3px}"));
		Assert.assertEquals(
			"p{padding-right:0}", cssRTLConverter.process("p{padding-left:0}"));
		Assert.assertEquals(
			"p{padding-left:0}", cssRTLConverter.process("p{padding-right:0}"));
	}

	@Test
	public void testPortalCss() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter(false);

		Class<?> clazz = getClass();

		URL url = clazz.getResource("dependencies");

		File folder = new File(url.toURI());

		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			if (filePath.contains("_rtl") || !filePath.endsWith(".css")) {
				continue;
			}

			Assert.assertEquals(
				formatCss(read(getRtlCustomFileName(filePath))),
				formatCss(cssRTLConverter.process(read(filePath))));
		}
	}

	@Test
	public void testPosition() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{right:50%}", cssRTLConverter.process("p{left:50%}"));
		Assert.assertEquals(
			"p{left:50%}", cssRTLConverter.process("p{right:50%}"));
	}

	@Test
	public void testSemicolonInContent() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"b.broke:before{content:\"&darr;\"}",
			cssRTLConverter.process("b.broke:before { content:\"&darr;\"}"));
	}

	@Test
	public void testTextAlign() throws Exception {
		CSSRTLConverter cssRTLConverter = new CSSRTLConverter();

		Assert.assertEquals(
			"p{text-align:left}",
			cssRTLConverter.process("p{text-align:right}"));
		Assert.assertEquals(
			"p{text-align:right}",
			cssRTLConverter.process("p{text-align:left}"));
	}

	protected String formatCss(String css) {
		CascadingStyleSheet cascadingStyleSheet = CSSReader.readFromString(
			css, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30,
			new DoNothingCSSParseErrorHandler());

		CSSWriterSettings cssWriterSettings = new CSSWriterSettings(
			ECSSVersion.CSS30, false);

		cssWriterSettings.setOptimizedOutput(false);
		cssWriterSettings.setRemoveUnnecessaryCode(Boolean.TRUE);

		CSSWriter cssWriter = new CSSWriter(cssWriterSettings);

		return cssWriter.getCSSAsString(cascadingStyleSheet);
	}

	protected String getRtlCustomFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");

		return fileName.substring(0, pos) + "_rtl" + fileName.substring(pos);
	}

	protected String read(String fileName) throws Exception {
		Path filePath = Paths.get(fileName);

		return new String(Files.readAllBytes(filePath));
	}

}