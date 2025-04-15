/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;

/**
 * @author Rachael Koestartyo
 */
public class SegmentsEntryCacheUtil {

	public static void clear() {
		_portalCache.removeAll();
	}

	public static long[] getSegmentsEntryIds(String userId) {
		return _portalCache.get(_generateCacheKey(userId));
	}

	public static void putSegmentsEntryIds(
		String userId, long[] segmentsEntryIds) {

		_portalCache.put(_generateCacheKey(userId), segmentsEntryIds, 1800);
	}

	private static String _generateCacheKey(String userId) {
		return _CACHE_PREFIX + userId;
	}

	private static final String _CACHE_PREFIX = "segments-entry-";

	private static final PortalCache<String, long[]> _portalCache =
		PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.MULTI_VM,
			SegmentsEntryCacheUtil.class.getName());

}