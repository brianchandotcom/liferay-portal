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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
		_destroyed = true;

		for (ServiceRegistration<T> serviceRegistration :
				_serviceRegistrations) {

			if (serviceFinalizer != null) {
				ServiceReference<T> serviceReference =
					serviceRegistration.getServiceReference();

				serviceFinalizer.finalize(
					serviceReference, _registry.getService(serviceReference));
			}

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	public Collection<ServiceRegistration<T>> getServiceRegistrations() {
		if (_destroyed) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableCollection(_serviceRegistrations);
	}

	public boolean isDestroyed() {
		return _destroyed;
	}

	public ServiceRegistration<T> registerService(Class<T> clazz, T service) {
		if (_destroyed) {
			return null;
		}

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			clazz, service);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> properties) {

		if (_destroyed) {
			return null;
		}

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			clazz, service, properties);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(String className, T service) {
		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			className, service);

		if (_destroyed) {
			return null;
		}

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> properties) {

		if (_destroyed) {
			return null;
		}

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			className, service, properties);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(
		String[] classNames, T service) {

		if (_destroyed) {
			return null;
		}

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			classNames, service);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	public ServiceRegistration<T> registerService(
		String[] classNames, T service, Map<String, Object> properties) {

		if (_destroyed) {
			return null;
		}

		ServiceRegistration<T> serviceRegistration = _registry.registerService(
			classNames, service, properties);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	private volatile boolean _destroyed;
	private final Registry _registry;
	private final Set<ServiceRegistration<T>> _serviceRegistrations =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

}