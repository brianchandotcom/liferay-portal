/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.util;

import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class ExpandoConverterUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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