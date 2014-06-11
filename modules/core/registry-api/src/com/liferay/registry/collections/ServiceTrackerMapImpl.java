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

package com.liferay.registry.collections;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapImpl<K, S, R> implements ServiceTrackerMap<K, R> {

	public ServiceTrackerMapImpl(
		Class<S> serviceClass, String filter) {

		Registry registry = RegistryUtil.getRegistry();

		String completeFilter =
			"(&(objectClass="+serviceClass.getName()+")"+filter+")";

		_serviceTracker = registry.trackServices(
			registry.getFilter(completeFilter),
			new MapServiceTrackerCustomizer());
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	public R getService(K key) {
		return null;
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	private ConcurrentHashMap<K, Bucket<S, R>> _indexedServices =
		new ConcurrentHashMap<K, Bucket<S, R>>();
	private final ServiceTracker<S, ServiceReference<S>> _serviceTracker;

	interface Bucket<S, R> {
		public R getContent();

		public boolean isDisposable();

		public void store(ServiceReference<S> serviceReference);

		public void remove(ServiceReference<S> serviceReference);
	}

	private class MapServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<S, ServiceReference<S>> {

		@Override
		public ServiceReference<S> addingService(
			final ServiceReference<S> serviceReference) {

		}

		@Override
		public void modifiedService(
			final ServiceReference<S> newServiceReference,
				final ServiceReference<S> oldServiceReference) {

		}

		@Override
		public void removedService(
			final ServiceReference<S> serviceReference,
				ServiceReference<S> serviceReference2) {

		}

	}

}