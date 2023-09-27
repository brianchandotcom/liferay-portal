/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.change.log.builder.internal.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class NaturalOrderStringComparatorTest {

	@Test
	public void testSortCaseSensitive() {
		_testSort(
			new String[] {"hello", "world", "Hello", "World", "HELLO", "WORLD"},
			new String[] {"HELLO", "Hello", "WORLD", "World", "hello", "world"},
			true);
	}

	@Test
	public void testSortNumericalString() {
		_testSort(
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
		_testSort(
			new String[] {"hello", "world", "helloworld"},
			new String[] {"hello", "helloworld", "world"}, false);
	}

	private void _testSort(
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