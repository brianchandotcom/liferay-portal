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

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;

/**
 * @author Renato Rego
 */
public class ExpandoConverterUtilTest {

	@Test
	public void testGetDateAttributeFromString() {
		long time = 0L;

		Date date = (Date) ExpandoConverterUtil.getAttributeFromString(
			ExpandoColumnConstants.DATE, String.valueOf(time));

		Assert.assertEquals(time, date.getTime());
	}

	@Test
	public void testGetDateArrayAttributeFromString() {
		long[] expectedTimeArray = { 0L, 10L, 100L };
		long[] actualTimeArray = new long[3];

		Date[] dateArray = (Date[]) ExpandoConverterUtil.getAttributeFromString(
			ExpandoColumnConstants.DATE_ARRAY,
			StringUtil.merge(expectedTimeArray));

		for (int i = 0; i < dateArray.length; i++) {
			actualTimeArray[i] = dateArray[i].getTime();
		}

		Assert.assertArrayEquals(expectedTimeArray, actualTimeArray);
	}

	@Test
	public void testGetDateAttributeFromStringArray() {
		long time = 0L;

		String[] timeStringArray = new String[1];

		timeStringArray[0] = String.valueOf(time);

		Date date = (Date) ExpandoConverterUtil.getAttributeFromStringArray(
			ExpandoColumnConstants.DATE, timeStringArray);

		Assert.assertEquals(time, date.getTime());
	}

	@Test
	public void testGetDateArrayAttributeFromStringArray() {
		long[] expectedTimeArray = { 0L, 10L, 100L };
		long[] actualTimeArray = new long[3];

		String[] expectedTimeStringArray = new String[3];

		for (int i = 0; i < expectedTimeArray.length; i++) {
			expectedTimeStringArray[i] = String.valueOf(expectedTimeArray[i]);
		}

		Date[] dateArray =
			(Date[]) ExpandoConverterUtil.getAttributeFromStringArray(
				ExpandoColumnConstants.DATE_ARRAY, expectedTimeStringArray);

		for (int i = 0; i < dateArray.length; i++) {
			actualTimeArray[i] = dateArray[i].getTime();
		}

		Assert.assertArrayEquals(expectedTimeArray, actualTimeArray);
	}

}