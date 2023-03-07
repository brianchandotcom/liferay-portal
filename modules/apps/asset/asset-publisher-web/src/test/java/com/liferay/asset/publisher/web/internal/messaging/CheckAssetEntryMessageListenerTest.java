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

package com.liferay.asset.publisher.web.internal.messaging;

import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Tina Tian
 */
public class CheckAssetEntryMessageListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_assetPublisherWebConfiguration = Mockito.mock(
			AssetPublisherWebConfiguration.class);
		_triggerFactory = Mockito.mock(TriggerFactory.class);

		_checkAssetEntryMessageListener = new CheckAssetEntryMessageListener();

		ReflectionTestUtil.setFieldValue(
			_checkAssetEntryMessageListener, "_assetPublisherWebConfiguration",
			_assetPublisherWebConfiguration);
		ReflectionTestUtil.setFieldValue(
			_checkAssetEntryMessageListener, "_triggerFactory",
			_triggerFactory);
	}

	@Test
	public void testGetTriggerEmptyCronExpression() {
		Mockito.when(
			_assetPublisherWebConfiguration.checkCronExpression()
		).thenReturn(
			StringPool.BLANK
		);

		int checkInterval = RandomTestUtil.randomInt();

		Mockito.when(
			_assetPublisherWebConfiguration.checkInterval()
		).thenReturn(
			checkInterval
		);

		TriggerConfiguration triggerConfiguration =
			_checkAssetEntryMessageListener.getTriggerConfiguration();

		Assert.assertNull(triggerConfiguration.getCronExpression());
		Assert.assertEquals(checkInterval, triggerConfiguration.getInterval());
		Assert.assertNotNull(triggerConfiguration.getTimeUnit());
	}

	@Test
	public void testGetTriggerInvalidCronExpression() {
		int checkInterval = RandomTestUtil.randomInt();

		Mockito.when(
			_assetPublisherWebConfiguration.checkInterval()
		).thenReturn(
			checkInterval
		);

		Mockito.when(
			_triggerFactory.createTrigger(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(),
				Mockito.any(), Mockito.anyString())
		).thenThrow(
			new RuntimeException()
		);

		TriggerConfiguration triggerConfiguration =
			_checkAssetEntryMessageListener.getTriggerConfiguration();

		Assert.assertNull(triggerConfiguration.getCronExpression());
		Assert.assertEquals(checkInterval, triggerConfiguration.getInterval());
		Assert.assertNotNull(triggerConfiguration.getTimeUnit());
	}

	@Test
	public void testGetTriggerValidCronExpression() {
		String checkCronExpression = RandomTestUtil.randomString();

		Mockito.when(
			_assetPublisherWebConfiguration.checkCronExpression()
		).thenReturn(
			checkCronExpression
		);

		Mockito.when(
			_triggerFactory.createTrigger(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(),
				Mockito.any(), Mockito.anyString())
		).thenReturn(
			null
		);

		TriggerConfiguration triggerConfiguration =
			_checkAssetEntryMessageListener.getTriggerConfiguration();

		Assert.assertEquals(
			checkCronExpression, triggerConfiguration.getCronExpression());
		Assert.assertEquals(0, triggerConfiguration.getInterval());
		Assert.assertNull(triggerConfiguration.getTimeUnit());
	}

	private AssetPublisherWebConfiguration _assetPublisherWebConfiguration;
	private CheckAssetEntryMessageListener _checkAssetEntryMessageListener;
	private TriggerFactory _triggerFactory;

}