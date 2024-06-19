/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.util;

import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.test.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
	public void testGetAttributeFromEmptyStringArray() {
		Assert.assertNull(
			ExpandoConverterUtil.getAttributeFromStringArray(
				ExpandoColumnConstants.DATE_ARRAY, new String[0]));
	}

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

	@Test
	public void testGetStringFromStringLocalizedAttribute() {
		Locale defaultLocale = LocaleUtil.getDefault();

		Locale nondefaultLocale = LocaleUtil.BRAZIL;

		if (defaultLocale.equals(nondefaultLocale)) {
			nondefaultLocale = LocaleUtil.GERMANY;
		}

		Assert.assertEquals(
			"hello",
			ExpandoConverterUtil.getStringFromAttribute(
				ExpandoColumnConstants.STRING_LOCALIZED,
				HashMapBuilder.put(
					defaultLocale, "hello"
				).put(
					nondefaultLocale, RandomTestUtil.randomString()
				).build()));
	}

	@Test
	public void testGetStringLocalizedAttributeFromStringArray() {
		String[] stringArray = {"hello", RandomTestUtil.randomString()};

		Serializable attribute =
			ExpandoConverterUtil.getAttributeFromStringArray(
				ExpandoColumnConstants.STRING_LOCALIZED, stringArray);

		Assert.assertTrue(attribute instanceof HashMap);

		Map<Locale, String> map = (HashMap<Locale, String>)attribute;

		Assert.assertEquals(map.toString(), 1, map.size());

		Assert.assertEquals("hello", map.get(LocaleUtil.getDefault()));
	}

}