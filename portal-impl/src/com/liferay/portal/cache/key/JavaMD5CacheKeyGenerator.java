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
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.StringBundler;

import java.security.NoSuchAlgorithmException;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Vilmos Papp
 */
public class JavaMD5CacheKeyGenerator extends MessageDigestCacheKeyGenerator {

	public JavaMD5CacheKeyGenerator() throws NoSuchAlgorithmException {
		this(-1);
	}

	public JavaMD5CacheKeyGenerator(int maxLength)
		throws NoSuchAlgorithmException {

		super(Digester.MD5);

		_maxLength = maxLength;
	}

	@Override
	public CacheKeyGenerator clone() {
		try {
			return new JavaMD5CacheKeyGenerator(_maxLength);
		}
		catch (NoSuchAlgorithmException nsae) {
			throw new IllegalStateException(nsae.getMessage(), nsae);
		}
	}

	@Override
	public String getCacheKey(String key) {
		if ((_maxLength > -1) && (key.length() < _maxLength)) {
			return key;
		}

		return (String)super.getCacheKey(key);
	}

	@Override
	public String getCacheKey(StringBundler sb) {
		if ((_maxLength > -1) && (sb.length() < _maxLength)) {
			return sb.toString();
		}

		return (String)super.getCacheKey(sb);
	}

	public void setMaxLength(int maxLength) {
		_maxLength = maxLength;
	}

	private int _maxLength = -1;

}