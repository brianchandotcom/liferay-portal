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

package com.liferay.portal.poller;

import com.liferay.portal.dao.shard.ShardPollerProcessorWrapper;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class PollerProcessorUtil {

	public static void addPollerProcessor(
		String portletId, PollerProcessor pollerProcessor) {

		_instance._addPollerProcessor(portletId, pollerProcessor);
	}

	public static void deletePollerProcessor(String portletId) {
		_instance._deletePollerProcessor(portletId);
	}

	public static PollerProcessor getPollerProcessor(String portletId) {
		return _instance._getPollerProcessor(portletId);
	}

	private PollerProcessorUtil() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=*)(objectClass=" +
				PollerProcessor.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new PollerProcessorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _addPollerProcessor(
		String portletId, PollerProcessor pollerProcessor) {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("javax.portlet.name", portletId);

		ServiceRegistration<PollerProcessor> serviceRegistration =
			registry.registerService(
				PollerProcessor.class, pollerProcessor, properties);

		_serviceRegistrations.put(portletId, serviceRegistration);
	}

	private void _deletePollerProcessor(String portletId) {
		ServiceRegistration<PollerProcessor> serviceRegistration =
			_serviceRegistrations.remove(portletId);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private PollerProcessor _getPollerProcessor(String portletId) {
		return _pollerPorcessors.get(portletId);
	}

	private static PollerProcessorUtil _instance = new PollerProcessorUtil();

	private Map<String, PollerProcessor> _pollerPorcessors =
		new ConcurrentHashMap<String, PollerProcessor>();
	private StringServiceRegistrationMap<PollerProcessor>
		_serviceRegistrations =
			new StringServiceRegistrationMap<PollerProcessor>();
	private ServiceTracker<PollerProcessor, PollerProcessor> _serviceTracker;

	private class PollerProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<PollerProcessor, PollerProcessor> {

		@Override
		public PollerProcessor addingService(
			ServiceReference<PollerProcessor> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PollerProcessor pollerProcessor = registry.getService(
				serviceReference);

			if (ShardUtil.isEnabled()) {
				pollerProcessor = new ShardPollerProcessorWrapper(
					pollerProcessor);
			}

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			_pollerPorcessors.put(portletId, pollerProcessor);

			return pollerProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<PollerProcessor> serviceReference,
			PollerProcessor pollerProcessor) {
		}

		@Override
		public void removedService(
			ServiceReference<PollerProcessor> serviceReference,
			PollerProcessor pollerProcessor) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			_pollerPorcessors.remove(portletId);
		}

	}

}