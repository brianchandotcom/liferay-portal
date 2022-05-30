/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.configuration.provider.SegmentsAsahConfigurationProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Sarai Díaz
 */
public class AsahInterestTermCacheTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_asahInterestTermCache, "_portalCache", _portalCache);
		ReflectionTestUtil.setFieldValue(
			_asahInterestTermCache, "_segmentsAsahConfigurationProvider",
			new SegmentsAsahConfigurationProvider() {

				public int getInterestTermsCacheExpirationTime(long companyId) {
					return 0;
				}

			});
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
	private final PortalCache<String, String[]> _portalCache = Mockito.mock(
		PortalCache.class);

}