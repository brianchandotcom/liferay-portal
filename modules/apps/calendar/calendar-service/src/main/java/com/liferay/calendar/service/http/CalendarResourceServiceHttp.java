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

import com.liferay.calendar.service.CalendarResourceServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CalendarResourceServiceUtil</code> service
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
 * @see CalendarResourceServiceSoap
 * @generated
 */
public class CalendarResourceServiceHttp {

	public static com.liferay.calendar.model.CalendarResource
			addCalendarResource(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				long classPK, String classUuid, String code,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "addCalendarResource",
				_addCalendarResourceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK, classUuid, code,
				nameMap, descriptionMap, active, serviceContext);

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

			return (com.liferay.calendar.model.CalendarResource)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarResource
			deleteCalendarResource(
				HttpPrincipal httpPrincipal, long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "deleteCalendarResource",
				_deleteCalendarResourceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarResourceId);

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

			return (com.liferay.calendar.model.CalendarResource)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarResource
			fetchCalendarResource(
				HttpPrincipal httpPrincipal, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "fetchCalendarResource",
				_fetchCalendarResourceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, classPK);

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

			return (com.liferay.calendar.model.CalendarResource)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.calendar.model.CalendarResource
			getCalendarResource(
				HttpPrincipal httpPrincipal, long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "getCalendarResource",
				_getCalendarResourceParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarResourceId);

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

			return (com.liferay.calendar.model.CalendarResource)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarResource>
		search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long[] classNameIds, String keywords, boolean active,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.CalendarResource>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "search",
				_searchParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, keywords, active,
				andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarResource>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.calendar.model.CalendarResource>
		search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long[] classNameIds, String code, String name, String description,
			boolean active, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.calendar.model.CalendarResource>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "search",
				_searchParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, code, name,
				description, active, andOperator, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.calendar.model.CalendarResource>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, String keywords, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "searchCount",
				_searchCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, keywords, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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
		long[] classNameIds, String code, String name, String description,
		boolean active, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "searchCount",
				_searchCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, code, name,
				description, active, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static com.liferay.calendar.model.CalendarResource
			updateCalendarResource(
				HttpPrincipal httpPrincipal, long calendarResourceId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CalendarResourceServiceUtil.class, "updateCalendarResource",
				_updateCalendarResourceParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, calendarResourceId, nameMap, descriptionMap, active,
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

			return (com.liferay.calendar.model.CalendarResource)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CalendarResourceServiceHttp.class);

	private static final Class<?>[] _addCalendarResourceParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, String.class, String.class,
			java.util.Map.class, java.util.Map.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCalendarResourceParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCalendarResourceParameterTypes2 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getCalendarResourceParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _searchParameterTypes4 = new Class[] {
		long.class, long[].class, long[].class, String.class, boolean.class,
		boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes5 = new Class[] {
		long.class, long[].class, long[].class, String.class, String.class,
		String.class, boolean.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes6 = new Class[] {
		long.class, long[].class, long[].class, String.class, boolean.class
	};
	private static final Class<?>[] _searchCountParameterTypes7 = new Class[] {
		long.class, long[].class, long[].class, String.class, String.class,
		String.class, boolean.class, boolean.class
	};
	private static final Class<?>[] _updateCalendarResourceParameterTypes8 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}