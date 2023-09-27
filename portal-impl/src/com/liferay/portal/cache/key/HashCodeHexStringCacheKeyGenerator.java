/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.key;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Preston Crary
 */
public class HashCodeHexStringCacheKeyGenerator extends BaseCacheKeyGenerator {

	@Override
	public CacheKeyGenerator clone() {
		return new HashCodeHexStringCacheKeyGenerator();
	}

	@Override
	public String getCacheKey(String key) {
		return StringUtil.toHexString(key.hashCode());
	}

	@Override
	public String getCacheKey(String[] keys) {
		int hashCode = 0;
		int weight = 1;

		for (int i = keys.length - 1; i >= 0; i--) {
			String s = keys[i];

			hashCode = (s.hashCode() * weight) + hashCode;

			for (int j = s.length(); j > 0; j--) {
				weight *= 31;
			}
		}

		return StringUtil.toHexString(hashCode);
	}

	@Override
	public String getCacheKey(StringBundler sb) {
		return getCacheKey(sb.getStrings());
	}

}