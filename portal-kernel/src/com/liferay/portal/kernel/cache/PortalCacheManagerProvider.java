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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class PortalCacheManagerProvider {

	public static PortalCacheManager<? extends Serializable, ?>
		getPortalCacheManager(String portalCacheManagerName) {

		return _instance._getPortalCacheManager(portalCacheManagerName);
	}

	public static Collection<PortalCacheManager<? extends Serializable, ?>>
		getPortalCacheManagers() {

		return _instance._getPortalCacheManagers();
	}

	private PortalCacheManagerProvider() {
		_portalCacheManagers = new ConcurrentHashMap<>();

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<PortalCacheManager<? extends Serializable, ?>>)(Class<?>)
				PortalCacheManager.class,
			new PortalCacheProviderServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private PortalCacheManager<? extends Serializable, ?>
		_getPortalCacheManager(String portalCacheManagerName) {

		return _portalCacheManagers.get(portalCacheManagerName);
	}

	private Collection<PortalCacheManager<? extends Serializable, ?>>
		_getPortalCacheManagers() {

		return Collections.unmodifiableCollection(
			_portalCacheManagers.values());
	}

	private static final PortalCacheManagerProvider _instance =
		new PortalCacheManagerProvider();

	private final
		Map<String, PortalCacheManager<? extends Serializable, ?>>
			_portalCacheManagers;
	private final
		ServiceTracker
			<PortalCacheManager<? extends Serializable, ?>,
			 PortalCacheManager<? extends Serializable, ?>> _serviceTracker;

	private class PortalCacheProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortalCacheManager<? extends Serializable, ?>,
			 PortalCacheManager<? extends Serializable, ?>> {

		@Override
		public PortalCacheManager<? extends Serializable, ?> addingService(
			ServiceReference<PortalCacheManager<? extends Serializable, ?>>
				serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PortalCacheManager<?, ?> portalCacheManager = registry.getService(
				serviceReference);

			_portalCacheManagers.put(
				portalCacheManager.getPortalCacheManagerName(),
				portalCacheManager);

			return portalCacheManager;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortalCacheManager<? extends Serializable, ?>>
				serviceReference,
			PortalCacheManager<? extends Serializable, ?> portalCacheManager) {
		}

		@Override
		public void removedService(
			ServiceReference<PortalCacheManager<? extends Serializable, ?>>
				serviceReference,
			PortalCacheManager<? extends Serializable, ?> portalCacheManager) {

			_portalCacheManagers.remove(
				portalCacheManager.getPortalCacheManagerName());
		}

	}

}