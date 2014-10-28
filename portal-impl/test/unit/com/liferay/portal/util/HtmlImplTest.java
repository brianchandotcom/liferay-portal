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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Olaf Kock
 * @author Neil Zhao Jin
 */
public class HtmlImplTest {

	@Test
	public void testEscapeBlank() {
		assertUnchangedEscape("");
	}

	@Test
	public void testEscapeCaseSensitive() {
		assertUnchangedEscape("CAPITAL lowercase Text");
	}

	@Test
	public void testEscapeHtmlEncodingAmpersand() {
		Assert.assertEquals("&amp;", _htmlImpl.escape("&"));
	}

	@Test
	public void testEscapeHtmlEncodingAmpersandInBetween() {
		Assert.assertEquals("You &amp; Me", _htmlImpl.escape("You & Me"));
	}

	@Test
	public void testEscapeHtmlEncodingDoubleQuotes() {
		Assert.assertEquals(
			"&lt;span class=&#034;test&#034;&gt;Test&lt;/span&gt;",
			_htmlImpl.escape("<span class=\"test\">Test</span>"));
	}

	@Test
	public void testEscapeHtmlEncodingGreaterThan() {
		Assert.assertEquals("&gt;", _htmlImpl.escape(">"));
	}

	@Test
	public void testEscapeHtmlEncodingLessThan() {
		Assert.assertEquals("&lt;", _htmlImpl.escape("<"));
	}

	@Test
	public void testEscapeHtmlEncodingQuotes() {
		Assert.assertEquals(
			"I&#039;m quoting: &#034;this is a quote&#034;",
			_htmlImpl.escape("I'm quoting: \"this is a quote\""));
	}

	@Test
	public void testEscapeHtmlEncodingScriptTag() {
		Assert.assertEquals("&lt;script&gt;", _htmlImpl.escape("<script>"));
	}

	@Test
	public void testEscapeNoTrimmingPerformed() {
		assertUnchangedEscape("  no trimming performed ");
	}

	@Test
	public void testEscapeNull() {
		Assert.assertNull(_htmlImpl.escape(null));
	}

	@Test
	public void testEscapeSemiColon() {
		assertUnchangedEscape(";");
	}

	@Test
	public void testEscapeText() {
		assertUnchangedEscape("text");
	}

	@Test
	public void testEscapeWhitespace() {
		assertUnchangedEscape(" ");
	}

	@Test
	public void testExtraction() {
		Assert.assertEquals(
			"whitespace removal",
			_htmlImpl.extractText("   whitespace \n   <br/> removal   "));
		Assert.assertEquals(
			"script removal",
			_htmlImpl.extractText(
				"script <script>   test   </script> removal"));
		Assert.assertEquals(
			"HTML attribute removal",
			_htmlImpl.extractText(
				"<h1>HTML</h1> <i>attribute</i> <strong>removal</strong>"));
		Assert.assertEquals(
			"onclick removal",
			_htmlImpl.extractText(
				"<div onclick=\"honk()\">onclick removal</div>"));
	}

	@Test
	public void testGetAUICompatibleId() {
		Assert.assertNull(_htmlImpl.getAUICompatibleId(null));
		Assert.assertEquals(
			StringPool.BLANK, _htmlImpl.getAUICompatibleId(StringPool.BLANK));
		Assert.assertEquals(
			"hello_20_world", _htmlImpl.getAUICompatibleId("hello world"));
		Assert.assertEquals(
			"hello__world", _htmlImpl.getAUICompatibleId("hello_world"));

		StringBundler actualSB = new StringBundler(53);

		for (int i = 0; i <= 47; i++) {
			actualSB.append(StringPool.ASCII_TABLE[i]);
		}

		actualSB.append(":;<=>?@[\\]^_`{|}~");
		actualSB.append(CharPool.DELETE);
		actualSB.append(CharPool.NO_BREAK_SPACE);
		actualSB.append(CharPool.FIGURE_SPACE);
		actualSB.append(CharPool.NARROW_NO_BREAK_SPACE);

		StringBundler expectedSB = new StringBundler(6);

		expectedSB.append("_0__1__2__3__4__5__6__7__8__9__a__b__c__d__e__f_");
		expectedSB.append("_10__11__12__13__14__15__16__17__18__19__1a__1b_");
		expectedSB.append("_1c__1d__1e__1f__20__21__22__23__24__25__26__27_");
		expectedSB.append("_28__29__2a__2b__2c__2d__2e__2f__3a__3b__3c__3d_");
		expectedSB.append("_3e__3f__40__5b__5c__5d__5e____60__7b__7c__7d__7e_");
		expectedSB.append("_7f__a0__2007__202f_");

		Assert.assertEquals(
			expectedSB.toString(),
			_htmlImpl.getAUICompatibleId(actualSB.toString()));
	}

