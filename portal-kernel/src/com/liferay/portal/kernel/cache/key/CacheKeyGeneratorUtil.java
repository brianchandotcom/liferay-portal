/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache.key;

import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class CacheKeyGeneratorUtil {

	public static CacheKeyGenerator getCacheKeyGenerator(String cacheName) {
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

		_cacheKeyGenerators = cacheKeyGenerators;
	}

	public void setDefaultCacheKeyGenerator(
		CacheKeyGenerator defaultCacheKeyGenerator) {

		_defaultCacheKeyGenerator = defaultCacheKeyGenerator;
	}

	private static Map<String, CacheKeyGenerator> _cacheKeyGenerators =
		new HashMap<>();
	private static CacheKeyGenerator _defaultCacheKeyGenerator;

}