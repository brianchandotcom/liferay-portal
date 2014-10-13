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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marcellus Tavares
 */
public class StorageAdapterRegistryImpl implements StorageAdapterRegistry {

	public StorageAdapterRegistryImpl() {
		Registry registry = RegistryUtil.getRegistry();

		Class<?> clazz = getClass();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + StorageAdapter.class.getName() +
				")(!(objectClass=" + clazz.getName() + ")))");

		_serviceTracker = registry.trackServices(
			filter, new StorageAdapterServiceTrackerCustomizer());

		_serviceTracker.open();
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
		Registry registry = RegistryUtil.getRegistry();

		for (StorageAdapter storageAdapter : storageAdapters) {
			registry.registerService(StorageAdapter.class, storageAdapter);
		}
	}

	private String _defaultStorageType;
	private final ServiceTracker<StorageAdapter, StorageAdapter>
		_serviceTracker;
	private final Map<String, StorageAdapter> _storageAdaptersMap =
		new ConcurrentHashMap<String, StorageAdapter>();

	private class StorageAdapterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<StorageAdapter, StorageAdapter> {

		@Override
		public StorageAdapter addingService(
			ServiceReference<StorageAdapter> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			StorageAdapter storageAdapter = registry.getService(
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

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

	}

}