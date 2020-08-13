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
import com.liferay.push.notifications.service.PushNotificationsDeviceServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>PushNotificationsDeviceServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.push.notifications.model.PushNotificationsDeviceSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.push.notifications.model.PushNotificationsDevice</code>, that is translated to a
 * <code>com.liferay.push.notifications.model.PushNotificationsDeviceSoap</code>. Methods that SOAP
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
 * @author Bruno Farache
 * @see PushNotificationsDeviceServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class PushNotificationsDeviceServiceSoap {

	public static
		com.liferay.push.notifications.model.PushNotificationsDeviceSoap
				addPushNotificationsDevice(String token, String platform)
			throws RemoteException {

		try {
			com.liferay.push.notifications.model.PushNotificationsDevice
				returnValue =
					PushNotificationsDeviceServiceUtil.
						addPushNotificationsDevice(token, platform);

			return com.liferay.push.notifications.model.
				PushNotificationsDeviceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.push.notifications.model.PushNotificationsDeviceSoap
				deletePushNotificationsDevice(long pushNotificationsDeviceId)
			throws RemoteException {

		try {
			com.liferay.push.notifications.model.PushNotificationsDevice
				returnValue =
					PushNotificationsDeviceServiceUtil.
						deletePushNotificationsDevice(
							pushNotificationsDeviceId);

			return com.liferay.push.notifications.model.
				PushNotificationsDeviceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.push.notifications.model.PushNotificationsDeviceSoap
				deletePushNotificationsDevice(String token)
			throws RemoteException {

		try {
			com.liferay.push.notifications.model.PushNotificationsDevice
				returnValue =
					PushNotificationsDeviceServiceUtil.
						deletePushNotificationsDevice(token);

			return com.liferay.push.notifications.model.
				PushNotificationsDeviceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void sendPushNotification(long[] toUserIds, String payload)
		throws RemoteException {

		try {
			PushNotificationsDeviceServiceUtil.sendPushNotification(
				toUserIds, payload);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void sendPushNotification(
			String platform, java.util.List<String> tokens, String payload)
		throws RemoteException {

		try {
			PushNotificationsDeviceServiceUtil.sendPushNotification(
				platform, tokens, payload);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PushNotificationsDeviceServiceSoap.class);

}