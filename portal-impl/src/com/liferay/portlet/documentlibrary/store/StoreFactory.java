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
import com.liferay.portal.kernel.util.ServiceProxyFactory;
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
		return _store;
	}

	private static volatile Store _store =
		ServiceProxyFactory.newServiceTrackedInstance(
			Store.class, StoreFactory.class, "_store", "(default=true)", true);
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

						_store = bundleContext.getService(serviceReference);

						return bundleContext.registerService(
							Store.class, _store,
							MapUtil.singletonDictionary("default", "true"));
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