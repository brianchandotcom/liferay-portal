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
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Sarai Díaz
 */
@RunWith(MockitoJUnitRunner.class)
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

	@Mock
	private AsahInterestTermCache _asahInterestTermCache;

	private final AsahInterestTermProvider _asahInterestTermProvider =
		new AsahInterestTermProvider();

	@Mock
	private MessageBus _messageBus;

}