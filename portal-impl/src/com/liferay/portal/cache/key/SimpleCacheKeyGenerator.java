/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.key;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;

/**
 * @author Shuyang Zhou
 */
public class SimpleCacheKeyGenerator extends BaseCacheKeyGenerator {

	@Override
	public CacheKeyGenerator clone() {
		return new SimpleCacheKeyGenerator();
	}

	@Override
	public String getCacheKey(String key) {
		return key;
	}

	@Override
	public String getCacheKey(String[] keys) {
		StringBundler sb = new StringBundler(keys);

		return sb.toString();
	}

	@Override
	public String getCacheKey(StringBundler sb) {
		return sb.toString();
	}

}