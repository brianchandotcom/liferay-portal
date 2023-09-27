/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.mockito.Mockito;

/**
 * @author Sarai Díaz
 */
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