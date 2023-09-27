/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.push.notifications.constants.PushNotificationsActionKeys;
import com.liferay.push.notifications.constants.PushNotificationsConstants;
import com.liferay.push.notifications.model.PushNotificationsDevice;
import com.liferay.push.notifications.service.base.PushNotificationsDeviceServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Silvio Santos
 * @author Bruno Farache
 */
@Component(
	property = {
		"json.web.service.context.name=pushnotifications",
		"json.web.service.context.path=PushNotificationsDevice"
	},
	service = AopService.class
)
public class PushNotificationsDeviceServiceImpl
	extends PushNotificationsDeviceServiceBaseImpl {

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public PushNotificationsDevice addPushNotificationsDevice(
			String token, String platform)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), 0,
			PushNotificationsActionKeys.MANAGE_DEVICES);

		PushNotificationsDevice pushNotificationsDevice =
			pushNotificationsDevicePersistence.fetchByToken(token);

		if (pushNotificationsDevice == null) {
			pushNotificationsDevice =
				pushNotificationsDeviceLocalService.addPushNotificationsDevice(
					getGuestOrUserId(), platform, token);
		}
		else if (!platform.equals("sms")) {
			pushNotificationsDevice.setUserId(getGuestOrUserId());

			pushNotificationsDevice =
				pushNotificationsDeviceLocalService.
					updatePushNotificationsDevice(pushNotificationsDevice);
		}

		return pushNotificationsDevice;
	}

	@Override
	public PushNotificationsDevice deletePushNotificationsDevice(
			long pushNotificationsDeviceId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), 0,
			PushNotificationsActionKeys.MANAGE_DEVICES);

		return pushNotificationsDeviceLocalService.
			deletePushNotificationsDevice(pushNotificationsDeviceId);
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public PushNotificationsDevice deletePushNotificationsDevice(String token)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), 0,
			PushNotificationsActionKeys.MANAGE_DEVICES);

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
					StringBundler.concat(
						"Device found with token ", token,
						" does not belong to user ", userId));
			}
		}

		return pushNotificationsDevice;
	}

	@Override
	public void sendPushNotification(long[] toUserIds, String payload)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), 0,
			PushNotificationsActionKeys.SEND_PUSH_NOTIFICATION);

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject(payload);

		pushNotificationsDeviceLocalService.sendPushNotification(
			toUserIds, payloadJSONObject);
	}

	@Override
	public void sendPushNotification(
			String platform, List<String> tokens, String payload)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), 0,
			PushNotificationsActionKeys.SEND_PUSH_NOTIFICATION);

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject(payload);

		pushNotificationsDeviceLocalService.sendPushNotification(
			platform, tokens, payloadJSONObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PushNotificationsDeviceServiceImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(resource.name=" + PushNotificationsConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}