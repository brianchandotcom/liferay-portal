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

package com.liferay.portal.kernel.util;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class GetterUtilTest {

	@Test
	public void testGetBoolean() {
		Assert.assertFalse(GetterUtil.getBoolean("false"));
		Assert.assertTrue(GetterUtil.getBoolean("true"));
		Assert.assertFalse(GetterUtil.getBoolean(Boolean.FALSE));
		Assert.assertTrue(GetterUtil.getBoolean(Boolean.TRUE));
		Assert.assertFalse(GetterUtil.getBoolean(null, false));
		Assert.assertTrue(GetterUtil.getBoolean(null, true));
		Assert.assertFalse(GetterUtil.getBoolean(StringPool.BLANK));
		Assert.assertFalse(GetterUtil.getBoolean(StringPool.BLANK, false));
		Assert.assertFalse(GetterUtil.getBoolean(StringPool.BLANK, true));

		for (String s : GetterUtil.BOOLEANS) {
			Assert.assertTrue(GetterUtil.getBoolean(s));
			Assert.assertTrue(GetterUtil.getBoolean(s, true));
			Assert.assertTrue(GetterUtil.getBoolean(s, false));
		}
	}

	@Test
	public void testGetDouble() {
		testWith("4.7", 4.7, LocaleUtil.US);
		testWith("4,7", 4.7, LocaleUtil.PORTUGAL);
		testWith("-4,7", -4.7, LocaleUtil.PORTUGAL);
		testWith("e4.7", _DEFAULT_VALUE, LocaleUtil.US);
		testWith("e4.7", _DEFAULT_VALUE, LocaleUtil.HUNGARY);
		testWith("-4.7", -4.7, LocaleUtil.US);
	}

	@Test
	public void testGetInteger() {

		// Wrong first char

		int result = GetterUtil.get("e123", -1);

		Assert.assertEquals(-1, result);

		// Wrong middle char

		result = GetterUtil.get("12e3", -1);

		Assert.assertEquals(-1, result);

		// Start with '+'

		result = GetterUtil.get("+123", -1);

		Assert.assertEquals(123, result);

		// Start with '-'

		result = GetterUtil.get("-123", -1);

		Assert.assertEquals(-123, result);

		// Maximum int

		result = GetterUtil.get(Integer.toString(Integer.MAX_VALUE), -1);

		Assert.assertEquals(Integer.MAX_VALUE, result);

		// Minimum int

		result = GetterUtil.get(Integer.toString(Integer.MIN_VALUE), -1);

		Assert.assertEquals(Integer.MIN_VALUE, result);

		// Larger than maximum int

		result = GetterUtil.get(Integer.toString(Integer.MAX_VALUE) + "0", -1);

		Assert.assertEquals(-1, result);

		// Smaller than minimum int

		result = GetterUtil.get(Integer.toString(Integer.MIN_VALUE) + "0", -1);

		Assert.assertEquals(-1, result);
	}

	@Test
	public void testGetLong() {

		// Wrong first char

		long result = GetterUtil.get("e123", -1L);

		Assert.assertEquals(-1L, result);

		// Wrong middle char

		result = GetterUtil.get("12e3", -1L);

		Assert.assertEquals(-1L, result);

		// Start with '+'

		result = GetterUtil.get("+123", -1L);

		Assert.assertEquals(123L, result);

		// Start with '-'

		result = GetterUtil.get("-123", -1L);

		Assert.assertEquals(-123L, result);

		// Maximum long

		result = GetterUtil.get(Long.toString(Long.MAX_VALUE), -1L);

		Assert.assertEquals(Long.MAX_VALUE, result);

		// Minimum long

		result = GetterUtil.get(Long.toString(Long.MIN_VALUE), -1L);

		Assert.assertEquals(Long.MIN_VALUE, result);

		// Larger than maximum long

		result = GetterUtil.get(Long.toString(Long.MAX_VALUE) + "0", -1L);

		Assert.assertEquals(-1L, result);

		// Smaller than minimum long

		result = GetterUtil.get(Long.toString(Long.MIN_VALUE) + "0", -1L);

		Assert.assertEquals(-1L, result);
	}

	@Test
	public void testGetShort() {

		// Wrong first char

		short result = GetterUtil.get("e123", (short)-1);

		Assert.assertEquals((short)-1, result);

		// Wrong middle char

		result = GetterUtil.get("12e3", (short)-1);

		Assert.assertEquals((short)-1, result);

		// Start with '+'

		result = GetterUtil.get("+123", (short)-1);

		Assert.assertEquals((short)123, result);

		// Start with '-'

		result = GetterUtil.get("-123", (short)-1);

		Assert.assertEquals((short)-123, result);

		// Maximum short

		result = GetterUtil.get(Short.toString(Short.MAX_VALUE), (short)-1);

		Assert.assertEquals(Short.MAX_VALUE, result);

		// Minimum short

		result = GetterUtil.get(Short.toString(Short.MIN_VALUE), (short)-1);

		Assert.assertEquals(Short.MIN_VALUE, result);

		// Larger than maximum short

		result = GetterUtil.get(
			Short.toString(Short.MAX_VALUE) + "0", (short)-1);

		Assert.assertEquals((short)-1, result);

		// Smaller than minimum short

		result = GetterUtil.get(
			Short.toString(Short.MIN_VALUE) + "0", (short)-1);

		Assert.assertEquals((short)-1, result);
	}

	@Test
	public void testGetString() {
		Assert.assertEquals(
			StringPool.BLANK,
			GetterUtil.getString(StringPool.BLANK, "default"));
		Assert.assertEquals(
			GetterUtil.DEFAULT_STRING, GetterUtil.getString(null));
		Assert.assertEquals("default", GetterUtil.getString(null, "default"));
		Assert.assertEquals(
			"default", GetterUtil.getString(new Object(), "default"));
		Assert.assertEquals("test", GetterUtil.getString("test"));
	}

	private void testWith(String expectedStr, double expected, Locale locale) {
		String paramName = "value";
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter(paramName, expectedStr);
		double actual = ParamUtil.getDouble(
			request, paramName, _DEFAULT_VALUE, locale);
		Assert.assertEquals(expected, actual, _DEFAULT_DELTA);
	}

	private static final double _DEFAULT_DELTA = 0.001;

	private static final double _DEFAULT_VALUE = -1.0;

}