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

package com.liferay.calendar.service.http;

import com.liferay.calendar.service.CalendarServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CalendarServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.calendar.model.CalendarSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.calendar.model.Calendar</code>, that is translated to a
 * <code>com.liferay.calendar.model.CalendarSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see CalendarServiceHttp
 * @generated
 */
public class CalendarServiceSoap {

	public static com.liferay.calendar.model.CalendarSoap addCalendar(
			long groupId, long calendarResourceId, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, String timeZoneId, int color,
			boolean defaultCalendar, boolean enableComments,
			boolean enableRatings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.calendar.model.Calendar returnValue =
				CalendarServiceUtil.addCalendar(
					groupId, calendarResourceId, nameMap, descriptionMap,
					timeZoneId, color, defaultCalendar, enableComments,
					enableRatings, serviceContext);

			return com.liferay.calendar.model.CalendarSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap deleteCalendar(
			long calendarId)
		throws RemoteException {

		try {
			com.liferay.calendar.model.Calendar returnValue =
				CalendarServiceUtil.deleteCalendar(calendarId);

			return com.liferay.calendar.model.CalendarSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String exportCalendar(long calendarId, String type)
		throws RemoteException {

		try {
			String returnValue = CalendarServiceUtil.exportCalendar(
				calendarId, type);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap fetchCalendar(
			long calendarId)
		throws RemoteException {

		try {
			com.liferay.calendar.model.Calendar returnValue =
				CalendarServiceUtil.fetchCalendar(calendarId);

			return com.liferay.calendar.model.CalendarSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap getCalendar(
			long calendarId)
		throws RemoteException {

		try {
			com.liferay.calendar.model.Calendar returnValue =
				CalendarServiceUtil.getCalendar(calendarId);

			return com.liferay.calendar.model.CalendarSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap[]
			getCalendarResourceCalendars(long groupId, long calendarResourceId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.calendar.model.Calendar> returnValue =
				CalendarServiceUtil.getCalendarResourceCalendars(
					groupId, calendarResourceId);

			return com.liferay.calendar.model.CalendarSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap[]
			getCalendarResourceCalendars(
				long groupId, long calendarResourceId, boolean defaultCalendar)
		throws RemoteException {

		try {
			java.util.List<com.liferay.calendar.model.Calendar> returnValue =
				CalendarServiceUtil.getCalendarResourceCalendars(
					groupId, calendarResourceId, defaultCalendar);

			return com.liferay.calendar.model.CalendarSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void importCalendar(long calendarId, String data, String type)
		throws RemoteException {

		try {
			CalendarServiceUtil.importCalendar(calendarId, data, type);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isManageableFromGroup(long calendarId, long groupId)
		throws RemoteException {

		try {
			boolean returnValue = CalendarServiceUtil.isManageableFromGroup(
				calendarId, groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap[] search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.Calendar> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.calendar.model.Calendar> returnValue =
				CalendarServiceUtil.search(
					companyId, groupIds, calendarResourceIds, keywords,
					andOperator, start, end, orderByComparator);

			return com.liferay.calendar.model.CalendarSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap[] search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.Calendar> orderByComparator,
			String actionId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.calendar.model.Calendar> returnValue =
				CalendarServiceUtil.search(
					companyId, groupIds, calendarResourceIds, keywords,
					andOperator, start, end, orderByComparator, actionId);

			return com.liferay.calendar.model.CalendarSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap[] search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.Calendar> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.calendar.model.Calendar> returnValue =
				CalendarServiceUtil.search(
					companyId, groupIds, calendarResourceIds, name, description,
					andOperator, start, end, orderByComparator);

			return com.liferay.calendar.model.CalendarSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap[] search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.Calendar> orderByComparator,
			String actionId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.calendar.model.Calendar> returnValue =
				CalendarServiceUtil.search(
					companyId, groupIds, calendarResourceIds, name, description,
					andOperator, start, end, orderByComparator, actionId);

			return com.liferay.calendar.model.CalendarSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = CalendarServiceUtil.searchCount(
				companyId, groupIds, calendarResourceIds, keywords,
				andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, String actionId)
		throws RemoteException {

		try {
			int returnValue = CalendarServiceUtil.searchCount(
				companyId, groupIds, calendarResourceIds, keywords, andOperator,
				actionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = CalendarServiceUtil.searchCount(
				companyId, groupIds, calendarResourceIds, name, description,
				andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator,
			String actionId)
		throws RemoteException {

		try {
			int returnValue = CalendarServiceUtil.searchCount(
				companyId, groupIds, calendarResourceIds, name, description,
				andOperator, actionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap updateCalendar(
			long calendarId, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, int color,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.calendar.model.Calendar returnValue =
				CalendarServiceUtil.updateCalendar(
					calendarId, nameMap, descriptionMap, color, serviceContext);

			return com.liferay.calendar.model.CalendarSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap updateCalendar(
			long calendarId, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, String timeZoneId, int color,
			boolean defaultCalendar, boolean enableComments,
			boolean enableRatings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.calendar.model.Calendar returnValue =
				CalendarServiceUtil.updateCalendar(
					calendarId, nameMap, descriptionMap, timeZoneId, color,
					defaultCalendar, enableComments, enableRatings,
					serviceContext);

			return com.liferay.calendar.model.CalendarSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.calendar.model.CalendarSoap updateColor(
			long calendarId, int color,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.calendar.model.Calendar returnValue =
				CalendarServiceUtil.updateColor(
					calendarId, color, serviceContext);

			return com.liferay.calendar.model.CalendarSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CalendarServiceSoap.class);

}