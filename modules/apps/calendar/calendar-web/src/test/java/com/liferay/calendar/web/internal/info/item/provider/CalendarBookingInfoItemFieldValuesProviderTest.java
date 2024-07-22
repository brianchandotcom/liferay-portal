/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.info.item.provider;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class CalendarBookingInfoItemFieldValuesProviderTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpCalendarBooking();
		_setUpCompanyLocalService();
		_setUpGroupLocalService();
		_setUpLayoutLocalService();
		_setUpPortal();
	}

	@Test
	public void testGetCalendarBookingURL() throws Exception {
		Assert.assertEquals(
			_getCalendarBookingURL(_LAYOUT_ACTUAL_URL),
			_calendarBookingInfoItemFieldValuesProvider.getCalendarBookingURL(
				_calendarBooking));

		Mockito.when(
			_layoutLocalService.fetchLayout(_DEFAULT_PUBLIC_PLID)
		).thenReturn(
			null
		);

		Assert.assertEquals(
			_getCalendarBookingURL(_GUEST_LAYOUT_ACTUAL_URL),
			_calendarBookingInfoItemFieldValuesProvider.getCalendarBookingURL(
				_calendarBooking));
	}

	private String _getCalendarBookingURL(String layoutActualURL) {
		return StringBundler.concat(
			_PORTAL_URL, layoutActualURL, StringPool.QUESTION,
			_PORTLET_NAMESPACE, "mvcPath=%2Fview_calendar_booking.jsp&p_p_id=",
			CalendarPortletKeys.CALENDAR,
			"&p_p_lifecycle=0&p_p_state=maximized&", _PORTLET_NAMESPACE,
			"calendarBookingId=", _CALENDAR_BOOKING_ID);
	}

	private void _setUpCalendarBooking() throws Exception {
		Mockito.when(
			_calendarBooking.getCalendarBookingId()
		).thenReturn(
			_CALENDAR_BOOKING_ID
		);

		Mockito.when(
			_calendarBooking.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			_calendarBooking.getGroupId()
		).thenReturn(
			_GROUP_ID
		);
	}

	private void _setUpCompanyLocalService() throws Exception {
		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			company.getPortalURL(_GROUP_ID)
		).thenReturn(
			_PORTAL_URL
		);

		Mockito.when(
			_companyLocalService.getCompany(_COMPANY_ID)
		).thenReturn(
			company
		);

		ReflectionTestUtil.setFieldValue(
			_calendarBookingInfoItemFieldValuesProvider, "_companyLocalService",
			_companyLocalService);
	}

	private void _setUpGroupLocalService() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getDefaultPublicPlid()
		).thenReturn(
			_DEFAULT_PUBLIC_PLID
		);

		Mockito.when(
			_groupLocalService.getGroup(_GROUP_ID)
		).thenReturn(
			group
		);

		Group guestGroup = Mockito.mock(Group.class);

		Mockito.when(
			guestGroup.getDefaultPublicPlid()
		).thenReturn(
			_GUEST_DEFAULT_PUBLIC_PLID
		);

		Mockito.when(
			_groupLocalService.getGroup(_COMPANY_ID, GroupConstants.GUEST)
		).thenReturn(
			guestGroup
		);

		ReflectionTestUtil.setFieldValue(
			_calendarBookingInfoItemFieldValuesProvider, "_groupLocalService",
			_groupLocalService);
	}

	private void _setUpLayoutLocalService() throws Exception {
		Mockito.when(
			_layoutLocalService.fetchLayout(_GUEST_DEFAULT_PUBLIC_PLID)
		).thenReturn(
			_guestLayout
		);

		Mockito.when(
			_layoutLocalService.fetchLayout(_DEFAULT_PUBLIC_PLID)
		).thenReturn(
			_layout
		);

		ReflectionTestUtil.setFieldValue(
			_calendarBookingInfoItemFieldValuesProvider, "_layoutLocalService",
			_layoutLocalService);
	}

	private void _setUpPortal() throws Exception {
		Mockito.when(
			_portal.getLayoutActualURL(_guestLayout)
		).thenReturn(
			_GUEST_LAYOUT_ACTUAL_URL
		);

		Mockito.when(
			_portal.getLayoutActualURL(_layout)
		).thenReturn(
			_LAYOUT_ACTUAL_URL
		);

		Mockito.when(
			_portal.getPortletNamespace(CalendarPortletKeys.CALENDAR)
		).thenReturn(
			_PORTLET_NAMESPACE
		);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgument(0, String.class), StringPool.BLANK
			}
		).when(
			_portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);

		ReflectionTestUtil.setFieldValue(
			_calendarBookingInfoItemFieldValuesProvider, "_portal", _portal);
	}

	private static final long _CALENDAR_BOOKING_ID =
		RandomTestUtil.randomLong();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _DEFAULT_PUBLIC_PLID =
		RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final long _GUEST_DEFAULT_PUBLIC_PLID =
		RandomTestUtil.randomLong();

	private static final String _GUEST_LAYOUT_ACTUAL_URL =
		RandomTestUtil.randomString();

	private static final String _LAYOUT_ACTUAL_URL =
		RandomTestUtil.randomString();

	private static final String _PORTAL_URL = RandomTestUtil.randomString();

	private static final String _PORTLET_NAMESPACE =
		RandomTestUtil.randomString();

	private final CalendarBooking _calendarBooking = Mockito.mock(
		CalendarBooking.class);
	private final CalendarBookingInfoItemFieldValuesProvider
		_calendarBookingInfoItemFieldValuesProvider =
			new CalendarBookingInfoItemFieldValuesProvider();
	private final CompanyLocalService _companyLocalService = Mockito.mock(
		CompanyLocalService.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final Layout _guestLayout = Mockito.mock(Layout.class);
	private final Layout _layout = Mockito.mock(Layout.class);
	private final LayoutLocalService _layoutLocalService = Mockito.mock(
		LayoutLocalService.class);
	private final Portal _portal = Mockito.mock(Portal.class);

}