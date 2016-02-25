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

package com.liferay.portlet.expando.util;

import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoConverterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class ExpandoConverterUtilTest {

	@Test
	public void testGetDateArrayAttributeFromString() {
		long[] expectedTimes = {0, 10, 100};

		Date[] dates = (Date[])ExpandoConverterUtil.getAttributeFromString(
			ExpandoColumnConstants.DATE_ARRAY, StringUtil.merge(expectedTimes));

		long[] actualTimes = new long[3];

		for (int i = 0; i < dates.length; i++) {
			actualTimes[i] = dates[i].getTime();
		}

		Assert.assertArrayEquals(expectedTimes, actualTimes);
	}

	@Test
	public void testGetDateArrayAttributeFromStringArray() {
		long[] expectedTimes = {0, 10, 100};

		String[] expectedTimeStrings = new String[3];

		for (int i = 0; i < expectedTimes.length; i++) {
			expectedTimeStrings[i] = String.valueOf(expectedTimes[i]);
		}

		long[] actualTimes = new long[3];

		Date[] actualDates =
			(Date[])ExpandoConverterUtil.getAttributeFromStringArray(
				ExpandoColumnConstants.DATE_ARRAY, expectedTimeStrings);

		for (int i = 0; i < actualDates.length; i++) {
			actualTimes[i] = actualDates[i].getTime();
		}

		Assert.assertArrayEquals(expectedTimes, actualTimes);
	}

	@Test
	public void testGetDateAttributeFromString() {
		long expectedTime = 0;

		Date date = (Date)ExpandoConverterUtil.getAttributeFromString(
			ExpandoColumnConstants.DATE, String.valueOf(expectedTime));

		Assert.assertEquals(expectedTime, date.getTime());
	}

	@Test
	public void testGetDateAttributeFromStringArray() {
		long expectedTime = 0;

		String[] expectedTimeStrings = new String[1];

		expectedTimeStrings[0] = String.valueOf(expectedTime);

		Date actualDate =
			(Date)ExpandoConverterUtil.getAttributeFromStringArray(
				ExpandoColumnConstants.DATE, expectedTimeStrings);

		Assert.assertEquals(expectedTime, actualDate.getTime());
	}

}