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

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.DeviceCapabilityFilter;
import com.liferay.portal.kernel.mobile.device.DeviceRecognitionProvider;
import com.liferay.portal.kernel.mobile.device.KnownDevices;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Michael C. Han
 */
public class DeviceRecognitionProviderProxyBean
	extends BaseProxyBean implements DeviceRecognitionProvider {

	@Override
	public Device detectDevice(HttpServletRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KnownDevices getKnownDevices() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reload() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDeviceCapabilityFilter(
		DeviceCapabilityFilter deviceCapabilityFilter) {

		throw new UnsupportedOperationException();
	}

}