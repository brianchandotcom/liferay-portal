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

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public interface CacheKeyGenerator extends Cloneable {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #append(StringBundler)}
	 */
	@Deprecated
	public CacheKeyGenerator append(
		com.liferay.portal.kernel.util.StringBundler sb);

	public CacheKeyGenerator append(String key);

	public CacheKeyGenerator append(String[] keys);

	public default CacheKeyGenerator append(StringBundler sb) {
		return append(sb.getStrings());
	}

	public CacheKeyGenerator clone();

	public Serializable finish();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getCacheKey(StringBundler)}
	 */
	@Deprecated
	public Serializable getCacheKey(
		com.liferay.portal.kernel.util.StringBundler sb);

	public Serializable getCacheKey(String key);

	public Serializable getCacheKey(String[] keys);

	public default Serializable getCacheKey(StringBundler sb) {
		return getCacheKey(sb.getStrings());
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public boolean isCallingGetCacheKeyThreadSafe();

}