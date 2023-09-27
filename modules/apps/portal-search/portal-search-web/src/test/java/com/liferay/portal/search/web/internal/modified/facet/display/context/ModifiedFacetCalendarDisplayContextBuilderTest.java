/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.modified.facet.display.context;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.search.web.internal.modified.facet.display.context.builder.ModifiedFacetCalendarDisplayContextBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class ModifiedFacetCalendarDisplayContextBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDoNotBreakWithoutSettingValues() {
		ModifiedFacetCalendarDisplayContextBuilder
			modifiedFacetCalendarDisplayContextBuilder =
				createDisplayContextBuilder();

		Assert.assertNotNull(
			modifiedFacetCalendarDisplayContextBuilder.build());
	}

	@Test
	public void testGetRangeFromCurrentDay() {
		TimeZone timeZone = TimeZoneUtil.getDefault();

		ModifiedFacetCalendarDisplayContextBuilder
			modifiedFacetCalendarDisplayContextBuilder =
				createDisplayContextBuilder(timeZone);

		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext =
				modifiedFacetCalendarDisplayContextBuilder.build();

		Calendar todayCalendar = CalendarFactoryUtil.getCalendar(timeZone);

		Calendar yesterdayCalendar = (Calendar)todayCalendar.clone();

		yesterdayCalendar.add(Calendar.DAY_OF_MONTH, -1);

		_assertFromDateValues(
			yesterdayCalendar.get(Calendar.YEAR),
			yesterdayCalendar.get(Calendar.MONTH),
			yesterdayCalendar.get(Calendar.DAY_OF_MONTH),
			modifiedFacetCalendarDisplayContext);

		_assertToDateValues(
			todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH),
			todayCalendar.get(Calendar.DAY_OF_MONTH),
			modifiedFacetCalendarDisplayContext);
	}

	@Test
	public void testGetRangeFromLimitAttributes() {
		ModifiedFacetCalendarDisplayContextBuilder
			modifiedFacetCalendarDisplayContextBuilder =
				createDisplayContextBuilder();

		modifiedFacetCalendarDisplayContextBuilder.setFrom("2018-01-31");
		modifiedFacetCalendarDisplayContextBuilder.setTo("2018-02-28");

		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext =
				modifiedFacetCalendarDisplayContextBuilder.build();

		_assertFromDateValues(
			2018, Calendar.JANUARY, 31, modifiedFacetCalendarDisplayContext);
		_assertToDateValues(
			2018, Calendar.FEBRUARY, 28, modifiedFacetCalendarDisplayContext);
	}

	@Test
	public void testGetRangeFromLimitAttributesWithWestwardTimeZone() {
		TimeZone timeZone = _getWestwardTimeZone(TimeZone.getDefault());

		if (timeZone == null) {
			return;
		}

		ModifiedFacetCalendarDisplayContextBuilder
			modifiedFacetCalendarDisplayContextBuilder =
				createDisplayContextBuilder(timeZone);

		modifiedFacetCalendarDisplayContextBuilder.setFrom("2018-01-31");
		modifiedFacetCalendarDisplayContextBuilder.setTo("2018-02-28");

		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext =
				modifiedFacetCalendarDisplayContextBuilder.build();

		_assertFromDateValues(
			2018, Calendar.JANUARY, 31, modifiedFacetCalendarDisplayContext);
		_assertToDateValues(
			2018, Calendar.FEBRUARY, 28, modifiedFacetCalendarDisplayContext);
	}

	protected ModifiedFacetCalendarDisplayContextBuilder
		createDisplayContextBuilder() {

		return createDisplayContextBuilder(TimeZoneUtil.getDefault());
	}

	protected ModifiedFacetCalendarDisplayContextBuilder
		createDisplayContextBuilder(TimeZone timeZone) {

		ModifiedFacetCalendarDisplayContextBuilder
			modifiedFacetCalendarDisplayContextBuilder =
				new ModifiedFacetCalendarDisplayContextBuilder();

		modifiedFacetCalendarDisplayContextBuilder.setLocale(
			LocaleUtil.getDefault());
		modifiedFacetCalendarDisplayContextBuilder.setTimeZone(timeZone);

		return modifiedFacetCalendarDisplayContextBuilder;
	}

	private void _assertFromDateValues(
		int year, int month, int dayOfMonth,
		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext) {

		Assert.assertEquals(
			year, modifiedFacetCalendarDisplayContext.getFromYearValue());
		Assert.assertEquals(
			month, modifiedFacetCalendarDisplayContext.getFromMonthValue());
		Assert.assertEquals(
			dayOfMonth, modifiedFacetCalendarDisplayContext.getFromDayValue());
	}

	private void _assertToDateValues(
		int year, int month, int dayOfMonth,
		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext) {

		Assert.assertEquals(
			year, modifiedFacetCalendarDisplayContext.getToYearValue());
		Assert.assertEquals(
			month, modifiedFacetCalendarDisplayContext.getToMonthValue());
		Assert.assertEquals(
			dayOfMonth, modifiedFacetCalendarDisplayContext.getToDayValue());
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