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

package com.liferay.registry.dependency;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class ServiceDependencyManager {

	public void addServiceDependencyListener(
		ServiceDependencyListener serviceDependencyListener) {

		_serviceDependencyListeners.add(serviceDependencyListener);
	}

	public void destroy() {
		_serviceDependencies.clear();

		for (ServiceTracker serviceTracker : _serviceTrackers) {
			serviceTracker.close();
		}

		_serviceTrackers.clear();

		for (ServiceDependencyListener serviceDependencyListener :
				_serviceDependencyListeners) {

			serviceDependencyListener.destroy();
		}

		_serviceDependencyListeners.clear();
	}

	public synchronized void registerDependencies(Class... serviceClasses) {
		Registry registry = RegistryUtil.getRegistry();

		for (Class serviceClass : serviceClasses) {
			ServiceDependency serviceDependency = new ServiceDependency(
				serviceClass);

			_serviceDependencies.add(serviceDependency);

			ServiceTracker serviceTracker = registry.trackServices(
				serviceClass,
				new ServiceDependencyServiceTrackerCustomizer(
					serviceDependency));

			serviceTracker.open();

			_serviceTrackers.add(serviceTracker);
		}
	}

	public synchronized void registerDependencies(Filter... filters) {
		Registry registry = RegistryUtil.getRegistry();

		for (Filter filter : filters) {
			ServiceDependency serviceDependency = new ServiceDependency(filter);

			_serviceDependencies.add(serviceDependency);

			ServiceTracker serviceTracker = registry.trackServices(
				filter,
				new ServiceDependencyServiceTrackerCustomizer(
					serviceDependency));

			serviceTracker.open();

			_serviceTrackers.add(serviceTracker);
		}
	}

	public void removeServiceDependencyListener(
		ServiceDependencyListener serviceDependencyListener) {

		_serviceDependencyListeners.remove(serviceDependencyListener);
	}

	public synchronized void verifyDependencies() {
		for (ServiceDependency serviceDependency : _serviceDependencies) {
			if (!serviceDependency.isFulfilled()) {
				return;
			}
		}

		for (ServiceDependencyListener serviceDependencyListener :
				_serviceDependencyListeners) {

			serviceDependencyListener.dependenciesFulfilled();
		}
	}

	private final Set<ServiceDependency> _serviceDependencies = new HashSet<>();
	private final Set<ServiceDependencyListener> _serviceDependencyListeners =
		new HashSet<>();
	private final Set<ServiceTracker> _serviceTrackers = new HashSet<>();

	private class ServiceDependencyServiceTrackerCustomizer
		implements ServiceTrackerCustomizer {

		public ServiceDependencyServiceTrackerCustomizer(
			ServiceDependency serviceDependency) {

			_serviceDependency = serviceDependency;
		}

		@Override
		public Object addingService(ServiceReference serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			Object service = registry.getService(serviceReference);

			_serviceDependency.fulfilled(serviceReference);

			verifyDependencies();

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference serviceReference, Object object) {
		}

		@Override
		public void removedService(
			ServiceReference serviceReference, Object object) {
		}

		private final ServiceDependency _serviceDependency;

	}

}