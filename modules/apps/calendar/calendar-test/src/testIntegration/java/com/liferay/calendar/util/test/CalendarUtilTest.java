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
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.calendar.test.util.RecurrenceTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle testBundle = FrameworkUtil.getBundle(CalendarUtilTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		Bundle calendarWebBundle = null;

		for (Bundle bundle : bundleContext.getBundles()) {
			String symbolicName = bundle.getSymbolicName();

			if (symbolicName.equals("com.liferay.calendar.web")) {
				calendarWebBundle = bundle;

				break;
			}
		}

		BundleWiring bundleWiring = calendarWebBundle.adapt(BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		_calendarUtilClass = classLoader.loadClass(
			"com.liferay.calendar.web.internal.util.CalendarUtil");
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	@Test
	public void testToCalendarBookingJSONObjectSendLastInstanceRecurrenceWithAllFollowingInstanceFromChildRecurringInstance()
		throws Exception {

		CalendarBooking calendarBookingInstance =
			getCalendarBookingChildAllFollowingInstnace();

		Method method = _calendarUtilClass.getMethod(
			"toCalendarBookingJSONObject", ThemeDisplay.class,
			CalendarBooking.class, TimeZone.class);

		JSONObject jsonObject = (JSONObject)method.invoke(
			null, createThemeDisplay(), calendarBookingInstance,
			calendarBookingInstance.getTimeZone());

		Recurrence recurrence = RecurrenceSerializer.deserialize(
			jsonObject.getString("recurrence"),
			calendarBookingInstance.getTimeZone());

		assertRepeatsForever(recurrence);
	}

	@Test
	public void testToCalendarBookingJSONObjectSendLastInstanceRecurrenceWithAllFollowingInstanceFromParentRecurringInstance()
		throws Exception {

		CalendarBooking calendarBookingInstance =
			getCalendarBookingChildAllFollowingInstnace();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getRecurringCalendarBookingId());

		Method method = _calendarUtilClass.getMethod(
			"toCalendarBookingJSONObject", ThemeDisplay.class,
			CalendarBooking.class, TimeZone.class);

		JSONObject jsonObject = (JSONObject)method.invoke(
			null, createThemeDisplay(), calendarBooking,
			calendarBookingInstance.getTimeZone());

		Recurrence recurrence = RecurrenceSerializer.deserialize(
			jsonObject.getString("recurrence"), calendarBooking.getTimeZone());

		assertRepeatsForever(recurrence);
	}

	@Test
	public void testToCalendarBookingJSONObjectSendLastInstanceRecurrenceWithSingleInstanceFromChildRecurringInstance()
		throws Exception {

		CalendarBooking calendarBookingInstance =
			getCalendarBookingChildSingleInstance();

		Method method = _calendarUtilClass.getMethod(
			"toCalendarBookingJSONObject", ThemeDisplay.class,
			CalendarBooking.class, TimeZone.class);

		JSONObject jsonObject = (JSONObject)method.invoke(
			null, createThemeDisplay(), calendarBookingInstance,
			calendarBookingInstance.getTimeZone());

		Recurrence recurrence = RecurrenceSerializer.deserialize(
			jsonObject.getString("recurrence"),
			calendarBookingInstance.getTimeZone());

		assertRepeatsForever(recurrence);
	}

	@Test
	public void testToCalendarBookingJSONObjectSendLastInstanceRecurrenceWithSingleInstanceFromParentRecurringInstance()
		throws Exception {

		CalendarBooking calendarBookingInstance =
			getCalendarBookingChildSingleInstance();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getRecurringCalendarBookingId());

		Method method = _calendarUtilClass.getMethod(
			"toCalendarBookingJSONObject", ThemeDisplay.class,
			CalendarBooking.class, TimeZone.class);

		JSONObject jsonObject = (JSONObject)method.invoke(
			null, createThemeDisplay(), calendarBookingInstance,
			calendarBookingInstance.getTimeZone());

		Recurrence recurrence = RecurrenceSerializer.deserialize(
			jsonObject.getString("recurrence"), calendarBooking.getTimeZone());

		assertRepeatsForever(recurrence);
	}

	@Test
	public void testToCalendarBookingJSONObjectWorksWithoutManageBookingsPermission()
		throws Exception {

		_privateUser = UserTestUtil.addUser();

		Calendar calendar = CalendarTestUtil.addCalendar(_privateUser);

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRecurringCalendarBooking(
				_privateUser, calendar, RecurrenceTestUtil.getDailyRecurrence(),
				serviceContext);

		Method method = _calendarUtilClass.getMethod(
			"toCalendarBookingJSONObject", ThemeDisplay.class,
			CalendarBooking.class, TimeZone.class);

		method.invoke(
			null, createThemeDisplay(), calendarBooking,
			calendarBooking.getTimeZone());
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

		List<CalendarBooking> calendarBookings = getCalendarBookings(
			approved, sameUserDraft, anotherUserDraft);

		Method method = _calendarUtilClass.getMethod(
			"toCalendarBookingsJSONArray", ThemeDisplay.class, List.class,
			TimeZone.class);

		JSONArray jsonArray = (JSONArray)method.invoke(
			null, createThemeDisplay(), calendarBookings,
			TimeZoneUtil.getDefault());

		Assert.assertEquals(2, jsonArray.length());

		Set<Long> actualCalendarBookingIds = getCalendarBookingIds(jsonArray);

		Set<Long> excpectedCalendarBookingIds = getCalendarBookingIds(
			calendarBookings);

		excpectedCalendarBookingIds.remove(
			anotherUserDraft.getCalendarBookingId());

		Assert.assertEquals(
			excpectedCalendarBookingIds, actualCalendarBookingIds);
	}

	protected void assertRepeatsForever(Recurrence recurrence) {
		Assert.assertNotNull(recurrence);

		Assert.assertNull(recurrence.getUntilJCalendar());

		Assert.assertEquals(0, recurrence.getCount());
	}

	protected ServiceContext createServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_user.getCompanyId());
		serviceContext.setScopeGroupId(_user.getGroupId());
		serviceContext.setUserId(_user.getUserId());

		return serviceContext;
	}

	protected ThemeDisplay createThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLocale(LocaleUtil.getSiteDefault());

		themeDisplay.setScopeGroupId(_group.getGroupId());

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		themeDisplay.setUser(_user);

		return themeDisplay;
	}

	protected CalendarBooking getCalendarBookingChildAllFollowingInstnace()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		return CalendarBookingTestUtil.
			updateCalendarBookingInstanceAndAllFollowing(
				calendarBooking, 2, RandomTestUtil.randomLocaleStringMap(),
				serviceContext);
	}

	protected CalendarBooking getCalendarBookingChildSingleInstance()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		return CalendarBookingTestUtil.updateCalendarBookingInstance(
			calendarBooking, 2, RandomTestUtil.randomLocaleStringMap(),
			serviceContext);
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

	protected List<CalendarBooking> getCalendarBookings(
		CalendarBooking... calendarBookings) {

		return ListUtil.fromArray(calendarBookings);
	}

	private static Class<?> _calendarUtilClass;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _permissionChecker;

	@DeleteAfterTestRun
	private User _privateUser;

	@DeleteAfterTestRun
	private User _user;

}