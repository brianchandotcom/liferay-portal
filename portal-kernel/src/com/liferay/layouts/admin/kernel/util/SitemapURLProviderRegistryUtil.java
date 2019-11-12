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

package com.liferay.layouts.admin.kernel.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author     Eduardo García
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 *             com.liferay.layout.admin.kernel.util.SitemapURLProviderRegistryUtil}
 */
@Deprecated
public class SitemapURLProviderRegistryUtil {

	public static SitemapURLProvider getSitemapURLProvider(String className) {
		return _sitemapURLProviderRegistryUtil._getSitemapURLProvider(
			className);
	}

	public static List<SitemapURLProvider> getSitemapURLProviders() {
		return _sitemapURLProviderRegistryUtil._getSitemapURLProviders();
	}

	public static void register(SitemapURLProvider sitemapURLProvider) {
		_sitemapURLProviderRegistryUtil._register(sitemapURLProvider);
	}

	public static void unregister(
		List<SitemapURLProvider> sitemapURLProviders) {

		for (SitemapURLProvider sitemapURLProvider : sitemapURLProviders) {
			unregister(sitemapURLProvider);
		}
	}

	public static void unregister(SitemapURLProvider sitemapURLProvider) {
		_sitemapURLProviderRegistryUtil._unregister(sitemapURLProvider);
	}

	private SitemapURLProviderRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			SitemapURLProvider.class,
			new SitemapURLProviderServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private SitemapURLProvider _getSitemapURLProvider(String className) {
		return _sitemapURLProviders.get(className);
	}

	private List<SitemapURLProvider> _getSitemapURLProviders() {
		Collection<SitemapURLProvider> values = _sitemapURLProviders.values();

		return ListUtil.fromCollection(values);
	}

	private void _register(SitemapURLProvider sitemapURLProvider) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<SitemapURLProvider> serviceRegistration =
			registry.registerService(
				SitemapURLProvider.class, sitemapURLProvider);

		_serviceRegistrations.put(sitemapURLProvider, serviceRegistration);
	}

	private void _unregister(SitemapURLProvider sitemapURLProvider) {
		ServiceRegistration<SitemapURLProvider> serviceRegistration =
			_serviceRegistrations.remove(sitemapURLProvider);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final SitemapURLProviderRegistryUtil
		_sitemapURLProviderRegistryUtil = new SitemapURLProviderRegistryUtil();

	private final ServiceRegistrationMap<SitemapURLProvider>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final ServiceTracker<SitemapURLProvider, SitemapURLProvider>
		_serviceTracker;
	private final Map<String, SitemapURLProvider> _sitemapURLProviders =
		new ConcurrentHashMap<>();

	private class SitemapURLProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SitemapURLProvider, SitemapURLProvider> {

		@Override
		public SitemapURLProvider addingService(
			ServiceReference<SitemapURLProvider> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			SitemapURLProvider sitemapURLProvider = registry.getService(
				serviceReference);

			_sitemapURLProviders.put(
				sitemapURLProvider.getClassName(), sitemapURLProvider);

			return sitemapURLProvider;
		}

		@Override
		public void modifiedService(
			ServiceReference<SitemapURLProvider> serviceReference,
			SitemapURLProvider sitemapURLProvider) {
		}

		@Override
		public void removedService(
			ServiceReference<SitemapURLProvider> serviceReference,
			SitemapURLProvider sitemapURLProvider) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_sitemapURLProviders.remove(sitemapURLProvider.getClassName());
		}

	}

}