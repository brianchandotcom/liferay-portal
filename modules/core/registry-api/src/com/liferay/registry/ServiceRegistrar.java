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

package com.liferay.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class ServiceRegistrar<T> {

	public ServiceRegistrar(Registry registry, Class<T> clazz) {
		_registry = registry;
	}

	public void destroy() {
		destroy(null);
	}

	public void destroy(ServiceFinalizer<T> serviceFinalizer) {
		for (ServiceRegistration<T> serviceRegistration :
				_serviceRegistrations) {

			if (serviceFinalizer != null) {
				ServiceReference<T> serviceReference =
					serviceRegistration.getServiceReference();

				T service = _registry.getService(serviceReference);

				serviceFinalizer.finalize(serviceReference, service);
			}

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	public Collection<ServiceRegistration<T>> getServiceRegistrations() {
		return Collections.unmodifiableCollection(_serviceRegistrations);
	}

	public ServiceRegistration<T> registerService(Class<T> clazz, T service) {
		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			clazz, service);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> properties) {

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			clazz, service, properties);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(String className, T service) {
		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			className, service);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> properties) {

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			className, service, properties);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(
		String[] classNames, T service) {

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			classNames, service);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(
		String[] classNames, T service, Map<String, Object> properties) {

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			classNames, service, properties);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	private final Registry _registry;
	private final Set<ServiceRegistration<T>> _serviceRegistrations =
		new HashSet<>();

}