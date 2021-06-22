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
@Component(immediate = true, service = DDMDataProviderTracker.class)
public class DDMDataProviderTracker {

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