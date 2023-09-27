/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcellus Tavares
 */
@Component(service = DDMDataProviderRegistry.class)
public class DDMDataProviderRegistry {

	public DDMDataProvider getDDMDataProvider(String type) {
		_initializeDDMDataProviderTypeServiceTrackerMap();

		return _ddmDataProviderTypeServiceTrackerMap.getService(type);
	}

	public DDMDataProvider getDDMDataProviderByInstanceId(String instanceId) {
		if (_ddmDataProviderInstanceIdServiceTrackerMap == null) {
			_ddmDataProviderInstanceIdServiceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, DDMDataProvider.class,
					"ddm.data.provider.instance.id");
		}

		return _ddmDataProviderInstanceIdServiceTrackerMap.getService(
			instanceId);
	}

	public Set<String> getDDMDataProviderTypes() {
		_initializeDDMDataProviderTypeServiceTrackerMap();

		return _ddmDataProviderTypeServiceTrackerMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_ddmDataProviderInstanceIdServiceTrackerMap != null) {
			_ddmDataProviderInstanceIdServiceTrackerMap.close();
		}

		if (_ddmDataProviderTypeServiceTrackerMap != null) {
			_ddmDataProviderTypeServiceTrackerMap.close();
		}
	}

	private void _initializeDDMDataProviderTypeServiceTrackerMap() {
		if (_ddmDataProviderTypeServiceTrackerMap == null) {
			_ddmDataProviderTypeServiceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, DDMDataProvider.class,
					"ddm.data.provider.type");
		}
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, DDMDataProvider>
		_ddmDataProviderInstanceIdServiceTrackerMap;
	private ServiceTrackerMap<String, DDMDataProvider>
		_ddmDataProviderTypeServiceTrackerMap;

}