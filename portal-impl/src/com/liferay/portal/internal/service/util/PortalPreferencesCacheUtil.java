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