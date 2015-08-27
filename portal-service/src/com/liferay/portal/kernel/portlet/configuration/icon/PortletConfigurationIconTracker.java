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

package com.liferay.portal.kernel.portlet.configuration.icon;

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

	public static List<PortletConfigurationIconFactory>
		getPortletConfigurationIcons() {

		return _portletConfigurationIconFactories;
	}

	public PortletConfigurationIconTracker() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortletConfigurationIconFactory.class,
			new PortletConfigurationIconServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final List<PortletConfigurationIconFactory>
		_portletConfigurationIconFactories = new CopyOnWriteArrayList<>();

	private final ServiceTracker
		<PortletConfigurationIconFactory, PortletConfigurationIconFactory>
			_serviceTracker;

	private static class PortletConfigurationIconServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortletConfigurationIconFactory, PortletConfigurationIconFactory> {

		@Override
		public PortletConfigurationIconFactory addingService(
			ServiceReference<PortletConfigurationIconFactory>
				serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PortletConfigurationIconFactory portletConfigurationIconFactory =
				registry.getService(serviceReference);

			_portletConfigurationIconFactories.add(
				portletConfigurationIconFactory);

			return portletConfigurationIconFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortletConfigurationIconFactory> serviceReference,
			PortletConfigurationIconFactory portletConfigurationIconFactory) {
		}

		@Override
		public void removedService(
			ServiceReference<PortletConfigurationIconFactory> serviceReference,
			PortletConfigurationIconFactory portletConfigurationIconFactory) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_portletConfigurationIconFactories.remove(
				portletConfigurationIconFactory);
		}

	}

}