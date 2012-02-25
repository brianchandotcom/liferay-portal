/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

/**
 * @author Michael C. Han
 */
public class PrimitiveIntListTest extends TestCase {

	public void testAdd() {
		int[] expected = new int[] {10, 11, 12};

		PrimitiveIntList primitiveIntList = new PrimitiveIntList();

		for (int i = 0; i < expected.length; i++) {
			primitiveIntList.add(expected[i]);
		}

		assertEquals(expected.length, primitiveIntList.size());

		int[] actual = primitiveIntList.getArray();

		assertEquals(expected.length, actual.length);

		for (int i = 0; i < actual.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}

	public void testAddAll() {
		int[] expected = new int[] {10, 11, 12};

		PrimitiveIntList primitiveIntList = new PrimitiveIntList();

		primitiveIntList.addAll(expected);

		assertEquals(expected.length, primitiveIntList.size());

		int[] actual = primitiveIntList.getArray();

		assertEquals(expected.length, actual.length);

		for (int i = 0; i < actual.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}

}