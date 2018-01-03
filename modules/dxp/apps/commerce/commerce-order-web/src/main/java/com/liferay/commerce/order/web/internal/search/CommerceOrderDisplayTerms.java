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

package com.liferay.commerce.order.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;

import javax.portlet.PortletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderDisplayTerms extends DisplayTerms {

	public static final String END_CREATE_DATE = "endCreateDate";

	public static final String END_CREATE_DATE_DAY = END_CREATE_DATE + "Day";

	public static final String END_CREATE_DATE_MONTH =
		END_CREATE_DATE + "Month";

	public static final String END_CREATE_DATE_YEAR = END_CREATE_DATE + "Year";

	public static final String ORGANIZATION_ID = "organizationId";

	public static final String START_CREATE_DATE = "startCreateDate";

	public static final String START_CREATE_DATE_DAY =
		START_CREATE_DATE + "Day";

	public static final String START_CREATE_DATE_MONTH =
		START_CREATE_DATE + "Month";

	public static final String START_CREATE_DATE_YEAR =
		START_CREATE_DATE + "Year";

	public CommerceOrderDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			themeDisplay.getTimeZone(), themeDisplay.getLocale());

		_endCreateDateDay = ParamUtil.getInteger(
			portletRequest, END_CREATE_DATE_DAY, calendar.get(Calendar.DATE));
		_endCreateDateMonth = ParamUtil.getInteger(
			portletRequest, END_CREATE_DATE_MONTH,
			calendar.get(Calendar.MONTH));
		_endCreateDateYear = ParamUtil.getInteger(
			portletRequest, END_CREATE_DATE_YEAR, calendar.get(Calendar.YEAR));

		_organizationId = ParamUtil.getLong(portletRequest, ORGANIZATION_ID);

		calendar.add(Calendar.MONTH, -1);

		_startCreateDateDay = ParamUtil.getInteger(
			portletRequest, START_CREATE_DATE_DAY, calendar.get(Calendar.DATE));
		_startCreateDateMonth = ParamUtil.getInteger(
			portletRequest, START_CREATE_DATE_MONTH,
			calendar.get(Calendar.MONTH));
		_startCreateDateYear = ParamUtil.getInteger(
			portletRequest, START_CREATE_DATE_YEAR,
			calendar.get(Calendar.YEAR));
	}

	public int getEndCreateDateDay() {
		return _endCreateDateDay;
	}

	public int getEndCreateDateMonth() {
		return _endCreateDateMonth;
	}

	public int getEndCreateDateYear() {
		return _endCreateDateYear;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public int getStartCreateDateDay() {
		return _startCreateDateDay;
	}

	public int getStartCreateDateMonth() {
		return _startCreateDateMonth;
	}

	public int getStartCreateDateYear() {
		return _startCreateDateYear;
	}

	private final int _endCreateDateDay;
	private final int _endCreateDateMonth;
	private final int _endCreateDateYear;
	private final long _organizationId;
	private final int _startCreateDateDay;
	private final int _startCreateDateMonth;
	private final int _startCreateDateYear;

}