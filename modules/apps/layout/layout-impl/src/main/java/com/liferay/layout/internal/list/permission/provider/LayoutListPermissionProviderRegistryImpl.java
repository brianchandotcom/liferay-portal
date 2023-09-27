/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.list.permission.provider;

import com.liferay.layout.list.permission.provider.LayoutListPermissionProvider;
import com.liferay.layout.list.permission.provider.LayoutListPermissionProviderRegistry;
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