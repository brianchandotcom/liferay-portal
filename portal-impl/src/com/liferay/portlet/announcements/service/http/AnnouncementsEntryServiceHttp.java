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

package com.liferay.portlet.announcements.service.http;

import com.liferay.announcements.kernel.service.AnnouncementsEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AnnouncementsEntryServiceUtil</code> service
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
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryServiceSoap
 * @generated
 */
public class AnnouncementsEntryServiceHttp {

	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			addEntry(
				HttpPrincipal httpPrincipal, long plid, long classNameId,
				long classPK, String title, String content, String url,
				String type, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				boolean displayImmediately, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute, int priority,
				boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AnnouncementsEntryServiceUtil.class, "addEntry",
				_addEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, plid, classNameId, classPK, title, content, url,
				type, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, displayImmediately,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, priority, alert);

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

			return (com.liferay.announcements.kernel.model.AnnouncementsEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			addEntry(
				HttpPrincipal httpPrincipal, long classNameId, long classPK,
				String title, String content, String url, String type,
				java.util.Date displayDate, java.util.Date expirationDate,
				int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AnnouncementsEntryServiceUtil.class, "addEntry",
				_addEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, classPK, title, content, url, type,
				displayDate, expirationDate, priority, alert);

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

			return (com.liferay.announcements.kernel.model.AnnouncementsEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteEntry(HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AnnouncementsEntryServiceUtil.class, "deleteEntry",
				_deleteEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

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

	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			getEntry(HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AnnouncementsEntryServiceUtil.class, "getEntry",
				_getEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

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

			return (com.liferay.announcements.kernel.model.AnnouncementsEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			updateEntry(
				HttpPrincipal httpPrincipal, long entryId, String title,
				String content, String url, String type,
				java.util.Date displayDate, java.util.Date expirationDate,
				int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AnnouncementsEntryServiceUtil.class, "updateEntry",
				_updateEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, entryId, title, content, url, type, displayDate,
				expirationDate, priority);

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

			return (com.liferay.announcements.kernel.model.AnnouncementsEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			updateEntry(
				HttpPrincipal httpPrincipal, long entryId, String title,
				String content, String url, String type, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, boolean displayImmediately,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AnnouncementsEntryServiceUtil.class, "updateEntry",
				_updateEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, entryId, title, content, url, type, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, displayImmediately, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, priority);

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

			return (com.liferay.announcements.kernel.model.AnnouncementsEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AnnouncementsEntryServiceHttp.class);

	private static final Class<?>[] _addEntryParameterTypes0 = new Class[] {
		long.class, long.class, long.class, String.class, String.class,
		String.class, String.class, int.class, int.class, int.class, int.class,
		int.class, boolean.class, int.class, int.class, int.class, int.class,
		int.class, int.class, boolean.class
	};
	private static final Class<?>[] _addEntryParameterTypes1 = new Class[] {
		long.class, long.class, String.class, String.class, String.class,
		String.class, java.util.Date.class, java.util.Date.class, int.class,
		boolean.class
	};
	private static final Class<?>[] _deleteEntryParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getEntryParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _updateEntryParameterTypes4 = new Class[] {
		long.class, String.class, String.class, String.class, String.class,
		java.util.Date.class, java.util.Date.class, int.class
	};
	private static final Class<?>[] _updateEntryParameterTypes5 = new Class[] {
		long.class, String.class, String.class, String.class, String.class,
		int.class, int.class, int.class, int.class, int.class, boolean.class,
		int.class, int.class, int.class, int.class, int.class, int.class
	};

}