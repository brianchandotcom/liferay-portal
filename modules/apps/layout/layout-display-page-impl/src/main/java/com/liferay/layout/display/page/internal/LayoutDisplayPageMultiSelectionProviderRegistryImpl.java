/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page.internal;

import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProviderRegistry;
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
@Component(service = LayoutDisplayPageMultiSelectionProviderRegistry.class)
public class LayoutDisplayPageMultiSelectionProviderRegistryImpl
	implements LayoutDisplayPageMultiSelectionProviderRegistry {

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