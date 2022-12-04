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

package com.liferay.change.tracking.store.internal;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.change.tracking.store.CTStoreFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

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
 * @author Shuyang Zhou
 */
@Component(service = {})
public class CTStoreRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, Store.class,
			new ServiceTrackerCustomizer<Store, ServiceRegistration<?>>() {

				@Override
				public ServiceRegistration<?> addingService(
					ServiceReference<Store> serviceReference) {

					if (GetterUtil.getBoolean(
							serviceReference.getProperty("ct.aware"))) {

						return null;
					}

					String storeType = String.valueOf(
						serviceReference.getProperty("store.type"));

					return bundleContext.registerService(
						Store.class,
						_ctStoreFactory.createCTStore(
							bundleContext.getService(serviceReference),
							storeType),
						HashMapDictionaryBuilder.<String, Object>put(
							"ct.aware", true
						).put(
							"store.type", storeType
						).build());
				}

				@Override
				public void modifiedService(
					ServiceReference<Store> serviceReference,
					ServiceRegistration<?> serviceRegistration) {
				}

				@Override
				public void removedService(
					ServiceReference<Store> serviceReference,
					ServiceRegistration<?> serviceRegistration) {

					serviceRegistration.unregister();

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference
	private CTStoreFactory _ctStoreFactory;

	private ServiceTracker<Store, ServiceRegistration<?>> _serviceTracker;

}