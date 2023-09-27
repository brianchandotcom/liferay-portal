/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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