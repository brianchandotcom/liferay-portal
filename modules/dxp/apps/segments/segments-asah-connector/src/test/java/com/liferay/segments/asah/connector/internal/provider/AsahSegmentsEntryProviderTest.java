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

package com.liferay.segments.asah.connector.internal.provider;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.segments.asah.connector.internal.cache.AsahSegmentsEntryCache;
import com.liferay.segments.asah.connector.internal.context.contributor.SegmentsAsahRequestContextContributor;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class AsahSegmentsEntryProviderTest {

	@Before
	public void setUp() throws PortalException {
		ReflectionTestUtil.setFieldValue(
			_asahSegmentsEntryProvider, "_asahSegmentsEntryCache",
			_asahSegmentsEntryCache);

		ReflectionTestUtil.setFieldValue(
			_asahSegmentsEntryProvider, "_messageBus", _messageBus);

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

		List<SegmentsEntryRel> segmentsEntryRels = new ArrayList<>();

		LongStream stream = Arrays.stream(segmentsEntryRelIds);

		stream.forEach(
			segmentsEntryRelId -> segmentsEntryRels.add(
				_createSegmentsEntryRel(segmentsEntryRelId)));

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
	public void testGetSegmentsEntryIdsWithCachedUserSegments() {
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

		Mockito.verify(
			_messageBus, Mockito.never()
		).sendMessage(
			Mockito.anyString(), Mockito.any(Message.class)
		);
	}

	@Test
	public void testGetSegmentsEntryIdsWithContextAndEmptyAcClientUserId() {
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
	public void testGetSegmentsEntryIdsWithEmptyContext() {
		Context context = new Context();

		Assert.assertArrayEquals(
			new long[0],
			_asahSegmentsEntryProvider.getSegmentsEntryIds(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLong(), context));
	}

	@Test
	public void testGetSegmentsEntryIdsWithNullContext() {
		Assert.assertArrayEquals(
			new long[0],
			_asahSegmentsEntryProvider.getSegmentsEntryIds(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLong(), null));
	}

	@Test
	public void testGetSegmentsEntryIdsWithUncachedUserSegments() {
		String userId = RandomTestUtil.randomString();

		Mockito.when(
			_asahSegmentsEntryCache.getSegmentsEntryIds(userId)
		).thenReturn(
			null
		);

		Context context = new Context();

		context.put(
			SegmentsAsahRequestContextContributor.
				KEY_SEGMENTS_ANONYMOUS_USER_ID,
			userId);

		Assert.assertArrayEquals(
			new long[0],
			_asahSegmentsEntryProvider.getSegmentsEntryIds(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLong(), context));

		Mockito.verify(
			_messageBus, Mockito.times(1)
		).sendMessage(
			Mockito.anyString(), Mockito.any(Message.class)
		);
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

	@Mock
	private AsahSegmentsEntryCache _asahSegmentsEntryCache;

	private final AsahSegmentsEntryProvider _asahSegmentsEntryProvider =
		new AsahSegmentsEntryProvider();

	@Mock
	private MessageBus _messageBus;

	@Mock
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}