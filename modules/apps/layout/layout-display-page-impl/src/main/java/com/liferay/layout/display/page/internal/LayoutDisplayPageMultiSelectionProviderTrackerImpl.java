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

import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProviderTracker;
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
@Component(service = LayoutDisplayPageMultiSelectionProviderTracker.class)
public class LayoutDisplayPageMultiSelectionProviderTrackerImpl
	implements LayoutDisplayPageMultiSelectionProviderTracker {

	@Override
	public LayoutDisplayPageMultiSelectionProvider<?>
		getLayoutDisplayPageMultiSelectionProvider(String className) {

		return _layoutDisplayPageMultiSelectionProviderServiceTrackerMap.
			getService(className);
	}

	@Override
	public List<LayoutDisplayPageMultiSelectionProvider<?>>
		getLayoutDisplayPageMultiSelectionProviders() {

		return new ArrayList(
			_layoutDisplayPageMultiSelectionProviderServiceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_layoutDisplayPageMultiSelectionProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<LayoutDisplayPageMultiSelectionProvider<?>>)
					(Class<?>)LayoutDisplayPageMultiSelectionProvider.class,
				null,
				(serviceReference, emitter) -> {
					LayoutDisplayPageMultiSelectionProvider<?>
						layoutDisplayPageMultiSelectionProvider =
							bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							layoutDisplayPageMultiSelectionProvider.
								getClassName());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				},
				new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	private ServiceTrackerMap
		<String, LayoutDisplayPageMultiSelectionProvider<?>>
			_layoutDisplayPageMultiSelectionProviderServiceTrackerMap;

}