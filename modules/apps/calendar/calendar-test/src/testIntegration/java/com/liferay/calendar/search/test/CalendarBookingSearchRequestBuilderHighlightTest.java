/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.constants.CalendarBookingConstants;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.search.test.util.BaseSearchRequestBuilderHighlightTestCase;
import com.liferay.portal.test.rule.Inject;

import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class CalendarBookingSearchRequestBuilderHighlightTest
	extends BaseSearchRequestBuilderHighlightTestCase {

	@Override
	protected void addModels(String... keywords) throws Exception {
		Calendar calendar = _addCalendar();

		for (String keyword : keywords) {
			long startTime = DateUtil.newTime() + RandomTestUtil.randomInt();

			long endTime = startTime + Time.HOUR;

			_calendarBookingLocalService.addCalendarBooking(
				TestPropsValues.getUserId(), calendar.getCalendarId(),
				new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT, 0,
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(), keyword
				).build(),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(), keyword
				).build(),
				null, startTime, endTime, false, null, 0, "email", 0, "email",
				serviceContext);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return CalendarBooking.class;
	}

	protected String[] getFieldNames() {
		return new String[] {"description_en_US", "title_en_US"};
	}

	private Calendar _addCalendar() throws Exception {
		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				group.getGroupId(), serviceContext);

		return _calendarLocalService.addCalendar(
			TestPropsValues.getUserId(), group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), StringPool.BLANK
			).build(),
			StringPool.UTC, RandomTestUtil.randomInt(0, 255), false, false,
			false, serviceContext);
	}

	@Inject
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Inject
	private CalendarLocalService _calendarLocalService;

}