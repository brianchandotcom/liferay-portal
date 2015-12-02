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

package com.liferay.dynamic.data.mapping.storage.impl;

import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistry;
import com.liferay.osgi.util.ServiceTrackerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marcellus Tavares
 */
public class StorageAdapterRegistryImpl implements StorageAdapterRegistry {

	public StorageAdapterRegistryImpl() throws InvalidSyntaxException {
		Class<?> clazz = getClass();

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
				_bundleContext,
				"(&(objectClass=" + StorageAdapter.class.getName() +
						")(!(objectClass=" + clazz.getName() + ")))",
				new StorageAdapterServiceTrackerCustomizer());
	}

	@Override
	public StorageAdapter getDefaultStorageAdapter() {
		return _storageAdaptersMap.get(_defaultStorageType);
	}

	@Override
	public StorageAdapter getStorageAdapter(String storageType) {
		return _storageAdaptersMap.get(storageType);
	}

	@Override
	public Set<String> getStorageTypes() {
		return _storageAdaptersMap.keySet();
	}

	public void setDefaultStorageType(String storageType) {
		_defaultStorageType = storageType;
	}

	public void setStorageAdapters(List<StorageAdapter> storageAdapters) {
		for (StorageAdapter storageAdapter : storageAdapters) {
			_bundleContext.registerService(
				StorageAdapter.class, storageAdapter, null);
		}
	}

	private final BundleContext _bundleContext;
	private String _defaultStorageType;
	private final ServiceTracker<StorageAdapter, StorageAdapter>
		_serviceTracker;
	private final Map<String, StorageAdapter> _storageAdaptersMap =
		new ConcurrentHashMap<>();

	private class StorageAdapterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<StorageAdapter, StorageAdapter> {

		@Override
		public StorageAdapter addingService(
			ServiceReference<StorageAdapter> serviceReference) {

			StorageAdapter storageAdapter = _bundleContext.getService(
				serviceReference);

			_storageAdaptersMap.put(
				storageAdapter.getStorageType(), storageAdapter);

			return storageAdapter;
		}

		@Override
		public void modifiedService(
			ServiceReference<StorageAdapter> serviceReference,
			StorageAdapter storageAdapter) {
		}

		@Override
		public void removedService(
			ServiceReference<StorageAdapter> serviceReference,
			StorageAdapter storageAdapter) {

			_bundleContext.ungetService(serviceReference);
		}

	}

}