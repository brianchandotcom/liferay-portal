/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.ci;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
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
				StringBundler.concat(
					"Running in CI mode with ", testClassGroupIndex + 1, "/",
					testClassGroupsSize));
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