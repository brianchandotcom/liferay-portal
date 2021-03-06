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

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class AsahSegmentsEntryCacheTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_asahSegmentsEntryCache, "_portalCache", _portalCache);
	}

	@Test
	public void testGetSegmentsEntryIds() {
		String userId = RandomTestUtil.randomString();

		long[] segmentsEntryIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		String cacheKey = _generateCacheKey(userId);

		Mockito.when(
			_portalCache.get(cacheKey)
		).thenReturn(
			segmentsEntryIds
		);

		Assert.assertArrayEquals(
			segmentsEntryIds,
			_asahSegmentsEntryCache.getSegmentsEntryIds(userId));

		Mockito.verify(
			_portalCache, Mockito.times(1)
		).get(
			Mockito.eq(cacheKey)
		);
	}

	@Test
	public void testPutSegmentsEntryIds() {
		String userId = RandomTestUtil.randomString();

		long[] segmentsEntryIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		String cacheKey = _generateCacheKey(userId);

		_asahSegmentsEntryCache.putSegmentsEntryIds(userId, segmentsEntryIds);

		Mockito.verify(
			_portalCache, Mockito.times(1)
		).put(
			cacheKey, segmentsEntryIds, 0
		);
	}

	private String _generateCacheKey(String userId) {
		return "segments-" + userId;
	}

	private final AsahSegmentsEntryCache _asahSegmentsEntryCache =
		new AsahSegmentsEntryCache();

	@Mock
	private PortalCache<String, long[]> _portalCache;

}