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
 * @author Alexander Chow
 */
public class UnicodePropertiesTest extends TestCase {

	@Test
	public void testLength() throws Exception {
		String key = "hello";
		String value = "world";

		UnicodeProperties props = new UnicodeProperties();

		props.setProperty(key, value);
		props.remove(key);

		assertEquals(0, props.getToStringLength());
	}

	@Test
	public void testSetNullProperty() throws Exception {
		UnicodeProperties props = new UnicodeProperties();

		int hashCode = props.hashCode();

		props.setProperty(null, "value");

		assertEquals(
			"setProperty() of null key must not change properties", hashCode,
			props.hashCode());

		props.setProperty("key", null);
		props.setProperty("key", "value");
		props.setProperty("key", null);

		assertEquals(
			"setProperty() of null value must remove entry", hashCode,
			props.hashCode());
	}

}