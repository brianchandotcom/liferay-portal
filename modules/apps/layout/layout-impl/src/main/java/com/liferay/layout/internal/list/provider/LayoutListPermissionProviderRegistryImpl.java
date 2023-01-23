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

package com.liferay.layout.internal.list.provider;

import com.liferay.layout.list.provider.LayoutListPermissionProvider;
import com.liferay.layout.list.provider.LayoutListPermissionProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LayoutListPermissionProviderRegistry.class)
public class LayoutListPermissionProviderRegistryImpl
	implements LayoutListPermissionProviderRegistry {

	@Override
	public LayoutListPermissionProvider<?> getLayoutListPermissionProvider(
		String type) {

		return _serviceTrackerMap.getService(type);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<LayoutListPermissionProvider<?>>)
				(Class<?>)LayoutListPermissionProvider.class,
			null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(layoutListPermissionProvider, emitter) -> emitter.emit(
					GenericUtil.getGenericClassName(
						layoutListPermissionProvider))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, LayoutListPermissionProvider<?>>
		_serviceTrackerMap;

}