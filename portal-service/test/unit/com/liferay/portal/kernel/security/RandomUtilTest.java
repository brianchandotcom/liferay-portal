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

package com.liferay.portal.kernel.security;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RandomUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testNext() {
		RandomUtil.random = new PredictableRandom(_NUMBERS);

		for (int number : _NUMBERS) {
			Assert.assertEquals(number, RandomUtil.nextInt(10));
		}
	}

	@Test
	public void testNextInts() {
		int[] expectedResult = new int[] {6, 0, 1, 8, 4, 9, 3, 7, 2, 5};

		RandomUtil.random = new PredictableRandom(_NUMBERS);

		Assert.assertArrayEquals(expectedResult, RandomUtil.nextInts(10, 10));

		RandomUtil.random = new PredictableRandom(_NUMBERS);

		Assert.assertArrayEquals(expectedResult, RandomUtil.nextInts(10, 20));

		RandomUtil.random = new PredictableRandom(_NUMBERS);

		Assert.assertArrayEquals(
			new int[] {6, 0, 1, 8, 4}, RandomUtil.nextInts(10, 5));
	}

	@Test
	public void testShuffle() {
		RandomUtil.random = new PredictableRandom(_NUMBERS);

		String inputString = "abcdefghij";

		String shutffledString = RandomUtil.shuffle(inputString);

		char[] shutffledChars = shutffledString.toCharArray();

		Arrays.sort(shutffledChars);

		Assert.assertEquals(inputString, new String(shutffledChars));
	}

	private static final int[] _NUMBERS =
		new int[] {5, 2, 7, 3, 5, 4, 2, 1, 0, 0};

	private static class PredictableRandom extends Random {

		public PredictableRandom(int[] values) {
			_values = values;
		}

		@Override
		public int nextInt(int n) {
			int value = _values[_index++ % _values.length];

			if (value >= n) {
				throw new IllegalArgumentException(
					"Value " + value + " is larger than " + n);
			}

			return value;
		}

		private int[] _values;
		private int _index;

	}

}