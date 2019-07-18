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

import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DDMStorageAdapterTracker.class)
public class DDMStorageAdapterTrackerImpl implements DDMStorageAdapterTracker {

	@Override
	public DDMStorageAdapter getDDMStorageAdapter(String type) {
		return ddmStorageAdapterServiceTrackerMap.getService(type);
	}

	@Override
	public Set<String> getDDMStorageAdapterTypes() {
		return ddmStorageAdapterServiceTrackerMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		ddmStorageAdapterServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMStorageAdapter.class,
				"ddm.storage.adapter.type");
	}

	@Deactivate
	protected void deactivate() {
		ddmStorageAdapterServiceTrackerMap.close();
	}

	protected ServiceTrackerMap<String, DDMStorageAdapter>
		ddmStorageAdapterServiceTrackerMap;

}