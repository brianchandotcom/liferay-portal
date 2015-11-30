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

package com.liferay.portal.parser.bbcode;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.HtmlImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sergio González
 * @author John Zhao
 */
public class HtmlBBCodeTranslatorImplTest {

	@Before
	public void setUp() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	@Test
	public void testAlign() {
		String expected = "<p style=\"text-align: center\">text</p>";
		String actual = _htmlBBCodeTranslator.parse("[center]text[/center]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testAsterisk() {
		String expected =
			"<strong>type</strong> some <u>text</u><li>this is a test</li>";
		String actual = _htmlBBCodeTranslator.parse(
			"[b]type[/b] some [u]text[/u]" + "[*]this is a test");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testAsteriskInBold() {
		String expected =
			"<strong>asterisk</strong><li> is inside the bold</li>";
		String actual = _htmlBBCodeTranslator.parse(
			"[b]asterisk[*][/b] is inside the bold");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testBold() {
		String expected = "<strong>text</strong>";
		String actual = _htmlBBCodeTranslator.parse("[b]text[/b]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCode() {
		String expected =
			"<div class=\"lfr-code\"><table><tbody><tr>" +
				"<td class=\"line-numbers\" data-line-number=\"1\"></td>" +
					"<td class=\"lines\"><div class=\"line\">:)</div>" +
						"</td></tr></tbody></table></div>";
		String actual = _htmlBBCodeTranslator.parse("[code]:)[/code]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testColor() {
		String expected = "<span style=\"color: #ff0000\">text</span>";
		String actual = _htmlBBCodeTranslator.parse(
			"[color=#ff0000]text[/color]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testEmotion() {
		String expected =
			"<img alt=\"emoticon\" " +
				"src=\"@theme_images_path@/emoticons/happy.gif\" >";
		String actual = _htmlBBCodeTranslator.parse(":)");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFontFamily() {
		String expected =
			"<span style=\"font-family: " +
				HtmlUtil.escapeAttribute("georgia, serif") + "\">text</span>";
		String actual = _htmlBBCodeTranslator.parse(
			"[font=georgia, serif]text[/font]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFontSize() {
		String expected = "<span style=\"font-size: 18px;\">text</span>";
		String actual = _htmlBBCodeTranslator.parse("[size=5]text[/size]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testImgXSS() {
		String expected = "<img src=\"\" />";
		String actual = _htmlBBCodeTranslator.parse(
			"[img]asd[font= onerror=alert(/XSS/.source)//]FF[/font][/img]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIncompleteTag() {
		String expected = "<strong>text</strong>";
		String actual = _htmlBBCodeTranslator.parse("[b]text");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testInvalidTag() {
		String expected = "[x]invalidTag[/x]";

		String actual = _htmlBBCodeTranslator.parse("[x]invalidTag[/x]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testInvalidTagAndValidTag() {
		String expected = "[x]bbb<u>XXX</u>ddd[/x]";
		String actual = _htmlBBCodeTranslator.parse("[x]bbb[u]XXX[/u]ddd[/x]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testItalic() {
		String expected = "<em>text</em>";
		String actual = _htmlBBCodeTranslator.parse("[i]text[/i]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testOrderedList() {
		String expected =
			"<ol style=\"list-style: lower-roman outside;\" start=\"2\">" +
				"<li>line1</li><li>line2</li></ol>";
		String actual = _htmlBBCodeTranslator.parse(
			"[list type=\"i\" start=\"2\"][*]line1[*]line2[/list]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testQuote() {
		String expected =
			"<div class=\"quote-title\">citer:</div><div class=\"quote\">" +
				"<div class=\"quote-content\">text</div></div>";
		String actual = _htmlBBCodeTranslator.parse(
			"[quote=citer]text[/quote]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testStrike() {
		String expected = "<strike>text</strike>";
		String actual = _htmlBBCodeTranslator.parse("[s]text[/s]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testUnderline() {
		String expected = "<u>text</u>";
		String actual = _htmlBBCodeTranslator.parse("[u]text[/u]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testUnorderedList() {
		String expected =
			"<ul style=\"list-style: circle outside;\">" +
				"<li>line1</li><li>line2</li></ul>";
		String actual = _htmlBBCodeTranslator.parse(
			"[list type=\"circle\"][*]line1[*]line2[/list]");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testURL() {
		String url = "https://msdn.microsoft.com/aa752574(VS.85).aspx";

		String expected =
			"<a href=\"" + HtmlUtil.escapeHREF(url) + "\">link</a>";
		String actual = _htmlBBCodeTranslator.parse(
			"[url=" + url + "]link[/url]");

		Assert.assertEquals(expected, actual);
	}

	private final HtmlBBCodeTranslatorImpl _htmlBBCodeTranslator =
		new HtmlBBCodeTranslatorImpl();

}