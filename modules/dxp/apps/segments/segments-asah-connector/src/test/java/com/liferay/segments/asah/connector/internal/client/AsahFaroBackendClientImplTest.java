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

package com.liferay.segments.asah.connector.internal.client;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Sarai Díaz
 */
public class AsahFaroBackendClientImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws ConfigurationException {
		_jsonWebServiceClient = Mockito.mock(JSONWebServiceClient.class);

		AnalyticsSettingsManager analyticsSettingsManager = Mockito.mock(
			AnalyticsSettingsManager.class);

		Mockito.when(
			analyticsSettingsManager.getAnalyticsConfiguration(
				Mockito.anyLong())
		).thenReturn(
			Mockito.mock(AnalyticsConfiguration.class)
		);

		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			analyticsSettingsManager, _jsonWebServiceClient);
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDuration() {
		String days = "14";

		Mockito.when(
			_jsonWebServiceClient.doPost(
				Mockito.eq(String.class), Mockito.nullable(String.class),
				Mockito.nullable(String.class),
				Mockito.any(ExperimentSettings.class), Mockito.anyMap())
		).thenReturn(
			days
		);

		Assert.assertEquals(
			Long.valueOf(days),
			_asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				new ExperimentSettings()));
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDurationWithEmptyResult() {
		Mockito.when(
			_jsonWebServiceClient.doPost(
				Mockito.eq(String.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(ExperimentSettings.class),
				Mockito.anyMap())
		).thenReturn(
			StringPool.BLANK
		);

		Assert.assertNull(
			_asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				new ExperimentSettings()));
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDurationWithInvalidResult() {
		Mockito.when(
			_jsonWebServiceClient.doPost(
				Mockito.eq(String.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(ExperimentSettings.class),
				Mockito.anyMap())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Assert.assertNull(
			_asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				new ExperimentSettings()));
	}

	private AsahFaroBackendClient _asahFaroBackendClient;
	private JSONWebServiceClient _jsonWebServiceClient;

}