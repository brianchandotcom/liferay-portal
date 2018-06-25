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

package com.liferay.apio.architect.impl.internal.wiring.osgi.manager.base;

import static com.liferay.apio.architect.impl.internal.wiring.osgi.manager.cache.ManagerCache.INSTANCE;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.impl.internal.wiring.osgi.tracker.map.listener.ClearCacheServiceTrackerMapListener;
import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.osgi.service.tracker.collections.internal.DefaultServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.internal.map.ServiceTrackerMapImpl;
import com.liferay.osgi.service.tracker.collections.internal.map.SingleValueServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;

import java.util.Set;
import java.util.function.BiConsumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

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

		INSTANCE.clear();
	}

	@Deactivate
	public void deactivate() {
		serviceTrackerMap.close();
		INSTANCE.clear();
	}

	public void forEachService(BiConsumer<U, T> biConsumer) {
		Set<U> keys = serviceTrackerMap.keySet();

		keys.forEach(
			u -> biConsumer.accept(u, serviceTrackerMap.getService(u)));
	}

	/**
	 * Emits a service's key using an {@code Emitter<String>}.
	 *
	 * @param serviceReference the service reference
	 * @param emitter the emitter
	 */
	protected abstract void emit(
		ServiceReference<T> serviceReference, Emitter<U> emitter);

	/**
	 * Warns a message through an {@code ApioLogger} if one can be found.
	 *
	 * @param  message the message to log
	 * @review
	 */
	protected void warning(String message) {
		if (apioLogger != null) {
			apioLogger.warning(message);
		}
	}

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	protected ApioLogger apioLogger;

	protected BundleContext bundleContext;
	protected ServiceTrackerMap<U, T> serviceTrackerMap;

	private final Class<T> _managedClass;

}