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

package com.liferay.portal.test.assertion;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

import org.junit.Assert;

/**
 * @author Miguel Pastor
 */
public class AssertUtils {

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

}