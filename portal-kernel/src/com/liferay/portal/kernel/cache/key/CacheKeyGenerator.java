/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache.key;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public interface CacheKeyGenerator extends Cloneable {

	public CacheKeyGenerator append(String key);

	public CacheKeyGenerator append(String[] keys);

	public default CacheKeyGenerator append(StringBundler sb) {
		return append(sb.getStrings());
	}

	public CacheKeyGenerator clone();

	public Serializable finish();

	public Serializable getCacheKey(String key);

	public Serializable getCacheKey(String[] keys);

	public default Serializable getCacheKey(StringBundler sb) {
		return getCacheKey(sb.getStrings());
	}

}