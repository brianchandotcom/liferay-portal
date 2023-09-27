/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.provider;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.cache.AsahSegmentsEntryCache;
import com.liferay.segments.asah.connector.internal.context.contributor.SegmentsAsahRequestContextContributor;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author David Arques
 */
public class AsahSegmentsEntryProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_asahSegmentsEntryProvider, "_asahSegmentsEntryCache",
			_asahSegmentsEntryCache);
		ReflectionTestUtil.setFieldValue(
			_asahSegmentsEntryProvider, "_segmentsEntryRelLocalService",
			_segmentsEntryRelLocalService);
	}

	@Test
	public void testGetSegmentsEntryClassPKs() throws PortalException {
		long[] segmentsEntryRelIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		List<SegmentsEntryRel> segmentsEntryRels =
			TransformUtil.transformToList(
				segmentsEntryRelIds, this::_createSegmentsEntryRel);

		long segmentsEntryId = RandomTestUtil.randomLong();

		Mockito.when(
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenReturn(
			segmentsEntryRels
		);

		Assert.assertArrayEquals(
			segmentsEntryRelIds,
			_asahSegmentsEntryProvider.getSegmentsEntryClassPKs(
				segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS));
	}

	@Test
	public void testGetSegmentsEntryClassPKsCount() throws PortalException {
		long segmentsEntryId = RandomTestUtil.randomLong();
		int segmentsEntryRelIdsLength = RandomTestUtil.randomInt();

		Mockito.when(
			_segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
				segmentsEntryId)
		).thenReturn(
			segmentsEntryRelIdsLength
		);

		Assert.assertEquals(
			segmentsEntryRelIdsLength,
			_asahSegmentsEntryProvider.getSegmentsEntryClassPKsCount(
				segmentsEntryId));
	}

	@Test
	public void testGetSegmentsEntryIdsWithCachedUserSegments()
		throws PortalException {

		String userId = RandomTestUtil.randomString();

		long[] segmentsEntryIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		Mockito.when(
			_asahSegmentsEntryCache.getSegmentsEntryIds(userId)
		).thenReturn(
			segmentsEntryIds
		);

		Context context = new Context();

		context.put(
			SegmentsAsahRequestContextContributor.
				KEY_SEGMENTS_ANONYMOUS_USER_ID,
			userId);

		Assert.assertArrayEquals(
			segmentsEntryIds,
			_asahSegmentsEntryProvider.getSegmentsEntryIds(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLong(), context));
	}

	@Test
	public void testGetSegmentsEntryIdsWithContextAndEmptyAcClientUserId()
		throws PortalException {

		Context context = new Context();

		context.put(
			SegmentsAsahRequestContextContributor.
				KEY_SEGMENTS_ANONYMOUS_USER_ID,
			StringPool.BLANK);

		Assert.assertArrayEquals(
			new long[0],
			_asahSegmentsEntryProvider.getSegmentsEntryIds(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLong(), context));
	}

	@Test
	public void testGetSegmentsEntryIdsWithEmptyContext()
		throws PortalException {

		Assert.assertArrayEquals(
			new long[0],
			_asahSegmentsEntryProvider.getSegmentsEntryIds(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLong(), new Context()));
	}

	@Test
	public void testGetSegmentsEntryIdsWithNullContext()
		throws PortalException {

		Assert.assertArrayEquals(
			new long[0],
			_asahSegmentsEntryProvider.getSegmentsEntryIds(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLong(), null));
	}

	private SegmentsEntryRel _createSegmentsEntryRel(long segmentsEntryRelId) {
		SegmentsEntryRel segmentsEntryRel = Mockito.mock(
			SegmentsEntryRel.class);

		Mockito.doReturn(
			segmentsEntryRelId
		).when(
			segmentsEntryRel
		).getClassPK();

		return segmentsEntryRel;
	}

	private final AsahSegmentsEntryCache _asahSegmentsEntryCache = Mockito.mock(
		AsahSegmentsEntryCache.class);
	private final AsahSegmentsEntryProvider _asahSegmentsEntryProvider =
		new AsahSegmentsEntryProvider();
	private final SegmentsEntryRelLocalService _segmentsEntryRelLocalService =
		Mockito.mock(SegmentsEntryRelLocalService.class);

}