/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.service.util;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portlet.PortalPreferenceKey;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class PortalPreferencesCacheUtil {

	public static Map<PortalPreferenceKey, String[]> get(
		long portalPreferencesId) {

		return _portalCache.get(portalPreferencesId);
	}

	public static void put(
		long portalPreferencesId,
		Map<PortalPreferenceKey, String[]> preferenceMap) {

		if (preferenceMap.isEmpty()) {
			preferenceMap = Collections.emptyMap();
		}
		else {
			preferenceMap = Collections.unmodifiableMap(
				new HashMap<>(preferenceMap));
		}

		_portalCache.put(portalPreferencesId, preferenceMap);
	}

	private PortalPreferencesCacheUtil() {
	}

	private static final PortalCache<Long, Map<PortalPreferenceKey, String[]>>
		_portalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.MULTI_VM,
			PortalPreferencesCacheUtil.class.getName());

}