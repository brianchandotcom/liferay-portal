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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;
import com.liferay.push.notifications.model.PushNotificationsDevice;
import com.liferay.push.notifications.sender.BaseResponse;
import com.liferay.push.notifications.sender.PushNotificationsSender;
import com.liferay.push.notifications.service.base.PushNotificationsDeviceLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Silvio Santos
 * @author Bruno Farache
 */
@Component(
	property = "model.class.name=com.liferay.push.notifications.model.PushNotificationsDevice",
	service = AopService.class
)
public class PushNotificationsDeviceLocalServiceImpl
	extends PushNotificationsDeviceLocalServiceBaseImpl {

	@Override
	public PushNotificationsDevice addPushNotificationsDevice(
			long userId, String platform, String token)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long pushNotificationsDeviceId = counterLocalService.increment();

		PushNotificationsDevice pushNotificationsDevice =
			pushNotificationsDevicePersistence.create(
				pushNotificationsDeviceId);

		pushNotificationsDevice.setCompanyId(user.getCompanyId());
		pushNotificationsDevice.setUserId(user.getUserId());
		pushNotificationsDevice.setCreateDate(new Date());
		pushNotificationsDevice.setPlatform(platform);
		pushNotificationsDevice.setToken(token);

		pushNotificationsDevicePersistence.update(pushNotificationsDevice);

		return pushNotificationsDevice;
	}

	@Override
	public PushNotificationsDevice deletePushNotificationsDevice(String token)
		throws PortalException {

		PushNotificationsDevice pushNotificationsDevice =
			pushNotificationsDevicePersistence.findByToken(token);

		pushNotificationsDevicePersistence.remove(pushNotificationsDevice);

		return pushNotificationsDevice;
	}

	@Override
	public List<PushNotificationsDevice> getPushNotificationsDevices(
		int start, int end,
		OrderByComparator<PushNotificationsDevice> orderByComparator) {

		return pushNotificationsDevicePersistence.findAll(
			start, end, orderByComparator);
	}

	@Override
	public void sendPushNotification(
			long[] toUserIds, JSONObject payloadJSONObject)
		throws PortalException {

		for (String platform : _serviceTrackerMap.keySet()) {
			List<String> tokens = new ArrayList<>();

			List<PushNotificationsDevice> pushNotificationsDevices =
				pushNotificationsDevicePersistence.findByU_P(
					toUserIds, platform, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (PushNotificationsDevice pushNotificationsDevice :
					pushNotificationsDevices) {

				tokens.add(pushNotificationsDevice.getToken());
			}

			if (tokens.isEmpty()) {
				continue;
			}

			sendPushNotification(platform, tokens, payloadJSONObject);
		}
	}

	@Override
	public void sendPushNotification(
			String platform, List<String> tokens, JSONObject payloadJSONObject)
		throws PortalException {

		PushNotificationsSender pushNotificationsSender =
			_serviceTrackerMap.getService(platform);

		if (pushNotificationsSender == null) {
			return;
		}

		Exception exception = null;

		try {
			pushNotificationsSender.send(tokens, payloadJSONObject);
		}
		catch (PortalException pe) {
			exception = pe;

			throw pe;
		}
		catch (Exception e) {
			exception = e;

			throw new PortalException(e);
		}
		finally {
			if (exception != null) {
				Message message = new Message();

				message.setPayload(new BaseResponse(platform, exception));

				_messageBus.sendMessage(
					PushNotificationsDestinationNames.
						PUSH_NOTIFICATION_RESPONSE,
					message);
			}
		}
	}

	@Override
	public void updateToken(String oldToken, String newToken)
		throws PortalException {

		PushNotificationsDevice oldPushNotificationsDevice =
			deletePushNotificationsDevice(oldToken);

		PushNotificationsDevice newPushNotificationsDevice =
			pushNotificationsDevicePersistence.fetchByToken(newToken);

		if (newPushNotificationsDevice == null) {
			addPushNotificationsDevice(
				oldPushNotificationsDevice.getUserId(),
				oldPushNotificationsDevice.getPlatform(), newToken);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PushNotificationsSender.class, "platform");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private MessageBus _messageBus;

	private ServiceTrackerMap<String, PushNotificationsSender>
		_serviceTrackerMap;

}