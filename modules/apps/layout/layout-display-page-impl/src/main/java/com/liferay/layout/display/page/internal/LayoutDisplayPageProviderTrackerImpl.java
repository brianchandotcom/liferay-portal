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

	public LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
		String className) {

		return _layoutDisplayPageProviderMap.getService(className);
	}

	public LayoutDisplayPageProvider<?>
		getLayoutDisplayPageProviderByURLSeparator(String urlSeparator) {

		return _layoutDisplayPageProviderByURLSeparatorMap.getService(
			urlSeparator);
	}

	public List<LayoutDisplayPageProvider<?>> getLayoutDisplayPageProviders() {
		return new ArrayList(_layoutDisplayPageProviderMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_layoutDisplayPageProviderMap =
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
				});
		_layoutDisplayPageProviderByURLSeparatorMap =
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
				});
	}

	private ServiceTrackerMap<String, LayoutDisplayPageProvider<?>>
		_layoutDisplayPageProviderByURLSeparatorMap;
	private ServiceTrackerMap<String, LayoutDisplayPageProvider<?>>
		_layoutDisplayPageProviderMap;

}