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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Manuel de la Peña
 * @author Edward C. Han
 */
public class StoreFactory {

	public static StoreFactory getInstance() {
		if (_storeFactory == null) {
			_storeFactory = new StoreFactory();
		}

		return _storeFactory;
	}

	public Store getStore() {
		Store store = _defaultStore;

		if (store == null) {
			throw new IllegalStateException(
				"Store is not available. Caller service needs to wait for " +
					"store factory with \"dl.store.impl.enabled=true\".");
		}

		return store;
	}

	private static volatile Store _defaultStore;
	private static StoreFactory _storeFactory;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceTracker<Store, ServiceRegistration<?>> serviceTracker =
			new ServiceTracker<>(
				bundleContext,
				SystemBundleUtil.createFilter(
					"(&(ct.aware=true)(objectClass=" + Store.class.getName() +
						"))"),
				new ServiceTrackerCustomizer<Store, ServiceRegistration<?>>() {

					@Override
					public ServiceRegistration<?> addingService(
						ServiceReference<Store> serviceReference) {

						String storeType = GetterUtil.getString(
							serviceReference.getProperty("store.type"));

						if (!StringUtil.equals(
								storeType, PropsValues.DL_STORE_IMPL)) {

							return null;
						}

						Store store = bundleContext.getService(
							serviceReference);

						_defaultStore = store;

						return bundleContext.registerService(
							StoreFactory.class,
							new StoreFactory() {

								@Override
								public Store getStore() {
									return store;
								}

							},
							MapUtil.singletonDictionary(
								"dl.store.impl.enabled", "true"));
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

		serviceTracker.open();
	}

}