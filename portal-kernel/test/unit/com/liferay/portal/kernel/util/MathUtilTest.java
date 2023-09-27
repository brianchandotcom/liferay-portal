/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class MathUtilTest {

	@Test
	public void testDifferenceWithIntegerValues() {
		int expected = 8 - 2;

		Assert.assertEquals(expected, MathUtil.difference(8, 2));
	}

	@Test
	public void testFactorialPositiveInteger() {
		int expected = 1 * 2 * 3 * 4;

		Assert.assertEquals(expected, MathUtil.factorial(4));
	}

	@Test
	public void testFactorialZero() {
		int expected = 1;

		Assert.assertEquals(expected, MathUtil.factorial(0));
	}

	@Test
	public void testIsEven() {
		Assert.assertFalse(MathUtil.isEven(3));
		Assert.assertTrue(MathUtil.isEven(4));
	}

	@Test
	public void testIsOdd() {
		Assert.assertFalse(MathUtil.isOdd(4));
		Assert.assertTrue(MathUtil.isOdd(3));
	}

	@Test
	public void testProductWithDoubleValues() {
		double expected = 1.1 * 2.2 * 3.3 * 5.5 * 8.8;

		Assert.assertEquals(
			expected, MathUtil.product(1.1, 2.2, 3.3, 5.5, 8.8), 0.01);
	}

	@Test
	public void testProductWithIntegerValues() {
		int expected = 1 * 2 * 3 * 5 * 8;

		Assert.assertEquals(expected, MathUtil.product(1, 2, 3, 5, 8));
	}

	@Test
	public void testProductWithLongValues() {
		long expected = 1 * 2 * 3 * 5 * 8;

		Assert.assertEquals(expected, MathUtil.product(1L, 2L, 3L, 5L, 8L));
	}

	@Test
	public void testQuotientWithIntegerValues() {
		int expected = 8 / 2;

		Assert.assertEquals(expected, MathUtil.quotient(8, 2));
	}

	@Test
	public void testSumWithDoubleValues() {
		double expected = 1.1 + 2.2 + 3.3 + 5.5 + 8.8;

		Assert.assertEquals(
			expected, MathUtil.sum(1.1, 2.2, 3.3, 5.5, 8.8), 0.01);
	}

	@Test
	public void testSumWithIntegerValues() {
		int expected = 1 + 2 + 3 + 5 + 8;

		Assert.assertEquals(expected, MathUtil.sum(1, 2, 3, 5, 8));
	}

	@Test
	public void testSumWithLongValues() {
		long expected = 1 + 2 + 3 + 5 + 8;

		Assert.assertEquals(expected, MathUtil.sum(1L, 2L, 3L, 5L, 8L));
	}

}