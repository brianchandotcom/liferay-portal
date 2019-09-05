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

import com.liferay.calendar.service.CalendarBookingServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CalendarBookingServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see CalendarBookingServiceSoap
 * @generated
 */
public class CalendarBookingServiceHttp {

	public static com.liferay.calendar.model.CalendarBooking addCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarId,
			long[] childCalendarIds, long parentCalendarBookingId,
			long recurringCalendarBookingId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, int startTimeYear, int startTimeMonth,
			int startTimeDay, int startTimeHour, int startTimeMinute,
			int endTimeYear, int endTimeMonth, int endTimeDay, int endTimeHour,
			int endTimeMinute, String timeZoneId, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "addCalendarBooking",
				_addCalendarBookingParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, childCalendarIds,
				parentCalendarBookingId, recurringCalendarBookingId, titleMap,
				descriptionMap, location, startTimeYear, startTimeMonth,
				startTimeDay, startTimeHour, startTimeMinute, endTimeYear,
				endTimeMonth, endTimeDay, endTimeHour, endTimeMinute,
				timeZoneId, allDay, recurrence, firstReminder,
				firstReminderType, secondReminder, secondReminderType,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking addCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarId,
			long[] childCalendarIds, long parentCalendarBookingId,
			long recurringCalendarBookingId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "addCalendarBooking",
				_addCalendarBookingParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, childCalendarIds,
				parentCalendarBookingId, recurringCalendarBookingId, titleMap,
				descriptionMap, location, startTime, endTime, allDay,
				recurrence, firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking addCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarId,
			long[] childCalendarIds, long parentCalendarBookingId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, int startTimeYear, int startTimeMonth,
			int startTimeDay, int startTimeHour, int startTimeMinute,
			int endTimeYear, int endTimeMonth, int endTimeDay, int endTimeHour,
			int endTimeMinute, String timeZoneId, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "addCalendarBooking",
				_addCalendarBookingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, childCalendarIds,
				parentCalendarBookingId, titleMap, descriptionMap, location,
				startTimeYear, startTimeMonth, startTimeDay, startTimeHour,
				startTimeMinute, endTimeYear, endTimeMonth, endTimeDay,
				endTimeHour, endTimeMinute, timeZoneId, allDay, recurrence,
				firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking addCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarId,
			long[] childCalendarIds, long parentCalendarBookingId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "addCalendarBooking",
				_addCalendarBookingParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, childCalendarIds,
				parentCalendarBookingId, titleMap, descriptionMap, location,
				startTime, endTime, allDay, recurrence, firstReminder,
				firstReminderType, secondReminder, secondReminderType,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			deleteCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "deleteCalendarBooking",
				_deleteCalendarBookingParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCalendarBookingInstance(
			HttpPrincipal httpPrincipal, long calendarBookingId,
			int instanceIndex, boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"deleteCalendarBookingInstance",
				_deleteCalendarBookingInstanceParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, allFollowing);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCalendarBookingInstance(
			HttpPrincipal httpPrincipal, long calendarBookingId,
			int instanceIndex, boolean allFollowing,
			boolean deleteRecurringCalendarBookings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"deleteCalendarBookingInstance",
				_deleteCalendarBookingInstanceParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, allFollowing,
				deleteRecurringCalendarBookings);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCalendarBookingInstance(
			HttpPrincipal httpPrincipal, long calendarBookingId, long startTime,
			boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"deleteCalendarBookingInstance",
				_deleteCalendarBookingInstanceParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, startTime, allFollowing);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String exportCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarBookingId, String type)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "exportCalendarBooking",
				_exportCalendarBookingParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof Exception) {
					throw (Exception)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			fetchCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "fetchCalendarBooking",
				_fetchCalendarBookingParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking getCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBooking",
				_getCalendarBookingParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking getCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarId,
			long parentCalendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBooking",
				_getCalendarBookingParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, parentCalendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			getCalendarBookingInstance(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				int instanceIndex)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBookingInstance",
				_getCalendarBookingInstanceParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			getCalendarBookings(
				HttpPrincipal httpPrincipal, long calendarId, int[] statuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBookings",
				_getCalendarBookingsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, statuses);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			getCalendarBookings(
				HttpPrincipal httpPrincipal, long calendarId, long startTime,
				long endTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBookings",
				_getCalendarBookingsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, startTime, endTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			getCalendarBookings(
				HttpPrincipal httpPrincipal, long calendarId, long startTime,
				long endTime, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBookings",
				_getCalendarBookingsParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, startTime, endTime, max);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getCalendarBookingsRSS(
			HttpPrincipal httpPrincipal, long calendarId, long startTime,
			long endTime, int max, String type, double version,
			String displayStyle,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBookingsRSS",
				_getCalendarBookingsRSSParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, startTime, endTime, max, type, version,
				displayStyle, themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			getChildCalendarBookings(
				HttpPrincipal httpPrincipal, long parentCalendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getChildCalendarBookings",
				_getChildCalendarBookingsParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCalendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			getChildCalendarBookings(
				HttpPrincipal httpPrincipal, long parentCalendarBookingId,
				boolean includeStagingCalendarBookings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getChildCalendarBookings",
				_getChildCalendarBookingsParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCalendarBookingId,
				includeStagingCalendarBookings);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			getChildCalendarBookings(
				HttpPrincipal httpPrincipal, long parentCalendarBookingId,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getChildCalendarBookings",
				_getChildCalendarBookingsParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCalendarBookingId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			getLastInstanceCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"getLastInstanceCalendarBooking",
				_getLastInstanceCalendarBookingParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			getNewStartTimeAndDurationCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				long offset, long duration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"getNewStartTimeAndDurationCalendarBooking",
				_getNewStartTimeAndDurationCalendarBookingParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, offset, duration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasChildCalendarBookings(
		HttpPrincipal httpPrincipal, long parentCalendarBookingId) {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "hasChildCalendarBookings",
				_hasChildCalendarBookingsParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCalendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking invokeTransition(
			HttpPrincipal httpPrincipal, long calendarBookingId,
			int instanceIndex, int status, boolean updateInstance,
			boolean allFollowing,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "invokeTransition",
				_invokeTransitionParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, status,
				updateInstance, allFollowing, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void invokeTransition(
			HttpPrincipal httpPrincipal, long calendarBookingId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "invokeTransition",
				_invokeTransitionParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, status, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking invokeTransition(
			HttpPrincipal httpPrincipal, long calendarBookingId, long startTime,
			int status, boolean updateInstance, boolean allFollowing,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "invokeTransition",
				_invokeTransitionParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, startTime, status, updateInstance,
				allFollowing, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			moveCalendarBookingToTrash(
				HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "moveCalendarBookingToTrash",
				_moveCalendarBookingToTrashParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			restoreCalendarBookingFromTrash(
				HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"restoreCalendarBookingFromTrash",
				_restoreCalendarBookingFromTrashParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			search(
				HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
				long[] calendarIds, long[] calendarResourceIds,
				long parentCalendarBookingId, String keywords, long startTime,
				long endTime, boolean recurring, int[] statuses, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.calendar.model.CalendarBooking>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "search",
				_searchParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, calendarIds,
				calendarResourceIds, parentCalendarBookingId, keywords,
				startTime, endTime, recurring, statuses, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			search(
				HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
				long[] calendarIds, long[] calendarResourceIds,
				long parentCalendarBookingId, String title, String description,
				String location, long startTime, long endTime,
				boolean recurring, int[] statuses, boolean andOperator,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.calendar.model.CalendarBooking>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "search",
				_searchParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, calendarIds,
				calendarResourceIds, parentCalendarBookingId, title,
				description, location, startTime, endTime, recurring, statuses,
				andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long[] calendarIds, long[] calendarResourceIds,
			long parentCalendarBookingId, String keywords, long startTime,
			long endTime, boolean recurring, int[] statuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "searchCount",
				_searchCountParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, calendarIds,
				calendarResourceIds, parentCalendarBookingId, keywords,
				startTime, endTime, recurring, statuses);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long[] calendarIds, long[] calendarResourceIds,
			long parentCalendarBookingId, String title, String description,
			String location, long startTime, long endTime, boolean recurring,
			int[] statuses, boolean andOperator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "searchCount",
				_searchCountParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, calendarIds,
				calendarResourceIds, parentCalendarBookingId, title,
				description, location, startTime, endTime, recurring, statuses,
				andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				long calendarId, long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				String recurrence, long firstReminder, String firstReminderType,
				long secondReminder, String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "updateCalendarBooking",
				_updateCalendarBookingParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, childCalendarIds,
				titleMap, descriptionMap, location, startTime, endTime, allDay,
				recurrence, firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				long calendarId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				String recurrence, long firstReminder, String firstReminderType,
				long secondReminder, String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "updateCalendarBooking",
				_updateCalendarBookingParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, titleMap,
				descriptionMap, location, startTime, endTime, allDay,
				recurrence, firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateCalendarBookingInstance(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				int instanceIndex, long calendarId, long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				boolean allFollowing, long firstReminder,
				String firstReminderType, long secondReminder,
				String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"updateCalendarBookingInstance",
				_updateCalendarBookingInstanceParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, calendarId,
				childCalendarIds, titleMap, descriptionMap, location, startTime,
				endTime, allDay, allFollowing, firstReminder, firstReminderType,
				secondReminder, secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateCalendarBookingInstance(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				int instanceIndex, long calendarId, long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				String recurrence, boolean allFollowing, long firstReminder,
				String firstReminderType, long secondReminder,
				String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"updateCalendarBookingInstance",
				_updateCalendarBookingInstanceParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, calendarId,
				childCalendarIds, titleMap, descriptionMap, location, startTime,
				endTime, allDay, recurrence, allFollowing, firstReminder,
				firstReminderType, secondReminder, secondReminderType,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateCalendarBookingInstance(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				int instanceIndex, long calendarId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, int startTimeYear, int startTimeMonth,
				int startTimeDay, int startTimeHour, int startTimeMinute,
				int endTimeYear, int endTimeMonth, int endTimeDay,
				int endTimeHour, int endTimeMinute, String timeZoneId,
				boolean allDay, String recurrence, boolean allFollowing,
				long firstReminder, String firstReminderType,
				long secondReminder, String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"updateCalendarBookingInstance",
				_updateCalendarBookingInstanceParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, calendarId,
				titleMap, descriptionMap, location, startTimeYear,
				startTimeMonth, startTimeDay, startTimeHour, startTimeMinute,
				endTimeYear, endTimeMonth, endTimeDay, endTimeHour,
				endTimeMinute, timeZoneId, allDay, recurrence, allFollowing,
				firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateCalendarBookingInstance(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				int instanceIndex, long calendarId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				String recurrence, boolean allFollowing, long firstReminder,
				String firstReminderType, long secondReminder,
				String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"updateCalendarBookingInstance",
				_updateCalendarBookingInstanceParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, calendarId,
				titleMap, descriptionMap, location, startTime, endTime, allDay,
				recurrence, allFollowing, firstReminder, firstReminderType,
				secondReminder, secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateLastInstanceCalendarBookingRecurrence(
			HttpPrincipal httpPrincipal, long calendarBookingId,
			String recurrence)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"updateLastInstanceCalendarBookingRecurrence",
				_updateLastInstanceCalendarBookingRecurrenceParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, recurrence);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateOffsetAndDuration(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				long calendarId, long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long offset, long duration, boolean allDay,
				String recurrence, long firstReminder, String firstReminderType,
				long secondReminder, String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "updateOffsetAndDuration",
				_updateOffsetAndDurationParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, childCalendarIds,
				titleMap, descriptionMap, location, offset, duration, allDay,
				recurrence, firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateOffsetAndDuration(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				long calendarId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long offset, long duration, boolean allDay,
				String recurrence, long firstReminder, String firstReminderType,
				long secondReminder, String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "updateOffsetAndDuration",
				_updateOffsetAndDurationParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, titleMap,
				descriptionMap, location, offset, duration, allDay, recurrence,
				firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateRecurringCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				long calendarId, long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				long firstReminder, String firstReminderType,
				long secondReminder, String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"updateRecurringCalendarBooking",
				_updateRecurringCalendarBookingParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, childCalendarIds,
				titleMap, descriptionMap, location, startTime, endTime, allDay,
				firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			updateRecurringCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId,
				long calendarId, long[] childCalendarIds,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String location, long startTime, long endTime, boolean allDay,
				String recurrence, long firstReminder, String firstReminderType,
				long secondReminder, String secondReminderType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class,
				"updateRecurringCalendarBooking",
				_updateRecurringCalendarBookingParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, childCalendarIds,
				titleMap, descriptionMap, location, startTime, endTime, allDay,
				recurrence, firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CalendarBookingServiceHttp.class);

	private static final Class<?>[] _addCalendarBookingParameterTypes0 =
		new Class[] {
			long.class, long[].class, long.class, long.class,
			java.util.Map.class, java.util.Map.class, String.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, String.class, boolean.class,
			String.class, long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCalendarBookingParameterTypes1 =
		new Class[] {
			long.class, long[].class, long.class, long.class,
			java.util.Map.class, java.util.Map.class, String.class, long.class,
			long.class, boolean.class, String.class, long.class, String.class,
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCalendarBookingParameterTypes2 =
		new Class[] {
			long.class, long[].class, long.class, java.util.Map.class,
			java.util.Map.class, String.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, String.class, boolean.class, String.class, long.class,
			String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCalendarBookingParameterTypes3 =
		new Class[] {
			long.class, long[].class, long.class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, String.class, long.class, String.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCalendarBookingParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_deleteCalendarBookingInstanceParameterTypes5 = new Class[] {
			long.class, int.class, boolean.class
		};
	private static final Class<?>[]
		_deleteCalendarBookingInstanceParameterTypes6 = new Class[] {
			long.class, int.class, boolean.class, boolean.class
		};
	private static final Class<?>[]
		_deleteCalendarBookingInstanceParameterTypes7 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[] _exportCalendarBookingParameterTypes8 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _fetchCalendarBookingParameterTypes9 =
		new Class[] {long.class};
	private static final Class<?>[] _getCalendarBookingParameterTypes10 =
		new Class[] {long.class};
	private static final Class<?>[] _getCalendarBookingParameterTypes11 =
		new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getCalendarBookingInstanceParameterTypes12 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _getCalendarBookingsParameterTypes13 =
		new Class[] {long.class, int[].class};
	private static final Class<?>[] _getCalendarBookingsParameterTypes14 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getCalendarBookingsParameterTypes15 =
		new Class[] {long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getCalendarBookingsRSSParameterTypes16 =
		new Class[] {
			long.class, long.class, long.class, int.class, String.class,
			double.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getChildCalendarBookingsParameterTypes17 =
		new Class[] {long.class};
	private static final Class<?>[] _getChildCalendarBookingsParameterTypes18 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getChildCalendarBookingsParameterTypes19 =
		new Class[] {long.class, int.class};
	private static final Class<?>[]
		_getLastInstanceCalendarBookingParameterTypes20 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getNewStartTimeAndDurationCalendarBookingParameterTypes21 =
			new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _hasChildCalendarBookingsParameterTypes22 =
		new Class[] {long.class};
	private static final Class<?>[] _invokeTransitionParameterTypes23 =
		new Class[] {
			long.class, int.class, int.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _invokeTransitionParameterTypes24 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _invokeTransitionParameterTypes25 =
		new Class[] {
			long.class, long.class, int.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_moveCalendarBookingToTrashParameterTypes26 = new Class[] {long.class};
	private static final Class<?>[]
		_restoreCalendarBookingFromTrashParameterTypes27 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchParameterTypes28 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, long.class, long.class, boolean.class, int[].class,
		int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes29 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, long.class, long.class,
		boolean.class, int[].class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes30 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, long.class, long.class, boolean.class, int[].class
	};
	private static final Class<?>[] _searchCountParameterTypes31 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, long.class, long.class,
		boolean.class, int[].class, boolean.class
	};
	private static final Class<?>[] _updateCalendarBookingParameterTypes32 =
		new Class[] {
			long.class, long.class, long[].class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, String.class, long.class, String.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCalendarBookingParameterTypes33 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			String.class, long.class, long.class, boolean.class, String.class,
			long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCalendarBookingInstanceParameterTypes34 = new Class[] {
			long.class, int.class, long.class, long[].class,
			java.util.Map.class, java.util.Map.class, String.class, long.class,
			long.class, boolean.class, boolean.class, long.class, String.class,
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCalendarBookingInstanceParameterTypes35 = new Class[] {
			long.class, int.class, long.class, long[].class,
			java.util.Map.class, java.util.Map.class, String.class, long.class,
			long.class, boolean.class, String.class, boolean.class, long.class,
			String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCalendarBookingInstanceParameterTypes36 = new Class[] {
			long.class, int.class, long.class, java.util.Map.class,
			java.util.Map.class, String.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, String.class, boolean.class, String.class, boolean.class,
			long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCalendarBookingInstanceParameterTypes37 = new Class[] {
			long.class, int.class, long.class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, String.class, boolean.class, long.class,
			String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateLastInstanceCalendarBookingRecurrenceParameterTypes38 =
			new Class[] {long.class, String.class};
	private static final Class<?>[] _updateOffsetAndDurationParameterTypes39 =
		new Class[] {
			long.class, long.class, long[].class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, String.class, long.class, String.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateOffsetAndDurationParameterTypes40 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			String.class, long.class, long.class, boolean.class, String.class,
			long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateRecurringCalendarBookingParameterTypes41 = new Class[] {
			long.class, long.class, long[].class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateRecurringCalendarBookingParameterTypes42 = new Class[] {
			long.class, long.class, long[].class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, String.class, long.class, String.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};

}