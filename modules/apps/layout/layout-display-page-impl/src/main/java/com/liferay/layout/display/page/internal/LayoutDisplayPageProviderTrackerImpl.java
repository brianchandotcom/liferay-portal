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

import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = LayoutDisplayPageProviderTracker.class)
public class LayoutDisplayPageProviderTrackerImpl
	implements LayoutDisplayPageProviderTracker {

	@Override
	public LayoutDisplayPageProvider<?> getLayoutDisplayPageProviderByClassName(
		String className) {

		return _layoutDisplayPageProviderByClassNameServiceTrackerMap.
			getService(className);
	}

	@Override
	public LayoutDisplayPageProvider<?>
		getLayoutDisplayPageProviderByURLSeparator(String urlSeparator) {

		return _layoutDisplayPageProviderByURLSeparatorServiceTrackerMap.
			getService(urlSeparator);
	}

	@Override
	public List<LayoutDisplayPageProvider<?>> getLayoutDisplayPageProviders() {
		return new ArrayList(
			_layoutDisplayPageProviderByClassNameServiceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_layoutDisplayPageProviderByClassNameServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<LayoutDisplayPageProvider<?>>)
					(Class<?>)LayoutDisplayPageProvider.class,
				null,
				(serviceReference, emitter) -> {
					LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
						bundleContext.getService(serviceReference);

					try {
						emitter.emit(layoutDisplayPageProvider.getClassName());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				},
				new PropertyServiceReferenceComparator<>("service.ranking"));
		_layoutDisplayPageProviderByURLSeparatorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<LayoutDisplayPageProvider<?>>)
					(Class<?>)LayoutDisplayPageProvider.class,
				null,
				(serviceReference, emitter) -> {
					LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
						bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							layoutDisplayPageProvider.getURLSeparator());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				},
				new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	private ServiceTrackerMap<String, LayoutDisplayPageProvider<?>>
		_layoutDisplayPageProviderByClassNameServiceTrackerMap;
	private ServiceTrackerMap<String, LayoutDisplayPageProvider<?>>
		_layoutDisplayPageProviderByURLSeparatorServiceTrackerMap;

}