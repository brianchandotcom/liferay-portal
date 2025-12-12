/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.asset.model;

import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.web.internal.util.CalendarResourceUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Yuri Monteiro
 */
public class CalendarBookingAssetRendererFactoryTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_calendarBookingAssetRendererFactory,
			"_calendarModelResourcePermission", _modelResourcePermission);
	}

	@After
	public void tearDown() {
		_calendarResourceUtilMockedStatic.close();
	}

	@Test
	public void testHasAddPermission() throws Exception {
		_calendarResourceUtilMockedStatic.when(
			() -> CalendarResourceUtil.getScopeGroupCalendarResource(
				Mockito.anyLong(), Mockito.any(ServiceContext.class))
		).thenReturn(
			null
		);

		Assert.assertFalse(
			_calendarBookingAssetRendererFactory.hasAddPermission(
				_permissionChecker, RandomTestUtil.randomLong(), 0));

		_mockCalendarResource();

		Assert.assertFalse(
			_calendarBookingAssetRendererFactory.hasAddPermission(
				_permissionChecker, RandomTestUtil.randomLong(), 0));

		Calendar calendar = Mockito.mock(Calendar.class);

		long calendarId = RandomTestUtil.randomLong();

		_mockCalendar(calendar, calendarId);

		_mockManageBookingsPermission(calendarId);

		Assert.assertTrue(
			_calendarBookingAssetRendererFactory.hasAddPermission(
				_permissionChecker, RandomTestUtil.randomLong(), 0));

		Mockito.verify(
			_modelResourcePermission
		).contains(
			_permissionChecker, calendarId, CalendarActionKeys.MANAGE_BOOKINGS
		);
	}

	private void _mockCalendar(Calendar calendar, long calendarId) {
		Mockito.when(
			calendar.getCalendarId()
		).thenReturn(
			calendarId
		);

		Mockito.when(
			_calendarResource.getDefaultCalendar()
		).thenReturn(
			calendar
		);
	}

	private void _mockCalendarResource() {
		_calendarResourceUtilMockedStatic.when(
			() -> CalendarResourceUtil.getScopeGroupCalendarResource(
				Mockito.anyLong(), Mockito.any(ServiceContext.class))
		).thenReturn(
			_calendarResource
		);

		Mockito.when(
			_calendarResource.getDefaultCalendar()
		).thenReturn(
			null
		);
	}

	private void _mockManageBookingsPermission(long calendarId)
		throws Exception {

		Mockito.when(
			_modelResourcePermission.contains(
				Mockito.eq(_permissionChecker), Mockito.eq(calendarId),
				Mockito.eq(CalendarActionKeys.MANAGE_BOOKINGS))
		).thenReturn(
			true
		);
	}

	private final CalendarBookingAssetRendererFactory
		_calendarBookingAssetRendererFactory =
			new CalendarBookingAssetRendererFactory();
	private final CalendarResource _calendarResource = Mockito.mock(
		CalendarResource.class);
	private final MockedStatic<CalendarResourceUtil>
		_calendarResourceUtilMockedStatic = Mockito.mockStatic(
			CalendarResourceUtil.class);
	private final ModelResourcePermission<Calendar> _modelResourcePermission =
		Mockito.mock(ModelResourcePermission.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);

}