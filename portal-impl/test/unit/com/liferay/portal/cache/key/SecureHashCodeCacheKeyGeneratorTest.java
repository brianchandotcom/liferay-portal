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

import com.liferay.portal.kernel.util.StringBundler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 */
public class SecureHashCodeCacheKeyGeneratorTest
	extends BaseCacheKeyGeneratorTestCase {

	@Override
	public void setUp() throws Exception {
		cacheKeyGenerator = new SecureHashCodeCacheKeyGenerator();
	}

	@Test
	public void testResultsDifferPerInstance() {
		SecureHashCodeCacheKeyGenerator cacheKeyGenerator1 =
			new SecureHashCodeCacheKeyGenerator();
		SecureHashCodeCacheKeyGenerator cacheKeyGenerator2 =
			new SecureHashCodeCacheKeyGenerator();

		long hashCode1 = cacheKeyGenerator1.getCacheKey("key1");
		long hashCode2 = cacheKeyGenerator2.getCacheKey("key1");

		Assert.assertNotEquals(hashCode1, hashCode2);

		hashCode1 = cacheKeyGenerator1.getCacheKey(
			new String[] {"key1", "key2"});
		hashCode2 = cacheKeyGenerator2.getCacheKey(
			new String[] {"key1", "key2"});

		Assert.assertNotEquals(hashCode1, hashCode2);

		hashCode1 = cacheKeyGenerator1.getCacheKey(
			new StringBundler(2).append("key1").append("key2"));
		hashCode2 = cacheKeyGenerator2.getCacheKey(
			new StringBundler(2).append("key1").append("key2"));

		Assert.assertNotEquals(hashCode1, hashCode2);
	}

}