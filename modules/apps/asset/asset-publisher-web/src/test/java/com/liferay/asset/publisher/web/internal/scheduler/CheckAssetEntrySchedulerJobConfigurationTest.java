/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.scheduler;

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
public class CheckAssetEntrySchedulerJobConfigurationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_assetPublisherWebConfiguration = Mockito.mock(
			AssetPublisherWebConfiguration.class);
		_triggerFactory = Mockito.mock(TriggerFactory.class);

		_checkAssetEntrySchedulerJobConfiguration =
			new CheckAssetEntrySchedulerJobConfiguration();

		ReflectionTestUtil.setFieldValue(
			_checkAssetEntrySchedulerJobConfiguration,
			"_assetPublisherWebConfiguration", _assetPublisherWebConfiguration);
		ReflectionTestUtil.setFieldValue(
			_checkAssetEntrySchedulerJobConfiguration, "_triggerFactory",
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
			_checkAssetEntrySchedulerJobConfiguration.getTriggerConfiguration();

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
			_checkAssetEntrySchedulerJobConfiguration.getTriggerConfiguration();

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
			_checkAssetEntrySchedulerJobConfiguration.getTriggerConfiguration();

		Assert.assertEquals(
			checkCronExpression, triggerConfiguration.getCronExpression());
		Assert.assertEquals(0, triggerConfiguration.getInterval());
		Assert.assertNull(triggerConfiguration.getTimeUnit());
	}

	private AssetPublisherWebConfiguration _assetPublisherWebConfiguration;
	private CheckAssetEntrySchedulerJobConfiguration
		_checkAssetEntrySchedulerJobConfiguration;
	private TriggerFactory _triggerFactory;

}