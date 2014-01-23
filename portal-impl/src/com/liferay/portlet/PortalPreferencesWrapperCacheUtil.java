/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Shuyang Zhou
 */
public class PortalPreferencesWrapperCacheUtil {

	public static final String CACHE_NAME =
		PortalPreferencesWrapperCacheUtil.class.getName();

	public static PortalPreferencesWrapper get(long ownerId, int ownerType) {
		String cacheKey = StringUtil.toHexString(ownerId).concat(
			StringUtil.toHexString(ownerType));

		return _portalPreferencesWrapperPortalCache.get(cacheKey);
	}

	public static void put(
		long ownerId, int ownerType,
		PortalPreferencesWrapper portalPreferencesWrapper) {

		String cacheKey = StringUtil.toHexString(ownerId).concat(
			StringUtil.toHexString(ownerType));

		_portalPreferencesWrapperPortalCache.putQuiet(
			cacheKey, portalPreferencesWrapper);
	}

	public static void remove(long ownerId, int ownerType) {
		String cacheKey = StringUtil.toHexString(ownerId).concat(
			StringUtil.toHexString(ownerType));

		_portalPreferencesWrapperPortalCache.remove(cacheKey);
	}

	private static PortalCache<String, PortalPreferencesWrapper>
		_portalPreferencesWrapperPortalCache = MultiVMPoolUtil.getCache(
			CACHE_NAME);

}