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

package com.liferay.push.notifications.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.push.notifications.service.PushNotificationsDeviceServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>PushNotificationsDeviceServiceUtil</code> service
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
 * @author Bruno Farache
 * @see PushNotificationsDeviceServiceSoap
 * @generated
 */
public class PushNotificationsDeviceServiceHttp {

	public static com.liferay.push.notifications.model.PushNotificationsDevice
			addPushNotificationsDevice(
				HttpPrincipal httpPrincipal, String token, String platform)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PushNotificationsDeviceServiceUtil.class,
				"addPushNotificationsDevice",
				_addPushNotificationsDeviceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, token, platform);

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

			return (com.liferay.push.notifications.model.
				PushNotificationsDevice)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.push.notifications.model.PushNotificationsDevice
			deletePushNotificationsDevice(
				HttpPrincipal httpPrincipal, long pushNotificationsDeviceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PushNotificationsDeviceServiceUtil.class,
				"deletePushNotificationsDevice",
				_deletePushNotificationsDeviceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, pushNotificationsDeviceId);

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

			return (com.liferay.push.notifications.model.
				PushNotificationsDevice)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.push.notifications.model.PushNotificationsDevice
			deletePushNotificationsDevice(
				HttpPrincipal httpPrincipal, String token)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PushNotificationsDeviceServiceUtil.class,
				"deletePushNotificationsDevice",
				_deletePushNotificationsDeviceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, token);

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

			return (com.liferay.push.notifications.model.
				PushNotificationsDevice)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void sendPushNotification(
			HttpPrincipal httpPrincipal, long[] toUserIds, String payload)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PushNotificationsDeviceServiceUtil.class,
				"sendPushNotification", _sendPushNotificationParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, toUserIds, payload);

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

	public static void sendPushNotification(
			HttpPrincipal httpPrincipal, String platform,
			java.util.List<String> tokens, String payload)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PushNotificationsDeviceServiceUtil.class,
				"sendPushNotification", _sendPushNotificationParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, platform, tokens, payload);

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

	private static Log _log = LogFactoryUtil.getLog(
		PushNotificationsDeviceServiceHttp.class);

	private static final Class<?>[] _addPushNotificationsDeviceParameterTypes0 =
		new Class[] {String.class, String.class};
	private static final Class<?>[]
		_deletePushNotificationsDeviceParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deletePushNotificationsDeviceParameterTypes2 = new Class[] {
			String.class
		};
	private static final Class<?>[] _sendPushNotificationParameterTypes3 =
		new Class[] {long[].class, String.class};
	private static final Class<?>[] _sendPushNotificationParameterTypes4 =
		new Class[] {String.class, java.util.List.class, String.class};

}