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

package com.liferay.portal.kernel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class TextFormatterTest {

	@Test
	public void testFormatA() {
		String original = "Web Search";
		String expected = "WEB_SEARCH";

		String actual = TextFormatter.format(original, TextFormatter.A);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatB() {
		String original = "Web Search";
		String expected = "websearch";

		String actual = TextFormatter.format(original, TextFormatter.B);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatC() {
		String original = "Web Search";
		String expected = "web_search";

		String actual = TextFormatter.format(original, TextFormatter.C);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatD() {
		String original = "Web Search";
		String expected = "WebSearch";

		String actual = TextFormatter.format(original, TextFormatter.D);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatE() {
		String original = "Web Search";
		String expected = "web search";

		String actual = TextFormatter.format(original, TextFormatter.E);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatF() {
		String original = "Web Search";
		String expected = "webSearch";

		String actual = TextFormatter.format(original, TextFormatter.F);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatG() {
		String original = "formatId";
		String expected = "FormatId";

		String actual = TextFormatter.format(original, TextFormatter.G);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatH() {
		String original = "formatId";
		String expected = "format id";

		String actual = TextFormatter.format(original, TextFormatter.H);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatI() {
		String original = "FormatId";
		String expected = "formatId";

		String actual = TextFormatter.format(original, TextFormatter.I);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatJ() {
		String original = "format-id";
		String expected = "Format Id";

		String actual = TextFormatter.format(original, TextFormatter.J);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatK() {
		String original = "formatId";
		String expected = "format-id";

		String actual = TextFormatter.format(original, TextFormatter.K);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatL() {
		String original = "FormatId";
		String expected = "formatId";

		String actual = TextFormatter.format(original, TextFormatter.L);

		Assert.assertEquals(expected, actual);

		original = "FOrmatId";
		expected = "FOrmatId";

		actual = TextFormatter.format(original, TextFormatter.L);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatM() {
		String original = "format-id";
		String expected = "formatId";

		String actual = TextFormatter.format(original, TextFormatter.M);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatN() {
		String original = "format-id";
		String expected = "format_id";

		String actual = TextFormatter.format(original, TextFormatter.N);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatO() {
		String original = "format_id";
		String expected = "format-id";

		String actual = TextFormatter.format(original, TextFormatter.O);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFormatP() {
		String original = "formatID";
		String expected = "format-id";

		String actual = TextFormatter.format(original, TextFormatter.P);

		Assert.assertEquals(expected, actual);
	}

}