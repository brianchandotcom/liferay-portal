/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.scheduler;

import com.liferay.fragment.configuration.FragmentEntryVersionConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class CleanUpFragmentEntryVersionsSchedulerJobConfigurationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_fragmentEntryVersionConfiguration = Mockito.mock(
			FragmentEntryVersionConfiguration.class);
		_triggerFactory = Mockito.mock(TriggerFactory.class);

		_cleanUpFragmentEntryVersionsSchedulerJobConfiguration =
			new CleanUpFragmentEntryVersionsSchedulerJobConfiguration();

		ReflectionTestUtil.setFieldValue(
			_cleanUpFragmentEntryVersionsSchedulerJobConfiguration,
			"_fragmentEntryVersionConfiguration",
			_fragmentEntryVersionConfiguration);
		ReflectionTestUtil.setFieldValue(
			_cleanUpFragmentEntryVersionsSchedulerJobConfiguration,
			"_triggerFactory", _triggerFactory);
	}

	@Test
	@TestInfo("LPD-75909")
	public void testGetTriggerConfigurationEmptyCronExpression() {
		Mockito.when(
			_fragmentEntryVersionConfiguration.cleanUpCronExpression()
		).thenReturn(
			StringPool.BLANK
		);

		TriggerConfiguration triggerConfiguration =
			(TriggerConfiguration)ReflectionTestUtil.invoke(
				_cleanUpFragmentEntryVersionsSchedulerJobConfiguration,
				"_getTriggerConfiguration", null, null);

		Assert.assertNull(triggerConfiguration.getCronExpression());
		Assert.assertEquals(1, triggerConfiguration.getInterval());
		Assert.assertEquals(TimeUnit.DAY, triggerConfiguration.getTimeUnit());
	}

	@Test
	@TestInfo("LPD-75909")
	public void testGetTriggerConfigurationInvalidCronExpression() {
		Mockito.when(
			_fragmentEntryVersionConfiguration.cleanUpCronExpression()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_triggerFactory.createTrigger(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(),
				Mockito.any(), Mockito.anyString())
		).thenThrow(
			new RuntimeException()
		);

		TriggerConfiguration triggerConfiguration =
			(TriggerConfiguration)ReflectionTestUtil.invoke(
				_cleanUpFragmentEntryVersionsSchedulerJobConfiguration,
				"_getTriggerConfiguration", null, null);

		Assert.assertNull(triggerConfiguration.getCronExpression());
		Assert.assertEquals(1, triggerConfiguration.getInterval());
		Assert.assertEquals(TimeUnit.DAY, triggerConfiguration.getTimeUnit());
	}

	@Test
	@TestInfo("LPD-75909")
	public void testGetTriggerConfigurationValidCronExpression() {
		String cleanUpCronExpression = RandomTestUtil.randomString();

		Mockito.when(
			_fragmentEntryVersionConfiguration.cleanUpCronExpression()
		).thenReturn(
			cleanUpCronExpression
		);

		Mockito.when(
			_triggerFactory.createTrigger(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(),
				Mockito.any(), Mockito.anyString())
		).thenReturn(
			null
		);

		TriggerConfiguration triggerConfiguration =
			(TriggerConfiguration)ReflectionTestUtil.invoke(
				_cleanUpFragmentEntryVersionsSchedulerJobConfiguration,
				"_getTriggerConfiguration", null, null);

		Assert.assertEquals(
			cleanUpCronExpression, triggerConfiguration.getCronExpression());
		Assert.assertEquals(0, triggerConfiguration.getInterval());
		Assert.assertNull(triggerConfiguration.getTimeUnit());
	}

	private CleanUpFragmentEntryVersionsSchedulerJobConfiguration
		_cleanUpFragmentEntryVersionsSchedulerJobConfiguration;
	private FragmentEntryVersionConfiguration
		_fragmentEntryVersionConfiguration;
	private TriggerFactory _triggerFactory;

}