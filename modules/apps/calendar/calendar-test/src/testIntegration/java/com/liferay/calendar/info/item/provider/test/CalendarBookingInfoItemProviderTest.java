/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class CalendarBookingInfoItemProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_calendar = CalendarTestUtil.addCalendar(_group);
	}

	@Test
	public void testGetInfoItemFromCalendarBookingInfoItemProvider()
		throws Exception {

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(_calendar);

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			calendarBooking.getPrimaryKey());

		InfoItemObjectProvider<CalendarBooking>
			calendarBookingInfoItemProvider =
				(InfoItemObjectProvider<CalendarBooking>)
					_infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemObjectProvider.class,
						CalendarBooking.class.getName(),
						infoItemIdentifier.getInfoItemServiceFilter());

		CalendarBooking publishedCalendarBooking =
			calendarBookingInfoItemProvider.getInfoItem(infoItemIdentifier);

		Assert.assertEquals(
			calendarBooking.getTitle(), publishedCalendarBooking.getTitle());
	}

	@Test(expected = NoSuchInfoItemException.class)
	public void testGetInvalidInfoItemFromCalendarBookingInfoItemProvider()
		throws Exception {

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			RandomTestUtil.randomLong());

		InfoItemObjectProvider<CalendarBooking>
			calendarBookingInfoItemProvider =
				(InfoItemObjectProvider<CalendarBooking>)
					_infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemObjectProvider.class,
						CalendarBooking.class.getName(),
						infoItemIdentifier.getInfoItemServiceFilter());

		calendarBookingInfoItemProvider.getInfoItem(infoItemIdentifier);
	}

	@DeleteAfterTestRun
	private Calendar _calendar;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}