/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.info.item.provider;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Shakir Shamim
 */
public class CalendarBookingInfoItemFriendlyURLProviderTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetFriendlyURL() {
		CalendarBooking calendarBooking = Mockito.mock(CalendarBooking.class);

		long calendarBookingId = RandomTestUtil.randomLong();

		Mockito.when(
			calendarBooking.getCalendarBookingId()
		).thenReturn(
			calendarBookingId
		);

		Assert.assertEquals(
			String.valueOf(calendarBookingId),
			_calendarBookingInfoItemFriendlyURLProvider.getFriendlyURL(
				calendarBooking, RandomTestUtil.randomString()));
	}

	private final CalendarBookingInfoItemFriendlyURLProvider
		_calendarBookingInfoItemFriendlyURLProvider =
			new CalendarBookingInfoItemFriendlyURLProvider();

}