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

package com.liferay.portal.mobile.device.messaging;

import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.mobile.device.DeviceDetectionUtil;
import com.liferay.portal.kernel.mobile.device.DeviceRecognitionProvider;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class DeviceRecognitionProviderDestinationEventListener
	implements DestinationEventListener {

	@Override
	public void messageListenerRegistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		DeviceDetectionUtil deviceDetectionUtil = new DeviceDetectionUtil();

		deviceDetectionUtil.setDeviceRecognitionProvider(
			_proxyDeviceRecognitionProvider);
	}

	@Override
	public void messageListenerUnregistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		DeviceDetectionUtil deviceDetectionUtil = new DeviceDetectionUtil();

		deviceDetectionUtil.setDeviceRecognitionProvider(
			_directDeviceRecognitionProvider);
	}

	public void setDirectDeviceRecognitionProvider(
		DeviceRecognitionProvider directDeviceRecognitionProvider) {

		_directDeviceRecognitionProvider = directDeviceRecognitionProvider;
	}

	public void setProxyDeviceRecognitionProvider(
		DeviceRecognitionProvider proxyDeviceRecognitionProvider) {

		_proxyDeviceRecognitionProvider = proxyDeviceRecognitionProvider;
	}

	protected boolean isProceed(
		String destinationName, MessageListener messageListener) {

		if (!destinationName.equals(
				DestinationNames.DEVICE_RECOGNITION_PROVIDER) ||
			!(messageListener instanceof ProxyMessageListener)) {

			return false;
		}
		else {
			return true;
		}
	}

	private DeviceRecognitionProvider _directDeviceRecognitionProvider;
	private DeviceRecognitionProvider _proxyDeviceRecognitionProvider;

}