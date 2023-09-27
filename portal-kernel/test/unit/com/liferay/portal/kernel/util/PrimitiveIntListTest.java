/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class PrimitiveIntListTest {

	@Test
	public void testAdd() {
		int[] expected = {10, 11, 12};

		PrimitiveIntList primitiveIntList = new PrimitiveIntList();

		for (int i : expected) {
			primitiveIntList.add(i);
		}

		Assert.assertEquals(expected.length, primitiveIntList.size());

		int[] actual = primitiveIntList.getArray();

		Assert.assertEquals(
			Arrays.toString(actual), expected.length, actual.length);

		for (int i = 0; i < actual.length; i++) {
			Assert.assertEquals(expected[i], actual[i]);
		}
	}

	@Test
	public void testAddAll() {
		int[] expected = {10, 11, 12};

		PrimitiveIntList primitiveIntList = new PrimitiveIntList();

		primitiveIntList.addAll(expected);

		Assert.assertEquals(expected.length, primitiveIntList.size());

		int[] actual = primitiveIntList.getArray();

		Assert.assertEquals(
			Arrays.toString(actual), expected.length, actual.length);

		for (int i = 0; i < actual.length; i++) {
			Assert.assertEquals(expected[i], actual[i]);
		}
	}

}