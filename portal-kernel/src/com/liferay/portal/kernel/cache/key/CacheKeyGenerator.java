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

package com.liferay.portal.kernel.cache.key;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public interface CacheKeyGenerator extends Cloneable {

	public CacheKeyGenerator append(String key);

	public CacheKeyGenerator append(String[] keys);

	public CacheKeyGenerator append(StringBundler sb);

	public CacheKeyGenerator clone();

	public Serializable finish();

	public Serializable getCacheKey(String key);

	public Serializable getCacheKey(String[] keys);

	public Serializable getCacheKey(StringBundler sb);

	public boolean isCallingGetCacheKeyThreadSafe();

}