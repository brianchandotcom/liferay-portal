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

package com.liferay.portal.kernel.search;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eudaldo Alonso
 */
public class OpenSearchRegistryUtil {

	public static OpenSearch getOpenSearch(Class<?> clazz) {
		return _openSearchRegistryUtil._openSearchInstances.get(
			clazz.getName());
	}

	public static OpenSearch getOpenSearch(String className) {
		return _openSearchRegistryUtil._openSearchInstances.get(className);
	}

	public static List<OpenSearch> getOpenSearchInstances() {
		List<OpenSearch> openSearchInstances = new ArrayList<>(
			_openSearchRegistryUtil._openSearchInstances.values());

		return Collections.unmodifiableList(openSearchInstances);
	}

	private OpenSearchRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			OpenSearch.class, new OpenSearchServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final OpenSearchRegistryUtil _openSearchRegistryUtil =
		new OpenSearchRegistryUtil();

	private final Map<String, OpenSearch> _openSearchInstances =
		new ConcurrentHashMap<>();
	private final ServiceTracker<OpenSearch, OpenSearch> _serviceTracker;

	private class OpenSearchServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<OpenSearch, OpenSearch> {

		@Override
		public OpenSearch addingService(
			ServiceReference<OpenSearch> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			OpenSearch openSearch = registry.getService(serviceReference);

			_openSearchInstances.put(openSearch.getClassName(), openSearch);

			return openSearch;
		}

		@Override
		public void modifiedService(
			ServiceReference<OpenSearch> serviceReference,
			OpenSearch openSearch) {
		}

		@Override
		public void removedService(
			ServiceReference<OpenSearch> serviceReference,
			OpenSearch openSearch) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_openSearchInstances.remove(openSearch.getClassName());
		}

	}

}