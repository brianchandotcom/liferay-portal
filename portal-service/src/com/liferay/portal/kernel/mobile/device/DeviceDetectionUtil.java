/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.mobile.device;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Milen Dyankov
 */
public class DeviceDetectionUtil {

	public static Device detectDevice(HttpServletRequest request) {
		return _deviceRecognitionProvider.detectDevice(request);
	}

	public static Set<VersionableName> getKnownBrands() {
		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		return knownDevices.getBrands();
	}

	public static Set<VersionableName> getKnownBrowsers() {
		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		return knownDevices.getBrowsers();
	}

	public static Set<String> getKnownDeviceIdsByCapability(
		Capability capability) {

		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		Map<Capability, Set<String>> deviceIds = knownDevices.getDeviceIds();

		return deviceIds.get(capability);
	}

	public static Set<VersionableName> getKnownOperatingSystems() {
		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		return knownDevices.getOperatingSystems();
	}

	public static Set<String> getKnownPointingMethods() {
		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		return knownDevices.getPointingMethods();
	}

	public void setDeviceRecognitionProvider(
		DeviceRecognitionProvider deviceRecognitionProvider) {

		_deviceRecognitionProvider = deviceRecognitionProvider;
	}

	private static volatile DeviceRecognitionProvider
		_deviceRecognitionProvider;

}