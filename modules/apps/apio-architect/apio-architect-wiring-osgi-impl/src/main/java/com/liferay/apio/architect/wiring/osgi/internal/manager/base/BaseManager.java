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

import static com.liferay.apio.architect.wiring.osgi.internal.manager.cache.ManagerCache.INSTANCE;

import com.liferay.apio.architect.wiring.osgi.internal.service.tracker.map.listener.ClearCacheServiceTrackerMapListener;
import com.liferay.osgi.service.tracker.collections.internal.DefaultServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.internal.map.ServiceTrackerMapImpl;
import com.liferay.osgi.service.tracker.collections.internal.map.SingleValueServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;

import java.util.Set;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Manages services that have a generic type.
 *
 * @author Alejandro Hernández
 */
public abstract class BaseManager<T, U> {

	public BaseManager(Class<T> managedClass) {
		_managedClass = managedClass;
	}

	@Activate
	public void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;

		serviceTrackerMap = new ServiceTrackerMapImpl<>(
			bundleContext, _managedClass, null, this::emit,
			new DefaultServiceTrackerCustomizer<>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<>(),
			new ClearCacheServiceTrackerMapListener<>());

		serviceTrackerMap.open();

		INSTANCE.clear();
	}

	@Deactivate
	public void deactivate() {
		serviceTrackerMap.close();
		INSTANCE.clear();
	}

	/**
	 * Returns the service tracker key stream.
	 *
	 * @return the service tracker key stream
	 */
	public Stream<U> getKeyStream() {
		Set<U> keys = serviceTrackerMap.keySet();

		return keys.stream();
	}

	/**
	 * Emits a service's key using an {@code Emitter<String>}.
	 *
	 * @param serviceReference the service reference
	 * @param emitter the emitter
	 */
	protected abstract void emit(
		ServiceReference<T> serviceReference, Emitter<U> emitter);

	protected BundleContext bundleContext;
	protected ServiceTrackerMap<U, T> serviceTrackerMap;

	private final Class<T> _managedClass;

}