/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.history;

import com.liferay.change.tracking.spi.history.CTCollectionHistoryProvider;
import com.liferay.change.tracking.spi.history.CTCollectionHistoryProviderRegistry;
import com.liferay.change.tracking.spi.history.DefaultCTCollectionHistoryProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = CTCollectionHistoryProviderRegistry.class)
public class CTCollectionHistoryProviderRegistryImpl
	implements CTCollectionHistoryProviderRegistry {

	@Override
	public CTCollectionHistoryProvider<?> getCTCollectionHistoryProvider(
		long classNameId) {

		ClassName className = _classNameLocalService.fetchClassName(
			classNameId);

		if (className == null) {
			return _getDefaultCTCollectionHistoryProvider();
		}

		CTCollectionHistoryProvider<?> ctCollectionHistoryProvider =
			_serviceTrackerMap.getService(className.getValue());

		if (ctCollectionHistoryProvider == null) {
			return _getDefaultCTCollectionHistoryProvider();
		}

		return ctCollectionHistoryProvider;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<CTCollectionHistoryProvider<?>>)
				(Class<?>)CTCollectionHistoryProvider.class,
			null,
			(serviceReference, emitter) -> {
				CTCollectionHistoryProvider<?> ctCollectionHistoryProvider =
					bundleContext.getService(serviceReference);

				try {
					emitter.emit(
						ctCollectionHistoryProvider.getModelClass(
						).getName());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	private CTCollectionHistoryProvider<?>
		_getDefaultCTCollectionHistoryProvider() {

		if (_defaultctCollectionHistoryProvider == null) {
			_defaultctCollectionHistoryProvider =
				new DefaultCTCollectionHistoryProvider<>();
		}

		return _defaultctCollectionHistoryProvider;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private CTCollectionHistoryProvider<?> _defaultctCollectionHistoryProvider;
	private volatile ServiceTrackerMap<String, CTCollectionHistoryProvider<?>>
		_serviceTrackerMap;

}