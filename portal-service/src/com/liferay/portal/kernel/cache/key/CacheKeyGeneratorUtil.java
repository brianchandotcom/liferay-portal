/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cache.key;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class CacheKeyGeneratorUtil {

	public static CacheKeyGenerator getCacheKeyGenerator() {
		return getCacheKeyGenerator(null);
	}

	public static CacheKeyGenerator getCacheKeyGenerator(String cacheName) {
		PortalRuntimePermission.checkGetBeanProperty(
			CacheKeyGeneratorUtil.class);

		ThreadLocalCache<CacheKeyGenerator> threadLocalCacheKeyGenerators =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.ETERNAL, CacheKeyGeneratorUtil.class.getName());

		CacheKeyGenerator cacheKeyGenerator = threadLocalCacheKeyGenerators.get(
			cacheName);

		if (cacheKeyGenerator != null) {
			return cacheKeyGenerator;
		}

		cacheKeyGenerator = _cacheKeyGenerators.get(cacheName);

		if (cacheKeyGenerator == null) {
			cacheKeyGenerator = _defaultCacheKeyGenerator;
		}

		cacheKeyGenerator = cacheKeyGenerator.clone();

		threadLocalCacheKeyGenerators.put(cacheName, cacheKeyGenerator);

		return cacheKeyGenerator;
	}

	public void setCacheKeyGenerators(
		Map<String, CacheKeyGenerator> cacheKeyGenerators) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_cacheKeyGenerators = cacheKeyGenerators;
	}

	public void setDefaultCacheKeyGenerator(
		CacheKeyGenerator defaultCacheKeyGenerator) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_defaultCacheKeyGenerator = defaultCacheKeyGenerator;
	}

	private static Map<String, CacheKeyGenerator> _cacheKeyGenerators =
		new HashMap<String, CacheKeyGenerator>();
	private static CacheKeyGenerator _defaultCacheKeyGenerator;

}