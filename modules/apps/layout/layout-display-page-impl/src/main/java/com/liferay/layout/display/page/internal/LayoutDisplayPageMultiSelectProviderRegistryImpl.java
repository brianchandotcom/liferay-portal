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

package com.liferay.layout.display.page.internal;

import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LayoutDisplayPageMultiSelectProviderRegistry.class)
public class LayoutDisplayPageMultiSelectProviderRegistryImpl
	implements LayoutDisplayPageMultiSelectProviderRegistry {

	@Override
	public LayoutDisplayPageMultiSelectProvider<?>
		getLayoutDisplayPageMultiSelectProvider(String className) {

		return _layoutDisplayPageMultiSelectProviderServiceTrackerMap.
			getService(className);
	}

	@Override
	public List<LayoutDisplayPageMultiSelectProvider<?>>
		getLayoutDisplayPageMultiSelectProviders() {

		return new ArrayList(
			_layoutDisplayPageMultiSelectProviderServiceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_layoutDisplayPageMultiSelectProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<LayoutDisplayPageMultiSelectProvider<?>>)
					(Class<?>)LayoutDisplayPageMultiSelectProvider.class,
				null,
				(serviceReference, emitter) -> {
					LayoutDisplayPageMultiSelectProvider<?>
						layoutDisplayPageMultiSelectProvider =
							bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							layoutDisplayPageMultiSelectProvider.
								getClassName());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				},
				new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	private ServiceTrackerMap<String, LayoutDisplayPageMultiSelectProvider<?>>
		_layoutDisplayPageMultiSelectProviderServiceTrackerMap;

}