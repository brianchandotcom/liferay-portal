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

package com.liferay.portal.reports.engine.console.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.reports.engine.console.exception.SourceJDBCConnectionException;

import java.sql.Connection;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * @author Gavin Wan
 */
public class ReportsEngineConsoleUtil {

	public static Calendar getDate(
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

		if (dateAmPm == Calendar.PM) {
			dateHour += 12;
		}

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			locale = themeDisplay.getLocale();
			timeZone = themeDisplay.getTimeZone();
		}
		else {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getDefault();
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

	public static Map<Locale, String> getLocalizationMap(
		HttpServletRequest request, String parameter) {

		Map<Locale, String> map = new HashMap<>();

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String localeParameter =
				parameter + StringPool.UNDERLINE + languageId;

			map.put(locale, ParamUtil.getString(request, localeParameter));
		}

		return map;
	}

	public static void validateJDBCConnection(
			String driverClassName, String driverUrl, String driverUserName,
			String driverPassword)
		throws PortalException {

		DataSource dataSource = null;
		Connection connection = null;

		try {
			Properties properties = new Properties();

			properties.setProperty("driverClassName", driverClassName);
			properties.setProperty("password", driverPassword);
			properties.setProperty("url", driverUrl);
			properties.setProperty("username", driverUserName);

			dataSource = BasicDataSourceFactory.createDataSource(properties);

			connection = dataSource.getConnection();
		}
		catch (Exception e) {
			throw new SourceJDBCConnectionException(e);
		}
		finally {
			DataAccess.cleanUp(connection);
		}
	}

}