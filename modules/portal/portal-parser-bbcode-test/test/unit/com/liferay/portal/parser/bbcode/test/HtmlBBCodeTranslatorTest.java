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
 */
public class HtmlBBCodeTranslatorTest {

	@Before
	public void setUp() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	@Test
	public void testBold() throws Exception {
		String content = "This is [b]bold[/b]";

		String expected = "This is <strong>bold</strong>";
		String actual = _htmlBBCodeTranslator.parse(content);

		Assert.assertEquals(expected, actual);
	}

	private final HtmlBBCodeTranslatorImpl _htmlBBCodeTranslator =
		new HtmlBBCodeTranslatorImpl();

}