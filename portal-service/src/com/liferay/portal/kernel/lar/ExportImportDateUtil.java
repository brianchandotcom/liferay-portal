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

package com.liferay.portal.kernel.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Mate Thurzo
 */
@ProviderType
public class ExportImportDateUtil {

	public static final String RANGE_ALL = "all";

	public static final String RANGE_DATE_RANGE = "dateRange";

	public static final String RANGE_FROM_LAST_PUBLISH_DATE =
		"fromLastPublishDate";

	public static final String RANGE_LAST = "last";

	public static Calendar getCalendar(
		PortletRequest portletRequest, String paramPrefix,
		boolean timeZoneSensitive) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		int dateMonth = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Month");
		int dateDay = ParamUtil.getInteger(portletRequest, paramPrefix + "Day");
		int dateYear = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Year");
		int dateHour = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Hour");
		int dateMinute = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Minute");
		int dateAmPm = ParamUtil.getInteger(
			portletRequest, paramPrefix + "AmPm");

		return getCalendar(
			dateAmPm, dateYear, dateMonth, dateDay, dateHour, dateMinute,
			themeDisplay.getLocale(), themeDisplay.getTimeZone(),
			timeZoneSensitive);
	}

	public static DateRange getDateRange(
			ExportImportConfiguration configuration)
		throws PortalException, SystemException {

		Map<String, Serializable> settingsMap = configuration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		String range = MapUtil.getString(parameterMap, "range");
		int rangeLast = MapUtil.getInteger(parameterMap, "last");
		int startDateAmPm = MapUtil.getInteger(parameterMap, "startDateAmPm");
		int startDateYear = MapUtil.getInteger(parameterMap, "startDateYear");
		int startDateMonth = MapUtil.getInteger(parameterMap, "startDateMonth");
		int startDateDay = MapUtil.getInteger(parameterMap, "startDateDay");
		int startDateHour = MapUtil.getInteger(parameterMap, "startDateHour");
		int startDateMinute = MapUtil.getInteger(
			parameterMap, "startDateMinute");
		int endDateAmPm = MapUtil.getInteger(parameterMap, "endDateAmPm");
		int endDateYear = MapUtil.getInteger(parameterMap, "endDateYear");
		int endDateMonth = MapUtil.getInteger(parameterMap, "endDateMonth");
		int endDateDay = MapUtil.getInteger(parameterMap, "endDateDay");
		int endDateHour = MapUtil.getInteger(parameterMap, "endDateHour");
		int endDateMinute = MapUtil.getInteger(parameterMap, "endDateMinute");

		long groupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		boolean privateLayout = MapUtil.getBoolean(
			settingsMap, "privateLayout");
		Locale locale = (Locale)settingsMap.get("locale");
		TimeZone timeZone = (TimeZone)settingsMap.get("timezone");

		return getDateRange(
			range, rangeLast, startDateAmPm, startDateYear, startDateMonth,
			startDateDay, startDateHour, startDateMinute, endDateAmPm,
			endDateYear, endDateMonth, endDateDay, endDateHour, endDateMinute,
			null, groupId, 0, privateLayout, locale, timeZone);
	}

	public static DateRange getDateRange(long configurationId)
		throws PortalException, SystemException {

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(configurationId);

		return getDateRange(exportImportConfiguration);
	}

	public static DateRange getDateRange(
			PortletRequest portletRequest, long groupId, boolean privateLayout,
			long plid, String portletId, String defaultRange)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String range = ParamUtil.getString(
			portletRequest, "range", defaultRange);
		int rangeLast = ParamUtil.getInteger(portletRequest, "last");
		int startDateAmPm = ParamUtil.getInteger(
			portletRequest, "startDateAmPm");
		int startDateYear = ParamUtil.getInteger(
			portletRequest, "startDateYear");
		int startDateMonth = ParamUtil.getInteger(
			portletRequest, "startDateMonth");
		int startDateDay = ParamUtil.getInteger(portletRequest, "startDateDay");
		int startDateHour = ParamUtil.getInteger(
			portletRequest, "startDateHour");
		int startDateMinute = ParamUtil.getInteger(
			portletRequest, "startDateMinute");
		int endDateAmPm = ParamUtil.getInteger(portletRequest, "endDateAmPm");
		int endDateYear = ParamUtil.getInteger(portletRequest, "endDateYear");
		int endDateMonth = ParamUtil.getInteger(portletRequest, "endDateMonth");
		int endDateDay = ParamUtil.getInteger(portletRequest, "endDateDay");
		int endDateHour = ParamUtil.getInteger(portletRequest, "endDateHour");
		int endDateMinute = ParamUtil.getInteger(
			portletRequest, "endDateMinute");

		return getDateRange(
			range, rangeLast, startDateAmPm, startDateYear, startDateMonth,
			startDateDay, startDateHour, startDateMinute, endDateAmPm,
			endDateYear, endDateMonth, endDateDay, endDateHour, endDateMinute,
			portletId, groupId, plid, privateLayout, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
	}

	protected static Calendar getCalendar(
		int dateAmPm, int dateYear, int dateMonth, int dateDay, int dateHour,
		int dateMinute, Locale locale, TimeZone timeZone,
		boolean timeZoneSensitive) {

		if (dateAmPm == Calendar.PM) {
			dateHour += 12;
		}

		if (!timeZoneSensitive) {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getTimeZone(StringPool.UTC);
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

		calendar.set(Calendar.MONTH, dateMonth);
		calendar.set(Calendar.DATE, dateDay);
		calendar.set(Calendar.YEAR, dateYear);
		calendar.set(Calendar.HOUR_OF_DAY, dateHour);
		calendar.set(Calendar.MINUTE, dateMinute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	protected static DateRange getDateRange(
			String range, int rangeLast, int startDateAmPm, int startDateYear,
			int startDateMonth, int startDateDay, int startDateHour,
			int startDateMinute, int endDateAmPm, int endDateYear,
			int endDateMonth, int endDateDay, int endDateHour,
			int endDateMinute, String portletId, long groupId, long plid,
			boolean privateLayout, Locale locale, TimeZone timeZone)
		throws PortalException, SystemException {

		Date startDate = null;
		Date endDate = null;

		if (range.equals(RANGE_DATE_RANGE)) {
			Calendar startCalendar = getCalendar(
				startDateAmPm, startDateYear, startDateMonth, startDateDay,
				startDateHour, startDateMinute, locale, timeZone, true);

			startDate = startCalendar.getTime();

			Calendar endCalendar = getCalendar(
				endDateAmPm, endDateYear, endDateMonth, endDateDay, endDateHour,
				endDateMinute, locale, timeZone, true);

			endDate = endCalendar.getTime();
		}
		else if (range.equals(RANGE_FROM_LAST_PUBLISH_DATE)) {
			long lastPublishDate = 0;

			if (Validator.isNotNull(portletId) && (plid > 0)) {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				PortletPreferences preferences =
					PortletPreferencesFactoryUtil.getStrictPortletSetup(
						layout, portletId);

				lastPublishDate = GetterUtil.getLong(
					preferences.getValue(
						"last-publish-date", StringPool.BLANK));
			}
			else {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

				lastPublishDate = GetterUtil.getLong(
					layoutSet.getSettingsProperty("last-publish-date"));
			}

			if (lastPublishDate > 0) {
				endDate = new Date();

				startDate = new Date(lastPublishDate);
			}
		}
		else if (range.equals(RANGE_LAST)) {
			Date now = new Date();

			startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

			endDate = now;
		}

		return new DateRange(startDate, endDate);
	}

}