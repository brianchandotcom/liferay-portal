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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class NaturalOrderStringComparatorTest {

	@Test
	public void testSortCaseSensitive() {
		testSort(
			new String[] {"hello", "world", "Hello", "World", "HELLO", "WORLD"},
			new String[] {"HELLO", "Hello", "WORLD", "World", "hello", "world"},
			true);
	}

	@Test
	public void testSortNumericalString() {
		testSort(
			new String[] {
				"1 book", "100 dollar", "25 shoes", "04:00", "4:00", "04:30",
				"hello07world", "hello8world", "hello007world"
			},
			new String[] {
				"1 book", "04:00", "4:00", "04:30", "25 shoes", "100 dollar",
				"hello007world", "hello07world", "hello8world"
			},
			false);
	}

	@Test
	public void testSortRegularString() {
		testSort(
			new String[] {"hello", "world", "helloworld"},
			new String[] {"hello", "helloworld", "world"}, false);
	}

	@Test
	public void testSortSpecialChars() {
		testSort(
			new String[] {
				"hello_world", "helloworld", "hello world", "~hello_world"
			},
			new String[] {
				"~hello_world", "hello world", "hello_world", "helloworld"
			},
			false);
	}

	protected void testSort(
		String[] array, String[] sortedArray, boolean caseSensitive) {

		Arrays.sort(
			array, new NaturalOrderStringComparator(true, caseSensitive));

		Assert.assertEquals(
			Arrays.toString(sortedArray), array.length, sortedArray.length);

		for (int i = 0; i < array.length; i++) {
			Assert.assertEquals(array[i], sortedArray[i]);
		}

		Arrays.sort(
			array, new NaturalOrderStringComparator(false, caseSensitive));

		for (int i = 0; i < array.length; i++) {
			Assert.assertEquals(
				array[i], sortedArray[sortedArray.length - (i + 1)]);
		}
	}

}