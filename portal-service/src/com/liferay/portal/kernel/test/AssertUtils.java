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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.sql.Blob;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Miguel Pastor
 */
public class AssertUtils {

	public static void assertEquals(Blob expectedBlob, Blob actualBlob)
		throws Exception {

		InputStream expectInputStream = expectedBlob.getBinaryStream();
		InputStream actualInputStream = actualBlob.getBinaryStream();

		while (true) {
			int expectValue = expectInputStream.read();
			int actualValue = actualInputStream.read();

			assertEquals(expectValue, actualValue);

			if (expectValue == -1) {
				break;
			}
		}

		expectInputStream.close();
		actualInputStream.close();
	}

	public static void assertEquals(
		double expectedDouble, double actualDouble) {

		Assert.assertEquals(expectedDouble, actualDouble, 0);
	}

	public static void assertEquals(
		double[] expectedArray, double[] actualArray) {

		Assert.assertArrayEquals(expectedArray, actualArray, 0);
	}

	public static void assertEquals(
		Map<String, ?> expectedMap, Map<String, ?> actualMap) {

		Assert.assertEquals(
			"The maps are different sizes", expectedMap.size(),
			actualMap.size());

		for (String name : expectedMap.keySet()) {
			Assert.assertEquals(
				"The values for key '" + name + "' are different",
				MapUtil.getString(expectedMap, name),
				MapUtil.getString(actualMap, name));
		}
	}

	public static void assertEqualsIgnoreCase(
		String expectedString, String actualString) {

		if (expectedString != null) {
			expectedString = expectedString.toLowerCase();
		}

		if (actualString != null) {
			actualString = actualString.toLowerCase();
		}

		Assert.assertEquals(expectedString, actualString);
	}

	public static void assertEqualsSorted(
		String[] expectedStringArray, String[] actualStringArray) {

		if (expectedStringArray != null) {
			Arrays.sort(expectedStringArray);
		}

		if (actualStringArray != null) {
			Arrays.sort(actualStringArray);
		}

		Assert.assertEquals(
			StringUtil.merge(expectedStringArray),
			StringUtil.merge(actualStringArray));
	}

	public static void assertLessThan(
			double expectedDouble, double actualDouble)
		throws Exception {

		if (actualDouble > expectedDouble) {
			Assert.fail(actualDouble + " is not less than " + expectedDouble);
		}
	}

}