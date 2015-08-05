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

package com.liferay.portal.parser.bbcode.test;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.parser.bbcode.HtmlBBCodeTranslatorImpl;
import com.liferay.portal.util.HtmlImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sergio González
 * @author John Zhao
 */
public class HtmlBBCodeTranslatorTest {

	@Before
	public void setUp() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	@Test
	public void testAlign() {
		String content = "[center]text[/center]";

		String expected = "<p style=\"text-align: center\">text</p>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testBold() {
		String content = "[b]text[/b]";

		String expected = "<strong>text</strong>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCode() {
		String content = "[code]:)[code]";

		String expected =
			"<div class=\"lfr-code\"><table><tbody><tr>" +
				"<td class=\"line-numbers\" data-line-number=\"1\"></td>" +
				"<td class=\"lines\"><div class=\"line\">:)[code]</div></td>" +
				"</tr></tbody></table></div>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testColor() {
		String content = "[color=#ff0000]text[/color]";

		String expected = "<span style=\"color: #ff0000\">text</span>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testEmotion() {
		String content = ":)";

		String expected ="<img alt=\"emoticon\" " +
			"src=\"@theme_images_path@/emoticons/happy.gif\" >";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFontFamily() {
		String content = "[font=georgia, serif]text[/font]";

		String expected =
			"<span style=\"font-family: " + HtmlUtil.escapeAttribute(
				"georgia, serif") + "\">text</span>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFontSize() {
		String content = "[size=5]text[/size]";

		String expected = "<span style=\"font-size: 18px;\">text</span>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIncompleteTag() {
		String content = "[b]text";

		String expected = "<strong>text</strong>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testItalic() {
		String content = "[i]text[/i]";

		String expected = "<em>text</em>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testQuote() {
		String content = "[quote=citer]text[/quote]";

		String expected =
			"<div class=\"quote-title\">citer:</div><div class=\"quote\">" +
				"<div class=\"quote-content\">text</div></div>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testStrike() {
		String content = "[s]text[/s]";

		String expected = "<strike>text</strike>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testUnderline() {
		String content = "[u]text[/u]";

		String expected = "<u>text</u>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}


	private final HtmlBBCodeTranslatorImpl _htmlBBCodeTranslator =
		new HtmlBBCodeTranslatorImpl();

}