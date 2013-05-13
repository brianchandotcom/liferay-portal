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

package com.liferay.portal.cache.key;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseCacheKeyGenerator implements CacheKeyGenerator {

	@Override
	public CacheKeyGenerator append(String key) {
		keyBundler.append(key);

		return this;
	}

	@Override
	public CacheKeyGenerator append(String[] keys) {
		keyBundler.append(keys);

		return this;
	}

	@Override
	public CacheKeyGenerator append(StringBundler sb) {
		keyBundler.append(sb);

		return this;
	}

	@Override
	public abstract CacheKeyGenerator clone();

	@Override
	public Serializable finish() {
		Serializable cacheKey = getCacheKey(keyBundler);

		keyBundler.setIndex(0);

		return cacheKey;
	}

	@Override
	public boolean isCallingGetCacheKeyThreadSafe() {
		return _CALLING_GET_CACHE_KEY_THREAD_SAFE;
	}

	protected StringBundler keyBundler = new StringBundler();

	private static final boolean _CALLING_GET_CACHE_KEY_THREAD_SAFE = true;

}