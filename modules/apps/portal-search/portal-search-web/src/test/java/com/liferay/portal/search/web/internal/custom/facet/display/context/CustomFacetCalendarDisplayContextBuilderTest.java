/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.facet.display.context;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.search.web.internal.custom.facet.display.context.builder.CustomFacetCalendarDisplayContextBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 * @author Petteri Karttunen
 */
public class CustomFacetCalendarDisplayContextBuilderTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDoNotBreakWithoutSettingValues() {
		CustomFacetCalendarDisplayContextBuilder
			customFacetCalendarDisplayContextBuilder =
				createDisplayContextBuilder();

		Assert.assertNotNull(customFacetCalendarDisplayContextBuilder.build());
	}

	@Test
	public void testGetRangeFromCurrentDay() {
		TimeZone timeZone = TimeZoneUtil.getDefault();

		CustomFacetCalendarDisplayContextBuilder
			customFacetCalendarDisplayContextBuilder =
				createDisplayContextBuilder(timeZone);

		CustomFacetCalendarDisplayContext customFacetCalendarDisplayContext =
			customFacetCalendarDisplayContextBuilder.build();

		Calendar todayCalendar = CalendarFactoryUtil.getCalendar(timeZone);

		Calendar yesterdayCalendar = (Calendar)todayCalendar.clone();

		yesterdayCalendar.add(Calendar.DAY_OF_MONTH, -1);

		_assertFromDateValues(
			yesterdayCalendar.get(Calendar.YEAR),
			yesterdayCalendar.get(Calendar.MONTH),
			yesterdayCalendar.get(Calendar.DAY_OF_MONTH),
			customFacetCalendarDisplayContext);

		_assertToDateValues(
			todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH),
			todayCalendar.get(Calendar.DAY_OF_MONTH),
			customFacetCalendarDisplayContext);
	}

	@Test
	public void testGetRangeFromLimitAttributes() {
		CustomFacetCalendarDisplayContextBuilder
			customFacetCalendarDisplayContextBuilder =
				createDisplayContextBuilder();

		CustomFacetCalendarDisplayContext customFacetCalendarDisplayContext =
			customFacetCalendarDisplayContextBuilder.from(
				"2018-01-31"
			).to(
				"2018-02-28"
			).build();

		_assertFromDateValues(
			2018, Calendar.JANUARY, 31, customFacetCalendarDisplayContext);
		_assertToDateValues(
			2018, Calendar.FEBRUARY, 28, customFacetCalendarDisplayContext);
	}

	@Test
	public void testGetRangeFromLimitAttributesWithWestwardTimeZone() {
		TimeZone timeZone = _getWestwardTimeZone(TimeZone.getDefault());

		if (timeZone == null) {
			return;
		}

		CustomFacetCalendarDisplayContextBuilder
			customFacetCalendarDisplayContextBuilder =
				createDisplayContextBuilder(timeZone);

		CustomFacetCalendarDisplayContext customFacetCalendarDisplayContext =
			customFacetCalendarDisplayContextBuilder.from(
				"2018-01-31"
			).to(
				"2018-02-28"
			).build();

		_assertFromDateValues(
			2018, Calendar.JANUARY, 31, customFacetCalendarDisplayContext);
		_assertToDateValues(
			2018, Calendar.FEBRUARY, 28, customFacetCalendarDisplayContext);
	}

	protected CustomFacetCalendarDisplayContextBuilder
		createDisplayContextBuilder() {

		return createDisplayContextBuilder(TimeZoneUtil.getDefault());
	}

	protected CustomFacetCalendarDisplayContextBuilder
		createDisplayContextBuilder(TimeZone timeZone) {

		CustomFacetCalendarDisplayContextBuilder
			customFacetCalendarDisplayContextBuilder =
				new CustomFacetCalendarDisplayContextBuilder();

		customFacetCalendarDisplayContextBuilder.locale(
			LocaleUtil.getDefault()
		).timeZone(
			timeZone
		);

		return customFacetCalendarDisplayContextBuilder;
	}

	private void _assertFromDateValues(
		int year, int month, int dayOfMonth,
		CustomFacetCalendarDisplayContext customFacetCalendarDisplayContext) {

		Assert.assertEquals(
			year, customFacetCalendarDisplayContext.getFromYearValue());
		Assert.assertEquals(
			month, customFacetCalendarDisplayContext.getFromMonthValue());
		Assert.assertEquals(
			dayOfMonth, customFacetCalendarDisplayContext.getFromDayValue());
	}

	private void _assertToDateValues(
		int year, int month, int dayOfMonth,
		CustomFacetCalendarDisplayContext customFacetCalendarDisplayContext) {

		Assert.assertEquals(
			year, customFacetCalendarDisplayContext.getToYearValue());
		Assert.assertEquals(
			month, customFacetCalendarDisplayContext.getToMonthValue());
		Assert.assertEquals(
			dayOfMonth, customFacetCalendarDisplayContext.getToDayValue());
	}

	private TimeZone _getWestwardTimeZone(TimeZone timeZone) {
		String[] availableIDs = TimeZone.getAvailableIDs(
			(int)(timeZone.getRawOffset() - Time.HOUR));

		if (availableIDs.length == 0) {
			return null;
		}

		return TimeZoneUtil.getTimeZone(availableIDs[0]);
	}

}