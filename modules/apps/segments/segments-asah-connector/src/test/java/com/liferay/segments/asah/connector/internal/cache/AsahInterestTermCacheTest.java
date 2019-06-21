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

package com.liferay.segments.asah.connector.internal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Sarai Díaz
 */
@RunWith(MockitoJUnitRunner.class)
public class AsahInterestTermCacheTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_asahInterestTermCache, "_portalCache", _portalCache);
	}

	@Test
	public void testGetInterestTerms() {
		String userId = RandomTestUtil.randomString();

		String[] interestTerms = {
			RandomTestUtil.randomString(), RandomTestUtil.randomString()
		};

		String cacheKey = _generateCacheKey(userId);

		Mockito.when(
			_portalCache.get(cacheKey)
		).thenReturn(
			interestTerms
		);

		Assert.assertArrayEquals(
			interestTerms, _asahInterestTermCache.getInterestTerms(userId));

		Mockito.verify(
			_portalCache, Mockito.times(1)
		).get(
			Mockito.eq(cacheKey)
		);
	}

	@Test
	public void testPutInterestTerms() {
		String userId = RandomTestUtil.randomString();

		String[] interestTerms = {
			RandomTestUtil.randomString(), RandomTestUtil.randomString()
		};

		String cacheKey = _generateCacheKey(userId);

		_asahInterestTermCache.putInterestTerms(userId, interestTerms);

		Mockito.verify(
			_portalCache, Mockito.times(1)
		).put(
			cacheKey, interestTerms, 0
		);
	}

	private String _generateCacheKey(String userId) {
		return "segments-" + userId;
	}

	private final AsahInterestTermCache _asahInterestTermCache =
		new AsahInterestTermCache();

	@Mock
	private PortalCache<String, String[]> _portalCache;

}