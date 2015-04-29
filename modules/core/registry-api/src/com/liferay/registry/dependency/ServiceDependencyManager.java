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
		synchronized (_serviceDependencies) {
			for (ServiceDependency serviceDependency : _serviceDependencies) {
				serviceDependency.close();
			}
		}

		_serviceDependencies.clear();

		for (ServiceDependencyListener serviceDependencyListener :
				_serviceDependencyListeners) {

			serviceDependencyListener.destroy();
		}

		_serviceDependencyListeners.clear();
	}

	public void registerDependencies(Class<?>... serviceClasses) {
		Registry registry = RegistryUtil.getRegistry();

		synchronized (_serviceDependencies) {
			for (Class<?> serviceClass : serviceClasses) {
				ServiceDependency serviceDependency = new ServiceDependency(
					serviceClass);

				_serviceDependencies.add(serviceDependency);

				ServiceTracker<Object, Object> serviceTracker =
					registry.trackServices(
						(Class<Object>)serviceClass,
						new ServiceDependencyServiceTrackerCustomizer(
							serviceDependency));

				serviceDependency.setServiceTracker(serviceTracker);
			}

			for (ServiceDependency serviceDependency : _serviceDependencies) {
				serviceDependency.open();
			}
		}
	}

	public void registerDependencies(Filter... filters) {
		Registry registry = RegistryUtil.getRegistry();

		synchronized (_serviceDependencies) {
			for (Filter filter : filters) {
				ServiceDependency serviceDependency = new ServiceDependency(
					filter);

				_serviceDependencies.add(serviceDependency);

				ServiceTracker<Object, Object> serviceTracker =
					registry.trackServices(
						filter,
						new ServiceDependencyServiceTrackerCustomizer(
							serviceDependency));

				serviceDependency.setServiceTracker(serviceTracker);
			}
		}
	}

	public void removeServiceDependencyListener(
		ServiceDependencyListener serviceDependencyListener) {

		_serviceDependencyListeners.remove(serviceDependencyListener);
	}

	public void verifyDependencies() {
		synchronized (_serviceDependencies) {
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
	}

	private final Set<ServiceDependency> _serviceDependencies = new HashSet<>();
	private final Set<ServiceDependencyListener> _serviceDependencyListeners =
		new HashSet<>();

	private class ServiceDependencyServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		public ServiceDependencyServiceTrackerCustomizer(
			ServiceDependency serviceDependency) {

			_serviceDependency = serviceDependency;
		}

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			synchronized (_serviceDependencies) {
				Registry registry = RegistryUtil.getRegistry();

				Object service = registry.getService(serviceReference);

				_serviceDependency.fulfilled(serviceReference);

				verifyDependencies();

				return service;
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object service) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object service) {
		}

		private final ServiceDependency _serviceDependency;

	}

}