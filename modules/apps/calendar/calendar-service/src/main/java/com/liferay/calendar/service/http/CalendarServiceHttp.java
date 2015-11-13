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

import aQute.bnd.annotation.ProviderType;

import com.liferay.calendar.service.CalendarServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * Provides the HTTP utility for the
 * {@link CalendarServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see CalendarServiceSoap
 * @see HttpPrincipal
 * @see CalendarServiceUtil
 * @generated
 */
@ProviderType
public class CalendarServiceHttp {
	public static com.liferay.calendar.model.Calendar addCalendar(
		HttpPrincipal httpPrincipal, long groupId, long calendarResourceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String timeZoneId, int color, boolean defaultCalendar,
		boolean enableComments, boolean enableRatings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"addCalendar", _addCalendarParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					calendarResourceId, nameMap, descriptionMap, timeZoneId,
					color, defaultCalendar, enableComments, enableRatings,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.calendar.model.Calendar)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.Calendar deleteCalendar(
		HttpPrincipal httpPrincipal, long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"deleteCalendar", _deleteCalendarParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					calendarId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.calendar.model.Calendar)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String exportCalendar(HttpPrincipal httpPrincipal,
		long calendarId, java.lang.String type) throws java.lang.Exception {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"exportCalendar", _exportCalendarParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					calendarId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof java.lang.Exception) {
					throw (java.lang.Exception)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.Calendar fetchCalendar(
		HttpPrincipal httpPrincipal, long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"fetchCalendar", _fetchCalendarParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					calendarId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.calendar.model.Calendar)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.Calendar getCalendar(
		HttpPrincipal httpPrincipal, long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"getCalendar", _getCalendarParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					calendarId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.calendar.model.Calendar)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.Calendar> getCalendarResourceCalendars(
		HttpPrincipal httpPrincipal, long groupId, long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"getCalendarResourceCalendars",
					_getCalendarResourceCalendarsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					calendarResourceId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.calendar.model.Calendar>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.Calendar> getCalendarResourceCalendars(
		HttpPrincipal httpPrincipal, long groupId, long calendarResourceId,
		boolean defaultCalendar)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"getCalendarResourceCalendars",
					_getCalendarResourceCalendarsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					calendarResourceId, defaultCalendar);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.calendar.model.Calendar>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void importCalendar(HttpPrincipal httpPrincipal,
		long calendarId, java.lang.String data, java.lang.String type)
		throws java.lang.Exception {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"importCalendar", _importCalendarParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					calendarId, data, type);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof java.lang.Exception) {
					throw (java.lang.Exception)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.Calendar> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] calendarResourceIds, java.lang.String keywords,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"search", _searchParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, calendarResourceIds, keywords,
					andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.calendar.model.Calendar>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.Calendar> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] calendarResourceIds, java.lang.String keywords,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar> orderByComparator,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"search", _searchParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, calendarResourceIds, keywords,
					andOperator, start, end, orderByComparator, actionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.calendar.model.Calendar>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.Calendar> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] calendarResourceIds, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"search", _searchParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, calendarResourceIds, name,
					description, andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.calendar.model.Calendar>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.Calendar> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] calendarResourceIds, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar> orderByComparator,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"search", _searchParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, calendarResourceIds, name,
					description, andOperator, start, end, orderByComparator,
					actionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.calendar.model.Calendar>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, long[] calendarResourceIds, java.lang.String keywords,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"searchCount", _searchCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, calendarResourceIds, keywords,
					andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, long[] calendarResourceIds, java.lang.String keywords,
		boolean andOperator, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"searchCount", _searchCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, calendarResourceIds, keywords,
					andOperator, actionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, long[] calendarResourceIds, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"searchCount", _searchCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, calendarResourceIds, name,
					description, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, long[] calendarResourceIds, java.lang.String name,
		java.lang.String description, boolean andOperator,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"searchCount", _searchCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, calendarResourceIds, name,
					description, andOperator, actionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static com.liferay.calendar.model.Calendar updateCalendar(
		HttpPrincipal httpPrincipal, long calendarId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int color, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"updateCalendar", _updateCalendarParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					calendarId, nameMap, descriptionMap, color, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.calendar.model.Calendar)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.Calendar updateCalendar(
		HttpPrincipal httpPrincipal, long calendarId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String timeZoneId, int color, boolean defaultCalendar,
		boolean enableComments, boolean enableRatings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"updateCalendar", _updateCalendarParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					calendarId, nameMap, descriptionMap, timeZoneId, color,
					defaultCalendar, enableComments, enableRatings,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.calendar.model.Calendar)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.Calendar updateColor(
		HttpPrincipal httpPrincipal, long calendarId, int color,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CalendarServiceUtil.class,
					"updateColor", _updateColorParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					calendarId, color, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.calendar.model.Calendar)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CalendarServiceHttp.class);
	private static final Class<?>[] _addCalendarParameterTypes0 = new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, int.class, boolean.class, boolean.class,
			boolean.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCalendarParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _exportCalendarParameterTypes2 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _fetchCalendarParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCalendarParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCalendarResourceCalendarsParameterTypes5 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getCalendarResourceCalendarsParameterTypes6 =
		new Class[] { long.class, long.class, boolean.class };
	private static final Class<?>[] _importCalendarParameterTypes7 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class
		};
	private static final Class<?>[] _searchParameterTypes8 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes9 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class,
			java.lang.String.class
		};
	private static final Class<?>[] _searchParameterTypes10 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			java.lang.String.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes11 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			java.lang.String.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class,
			java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes12 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			boolean.class
		};
	private static final Class<?>[] _searchCountParameterTypes13 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			boolean.class, java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes14 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _searchCountParameterTypes15 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			java.lang.String.class, boolean.class, java.lang.String.class
		};
	private static final Class<?>[] _updateCalendarParameterTypes16 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCalendarParameterTypes17 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, int.class, boolean.class, boolean.class,
			boolean.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateColorParameterTypes18 = new Class[] {
			long.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
}