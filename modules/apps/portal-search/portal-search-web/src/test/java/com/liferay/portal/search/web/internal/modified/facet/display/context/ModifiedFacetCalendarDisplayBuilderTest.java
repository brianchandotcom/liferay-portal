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

package com.liferay.portal.search.web.internal.modified.facet.display.context;

import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.util.CalendarFactoryImpl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class ModifiedFacetCalendarDisplayBuilderTest {

	@Before
	public void setUp() throws Exception {
		_calendarFactory = new CalendarFactoryImpl();
	}

	@Test
	public void testDoNotBreakWithoutSettingValues() {
		ModifiedFacetCalendarDisplayBuilder
			modifiedFacetCalendarDisplayBuilder = createDisplayBuilder();

		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext =
				modifiedFacetCalendarDisplayBuilder.build();

		Assert.assertNotNull(modifiedFacetCalendarDisplayContext);
	}

	@Test
	public void testGetRangeFromCurrentDay() {
		TimeZone timeZone = TimeZoneUtil.getDefault();

		ModifiedFacetCalendarDisplayBuilder
			modifiedFacetCalendarDisplayBuilder = createDisplayBuilder(
				timeZone);

		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext =
				modifiedFacetCalendarDisplayBuilder.build();

		Calendar todayCalendar = _calendarFactory.getCalendar(timeZone);

		Calendar yesterdayCalendar = (Calendar)todayCalendar.clone();

		yesterdayCalendar.add(Calendar.DAY_OF_MONTH, -1);

		assertFromDateValues(
			yesterdayCalendar.get(Calendar.YEAR),
			yesterdayCalendar.get(Calendar.MONTH),
			yesterdayCalendar.get(Calendar.DAY_OF_MONTH),
			modifiedFacetCalendarDisplayContext);

		assertToDateValues(
			todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH),
			todayCalendar.get(Calendar.DAY_OF_MONTH),
			modifiedFacetCalendarDisplayContext);
	}

	@Test
	public void testGetRangeFromLimitAttributes() {
		ModifiedFacetCalendarDisplayBuilder
			modifiedFacetCalendarDisplayBuilder = createDisplayBuilder();

		modifiedFacetCalendarDisplayBuilder.setFrom("2018-01-31");
		modifiedFacetCalendarDisplayBuilder.setTo("2018-02-28");

		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext =
				modifiedFacetCalendarDisplayBuilder.build();

		assertFromDateValues(
			2018, Calendar.JANUARY, 31, modifiedFacetCalendarDisplayContext);
		assertToDateValues(
			2018, Calendar.FEBRUARY, 28, modifiedFacetCalendarDisplayContext);
	}

	@Test
	public void testGetRangeFromLimitAttributesWithWestwardTimeZone() {
		Optional<TimeZone> timeZoneOptional = findWestwardTimeZoneOptional(
			TimeZone.getDefault());

		timeZoneOptional.ifPresent(
			timeZone -> {
				ModifiedFacetCalendarDisplayBuilder
					modifiedFacetCalendarDisplayBuilder = createDisplayBuilder(
						timeZone);

				modifiedFacetCalendarDisplayBuilder.setFrom("2018-01-31");
				modifiedFacetCalendarDisplayBuilder.setTo("2018-02-28");

				ModifiedFacetCalendarDisplayContext
					modifiedFacetCalendarDisplayContext =
						modifiedFacetCalendarDisplayBuilder.build();

				assertFromDateValues(
					2018, Calendar.JANUARY, 31,
					modifiedFacetCalendarDisplayContext);
				assertToDateValues(
					2018, Calendar.FEBRUARY, 28,
					modifiedFacetCalendarDisplayContext);
			});
	}

	protected void assertFromDateValues(
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

	protected void assertToDateValues(
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

	protected ModifiedFacetCalendarDisplayBuilder createDisplayBuilder() {
		return createDisplayBuilder(TimeZoneUtil.getDefault());
	}

	protected ModifiedFacetCalendarDisplayBuilder createDisplayBuilder(
		TimeZone timeZone) {

		ModifiedFacetCalendarDisplayBuilder
			modifiedFacetCalendarDisplayBuilder =
				new ModifiedFacetCalendarDisplayBuilder(_calendarFactory);

		modifiedFacetCalendarDisplayBuilder.setLocale(LocaleUtil.getDefault());
		modifiedFacetCalendarDisplayBuilder.setTimeZone(timeZone);

		return modifiedFacetCalendarDisplayBuilder;
	}

	protected Optional<TimeZone> findWestwardTimeZoneOptional(
		TimeZone timeZone) {

		String[] availableIDs = TimeZone.getAvailableIDs(
			(int)(timeZone.getRawOffset() - Time.HOUR));

		if (availableIDs.length == 0) {
			return Optional.empty();
		}

		return Optional.of(TimeZoneUtil.getTimeZone(availableIDs[0]));
	}

	private CalendarFactory _calendarFactory;

}