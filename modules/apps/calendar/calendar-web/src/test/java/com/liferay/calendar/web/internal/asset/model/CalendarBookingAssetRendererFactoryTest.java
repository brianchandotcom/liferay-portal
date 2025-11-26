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
		_calendarBookingAssetRendererFactory =
			new CalendarBookingAssetRendererFactory();

		_modelResourcePermission = Mockito.mock(ModelResourcePermission.class);

		ReflectionTestUtil.setFieldValue(
			_calendarBookingAssetRendererFactory,
			"_calendarModelResourcePermission", _modelResourcePermission);
	}

	@Test
	public void testHasAddPermission() throws Exception {
		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			permissionChecker.getCompanyId()
		).thenReturn(
			companyId
		);

		CalendarResource calendarResource = Mockito.mock(
			CalendarResource.class);

		Mockito.when(
			calendarResource.getDefaultCalendar()
		).thenReturn(
			null
		);

		try (MockedStatic<CalendarResourceUtil>
				calendarResourceUtilMockedStatic = Mockito.mockStatic(
					CalendarResourceUtil.class)) {

			calendarResourceUtilMockedStatic.when(
				() -> CalendarResourceUtil.getScopeGroupCalendarResource(
					Mockito.anyLong(), Mockito.any(ServiceContext.class))
			).thenReturn(
				calendarResource
			);

			long groupId = RandomTestUtil.randomLong();

			Assert.assertFalse(
				_calendarBookingAssetRendererFactory.hasAddPermission(
					permissionChecker, groupId, 0));

			Calendar calendar = Mockito.mock(Calendar.class);

			long calendarId = RandomTestUtil.randomLong();

			Mockito.when(
				calendar.getCalendarId()
			).thenReturn(
				calendarId
			);

			Mockito.when(
				calendarResource.getDefaultCalendar()
			).thenReturn(
				calendar
			);

			Mockito.when(
				_modelResourcePermission.contains(
					Mockito.eq(permissionChecker), Mockito.eq(calendarId),
					Mockito.anyString())
			).thenReturn(
				true
			);

			Assert.assertTrue(
				_calendarBookingAssetRendererFactory.hasAddPermission(
					permissionChecker, groupId, 0));

			Mockito.verify(
				_modelResourcePermission
			).contains(
				permissionChecker, calendarId,
				CalendarActionKeys.MANAGE_BOOKINGS
			);
		}
	}

	private CalendarBookingAssetRendererFactory
		_calendarBookingAssetRendererFactory;
	private ModelResourcePermission<Calendar> _modelResourcePermission;

}