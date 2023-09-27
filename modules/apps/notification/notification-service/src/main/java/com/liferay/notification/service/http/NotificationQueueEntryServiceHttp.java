/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.http;

import com.liferay.notification.service.NotificationQueueEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>NotificationQueueEntryServiceUtil</code> service
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
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationQueueEntryServiceHttp {

	public static com.liferay.notification.model.NotificationQueueEntry
			addNotificationQueueEntry(
				HttpPrincipal httpPrincipal,
				com.liferay.notification.context.NotificationContext
					notificationContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationQueueEntryServiceUtil.class,
				"addNotificationQueueEntry",
				_addNotificationQueueEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, notificationContext);

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

			return (com.liferay.notification.model.NotificationQueueEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.notification.model.NotificationQueueEntry
			deleteNotificationQueueEntry(
				HttpPrincipal httpPrincipal, long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationQueueEntryServiceUtil.class,
				"deleteNotificationQueueEntry",
				_deleteNotificationQueueEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, notificationQueueEntryId);

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

			return (com.liferay.notification.model.NotificationQueueEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.notification.model.NotificationQueueEntry
			getNotificationQueueEntry(
				HttpPrincipal httpPrincipal, long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationQueueEntryServiceUtil.class,
				"getNotificationQueueEntry",
				_getNotificationQueueEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, notificationQueueEntryId);

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

			return (com.liferay.notification.model.NotificationQueueEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.notification.model.NotificationQueueEntry
			resendNotificationQueueEntry(
				HttpPrincipal httpPrincipal, long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationQueueEntryServiceUtil.class,
				"resendNotificationQueueEntry",
				_resendNotificationQueueEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, notificationQueueEntryId);

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

			return (com.liferay.notification.model.NotificationQueueEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		NotificationQueueEntryServiceHttp.class);

	private static final Class<?>[] _addNotificationQueueEntryParameterTypes0 =
		new Class[] {
			com.liferay.notification.context.NotificationContext.class
		};
	private static final Class<?>[]
		_deleteNotificationQueueEntryParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[] _getNotificationQueueEntryParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_resendNotificationQueueEntryParameterTypes3 = new Class[] {long.class};

}