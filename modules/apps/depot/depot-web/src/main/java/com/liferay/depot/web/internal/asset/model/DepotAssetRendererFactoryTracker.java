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

package com.liferay.depot.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = DepotAssetRendererFactoryTracker.class)
public class DepotAssetRendererFactoryTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			(Class<AssetRendererFactory<?>>)
				(Class<?>)AssetRendererFactory.class,
			new DepotAssetRendererFactoryServiceTrackerCustomizer(
				bundleContext, _serviceRegistrations));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		for (ServiceRegistration<AssetRendererFactory<?>> serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Reference
	private DepotApplicationController _depotApplicationController;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private final Map
		<ServiceReference<AssetRendererFactory<?>>,
		 ServiceRegistration<AssetRendererFactory<?>>> _serviceRegistrations =
			new ConcurrentHashMap<>();
	private ServiceTracker<AssetRendererFactory<?>, AssetRendererFactory<?>>
		_serviceTracker;

	private class DepotAssetRendererFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AssetRendererFactory<?>, AssetRendererFactory<?>> {

		public DepotAssetRendererFactoryServiceTrackerCustomizer(
			BundleContext bundleContext,
			Map
				<ServiceReference<AssetRendererFactory<?>>,
				 ServiceRegistration<AssetRendererFactory<?>>>
					serviceRegistrations) {

			_bundleContext = bundleContext;
			_serviceRegistrations = serviceRegistrations;
		}

		@Override
		public AssetRendererFactory<?> addingService(
			ServiceReference<AssetRendererFactory<?>> serviceReference) {

			AssetRendererFactory<?> assetRendererFactory =
				_bundleContext.getService(serviceReference);

			if (assetRendererFactory instanceof
					DepotAssetRendererFactoryWrapper) {

				return assetRendererFactory;
			}

			Dictionary<String, Object>
				depotAssetRendererFactoryWrapperProperties =
					new HashMapDictionary<>();

			for (String key : serviceReference.getPropertyKeys()) {
				depotAssetRendererFactoryWrapperProperties.put(
					key, serviceReference.getProperty(key));
			}

			Integer serviceRanking = (Integer)serviceReference.getProperty(
				"service.ranking:Integer");

			long defaultRanking = Integer.MAX_VALUE - 1000;

			if (serviceRanking != null) {
				defaultRanking += serviceRanking;
			}

			depotAssetRendererFactoryWrapperProperties.put(
				"service.ranking",
				(int)Math.min(Integer.MAX_VALUE, defaultRanking));

			AssetRendererFactory<?> depotAssetRendererFactory =
				new DepotAssetRendererFactoryWrapper(
					assetRendererFactory, _depotApplicationController,
					_depotEntryLocalService, _groupLocalService);

			ServiceRegistration<AssetRendererFactory<?>> serviceRegistration =
				_bundleContext.registerService(
					(Class<AssetRendererFactory<?>>)
						(Class<?>)AssetRendererFactory.class,
					depotAssetRendererFactory,
					depotAssetRendererFactoryWrapperProperties);

			_serviceRegistrations.put(serviceReference, serviceRegistration);

			return depotAssetRendererFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<AssetRendererFactory<?>> serviceReference,
			AssetRendererFactory<?> assetRendererFactory) {

			removedService(serviceReference, assetRendererFactory);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<AssetRendererFactory<?>> serviceReference,
			AssetRendererFactory<?> assetRendererFactory) {

			ServiceRegistration<AssetRendererFactory<?>> serviceRegistration =
				_serviceRegistrations.remove(serviceReference);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

		private final BundleContext _bundleContext;
		private final Map
			<ServiceReference<AssetRendererFactory<?>>,
			 ServiceRegistration<AssetRendererFactory<?>>>
				_serviceRegistrations;

	}

}