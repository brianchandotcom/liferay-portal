/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Olaf Kock
 */
public class HtmlImplTest {

	@Test
	public void testEscapeHtmlEncodingLessThan() {
		Assert.assertEquals("&lt;", _htmlImpl.escape("<"));
	}

	@Test
	public void testEscapeHtmlEncodingGreaterThan() {
		Assert.assertEquals("&gt;", _htmlImpl.escape(">"));
	}

	@Test
	public void testEscapeHtmlEncodingScriptTag() {
		Assert.assertEquals("&lt;script&gt;", _htmlImpl.escape("<script>"));
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
	public void testEscapeHtmlEncodingQuotes() {
		Assert.assertEquals(
			"I&#039;m quoting: &#034;this is a quote&#034;",
			_htmlImpl.escape("I'm quoting: \"this is a quote\""));
	}

	@Test
	public void testEscapeBlank() {
		_assertUnchangedEscape("");
	}

	@Test
	public void testEscapeWhitespace() {
		_assertUnchangedEscape(" ");
	}

	@Test
	public void testEscapeNull() {
		Assert.assertNull(_htmlImpl.escape(null));
	}

	@Test
	public void testEscapeText() {
		_assertUnchangedEscape("text");
	}

	@Test
	public void testEscapeCaseSensitive() {
		_assertUnchangedEscape("CAPITAL lowercase Text");
	}

	@Test
	public void testEscapeNoTrimmingPerformed() {
		_assertUnchangedEscape("  no trimming performed ");
	}

	@Test
	public void testEscapeSemiColon() {
		_assertUnchangedEscape(";");
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
	public void testNewLineConversion() {
		Assert.assertEquals(
			"one<br />two<br />three<br /><br />five",
			_htmlImpl.replaceNewLine("one\ntwo\r\nthree\n\nfive"));
	}

	@Test
	public void testStripBetweenNull() {
		Assert.assertNull(_htmlImpl.stripBetween(null, "test"));
	}

	@Test
	public void testStripBetweenHtmlElement() {
		Assert.assertEquals(
			"test--test",
			_htmlImpl.stripBetween(
				"test-<honk>thiswillbestripped</honk>-test", "honk"));
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
	public void testStripBetween() {
		Assert.assertEquals(
			"test-test-test", _htmlImpl.stripBetween("test-test-test", "test"));
	}

	@Test
	public void testStripBetweenHtmlElementAcrossLines() {
		Assert.assertEquals(
			"works across  lines",
			_htmlImpl.stripBetween(
				"works across <honk>\r\n a number of </honk> lines", "honk"));
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
	public void testStripNullComments() {
		Assert.assertNull(_htmlImpl.stripComments(null));
	}

	@Test
	public void testStripComments() {
		Assert.assertEquals("", _htmlImpl.stripComments("<!-- bla -->"));
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
	public void testStripCommentsAccrossLines() {
		Assert.assertEquals("test", _htmlImpl.stripComments("te<!-- \n -->st"));
	}

	@Test
	public void testStripCommentsBefore() {
		Assert.assertEquals(
			"test", _htmlImpl.stripComments("<!--  bla -->test"));
	}

	@Test
	public void testStripCommentsAfter() {
		Assert.assertEquals(
			"test", _htmlImpl.stripComments("test<!--  bla -->"));
	}

	@Test
	public void testStripMultipleEmptyComments() {
		Assert.assertEquals(
			"test", _htmlImpl.stripComments("te<!-- --><!-- -->st"));
	}

	private void _assertUnchangedEscape(String input) {
		Assert.assertEquals(input, _htmlImpl.escape(input));
	}

	private HtmlImpl _htmlImpl = new HtmlImpl();

}