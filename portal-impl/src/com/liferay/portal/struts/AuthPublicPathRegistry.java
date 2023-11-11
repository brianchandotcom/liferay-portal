/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.struts;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class AuthPublicPathRegistry {

	public static boolean contains(String path) {
		if (!_paths.contains(path) && !_serviceTrackerMap.containsKey(path)) {
			return false;
		}

		return true;
	}

	public static void register(String... paths) {
		Collections.addAll(_paths, paths);
	}

	public static void unregister(String... paths) {
		_paths.removeAll(Arrays.asList(paths));
	}

	private static final Set<String> _paths = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private static final ServiceTrackerMap<String, Object> _serviceTrackerMap =
		ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(), Object.class,
			"auth.public.path");

}