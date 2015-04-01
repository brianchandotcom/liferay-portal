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

package com.liferay.portal.kernel.portlet.configuration;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationIconTracker {

	public static List<PortletConfigurationIcon>
		getPortletConfigurationIcons() {

		return _portletConfigurationIcons;
	}

	public PortletConfigurationIconTracker() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortletConfigurationIcon.class,
			new PortletConfigurationIconServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final List<PortletConfigurationIcon>
		_portletConfigurationIcons = new CopyOnWriteArrayList<>();

	private final ServiceTracker
		<PortletConfigurationIcon, PortletConfigurationIcon> _serviceTracker;

	private static class PortletConfigurationIconServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortletConfigurationIcon, PortletConfigurationIcon> {

		@Override
		public PortletConfigurationIcon addingService(
			ServiceReference<PortletConfigurationIcon> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PortletConfigurationIcon portletConfigurationIcon =
				registry.getService(serviceReference);

			_portletConfigurationIcons.add(portletConfigurationIcon);

			return portletConfigurationIcon;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortletConfigurationIcon> serviceReference,
			PortletConfigurationIcon portletConfigurationIcon) {
		}

		@Override
		public void removedService(
			ServiceReference<PortletConfigurationIcon> serviceReference,
			PortletConfigurationIcon portletConfigurationIcon) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_portletConfigurationIcons.remove(portletConfigurationIcon);
		}

	}

}