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

package com.liferay.portal.kernel.test.ci;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.BeforeClass;

/**
 * @author Shuyang Zhou
 */
public abstract class AutoBalanceTestCase {

	@BeforeClass
	public static void setUpClass() {
		testClassGroupIndex = GetterUtil.getInteger(
			System.getProperty("test.class.group.index"), -1);

		if (testClassGroupIndex >= 0) {
			String[] testClassGroupArray = StringUtil.split(
				System.getProperty("test.class.groups"), CharPool.SPACE);

			testClassGroupsSize = testClassGroupArray.length;
		}

		if (isCIMode()) {
			System.out.println(
				"Running in CI mode with " + (testClassGroupIndex + 1) + "/" +
					testClassGroupsSize);
		}
	}

	protected static boolean isCIMode() {
		if ((testClassGroupIndex >= 0) && (testClassGroupsSize > 0)) {
			return true;
		}

		return false;
	}

	protected static <T> T[] slice(T[] array) {
		int groupSize = array.length / testClassGroupsSize;

		if ((array.length % testClassGroupsSize) != 0) {
			groupSize++;
		}

		int start = groupSize * testClassGroupIndex;

		int end = start + groupSize;

		if (end > array.length) {
			end = array.length;
		}

		return ArrayUtil.subset(array, start, end);
	}

	protected static int testClassGroupIndex;
	protected static int testClassGroupsSize;

}