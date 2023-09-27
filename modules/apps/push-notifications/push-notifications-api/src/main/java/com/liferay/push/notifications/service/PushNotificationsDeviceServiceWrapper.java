/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PushNotificationsDeviceService}.
 *
 * @author Bruno Farache
 * @see PushNotificationsDeviceService
 * @generated
 */
public class PushNotificationsDeviceServiceWrapper
	implements PushNotificationsDeviceService,
			   ServiceWrapper<PushNotificationsDeviceService> {

	public PushNotificationsDeviceServiceWrapper() {
		this(null);
	}

	public PushNotificationsDeviceServiceWrapper(
		PushNotificationsDeviceService pushNotificationsDeviceService) {

		_pushNotificationsDeviceService = pushNotificationsDeviceService;
	}

	@Override
	public com.liferay.push.notifications.model.PushNotificationsDevice
			addPushNotificationsDevice(String token, String platform)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pushNotificationsDeviceService.addPushNotificationsDevice(
			token, platform);
	}

	@Override
	public com.liferay.push.notifications.model.PushNotificationsDevice
			deletePushNotificationsDevice(long pushNotificationsDeviceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pushNotificationsDeviceService.deletePushNotificationsDevice(
			pushNotificationsDeviceId);
	}

	@Override
	public com.liferay.push.notifications.model.PushNotificationsDevice
			deletePushNotificationsDevice(String token)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pushNotificationsDeviceService.deletePushNotificationsDevice(
			token);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _pushNotificationsDeviceService.getOSGiServiceIdentifier();
	}

	@Override
	public void sendPushNotification(long[] toUserIds, String payload)
		throws com.liferay.portal.kernel.exception.PortalException {

		_pushNotificationsDeviceService.sendPushNotification(
			toUserIds, payload);
	}

	@Override
	public void sendPushNotification(
			String platform, java.util.List<String> tokens, String payload)
		throws com.liferay.portal.kernel.exception.PortalException {

		_pushNotificationsDeviceService.sendPushNotification(
			platform, tokens, payload);
	}

	@Override
	public PushNotificationsDeviceService getWrappedService() {
		return _pushNotificationsDeviceService;
	}

	@Override
	public void setWrappedService(
		PushNotificationsDeviceService pushNotificationsDeviceService) {

		_pushNotificationsDeviceService = pushNotificationsDeviceService;
	}

	private PushNotificationsDeviceService _pushNotificationsDeviceService;

}