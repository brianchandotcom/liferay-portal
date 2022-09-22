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

package com.liferay.portal.background.task.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BackgroundTaskExecutorRegistry.class)
public class BackgroundTaskExecutorRegistryImpl
	implements BackgroundTaskExecutorRegistry {

	@Override
	public synchronized BackgroundTaskExecutor getBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName) {

		if (_serviceTrackerMap == null) {
			_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, BackgroundTaskExecutor.class,
				"background.task.executor.class.name");
		}

		return _serviceTrackerMap.getService(backgroundTaskExecutorClassName);
	}

	@Override
	public synchronized void registerBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName,
		BackgroundTaskExecutor backgroundTaskExecutor) {

		ServiceRegistration<BackgroundTaskExecutor> serviceRegistration =
			_bundleContext.registerService(
				BackgroundTaskExecutor.class, backgroundTaskExecutor,
				HashMapDictionaryBuilder.<String, Object>put(
					"background.task.executor.class.name",
					backgroundTaskExecutorClassName
				).build());

		_serviceRegistrations.put(
			backgroundTaskExecutorClassName, serviceRegistration);
	}

	@Override
	public synchronized void unregisterBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName) {

		if (!_serviceRegistrations.containsKey(
				backgroundTaskExecutorClassName)) {

			return;
		}

		ServiceRegistration<BackgroundTaskExecutor> serviceRegistration =
			_serviceRegistrations.get(backgroundTaskExecutorClassName);

		serviceRegistration.unregister();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected synchronized void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}

		_bundleContext = null;

		for (ServiceRegistration<BackgroundTaskExecutor> serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	private BundleContext _bundleContext;
	private final Map<String, ServiceRegistration<BackgroundTaskExecutor>>
		_serviceRegistrations = new HashMap<>();
	private ServiceTrackerMap<String, BackgroundTaskExecutor>
		_serviceTrackerMap;

}