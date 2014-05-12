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

package com.liferay.portal.kernel.util;

import com.liferay.portal.util.CalendarFactoryImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Adam Brandizzi
 */
public class ParamUtilTest {

	@Before
	public void setUp() {
		setupCalendarFactory();
	}

	@Test
	public void testGetDateFromPortletRequestMissingOneDateParam()
		throws Exception {

		_mockPortletRequest.setParameter("myDateYear", String.valueOf(_YEAR));
		_mockPortletRequest.setParameter("myDateDay", String.valueOf(_DAY));

		Date actualDate = ParamUtil.getDate(
			_mockPortletRequest, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, actualDate);
	}

	@Test
	public void testGetDateFromPortletRequestWithAmPmParam() throws Exception {
		_mockPortletRequest.setParameter("myDateYear", String.valueOf(_YEAR));
		_mockPortletRequest.setParameter("myDateMonth", String.valueOf(_MONTH));
		_mockPortletRequest.setParameter("myDateDay", String.valueOf(_DAY));
		_mockPortletRequest.setParameter("myDateHour", String.valueOf(_HOUR));
		_mockPortletRequest.setParameter(
			"myDateMinute", String.valueOf(_MINUTE));
		_mockPortletRequest.setParameter(
			"myDateAmPm", String.valueOf(Calendar.PM));

		Date actualDate = ParamUtil.getDate(
			_mockPortletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_YEAR, _MONTH, _DAY, _HOUR + 12, _MINUTE, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), actualDate);
	}

	@Test
	public void testGetDateFromPortletRequestWithDateAndTimeParams()
		throws Exception {

		_mockPortletRequest.setParameter("myDateYear", String.valueOf(_YEAR));
		_mockPortletRequest.setParameter("myDateMonth", String.valueOf(_MONTH));
		_mockPortletRequest.setParameter("myDateDay", String.valueOf(_DAY));
		_mockPortletRequest.setParameter("myDateHour", String.valueOf(_HOUR));
		_mockPortletRequest.setParameter(
			"myDateMinute", String.valueOf(_MINUTE));

		Date actualDate = ParamUtil.getDate(
			_mockPortletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_YEAR, _MONTH, _DAY, _HOUR, _MINUTE, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), actualDate);
	}

	@Test
	public void testGetDateFromPortletRequestWithDateParams() throws Exception {
		_mockPortletRequest.setParameter("myDateYear", String.valueOf(_YEAR));
		_mockPortletRequest.setParameter("myDateMonth", String.valueOf(_MONTH));
		_mockPortletRequest.setParameter("myDateDay", String.valueOf(_DAY));

		Date actualDate = ParamUtil.getDate(
			_mockPortletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_YEAR, _MONTH, _DAY, 0, 0, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), actualDate);
	}

	@Test
	public void testGetDateFromPortletRequestWithNoParam() throws Exception {
		Date actualDate = ParamUtil.getDate(
			_mockPortletRequest, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, actualDate);
	}

	@Test
	public void testGetDateFromServletRequestMissingOneDateParam()
		throws Exception {

		_mockHttpServletRequest.setParameter(
			"myDateYear", String.valueOf(_YEAR));
		_mockHttpServletRequest.setParameter("myDateDay", String.valueOf(_DAY));

		Date actualDate = ParamUtil.getDate(
			_mockHttpServletRequest, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, actualDate);
	}

	@Test
	public void testGetDateFromServletRequestWithAmPmParam() throws Exception {
		_mockHttpServletRequest.setParameter(
			"myDateYear", String.valueOf(_YEAR));
		_mockHttpServletRequest.setParameter(
			"myDateMonth", String.valueOf(_MONTH));
		_mockHttpServletRequest.setParameter("myDateDay", String.valueOf(_DAY));
		_mockHttpServletRequest.setParameter(
			"myDateHour", String.valueOf(_HOUR));
		_mockHttpServletRequest.setParameter(
			"myDateMinute", String.valueOf(_MINUTE));
		_mockHttpServletRequest.setParameter(
			"myDateAmPm", String.valueOf(Calendar.PM));

		Date actualDate = ParamUtil.getDate(
			_mockHttpServletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_YEAR, _MONTH, _DAY, _HOUR + 12, _MINUTE, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), actualDate);
	}

	@Test
	public void testGetDateFromServletRequestWithDateAndTimeParams()
		throws Exception {

		_mockHttpServletRequest.setParameter(
			"myDateYear", String.valueOf(_YEAR));
		_mockHttpServletRequest.setParameter(
			"myDateMonth", String.valueOf(_MONTH));
		_mockHttpServletRequest.setParameter("myDateDay", String.valueOf(_DAY));
		_mockHttpServletRequest.setParameter(
			"myDateHour", String.valueOf(_HOUR));
		_mockHttpServletRequest.setParameter(
			"myDateMinute", String.valueOf(_MINUTE));

		Date actualDate = ParamUtil.getDate(
			_mockHttpServletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_YEAR, _MONTH, _DAY, _HOUR, _MINUTE, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), actualDate);
	}

	@Test
	public void testGetDateFromServletRequestWithDateParams() throws Exception {
		_mockHttpServletRequest.setParameter(
			"myDateYear", String.valueOf(_YEAR));
		_mockHttpServletRequest.setParameter(
			"myDateMonth", String.valueOf(_MONTH));
		_mockHttpServletRequest.setParameter("myDateDay", String.valueOf(_DAY));

		Date actualDate = ParamUtil.getDate(
			_mockHttpServletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_YEAR, _MONTH, _DAY, 0, 0, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), actualDate);
	}

	@Test
	public void testGetDateFromServletRequestWithNoParam() throws Exception {
		Date actualDate = ParamUtil.getDate(
			_mockHttpServletRequest, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, actualDate);
	}

	protected void setupCalendarFactory() {
		CalendarFactoryUtil calendarFactoryUtil = new CalendarFactoryUtil();

		calendarFactoryUtil.setCalendarFactory(new CalendarFactoryImpl());
	}

	private static final int _DAY = 22;

	private static final int _HOUR = 10;

	private static final int _MINUTE = 32;

	private static final int _MONTH = 11;

	private static final int _YEAR = 2014;

	private Date _defaultDate  = DateUtil.newDate();
	private MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private MockPortletRequest _mockPortletRequest = new MockPortletRequest();
	private TimeZone _timeZone = TimeZoneUtil.getTimeZone(
		"America/Los_Angeles");

}