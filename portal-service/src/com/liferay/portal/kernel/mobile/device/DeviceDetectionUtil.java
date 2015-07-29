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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Milen Dyankov
 * @author Raymond Augé
 */
public class DeviceDetectionUtil {

	public static Device detectDevice(HttpServletRequest request) {
		DeviceRecognitionProvider deviceRecognitionProvider =
			getDeviceRecognitionProvider();

		if (deviceRecognitionProvider == null) {
			return UnknownDevice.getInstance();
		}

		return deviceRecognitionProvider.detectDevice(request);
	}

	public static DeviceRecognitionProvider getDeviceRecognitionProvider() {
		PortalRuntimePermission.checkGetBeanProperty(DeviceDetectionUtil.class);

		if (_instance._deviceRecognitionProvider != null) {
			return _instance._deviceRecognitionProvider;
		}

		return _instance._defaultDeviceRecognitionProvider;
	}

	public static Set<VersionableName> getKnownBrands() {
		KnownDevices knownDevices = getKnownDevices();

		return knownDevices.getBrands();
	}

	public static Set<VersionableName> getKnownBrowsers() {
		KnownDevices knownDevices = getKnownDevices();

		return knownDevices.getBrowsers();
	}

	public static Set<String> getKnownDeviceIdsByCapability(
		Capability capability) {

		KnownDevices knownDevices = getKnownDevices();

		Map<Capability, Set<String>> deviceIds = knownDevices.getDeviceIds();

		return deviceIds.get(capability);
	}

	public static Set<VersionableName> getKnownOperatingSystems() {
		KnownDevices knownDevices = getKnownDevices();

		return knownDevices.getOperatingSystems();
	}

	public static Set<String> getKnownPointingMethods() {
		KnownDevices knownDevices = getKnownDevices();

		return knownDevices.getPointingMethods();
	}

	public DeviceDetectionUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			DeviceRecognitionProvider.class,
			new DeviceRecognitionProviderServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	protected static KnownDevices getKnownDevices() {
		DeviceRecognitionProvider deviceRecognitionProvider =
			getDeviceRecognitionProvider();

		KnownDevices knownDevices = null;

		if (deviceRecognitionProvider == null) {
			knownDevices = NoKnownDevices.getInstance();
		}
		else {
			knownDevices = deviceRecognitionProvider.getKnownDevices();
		}

		return knownDevices;
	}

	private static final DeviceDetectionUtil _instance =
		new DeviceDetectionUtil();

	private volatile DeviceRecognitionProvider
		_defaultDeviceRecognitionProvider;
	private volatile DeviceRecognitionProvider _deviceRecognitionProvider;
	private final ServiceTracker
		<DeviceRecognitionProvider, DeviceRecognitionProvider> _serviceTracker;

	private class DeviceRecognitionProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<DeviceRecognitionProvider, DeviceRecognitionProvider> {

		@Override
		public DeviceRecognitionProvider addingService(
			ServiceReference<DeviceRecognitionProvider> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			DeviceRecognitionProvider deviceRecognitionProvider =
				registry.getService(serviceReference);

			String type = (String)serviceReference.getProperty("type");

			if (Validator.isNotNull(type) && type.equals("default")) {
				_defaultDeviceRecognitionProvider = deviceRecognitionProvider;
			}
			else {
				_deviceRecognitionProvider = deviceRecognitionProvider;
			}

			return deviceRecognitionProvider;
		}

		@Override
		public void modifiedService(
			ServiceReference<DeviceRecognitionProvider> serviceReference,
			DeviceRecognitionProvider deviceRecognitionProvider) {
		}

		@Override
		public void removedService(
			ServiceReference<DeviceRecognitionProvider> serviceReference,
			DeviceRecognitionProvider deviceRecognitionProvider) {

			Registry registry = RegistryUtil.getRegistry();

			String type = (String)serviceReference.getProperty("type");

			if (type.equals("default")) {
				_defaultDeviceRecognitionProvider = null;
			}
			else {
				_deviceRecognitionProvider = null;
			}

			registry.ungetService(serviceReference);
		}

	}

}