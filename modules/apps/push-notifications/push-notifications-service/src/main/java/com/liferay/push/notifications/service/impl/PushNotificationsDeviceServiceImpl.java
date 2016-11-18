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

package com.liferay.push.notifications.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.push.notifications.constants.PushNotificationsActionKeys;
import com.liferay.push.notifications.model.PushNotificationsDevice;
import com.liferay.push.notifications.service.base.PushNotificationsDeviceServiceBaseImpl;
import com.liferay.push.notifications.service.permission.PushNotificationsPermission;

import java.util.List;

/**
 * @author Silvio Santos
 * @author Bruno Farache
 */
@ProviderType
public class PushNotificationsDeviceServiceImpl
	extends PushNotificationsDeviceServiceBaseImpl {

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public PushNotificationsDevice addPushNotificationsDevice(
			String token, String platform)
		throws PortalException {

		PushNotificationsPermission.check(
			getPermissionChecker(), PushNotificationsActionKeys.MANAGE_DEVICES);

		PushNotificationsDevice pushNotificationsDevice =
			pushNotificationsDevicePersistence.fetchByToken(token);

		if (pushNotificationsDevice == null) {
			pushNotificationsDevice =
				pushNotificationsDeviceLocalService.addPushNotificationsDevice(
					getGuestOrUserId(), platform, token);
		}
		else if (!platform.equals("sms")) {
			long userId = getGuestOrUserId();

			pushNotificationsDevice.setUserId(userId);

			pushNotificationsDeviceLocalService.updatePushNotificationsDevice(
				pushNotificationsDevice);
		}

		return pushNotificationsDevice;
	}

	@Override
	public PushNotificationsDevice deletePushNotificationsDevice(
			long pushNotificationsDeviceId)
		throws PortalException {

		PushNotificationsPermission.check(
			getPermissionChecker(), PushNotificationsActionKeys.MANAGE_DEVICES);

		return pushNotificationsDeviceLocalService.
			deletePushNotificationsDevice(pushNotificationsDeviceId);
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public PushNotificationsDevice deletePushNotificationsDevice(String token)
		throws PortalException {

		PushNotificationsPermission.check(
			getPermissionChecker(), PushNotificationsActionKeys.MANAGE_DEVICES);

		PushNotificationsDevice pushNotificationsDevice =
			pushNotificationsDevicePersistence.fetchByToken(token);

		if (pushNotificationsDevice == null) {
			if (_log.isInfoEnabled()) {
				_log.info("No device found with token " + token);
			}
		}
		else {
			long userId = getGuestOrUserId();

			if (pushNotificationsDevice.getUserId() == userId) {
				pushNotificationsDevice =
					pushNotificationsDeviceLocalService.
						deletePushNotificationsDevice(token);
			}
			else if (_log.isInfoEnabled()) {
				_log.info(
					"Device found with token " + token +
						" does not belong to user " + userId);
			}
		}

		return pushNotificationsDevice;
	}

	@Override
	public void sendPushNotification(long[] toUserIds, String payload)
		throws PortalException {

		PushNotificationsPermission.check(
			getPermissionChecker(),
			PushNotificationsActionKeys.SEND_PUSH_NOTIFICATION);

		JSONObject payloadJSONObject = JSONFactoryUtil.createJSONObject(
			payload);

		pushNotificationsDeviceLocalService.sendPushNotification(
			toUserIds, payloadJSONObject);
	}

	@Override
	public void sendPushNotification(
			String platform, List<String> tokens, String payload)
		throws PortalException {

		PushNotificationsPermission.check(
			getPermissionChecker(),
			PushNotificationsActionKeys.SEND_PUSH_NOTIFICATION);

		JSONObject payloadJSONObject = JSONFactoryUtil.createJSONObject(
			payload);

		pushNotificationsDeviceLocalService.sendPushNotification(
			platform, tokens, payloadJSONObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PushNotificationsDeviceServiceImpl.class);

}