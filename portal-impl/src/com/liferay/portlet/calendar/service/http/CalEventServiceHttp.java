/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.calendar.service.CalEventServiceUtil;

/**
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.calendar.service.CalEventServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
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
 * @author    Brian Wing Shun Chan
 * @see       CalEventServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.calendar.service.CalEventServiceUtil
 * @generated
 */
public class CalEventServiceHttp {
	public static com.liferay.portlet.calendar.model.CalEvent addEvent(
		HttpPrincipal httpPrincipal, java.lang.String title,
		java.lang.String description, java.lang.String location,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"addEvent", _addEventParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, title,
					description, location, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					durationHour, durationMinute, allDay, timeZoneSensitive,
					type, repeating, recurrence, remindBy, firstReminder,
					secondReminder, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.calendar.model.CalEvent)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.calendar.model.CalEvent addEvent(
		HttpPrincipal httpPrincipal, java.lang.String title,
		java.lang.String description, java.lang.String location,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"addEvent", _addEventParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, title,
					description, location, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear, durationHour,
					durationMinute, allDay, timeZoneSensitive, type, repeating,
					recurrence, remindBy, firstReminder, secondReminder,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.calendar.model.CalEvent)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteEvent(HttpPrincipal httpPrincipal, long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"deleteEvent", _deleteEventParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, eventId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.io.File exportEvent(HttpPrincipal httpPrincipal,
		long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"exportEvent", _exportEventParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, eventId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.io.File exportEvents(HttpPrincipal httpPrincipal,
		java.util.List<com.liferay.portlet.calendar.model.CalEvent> events,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"exportEvents", _exportEventsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, events,
					fileName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.io.File exportGroupEvents(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"exportGroupEvents", _exportGroupEventsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					fileName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.calendar.model.CalEvent getEvent(
		HttpPrincipal httpPrincipal, long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"getEvent", _getEventParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, eventId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.calendar.model.CalEvent)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		HttpPrincipal httpPrincipal, long groupId, java.util.Calendar cal,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"getEvents", _getEventsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					cal, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.calendar.model.CalEvent>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		HttpPrincipal httpPrincipal, long groupId, java.util.Calendar cal,
		java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"getEvents", _getEventsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					cal, types);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.calendar.model.CalEvent>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String type,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"getEvents", _getEventsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.calendar.model.CalEvent>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String[] types,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"getEvents", _getEventsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					types, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.calendar.model.CalEvent>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getEventsCount(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"getEventsCount", _getEventsCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getEventsCount(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"getEventsCount", _getEventsCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					types);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasEvents(HttpPrincipal httpPrincipal, long groupId,
		java.util.Calendar cal)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"hasEvents", _hasEventsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					cal);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasEvents(HttpPrincipal httpPrincipal, long groupId,
		java.util.Calendar cal, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"hasEvents", _hasEventsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					cal, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasEvents(HttpPrincipal httpPrincipal, long groupId,
		java.util.Calendar cal, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"hasEvents", _hasEventsParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					cal, types);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void importICal4j(HttpPrincipal httpPrincipal, long groupId,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"importICal4j", _importICal4jParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					inputStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateEvent(
		HttpPrincipal httpPrincipal, long eventId, java.lang.String title,
		java.lang.String description, java.lang.String location,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"updateEvent", _updateEventParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey, eventId,
					title, description, location, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					durationHour, durationMinute, allDay, timeZoneSensitive,
					type, repeating, recurrence, remindBy, firstReminder,
					secondReminder, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.calendar.model.CalEvent)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateEvent(
		HttpPrincipal httpPrincipal, long eventId, java.lang.String title,
		java.lang.String description, java.lang.String location,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(CalEventServiceUtil.class,
					"updateEvent", _updateEventParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey, eventId,
					title, description, location, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear, durationHour,
					durationMinute, allDay, timeZoneSensitive, type, repeating,
					recurrence, remindBy, firstReminder, secondReminder,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.calendar.model.CalEvent)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CalEventServiceHttp.class);
	private static final Class<?>[] _addEventParameterTypes0 = new Class[] {
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class, boolean.class,
			java.lang.String.class, boolean.class,
			com.liferay.portal.kernel.cal.TZSRecurrence.class, int.class,
			int.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addEventParameterTypes1 = new Class[] {
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			boolean.class, boolean.class, java.lang.String.class, boolean.class,
			com.liferay.portal.kernel.cal.TZSRecurrence.class, int.class,
			int.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteEventParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _exportEventParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _exportEventsParameterTypes4 = new Class[] {
			java.util.List.class, java.lang.String.class
		};
	private static final Class<?>[] _exportGroupEventsParameterTypes5 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getEventParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getEventsParameterTypes7 = new Class[] {
			long.class, java.util.Calendar.class, java.lang.String.class
		};
	private static final Class<?>[] _getEventsParameterTypes8 = new Class[] {
			long.class, java.util.Calendar.class, java.lang.String[].class
		};
	private static final Class<?>[] _getEventsParameterTypes9 = new Class[] {
			long.class, java.lang.String.class, int.class, int.class
		};
	private static final Class<?>[] _getEventsParameterTypes10 = new Class[] {
			long.class, java.lang.String[].class, int.class, int.class
		};
	private static final Class<?>[] _getEventsCountParameterTypes11 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getEventsCountParameterTypes12 = new Class[] {
			long.class, java.lang.String[].class
		};
	private static final Class<?>[] _hasEventsParameterTypes13 = new Class[] {
			long.class, java.util.Calendar.class
		};
	private static final Class<?>[] _hasEventsParameterTypes14 = new Class[] {
			long.class, java.util.Calendar.class, java.lang.String.class
		};
	private static final Class<?>[] _hasEventsParameterTypes15 = new Class[] {
			long.class, java.util.Calendar.class, java.lang.String[].class
		};
	private static final Class<?>[] _importICal4jParameterTypes16 = new Class[] {
			long.class, java.io.InputStream.class
		};
	private static final Class<?>[] _updateEventParameterTypes17 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class, boolean.class,
			java.lang.String.class, boolean.class,
			com.liferay.portal.kernel.cal.TZSRecurrence.class, int.class,
			int.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateEventParameterTypes18 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			boolean.class, boolean.class, java.lang.String.class, boolean.class,
			com.liferay.portal.kernel.cal.TZSRecurrence.class, int.class,
			int.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
}