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

package com.liferay.portal.reports.engine.console.web.admin.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;

import javax.portlet.PortletRequest;

/**
 * @author Rafael Praxedes
 */
public class EntryDisplayTerms extends DisplayTerms {

	public static final String DEFINITION_NAME = "definitionName";

	public static final String END_DATE_DAY = "endDateDay";

	public static final String END_DATE_MONTH = "endDateMonth";

	public static final String END_DATE_YEAR = "endDateYear";

	public static final String START_DATE_DAY = "startDateDay";

	public static final String START_DATE_MONTH = "startDateMonth";

	public static final String START_DATE_YEAR = "startDateYear";

	public static final String USERNAME = "userName";

	public EntryDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		definitionName = ParamUtil.getString(portletRequest, DEFINITION_NAME);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			themeDisplay.getTimeZone(), themeDisplay.getLocale());

		endDateDay = ParamUtil.getInteger(
			portletRequest, END_DATE_DAY, calendar.get(Calendar.DATE));
		endDateMonth = ParamUtil.getInteger(
			portletRequest, END_DATE_MONTH, calendar.get(Calendar.MONTH));
		endDateYear = ParamUtil.getInteger(
			portletRequest, END_DATE_YEAR, calendar.get(Calendar.YEAR));

		calendar.add(Calendar.DATE, -1);

		startDateDay = ParamUtil.getInteger(
			portletRequest, START_DATE_DAY, calendar.get(Calendar.DATE));
		startDateMonth = ParamUtil.getInteger(
			portletRequest, START_DATE_MONTH, calendar.get(Calendar.MONTH));
		startDateYear = ParamUtil.getInteger(
			portletRequest, START_DATE_YEAR, calendar.get(Calendar.YEAR));

		userName = ParamUtil.getString(portletRequest, USERNAME);
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public int getEndDateDay() {
		return endDateDay;
	}

	public int getEndDateMonth() {
		return endDateMonth;
	}

	public int getEndDateYear() {
		return endDateYear;
	}

	public int getStartDateDay() {
		return startDateDay;
	}

	public int getStartDateMonth() {
		return startDateMonth;
	}

	public int getStartDateYear() {
		return startDateYear;
	}

	public String getUserName() {
		return userName;
	}

	protected String definitionName;
	protected int endDateDay;
	protected int endDateMonth;
	protected int endDateYear;
	protected int startDateDay;
	protected int startDateMonth;
	protected int startDateYear;
	protected String userName;

}