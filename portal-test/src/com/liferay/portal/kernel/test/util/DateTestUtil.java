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

package com.liferay.portal.kernel.test.util;

import java.util.Date;

import org.junit.Assert;

/**
 * @author Kyle Miho
 */
public class DateTestUtil {

	public static void assertEquals(Date expectedDate, Date actualDate) {
		if ((expectedDate == null) && (actualDate == null)) {
			return;
		}

		Assert.assertNotNull(expectedDate);

		Assert.assertNotNull(actualDate);

		Assert.assertEquals(expectedDate.getTime(), actualDate.getTime());
	}

	public static void assertNotEquals(Date unexpectedDate, Date actualDate) {
		if ((unexpectedDate == null) && (actualDate == null)) {
			Assert.fail("Values should be different, but both are null");
		}

		if ((unexpectedDate == null) ^ (actualDate == null)) {
			return;
		}

		Assert.assertNotEquals(unexpectedDate.getTime(), actualDate.getTime());
	}

}