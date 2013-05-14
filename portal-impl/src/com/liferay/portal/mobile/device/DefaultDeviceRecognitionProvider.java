/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.device;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.DeviceCapabilityFilter;
import com.liferay.portal.kernel.mobile.device.DeviceRecognitionProvider;
import com.liferay.portal.kernel.mobile.device.KnownDevices;
import com.liferay.portal.kernel.mobile.device.NoKnownDevices;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Milen Dyankov
 */
public class DefaultDeviceRecognitionProvider
	implements DeviceRecognitionProvider {

	@Override
	public Device detectDevice(HttpServletRequest request) {
		if (_log.isWarnEnabled()) {
			_log.warn("Device recognition provider is not available");
		}

		return UnknownDevice.getInstance();
	}

	@Override
	public KnownDevices getKnownDevices() {
		if (_log.isWarnEnabled()) {
			_log.warn("Device recognition provider is not available");
		}

		return NoKnownDevices.getInstance();
	}

	@Override
	public void reload() {
	}

	@Override
	public void setDeviceCapabilityFilter(
		DeviceCapabilityFilter deviceCapabilityFilter) {
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultDeviceRecognitionProvider.class);

}