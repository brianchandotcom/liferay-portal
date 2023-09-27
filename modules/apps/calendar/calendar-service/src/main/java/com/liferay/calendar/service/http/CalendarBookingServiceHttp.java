/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			deleteCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "deleteCalendarBooking",
				_deleteCalendarBookingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_deleteCalendarBookingInstanceParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, allFollowing);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_deleteCalendarBookingInstanceParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, allFollowing,
				deleteRecurringCalendarBookings);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_deleteCalendarBookingInstanceParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, startTime, allFollowing);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static String exportCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarBookingId, String type)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "exportCalendarBooking",
				_exportCalendarBookingParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof Exception) {
					throw (Exception)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			fetchCalendarBooking(
				HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "fetchCalendarBooking",
				_fetchCalendarBookingParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking getCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBooking",
				_getCalendarBookingParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking getCalendarBooking(
			HttpPrincipal httpPrincipal, long calendarId,
			long parentCalendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBooking",
				_getCalendarBookingParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, parentCalendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getCalendarBookingInstanceParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			getCalendarBookings(
				HttpPrincipal httpPrincipal, long calendarId, int[] statuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getCalendarBookings",
				_getCalendarBookingsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, statuses);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getCalendarBookingsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, startTime, endTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getCalendarBookingsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, startTime, endTime, max);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getCalendarBookingsRSSParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarId, startTime, endTime, max, type, version,
				displayStyle, themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			getChildCalendarBookings(
				HttpPrincipal httpPrincipal, long parentCalendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "getChildCalendarBookings",
				_getChildCalendarBookingsParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCalendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getChildCalendarBookingsParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCalendarBookingId,
				includeStagingCalendarBookings);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getChildCalendarBookingsParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCalendarBookingId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getLastInstanceCalendarBookingParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getNewStartTimeAndDurationCalendarBookingParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, offset, duration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean hasChildCalendarBookings(
		HttpPrincipal httpPrincipal, long parentCalendarBookingId) {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "hasChildCalendarBookings",
				_hasChildCalendarBookingsParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCalendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_invokeTransitionParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, status,
				updateInstance, allFollowing, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_invokeTransitionParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, startTime, status, updateInstance,
				allFollowing, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.calendar.model.CalendarBooking
			moveCalendarBookingToTrash(
				HttpPrincipal httpPrincipal, long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "moveCalendarBookingToTrash",
				_moveCalendarBookingToTrashParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_restoreCalendarBookingFromTrashParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarBooking>
			search(
				HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
				long[] calendarIds, long[] calendarResourceIds,
				long parentCalendarBookingId, String keywords, long startTime,
				long endTime, java.util.TimeZone displayTimeZone,
				boolean recurring, int[] statuses, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.calendar.model.CalendarBooking>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarBookingServiceUtil.class, "search",
				_searchParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, calendarIds,
				calendarResourceIds, parentCalendarBookingId, keywords,
				startTime, endTime, displayTimeZone, recurring, statuses, start,
				end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_searchParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, calendarIds,
				calendarResourceIds, parentCalendarBookingId, title,
				description, location, startTime, endTime, recurring, statuses,
				andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarBooking>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_searchCountParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, calendarIds,
				calendarResourceIds, parentCalendarBookingId, keywords,
				startTime, endTime, recurring, statuses);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_searchCountParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, calendarIds,
				calendarResourceIds, parentCalendarBookingId, title,
				description, location, startTime, endTime, recurring, statuses,
				andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateCalendarBookingParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, childCalendarIds,
				titleMap, descriptionMap, location, startTime, endTime, allDay,
				recurrence, firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateCalendarBookingParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, titleMap,
				descriptionMap, location, startTime, endTime, allDay,
				recurrence, firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateCalendarBookingInstanceParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, calendarId,
				childCalendarIds, titleMap, descriptionMap, location, startTime,
				endTime, allDay, allFollowing, firstReminder, firstReminderType,
				secondReminder, secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateCalendarBookingInstanceParameterTypes32);

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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateCalendarBookingInstanceParameterTypes33);

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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateCalendarBookingInstanceParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, instanceIndex, calendarId,
				titleMap, descriptionMap, location, startTime, endTime, allDay,
				recurrence, allFollowing, firstReminder, firstReminderType,
				secondReminder, secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateLastInstanceCalendarBookingRecurrenceParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, recurrence);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateOffsetAndDurationParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, childCalendarIds,
				titleMap, descriptionMap, location, offset, duration, allDay,
				recurrence, firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateOffsetAndDurationParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, titleMap,
				descriptionMap, location, offset, duration, allDay, recurrence,
				firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateRecurringCalendarBookingParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarBookingId, calendarId, childCalendarIds,
				titleMap, descriptionMap, location, startTime, endTime, allDay,
				firstReminder, firstReminderType, secondReminder,
				secondReminderType, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.calendar.model.CalendarBooking)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
	private static final Class<?>[] _deleteCalendarBookingParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_deleteCalendarBookingInstanceParameterTypes3 = new Class[] {
			long.class, int.class, boolean.class
		};
	private static final Class<?>[]
		_deleteCalendarBookingInstanceParameterTypes4 = new Class[] {
			long.class, int.class, boolean.class, boolean.class
		};
	private static final Class<?>[]
		_deleteCalendarBookingInstanceParameterTypes5 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[] _exportCalendarBookingParameterTypes6 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _fetchCalendarBookingParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _getCalendarBookingParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCalendarBookingParameterTypes9 =
		new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getCalendarBookingInstanceParameterTypes10 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _getCalendarBookingsParameterTypes11 =
		new Class[] {long.class, int[].class};
	private static final Class<?>[] _getCalendarBookingsParameterTypes12 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getCalendarBookingsParameterTypes13 =
		new Class[] {long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getCalendarBookingsRSSParameterTypes14 =
		new Class[] {
			long.class, long.class, long.class, int.class, String.class,
			double.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getChildCalendarBookingsParameterTypes15 =
		new Class[] {long.class};
	private static final Class<?>[] _getChildCalendarBookingsParameterTypes16 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getChildCalendarBookingsParameterTypes17 =
		new Class[] {long.class, int.class};
	private static final Class<?>[]
		_getLastInstanceCalendarBookingParameterTypes18 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getNewStartTimeAndDurationCalendarBookingParameterTypes19 =
			new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _hasChildCalendarBookingsParameterTypes20 =
		new Class[] {long.class};
	private static final Class<?>[] _invokeTransitionParameterTypes21 =
		new Class[] {
			long.class, int.class, int.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _invokeTransitionParameterTypes22 =
		new Class[] {
			long.class, long.class, int.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_moveCalendarBookingToTrashParameterTypes23 = new Class[] {long.class};
	private static final Class<?>[]
		_restoreCalendarBookingFromTrashParameterTypes24 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchParameterTypes25 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, long.class, long.class, java.util.TimeZone.class,
		boolean.class, int[].class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes26 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, long.class, long.class,
		boolean.class, int[].class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes27 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, long.class, long.class, boolean.class, int[].class
	};
	private static final Class<?>[] _searchCountParameterTypes28 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, long.class, long.class,
		boolean.class, int[].class, boolean.class
	};
	private static final Class<?>[] _updateCalendarBookingParameterTypes29 =
		new Class[] {
			long.class, long.class, long[].class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, String.class, long.class, String.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCalendarBookingParameterTypes30 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			String.class, long.class, long.class, boolean.class, String.class,
			long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCalendarBookingInstanceParameterTypes31 = new Class[] {
			long.class, int.class, long.class, long[].class,
			java.util.Map.class, java.util.Map.class, String.class, long.class,
			long.class, boolean.class, boolean.class, long.class, String.class,
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCalendarBookingInstanceParameterTypes32 = new Class[] {
			long.class, int.class, long.class, long[].class,
			java.util.Map.class, java.util.Map.class, String.class, long.class,
			long.class, boolean.class, String.class, boolean.class, long.class,
			String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCalendarBookingInstanceParameterTypes33 = new Class[] {
			long.class, int.class, long.class, java.util.Map.class,
			java.util.Map.class, String.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, String.class, boolean.class, String.class, boolean.class,
			long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCalendarBookingInstanceParameterTypes34 = new Class[] {
			long.class, int.class, long.class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, String.class, boolean.class, long.class,
			String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateLastInstanceCalendarBookingRecurrenceParameterTypes35 =
			new Class[] {long.class, String.class};
	private static final Class<?>[] _updateOffsetAndDurationParameterTypes36 =
		new Class[] {
			long.class, long.class, long[].class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, String.class, long.class, String.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateOffsetAndDurationParameterTypes37 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			String.class, long.class, long.class, boolean.class, String.class,
			long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateRecurringCalendarBookingParameterTypes38 = new Class[] {
			long.class, long.class, long[].class, java.util.Map.class,
			java.util.Map.class, String.class, long.class, long.class,
			boolean.class, long.class, String.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}