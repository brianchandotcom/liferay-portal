/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.BasePreferencesImpl;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class PortletPreferencesLocalUtil {

	public static final String CACHE_NAME =
		PortletPreferencesLocalUtil.class.getName();

	protected static void clearPreferencesPool() {
		_portalCache.removeAll();
	}

	protected static void clearPreferencesPool(long ownerId, int ownerType) {
		Serializable key = _encodeKey(ownerId, ownerType);

		_portalCache.remove(key);
	}

	protected static Map<String, BasePreferencesImpl> getPreferencesPool(
		long ownerId, int ownerType) {

		Serializable key = _encodeKey(ownerId, ownerType);

		Map<String, BasePreferencesImpl> preferencesPool =
			(Map<String, BasePreferencesImpl>)_portalCache.get(key);

		if (preferencesPool == null) {
			preferencesPool =
				new ConcurrentHashMap<String, BasePreferencesImpl>();

			_portalCache.put(key, preferencesPool);
		}

		return preferencesPool;
	}

	private static Serializable _encodeKey(long ownerId, int ownerType) {
		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				CACHE_NAME);

		cacheKeyGenerator.append(StringUtil.toHexString(ownerId));
		cacheKeyGenerator.append(StringPool.POUND);
		cacheKeyGenerator.append(StringUtil.toHexString(ownerType));

		return cacheKeyGenerator.finish();
	}

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		CACHE_NAME);

}