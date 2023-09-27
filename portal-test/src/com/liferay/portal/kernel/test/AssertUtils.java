/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.sql.Blob;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Miguel Pastor
 */
public class AssertUtils {

	public static void assertEquals(Blob expectedBlob, Blob actualBlob)
		throws Exception {

		try (InputStream expectInputStream = expectedBlob.getBinaryStream();
			InputStream actualInputStream = actualBlob.getBinaryStream()) {

			while (true) {
				int expectValue = expectInputStream.read();
				int actualValue = actualInputStream.read();

				assertEquals(expectValue, actualValue);

				if (expectValue == -1) {
					break;
				}
			}
		}
	}

	public static void assertEquals(
		double expectedDouble, double actualDouble) {

		Assert.assertEquals(expectedDouble, actualDouble, 0);
	}

	public static void assertEquals(
		double[] expectedArray, double[] actualArray) {

		Assert.assertArrayEquals(expectedArray, actualArray, 0);
	}

	public static void assertEquals(List<?> expectedList, List<?> actualList) {
		Assert.assertEquals(
			"The lists have different sizes", expectedList.size(),
			actualList.size());

		Assert.assertTrue(expectedList.containsAll(actualList));
	}

	public static void assertEquals(
		Map<String, ?> expectedMap, Map<String, ?> actualMap) {

		Assert.assertEquals(
			"The maps have different sizes", expectedMap.size(),
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
			expectedString = StringUtil.toLowerCase(expectedString);
		}

		if (actualString != null) {
			actualString = StringUtil.toLowerCase(actualString);
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

	public static void assertFailure(
		Class<?> clazz, String message,
		UnsafeRunnable<Exception> unsafeRunnable) {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(clazz.isInstance(exception));
			Assert.assertEquals(exception.getMessage(), message);
		}
	}

	public static void assertLessThan(
			double expectedDouble, double actualDouble)
		throws Exception {

		if (actualDouble > expectedDouble) {
			Assert.fail(actualDouble + " is not less than " + expectedDouble);
		}
	}

}