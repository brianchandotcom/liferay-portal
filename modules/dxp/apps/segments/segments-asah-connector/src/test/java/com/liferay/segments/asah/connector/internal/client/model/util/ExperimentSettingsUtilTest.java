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

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariantSettings;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;
import com.liferay.segments.model.SegmentsExperiment;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
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
public class ExperimentSettingsUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToExperimentSettings() {
		double confidenceLevel = RandomTestUtil.randomDouble();

		Map<String, Double> segmentsExperienceKeySplitMap = HashMapBuilder.put(
			RandomTestUtil.randomString(), RandomTestUtil.randomDouble()
		).build();

		SegmentsExperiment segmentsExperiment = Mockito.mock(
			SegmentsExperiment.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsExperiment
		).getSegmentsExperimentKey();

		ExperimentSettings experimentSettings =
			ExperimentSettingsUtil.toExperimentSettings(
				confidenceLevel, segmentsExperienceKeySplitMap,
				segmentsExperiment);

		Assert.assertEquals(
			confidenceLevel, experimentSettings.getConfidenceLevel(), 0.001);

		List<DXPVariantSettings> dxpVariantsSettings =
			experimentSettings.getDXPVariantsSettings();

		Assert.assertEquals(
			dxpVariantsSettings.toString(), 1, dxpVariantsSettings.size());

		Map<String, DXPVariantSettings> dxpVariantsSettingsMap =
			experimentSettings.getDXPVariantsSettingsMap();

		Assert.assertEquals(
			dxpVariantsSettingsMap.toString(), 1,
			dxpVariantsSettingsMap.size());
	}

}