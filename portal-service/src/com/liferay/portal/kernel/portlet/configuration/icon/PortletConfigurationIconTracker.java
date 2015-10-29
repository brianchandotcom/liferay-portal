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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationIconTracker {

	public static List<PortletConfigurationIconFactory>
		getPortletConfigurationIcons(String portletId) {

		List<PortletConfigurationIconFactory>
			portletConfigurationIconFactories = new ArrayList<>();

		List<PortletConfigurationIconFactory>
			portletPortletConfigurationIconFactories =
				_portletConfigurationIconFactoriesMap.get(StringPool.STAR);

		if (portletPortletConfigurationIconFactories != null) {
			portletConfigurationIconFactories.addAll(
				portletPortletConfigurationIconFactories);
		}

		portletPortletConfigurationIconFactories =
			_portletConfigurationIconFactoriesMap.get(portletId);

		if (portletPortletConfigurationIconFactories != null) {
			portletConfigurationIconFactories.addAll(
				portletPortletConfigurationIconFactories);
		}

		return portletConfigurationIconFactories;
	}

	public PortletConfigurationIconTracker() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortletConfigurationIconFactory.class,
			new PortletConfigurationIconServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final Map<String, List<PortletConfigurationIconFactory>>
		_portletConfigurationIconFactoriesMap = new ConcurrentHashMap<>();

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

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			if (Validator.isNull(portletId)) {
				portletId = StringPool.STAR;
			}

			List<PortletConfigurationIconFactory>
				portletConfigurationIconFactories =
					_portletConfigurationIconFactoriesMap.get(portletId);

			if (portletConfigurationIconFactories == null) {
				portletConfigurationIconFactories = new ArrayList<>();

				_portletConfigurationIconFactoriesMap.put(
					portletId, portletConfigurationIconFactories);
			}

			portletConfigurationIconFactories.add(
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

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			if (Validator.isNull(portletId)) {
				portletId = StringPool.STAR;
			}

			List<PortletConfigurationIconFactory>
				portletConfigurationIconFactories =
					_portletConfigurationIconFactoriesMap.get(portletId);

			if (portletConfigurationIconFactories == null) {
				return;
			}

			portletConfigurationIconFactories.remove(
				portletConfigurationIconFactory);
		}

	}

}