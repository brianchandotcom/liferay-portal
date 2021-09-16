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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * @author Eudaldo Alonso
 */
public class OpenSearchRegistryUtil {

	public static OpenSearch getOpenSearch(Class<?> clazz) {
		return getOpenSearch(clazz.getName());
	}

	public static OpenSearch getOpenSearch(String className) {
		return _openSearchs.getService(className);
	}

	public static List<OpenSearch> getOpenSearchInstances() {
		List<OpenSearch> openSearchInstances = new ArrayList<>(
			_openSearchs.values());

		return Collections.unmodifiableList(openSearchInstances);
	}

	private OpenSearchRegistryUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, OpenSearch> _openSearchs =
		ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, OpenSearch.class, null,
			(serviceReference, emitter) -> {
				OpenSearch openSearch = _bundleContext.getService(
					serviceReference);

				emitter.emit(openSearch.getClassName());

				_bundleContext.ungetService(serviceReference);
			});

}