	@Test
	public void testNewLineConversion() {
		Assert.assertEquals(
			"one<br />two<br />three<br /><br />five",
			_htmlImpl.replaceNewLine("one\ntwo\r\nthree\n\nfive"));
	}

	@Test
	public void testStripBetween() {
		Assert.assertEquals(
			"test-test-test", _htmlImpl.stripBetween("test-test-test", "test"));
	}

	@Test
	public void testStripBetweenHtmlElement() {
		Assert.assertEquals(
			"test--test",
			_htmlImpl.stripBetween(
				"test-<honk>thiswillbestripped</honk>-test", "honk"));
	}

	@Test
	public void testStripBetweenHtmlElementAcrossLines() {
		Assert.assertEquals(
			"works across  lines",
			_htmlImpl.stripBetween(
				"works across <honk>\r\n a number of </honk> lines", "honk"));
	}

	@Test
	public void testStripBetweenHtmlElementWithAttribute() {
		Assert.assertEquals(
			"test--test",
			_htmlImpl.stripBetween(
				"test-<honk attribute=\"value\">thiswillbestripped</honk>-test",
				"honk"));
	}

	@Test
	public void testStripBetweenMultipleOcurrencesOfHtmlElement() {
		Assert.assertEquals(
			"multiple occurrences, multiple indeed",
			_htmlImpl.stripBetween(
				"multiple <a>many</a>occurrences, multiple <a>HONK</a>indeed",
				"a"));
	}

	@Test
	public void testStripBetweenNull() {
		Assert.assertNull(_htmlImpl.stripBetween(null, "test"));
	}

	@Test
	public void testStripBetweenSelfClosedHtmlElement() {
		Assert.assertEquals(
			"self-closing <test/> is unhandled",
			_htmlImpl.stripBetween(
				"self-closing <test/> is unhandled", "test"));
	}

	@Test
	public void testStripBetweenSelfClosedHtmlElementWithWhitespaceEnding() {
		Assert.assertEquals(
			"self-closing <test /> is unhandled",
			_htmlImpl.stripBetween(
				"self-closing <test /> is unhandled", "test"));
	}

	@Test
	public void testStripComments() {
		Assert.assertEquals("", _htmlImpl.stripComments("<!-- bla -->"));
	}

	@Test
	public void testStripCommentsAccrossLines() {
		Assert.assertEquals("test", _htmlImpl.stripComments("te<!-- \n -->st"));
	}

	@Test
	public void testStripCommentsAfter() {
		Assert.assertEquals(
			"test", _htmlImpl.stripComments("test<!--  bla -->"));
	}

	@Test
	public void testStripCommentsBefore() {
		Assert.assertEquals(
			"test", _htmlImpl.stripComments("<!--  bla -->test"));
	}

	@Test
	public void testStripEmptyComments() {
		Assert.assertEquals("", _htmlImpl.stripComments("<!---->"));
	}

	@Test
	public void testStripMultipleComments() {
		Assert.assertEquals(
			"test",
			_htmlImpl.stripComments("te<!--  bla -->s<!-- bla bla -->t"));
	}

	@Test
	public void testStripMultipleEmptyComments() {
		Assert.assertEquals(
			"test", _htmlImpl.stripComments("te<!-- --><!-- -->st"));
	}

	@Test
	public void testStripNullComments() {
		Assert.assertNull(_htmlImpl.stripComments(null));
	}

	@Test
	public void testUnescapeDoubleHtmlEncoding() {
		Assert.assertEquals(
			"&#034;", _htmlImpl.unescape(_htmlImpl.escape("&#034;")));
	}

	@Test
	public void testUnescapeHtmlEncodingAmpersand() {
		Assert.assertEquals("&", _htmlImpl.unescape("&amp;"));
	}

	@Test
	public void testUnescapeHtmlEncodingAmpersandInBetween() {
		Assert.assertEquals("You & Me", _htmlImpl.unescape("You &amp; Me"));
	}

	@Test
	public void testUnescapeHtmlEncodingRightSingleQuote() {
		Assert.assertEquals("\u2019", _htmlImpl.unescape("&rsquo;"));
	}

	protected void assertUnchangedEscape(String input) {
		Assert.assertEquals(input, _htmlImpl.escape(input));
	}

	private HtmlImpl _htmlImpl = new HtmlImpl();

}