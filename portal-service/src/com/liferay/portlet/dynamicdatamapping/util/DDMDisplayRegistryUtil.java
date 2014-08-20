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

package com.liferay.portlet.dynamicdatamapping.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eduardo Garcia
 */
@ProviderType
public class DDMDisplayRegistryUtil {

	public static DDMDisplay getDDMDisplay(String portletId) {
		return _instance._getDDMDisplay(portletId);
	}

	public static List<DDMDisplay> getDDMDisplays() {
		return _instance._getDDMDisplays();
	}

	public static String[] getPortletIds() {
		return _instance._getPortletIds();
	}

	public static void register(DDMDisplay ddmDisplay) {
		_instance._register(ddmDisplay);
	}

	public static void unregister(DDMDisplay ddmDisplay) {
		_instance._unregister(ddmDisplay);
	}

	private DDMDisplayRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			DDMDisplay.class, new DDMDisplayServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private DDMDisplay _getDDMDisplay(String portletId) {
		return _ddmDisplays.get(portletId);
	}

	private List<DDMDisplay> _getDDMDisplays() {
		return ListUtil.fromMapValues(_ddmDisplays);
	}

	private String[] _getPortletIds() {
		Set<String> portletIds = _ddmDisplays.keySet();

		return portletIds.toArray(new String[portletIds.size()]);
	}

	private void _register(DDMDisplay ddmDisplay) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<DDMDisplay> serviceRegistration =
			registry.registerService(DDMDisplay.class, ddmDisplay);

		_serviceRegistrations.put(ddmDisplay, serviceRegistration);
	}

	private void _unregister(DDMDisplay ddmDisplay) {
		ServiceRegistration<DDMDisplay> serviceRegistration =
			_serviceRegistrations.remove(ddmDisplay);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final DDMDisplayRegistryUtil _instance =
		new DDMDisplayRegistryUtil();

	private final Map<String, DDMDisplay> _ddmDisplays =
		new ConcurrentHashMap<String, DDMDisplay>();
	private final ServiceRegistrationMap<DDMDisplay> _serviceRegistrations =
		new ServiceRegistrationMap<DDMDisplay>();
	private final ServiceTracker<DDMDisplay, DDMDisplay> _serviceTracker;

	private class DDMDisplayServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<DDMDisplay, DDMDisplay> {

		@Override
		public DDMDisplay addingService(
			ServiceReference<DDMDisplay> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			DDMDisplay ddmDisplay = registry.getService(serviceReference);

			_ddmDisplays.put(ddmDisplay.getPortletId(), ddmDisplay);

			return ddmDisplay;
		}

		@Override
		public void modifiedService(
			ServiceReference<DDMDisplay> serviceReference,
			DDMDisplay ddmDisplay) {
		}

		@Override
		public void removedService(
			ServiceReference<DDMDisplay> serviceReference,
			DDMDisplay ddmDisplay) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_ddmDisplays.remove(ddmDisplay.getPortletId());
		}

	}

}