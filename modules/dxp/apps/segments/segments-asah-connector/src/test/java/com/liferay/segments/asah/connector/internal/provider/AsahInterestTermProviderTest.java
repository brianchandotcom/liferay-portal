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
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.segments.asah.connector.internal.cache.AsahInterestTermCache;

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
public class AsahInterestTermProviderTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_asahInterestTermProvider, "_messageBus", _messageBus);

		ReflectionTestUtil.setFieldValue(
			_asahInterestTermProvider, "_asahInterestTermCache",
			_asahInterestTermCache);
	}

	@Test
	public void testGetInterestTermsWithCachedTerms() {
		String userId = RandomTestUtil.randomString();

		String[] interestTerms = {
			RandomTestUtil.randomString(), RandomTestUtil.randomString()
		};

		Mockito.when(
			_asahInterestTermCache.getInterestTerms(userId)
		).thenReturn(
			interestTerms
		);

		Assert.assertArrayEquals(
			interestTerms, _asahInterestTermProvider.getInterestTerms(userId));

		Mockito.verify(
			_messageBus, Mockito.never()
		).sendMessage(
			Mockito.anyString(), Mockito.any(Message.class)
		);
	}

	@Test
	public void testGetInterestTermsWithEmptyAcClientUserId() {
		Assert.assertArrayEquals(
			new String[0],
			_asahInterestTermProvider.getInterestTerms(StringPool.BLANK));
	}

	@Test
	public void testGetInterestTermsWithUncachedTerms() {
		String userId = RandomTestUtil.randomString();

		Mockito.when(
			_asahInterestTermCache.getInterestTerms(userId)
		).thenReturn(
			null
		);

		Assert.assertArrayEquals(
			new String[0], _asahInterestTermProvider.getInterestTerms(userId));

		Mockito.verify(
			_messageBus, Mockito.times(1)
		).sendMessage(
			Mockito.anyString(), Mockito.any(Message.class)
		);
	}

	@Mock
	private AsahInterestTermCache _asahInterestTermCache;

	private final AsahInterestTermProvider _asahInterestTermProvider =
		new AsahInterestTermProvider();

	@Mock
	private MessageBus _messageBus;

}