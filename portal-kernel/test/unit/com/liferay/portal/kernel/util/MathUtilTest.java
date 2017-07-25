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