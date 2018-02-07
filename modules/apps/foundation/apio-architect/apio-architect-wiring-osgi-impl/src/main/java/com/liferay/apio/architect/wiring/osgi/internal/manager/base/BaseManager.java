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

package com.liferay.apio.architect.wiring.osgi.internal.manager.base;

import static com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory.openSingleValueMap;

import com.liferay.apio.architect.wiring.osgi.internal.service.reference.mapper.CustomServiceReferenceMapper;
import com.liferay.apio.architect.wiring.osgi.internal.service.tracker.customizer.TransformServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;

import java.util.Optional;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Manages services that have a generic type. Stores the services transformed
 * with the {@link #map(Object, ServiceReference, Class)} function.
 *
 * @author Alejandro Hernández
 * @review
 */
public abstract class BaseManager<T, U>
	extends TransformServiceTrackerCustomizer<T, U> {

	public BaseManager(Class<T> managedClass) {
		super(managedClass);
	}

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = openSingleValueMap(
			bundleContext, getManagedClass(), null, this::emit, this);
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	/**
	 * Returns the {@code ServiceTrackerMap}.
	 *
	 * @return the service tracker map
	 */
	public ServiceTrackerMap<String, U> getServiceTrackerMap() {
		return _serviceTrackerMap;
	}

	/**
	 * Emits a the key of a service using an {@link Emitter<String>}.
	 *
	 * @param  serviceReference the service reference
	 * @param  emitter the emitter
	 * @review
	 */
	protected void emit(
		ServiceReference<T> serviceReference, Emitter<String> emitter) {

		Bundle bundle = FrameworkUtil.getBundle(BaseManager.class);

		BundleContext bundleContext = bundle.getBundleContext();

		CustomServiceReferenceMapper<T> customServiceReferenceMapper =
			new CustomServiceReferenceMapper<>(
				bundleContext, getManagedClass(),
				getPrincipalTypeParamPosition());

		customServiceReferenceMapper.map(serviceReference, emitter);
	}

	/**
	 * Returns a service from the inner map based on the service's generic inner
	 * class, if the service exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  clazz the generic inner class
	 * @return the service, if present; {@code Optional#empty()} otherwise
	 */
	protected <V> Optional<U> getServiceOptional(Class<V> clazz) {
		return getServiceOptional(clazz.getName());
	}

	/**
	 * Returns a service from the inner map based on the service's generic inner
	 * class name, if the service exists. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @param  className the generic inner class name
	 * @return the service, if present; {@code Optional#empty()} otherwise
	 */
	protected Optional<U> getServiceOptional(String className) {
		return Optional.ofNullable(_serviceTrackerMap.getService(className));
	}

	private ServiceTrackerMap<String, U> _serviceTrackerMap;

}