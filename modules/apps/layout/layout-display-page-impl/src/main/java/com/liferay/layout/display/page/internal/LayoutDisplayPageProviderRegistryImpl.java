/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page.internal;

import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
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
@Component(service = LayoutDisplayPageProviderRegistry.class)
public class LayoutDisplayPageProviderRegistryImpl
	implements LayoutDisplayPageProviderRegistry {

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