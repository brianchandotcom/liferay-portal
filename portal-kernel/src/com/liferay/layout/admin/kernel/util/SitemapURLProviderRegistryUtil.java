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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;

/**
 * @author Eduardo García
 */
public class SitemapURLProviderRegistryUtil {

	public static SitemapURLProvider getSitemapURLProvider(String className) {
		return _sitemapURLProvidersServiceTrackerMap.getService(className);
	}

	public static List<SitemapURLProvider> getSitemapURLProviders() {
		Set<String> classNames = _sitemapURLProvidersServiceTrackerMap.keySet();

		List<SitemapURLProvider> sitemapURLProviders = new ArrayList<>(
			classNames.size());

		for (String className : classNames) {
			sitemapURLProviders.add(
				_sitemapURLProvidersServiceTrackerMap.getService(className));
		}

		return sitemapURLProviders;
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, SitemapURLProvider>
		_sitemapURLProvidersServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, SitemapURLProvider.class, null,
				(serviceReference, emitter) -> {
					SitemapURLProvider sitemapURLProvider =
						_bundleContext.getService(serviceReference);

					emitter.emit(sitemapURLProvider.getClassName());
				});

}