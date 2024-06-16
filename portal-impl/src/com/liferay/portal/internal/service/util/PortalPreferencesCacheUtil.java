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
			Map<PortalPreferenceKey, String[]> copiedPreferenceMap =
				new HashMap<>();

			for (Map.Entry<PortalPreferenceKey, String[]> entry :
					preferenceMap.entrySet()) {

				copiedPreferenceMap.put(
					_normalize(entry.getKey()), entry.getValue());
			}

			preferenceMap = Collections.unmodifiableMap(copiedPreferenceMap);
		}

		_portalCache.put(portalPreferencesId, preferenceMap);
	}

	private static PortalPreferenceKey _normalize(
		PortalPreferenceKey portalPreferenceKey) {

		PortalPreferenceKey normalizedPortalPreferenceKey =
			_normalizedPortalCache.get(portalPreferenceKey);

		if (normalizedPortalPreferenceKey == null) {
			_normalizedPortalCache.put(
				portalPreferenceKey, portalPreferenceKey);

			normalizedPortalPreferenceKey = portalPreferenceKey;
		}

		return normalizedPortalPreferenceKey;
	}

	private PortalPreferencesCacheUtil() {
	}

	private static final PortalCache<PortalPreferenceKey, PortalPreferenceKey>
		_normalizedPortalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.SINGLE_VM,
			PortalPreferencesCacheUtil.class.getName() + "#_normalized");
	private static final PortalCache<Long, Map<PortalPreferenceKey, String[]>>
		_portalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.MULTI_VM,
			PortalPreferencesCacheUtil.class.getName());

}