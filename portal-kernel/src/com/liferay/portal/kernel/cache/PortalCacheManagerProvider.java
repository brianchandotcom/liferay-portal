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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tina Tian
 */
public class PortalCacheManagerProvider {

	public static PortalCacheManager<? extends Serializable, ?>
		getPortalCacheManager(String portalCacheManagerName) {

		return _dynamicPortalCacheManagers.computeIfAbsent(
			portalCacheManagerName,
			key -> new DynamicPortalCacheManager<>(key));
	}

	public static Collection<PortalCacheManager<? extends Serializable, ?>>
		getPortalCacheManagers() {

		return Collections.unmodifiableCollection(
			_dynamicPortalCacheManagers.values());
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final Map
		<String, DynamicPortalCacheManager<? extends Serializable, ?>>
			_dynamicPortalCacheManagers = new ConcurrentHashMap<>();
	private static final ServiceTracker
		<PortalCacheManager<? extends Serializable, ?>,
		 DynamicPortalCacheManager<? extends Serializable, ?>> _serviceTracker;

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			(Class<PortalCacheManager<? extends Serializable, ?>>)
				(Class<?>)PortalCacheManager.class,
			new PortalCacheProviderServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static class PortalCacheProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortalCacheManager<? extends Serializable, ?>,
			 DynamicPortalCacheManager<? extends Serializable, ?>> {

		@Override
		public DynamicPortalCacheManager<? extends Serializable, ?>
			addingService(
				ServiceReference<PortalCacheManager<? extends Serializable, ?>>
					serviceReference) {

			PortalCacheManager<? extends Serializable, ?> portalCacheManager =
				_bundleContext.getService(serviceReference);

			DynamicPortalCacheManager<? extends Serializable, ?>
				dynamicPortalCacheManager =
					_dynamicPortalCacheManagers.computeIfAbsent(
						portalCacheManager.getPortalCacheManagerName(),
						key -> new DynamicPortalCacheManager<>(key));

			dynamicPortalCacheManager.setPortalCacheManager(portalCacheManager);

			return dynamicPortalCacheManager;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortalCacheManager<? extends Serializable, ?>>
				serviceReference,
			DynamicPortalCacheManager<? extends Serializable, ?>
				dynamicPortalCacheManager) {
		}

		@Override
		public void removedService(
			ServiceReference<PortalCacheManager<? extends Serializable, ?>>
				serviceReference,
			DynamicPortalCacheManager<? extends Serializable, ?>
				dynamicPortalCacheManager) {

			_bundleContext.ungetService(serviceReference);

			dynamicPortalCacheManager.setPortalCacheManager(null);
		}

	}

}