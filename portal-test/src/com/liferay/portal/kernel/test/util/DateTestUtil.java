/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import java.util.Date;

import org.junit.Assert;

/**
 * @author Kyle Miho
 */
public class DateTestUtil {

	public static void assertEquals(Date expectedDate, Date actualDate) {
		if (expectedDate == actualDate) {
			return;
		}

		Assert.assertNotNull(expectedDate);

		Assert.assertNotNull(actualDate);

		Assert.assertEquals(expectedDate.getTime(), actualDate.getTime());
	}

	public static void assertNotEquals(Date expectedDate, Date actualDate) {
		Assert.assertNotSame(
			expectedDate + " is same as " + actualDate, expectedDate,
			actualDate);

		if ((expectedDate == null) || (actualDate == null)) {
			return;
		}

		Assert.assertNotEquals(expectedDate.getTime(), actualDate.getTime());
	}

}