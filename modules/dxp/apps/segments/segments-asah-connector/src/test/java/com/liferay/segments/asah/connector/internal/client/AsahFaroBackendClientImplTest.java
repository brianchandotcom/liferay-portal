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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Sarai Díaz
 */
@RunWith(MockitoJUnitRunner.class)
public class AsahFaroBackendClientImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_jsonWebServiceClient = Mockito.mock(JSONWebServiceClient.class);

		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_jsonWebServiceClient);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			Mockito.mock(PrefsProps.class));
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDuration() {
		String days = "14";

		Mockito.when(
			_jsonWebServiceClient.doPost(
				Mockito.eq(String.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(ExperimentSettings.class),
				Mockito.anyMapOf(String.class, String.class))
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
				Mockito.anyMapOf(String.class, String.class))
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
				Mockito.anyMapOf(String.class, String.class))
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