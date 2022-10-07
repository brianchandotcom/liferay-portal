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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = StorageAdapterRegistry.class)
public class StorageAdapterRegistryImpl implements StorageAdapterRegistry {

	@Override
	public StorageAdapter getDefaultStorageAdapter() {
		return _defaultStorageAdapter;
	}

	@Override
	public StorageAdapter getStorageAdapter(String storageType) {
		return _serviceTrackerMap.getService(storageType);
	}

	@Override
	public Set<String> getStorageTypes() {
		return _serviceTrackerMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, StorageAdapter.class, null,
			ServiceReferenceMapperFactory.createFromFunction(
				bundleContext, StorageAdapter::getStorageType));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private DefaultStorageAdapter _defaultStorageAdapter;

	private ServiceTrackerMap<String, StorageAdapter> _serviceTrackerMap;

}