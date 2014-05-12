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
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Adam Brandizzi
 */
public class ParamUtilTest {

	@BeforeClass
	public static void setUpClass() {
		new CalendarFactoryUtil().setCalendarFactory(new CalendarFactoryImpl());
	}

	@Before
	public void setUp() {
		_portletRequest = new MockPortletRequest();
		_request = new MockHttpServletRequest();
	}

	@Test
	public void testGetDatePortletRequestMissingOneDateParam()
		throws Exception {

		_portletRequest.setParameter("myDateYear", String.valueOf(_year));
		_portletRequest.setParameter("myDateDay", String.valueOf(_day));

		Date date = ParamUtil.getDate(
			_portletRequest, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, date);
	}

	@Test
	public void testGetDatePortletRequestMissingOneTimeParam()
		throws Exception {

		_portletRequest.setParameter("myDateHour", String.valueOf(_hour));

		Date date = ParamUtil.getDate(
			_portletRequest, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, date);
	}

	@Test
	public void testGetDatePortletRequestWithAmPmParam() throws Exception {
		_portletRequest.setParameter("myDateHour", String.valueOf(_hour));
		_portletRequest.setParameter("myDateMinute", String.valueOf(_minute));
		_portletRequest.setParameter("myDateAmPm", String.valueOf(Calendar.PM));

		Date date = ParamUtil.getDate(
			_portletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			0, 0, 0, _hour + 12, _minute, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), date);
	}

	@Test
	public void testGetDatePortletRequestWithDateAndTimeParams()
		throws Exception {

		_portletRequest.setParameter("myDateYear", String.valueOf(_year));
		_portletRequest.setParameter("myDateMonth", String.valueOf(_month));
		_portletRequest.setParameter("myDateDay", String.valueOf(_day));
		_portletRequest.setParameter("myDateHour", String.valueOf(_hour));
		_portletRequest.setParameter("myDateMinute", String.valueOf(_minute));

		Date date = ParamUtil.getDate(
			_portletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_year, _month, _day, _hour, _minute, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), date);
	}

	@Test
	public void testGetDatePortletRequestWithDateParams() throws Exception {
		_portletRequest.setParameter("myDateYear", String.valueOf(_year));
		_portletRequest.setParameter("myDateMonth", String.valueOf(_month));
		_portletRequest.setParameter("myDateDay", String.valueOf(_day));

		Date date = ParamUtil.getDate(
			_portletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_year, _month, _day, 0, 0, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), date);
	}

	@Test
	public void testGetDatePortletRequestWithNoParam() throws Exception {
		Date date = ParamUtil.getDate(
			_portletRequest, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, date);
	}

	@Test
	public void testGetDatePortletRequestWithTimeParams() throws Exception {
		_portletRequest.setParameter("myDateHour", String.valueOf(_hour));
		_portletRequest.setParameter("myDateMinute", String.valueOf(_minute));

		Date date = ParamUtil.getDate(
			_portletRequest, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			0, 0, 0, _hour, _minute, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), date);
	}

	@Test
	public void testGetDateServletRequestMissingOneDateParam()
		throws Exception {

		_request.setParameter("myDateYear", String.valueOf(_year));
		_request.setParameter("myDateDay", String.valueOf(_day));

		Date date = ParamUtil.getDate(
			_request, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, date);
	}

	@Test
	public void testGetDateServletRequestMissingOneTimeParam()
		throws Exception {

		_request.setParameter("myDateHour", String.valueOf(_hour));

		Date date = ParamUtil.getDate(
			_request, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, date);
	}

	@Test
	public void testGetDateServletRequestWithAmPmParam() throws Exception {
		_request.setParameter("myDateHour", String.valueOf(_hour));
		_request.setParameter("myDateMinute", String.valueOf(_minute));
		_request.setParameter("myDateAmPm", String.valueOf(Calendar.PM));

		Date date = ParamUtil.getDate(
			_request, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			0, 0, 0, _hour + 12, _minute, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), date);
	}

	@Test
	public void testGetDateServletRequestWithDateAndTimeParams()
		throws Exception {

		_request.setParameter("myDateYear", String.valueOf(_year));
		_request.setParameter("myDateMonth", String.valueOf(_month));
		_request.setParameter("myDateDay", String.valueOf(_day));
		_request.setParameter("myDateHour", String.valueOf(_hour));
		_request.setParameter("myDateMinute", String.valueOf(_minute));

		Date date = ParamUtil.getDate(
			_request, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_year, _month, _day, _hour, _minute, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), date);
	}

	@Test
	public void testGetDateServletRequestWithDateParams() throws Exception {
		_request.setParameter("myDateYear", String.valueOf(_year));
		_request.setParameter("myDateMonth", String.valueOf(_month));
		_request.setParameter("myDateDay", String.valueOf(_day));

		Date date = ParamUtil.getDate(
			_request, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			_year, _month, _day, 0, 0, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), date);
	}

	@Test
	public void testGetDateServletRequestWithNoParam() throws Exception {
		Date date = ParamUtil.getDate(
			_request, "myDate", _timeZone, _defaultDate);

		Assert.assertEquals(_defaultDate, date);
	}

	@Test
	public void testGetDateServletRequestWithTimeParams() throws Exception {
		_request.setParameter("myDateHour", String.valueOf(_hour));
		_request.setParameter("myDateMinute", String.valueOf(_minute));

		Date date = ParamUtil.getDate(
			_request, "myDate", _timeZone, _defaultDate);

		Calendar expectedCalendar = CalendarFactoryUtil.getCalendar(
			0, 0, 0, _hour, _minute, 0, 0, _timeZone);

		Assert.assertEquals(expectedCalendar.getTime(), date);
	}

	private static final int _day = 22;
	private static final int _hour = 10;
	private static final int _minute = 32;
	private static final int _month = 11;
	private static final int _year = 2014;

	private Date _defaultDate  = DateUtil.newDate();
	private MockPortletRequest _portletRequest;
	private MockHttpServletRequest _request;
	private TimeZone _timeZone = TimeZoneUtil.getTimeZone(
		"America/Los_Angeles");

}