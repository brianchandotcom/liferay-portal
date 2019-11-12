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

package com.liferay.document.library.internal.upgrade.v1_0_0;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Dictionary;

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
 * @author Sergio González
 */
@Component(service = {})
public class StoreProvider {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, Store.class, new StoreServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private BundleContext _bundleContext;

	@Reference
	private Props _props;

	private ServiceRegistration<Store> _serviceRegistration;
	private ServiceTracker<Store, Store> _serviceTracker;

	private class StoreServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Store, Store> {

		@Override
		public Store addingService(ServiceReference<Store> serviceReference) {
			String storeType = (String)serviceReference.getProperty(
				"store.type");

			if ((storeType == null) ||
				!storeType.equals(_props.get(PropsKeys.DL_STORE_IMPL))) {

				return null;
			}

			Store store = _bundleContext.getService(serviceReference);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("dl.store.upgrade", "true");

			_serviceRegistration = _bundleContext.registerService(
				Store.class, store, properties);

			return store;
		}

		@Override
		public void modifiedService(
			ServiceReference<Store> serviceReference, Store store) {

			removedService(serviceReference, store);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Store> serviceReference, Store store) {

			_bundleContext.ungetService(serviceReference);

			try {
				_serviceRegistration.unregister();
			}
			catch (IllegalStateException ise) {
			}
		}

	}

}