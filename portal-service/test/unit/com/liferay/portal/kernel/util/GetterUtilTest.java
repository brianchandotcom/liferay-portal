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

import com.liferay.portal.kernel.test.TestCase;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class GetterUtilTest extends TestCase {

	@Test
	public void testGetBoolean() {
		assertFalse(GetterUtil.getBoolean("false"));
		assertTrue(GetterUtil.getBoolean("true"));
		assertFalse(GetterUtil.getBoolean(Boolean.FALSE));
		assertTrue(GetterUtil.getBoolean(Boolean.TRUE));
		assertFalse(GetterUtil.getBoolean(null, false));
		assertTrue(GetterUtil.getBoolean(null, true));
		assertFalse(GetterUtil.getBoolean(StringPool.BLANK));
		assertFalse(GetterUtil.getBoolean(StringPool.BLANK, false));
		assertFalse(GetterUtil.getBoolean(StringPool.BLANK, true));

		for (String s : GetterUtil.BOOLEANS) {
			assertTrue(GetterUtil.getBoolean(s));
			assertTrue(GetterUtil.getBoolean(s, true));
			assertTrue(GetterUtil.getBoolean(s, false));
		}
	}

	@Test
	public void testGetInteger() {

		// Wrong first char

		int result = GetterUtil.get("e123", -1);

		assertEquals(-1, result);

		// Wrong middle char

		result = GetterUtil.get("12e3", -1);

		assertEquals(-1, result);

		// Start with '+'

		result = GetterUtil.get("+123", -1);

		assertEquals(123, result);

		// Start with '-'

		result = GetterUtil.get("-123", -1);

		assertEquals(-123, result);

		// Maximum int

		result = GetterUtil.get(Integer.toString(Integer.MAX_VALUE), -1);

		assertEquals(Integer.MAX_VALUE, result);

		// Minimum int

		result = GetterUtil.get(Integer.toString(Integer.MIN_VALUE), -1);

		assertEquals(Integer.MIN_VALUE, result);

		// Larger than maximum int

		result = GetterUtil.get(Integer.toString(Integer.MAX_VALUE) + "0", -1);

		assertEquals(-1, result);

		// Smaller than minimum int

		result = GetterUtil.get(Integer.toString(Integer.MIN_VALUE) + "0", -1);

		assertEquals(-1, result);
	}

	@Test
	public void testGetLong() {

		// Wrong first char

		long result = GetterUtil.get("e123", -1L);

		assertEquals(-1L, result);

		// Wrong middle char

		result = GetterUtil.get("12e3", -1L);

		assertEquals(-1L, result);

		// Start with '+'

		result = GetterUtil.get("+123", -1L);

		assertEquals(123L, result);

		// Start with '-'

		result = GetterUtil.get("-123", -1L);

		assertEquals(-123L, result);

		// Maximum long

		result = GetterUtil.get(Long.toString(Long.MAX_VALUE), -1L);

		assertEquals(Long.MAX_VALUE, result);

		// Minimum long

		result = GetterUtil.get(Long.toString(Long.MIN_VALUE), -1L);

		assertEquals(Long.MIN_VALUE, result);

		// Larger than maximum long

		result = GetterUtil.get(Long.toString(Long.MAX_VALUE) + "0", -1L);

		assertEquals(-1L, result);

		// Smaller than minimum long

		result = GetterUtil.get(Long.toString(Long.MIN_VALUE) + "0", -1L);

		assertEquals(-1L, result);
	}

	@Test
	public void testGetShort() {

		// Wrong first char

		short result = GetterUtil.get("e123", (short)-1);

		assertEquals((short)-1, result);

		// Wrong middle char

		result = GetterUtil.get("12e3", (short)-1);

		assertEquals((short)-1, result);

		// Start with '+'

		result = GetterUtil.get("+123", (short)-1);

		assertEquals((short)123, result);

		// Start with '-'

		result = GetterUtil.get("-123", (short)-1);

		assertEquals((short)-123, result);

		// Maximum short

		result = GetterUtil.get(Short.toString(Short.MAX_VALUE), (short)-1);

		assertEquals(Short.MAX_VALUE, result);

		// Minimum short

		result = GetterUtil.get(Short.toString(Short.MIN_VALUE), (short)-1);

		assertEquals(Short.MIN_VALUE, result);

		// Larger than maximum short

		result = GetterUtil.get(
			Short.toString(Short.MAX_VALUE) + "0", (short)-1);

		assertEquals((short)-1, result);

		// Smaller than minimum short

		result = GetterUtil.get(
			Short.toString(Short.MIN_VALUE) + "0", (short)-1);

		assertEquals((short)-1, result);
	}

	@Test
	public void testGetString() {
		assertEquals(
			StringPool.BLANK,
			GetterUtil.getString(StringPool.BLANK, "default"));
		assertEquals(GetterUtil.DEFAULT_STRING, GetterUtil.getString(null));
		assertEquals("default", GetterUtil.getString(null, "default"));
		assertEquals("default", GetterUtil.getString(new Object(), "default"));
		assertEquals("test", GetterUtil.getString("test"));
	}

}