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

package com.liferay.layout.admin.kernel.util;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Eduardo García
 */
public class SitemapURLProviderRegistryUtil {

	public static SitemapURLProvider getSitemapURLProvider(String className) {
		return _sitemapURLProvidersServiceTrackerMap.getService(className);
	}

	public static List<SitemapURLProvider> getSitemapURLProviders() {
		Set<String> keySet = _sitemapURLProvidersServiceTrackerMap.keySet();

		List<SitemapURLProvider> sitemapURLProviders = new ArrayList<>(
			keySet.size());

		for (String key : keySet) {
			sitemapURLProviders.add(
				_sitemapURLProvidersServiceTrackerMap.getService(key));
		}

		return sitemapURLProviders;
	}

	public static void register(SitemapURLProvider sitemapURLProvider) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<SitemapURLProvider> serviceRegistration =
			registry.registerService(
				SitemapURLProvider.class, sitemapURLProvider);

		_serviceRegistrations.put(sitemapURLProvider, serviceRegistration);
	}

	public static void unregister(
		List<SitemapURLProvider> sitemapURLProviders) {

		for (SitemapURLProvider sitemapURLProvider : sitemapURLProviders) {
			unregister(sitemapURLProvider);
		}
	}

	public static void unregister(SitemapURLProvider sitemapURLProvider) {
		ServiceRegistration<SitemapURLProvider> serviceRegistration =
			_serviceRegistrations.remove(sitemapURLProvider);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final ServiceRegistrationMap<SitemapURLProvider>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();

	private static final ServiceTrackerMap<String, SitemapURLProvider>
		_sitemapURLProvidersServiceTrackerMap =
			ServiceTrackerCollections.openSingleValueMap(
				SitemapURLProvider.class, null,
				(serviceReference, emitter) -> {
					Registry registry = RegistryUtil.getRegistry();

					SitemapURLProvider sitemapURLProvider = registry.getService(
						serviceReference);

					emitter.emit(sitemapURLProvider.getClassName());
				});

}