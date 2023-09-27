/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterRegistry;
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
@Component(service = DDMStorageAdapterRegistry.class)
public class DDMStorageAdapterRegistryImpl
	implements DDMStorageAdapterRegistry {

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