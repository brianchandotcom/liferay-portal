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

import java.security.SecureRandom;

/**
 * @author Tomas Polesovsky
 */
public class SecureHashCodeCacheKeyGenerator extends BaseCacheKeyGenerator {

	public SecureHashCodeCacheKeyGenerator() {
		_secret = new SecureRandom().nextLong();
	}

	@Override
	public CacheKeyGenerator clone() {
		SecureHashCodeCacheKeyGenerator result =
			new SecureHashCodeCacheKeyGenerator();

		result._secret = this._secret;

		return result;
	}

	@Override
	public Long getCacheKey(String key) {
		long hashCode = 0;

		for (int i = 0; i < key.length(); i++) {
			hashCode = 31 * hashCode + key.charAt(i);
			hashCode ^= _secret;
		}

		return hashCode;
	}

	@Override
	public Long getCacheKey(String[] keys) {
		long hashCode = 0;

		for (String key : keys) {
			if (key == null) {
				continue;
			}

			for (int i = 0; i < key.length(); i++) {
				hashCode = 31 * hashCode + key.charAt(i);
				hashCode ^= _secret;
			}
		}

		return hashCode;
	}

	@Override
	public Long getCacheKey(StringBundler sb) {
		long hashCode = 0;

		for (int i = 0; i < sb.index(); i++) {
			String key = sb.stringAt(i);

			for (int j = 0; j < key.length(); j++) {
				hashCode = 31 * hashCode + key.charAt(j);
				hashCode ^= _secret;
			}
		}

		return hashCode;
	}

	private long _secret;

}