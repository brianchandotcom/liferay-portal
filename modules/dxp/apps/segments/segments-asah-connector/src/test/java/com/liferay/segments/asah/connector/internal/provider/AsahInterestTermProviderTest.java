/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.provider;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.cache.AsahInterestTermCache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Sarai Díaz
 */
public class AsahInterestTermProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
			interestTerms,
			_asahInterestTermProvider.getInterestTerms(
				RandomTestUtil.randomLong(), userId));

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
			_asahInterestTermProvider.getInterestTerms(
				RandomTestUtil.randomLong(), StringPool.BLANK));
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
			new String[0],
			_asahInterestTermProvider.getInterestTerms(
				RandomTestUtil.randomLong(), userId));

		Mockito.verify(
			_messageBus, Mockito.times(1)
		).sendMessage(
			Mockito.anyString(), Mockito.any(Message.class)
		);
	}

	private final AsahInterestTermCache _asahInterestTermCache = Mockito.mock(
		AsahInterestTermCache.class);
	private final AsahInterestTermProvider _asahInterestTermProvider =
		new AsahInterestTermProvider();
	private final MessageBus _messageBus = Mockito.mock(MessageBus.class);

}