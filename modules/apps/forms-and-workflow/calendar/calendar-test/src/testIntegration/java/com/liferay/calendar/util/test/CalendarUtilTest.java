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

package com.liferay.calendar.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.util.CalendarUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRuel =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	@Test
	public void testToCalendarBookingsJSONArray() throws Exception {
		CalendarBooking approved =
			CalendarBookingTestUtil.addPublishedCalendarBooking(_user);

		CalendarBooking sameUserDraft =
			CalendarBookingTestUtil.addDraftCalendarBooking(_user);

		User user2 = UserTestUtil.addUser();

		CalendarBooking anotherUserDraft =
			CalendarBookingTestUtil.addDraftCalendarBooking(user2);

		List<CalendarBooking> calendarBookings = getCalendarBookingList(
			approved, sameUserDraft, anotherUserDraft);

		JSONArray jsonArray = CalendarUtil.toCalendarBookingsJSONArray(
			createThemeDisplay(), calendarBookings, TimeZoneUtil.getDefault());

		Assert.assertEquals(2, jsonArray.length());

		Set<Long> actualCalendarBookingIds = getCalendarBookingIds(jsonArray);

		Set<Long> excpectedCalendarBookingIds = getCalendarBookingIds(
			calendarBookings);

		excpectedCalendarBookingIds.remove(
			anotherUserDraft.getCalendarBookingId());

		Assert.assertEquals(
			excpectedCalendarBookingIds, actualCalendarBookingIds);
	}

	protected ThemeDisplay createThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLocale(LocaleUtil.getDefault());

		themeDisplay.setScopeGroupId(_group.getGroupId());

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		themeDisplay.setUser(_user);

		return themeDisplay;
	}

	protected Set<Long> getCalendarBookingIds(JSONArray jsonArray) {
		Set<Long> calendarBookingIds = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			calendarBookingIds.add(jsonObject.getLong("calendarBookingId"));
		}

		return calendarBookingIds;
	}

	protected Set<Long> getCalendarBookingIds(
		List<CalendarBooking> calendarBookings) {

		Set<Long> calendarBookingIds = new HashSet<>();

		for (CalendarBooking calendarBooking : calendarBookings) {
			calendarBookingIds.add(calendarBooking.getCalendarBookingId());
		}

		return calendarBookingIds;
	}

	protected List<CalendarBooking> getCalendarBookingList(
		CalendarBooking... calendarBookings) {

		return ListUtil.fromArray(calendarBookings);
	}

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _permissionChecker;

	@DeleteAfterTestRun
	private User _user;

}