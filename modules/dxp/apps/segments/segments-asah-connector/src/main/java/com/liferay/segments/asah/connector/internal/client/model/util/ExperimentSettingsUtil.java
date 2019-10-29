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

import com.liferay.segments.asah.connector.internal.client.model.DXPVariantSettings;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;
import com.liferay.segments.model.SegmentsExperiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sarai Díaz
 */
public class ExperimentSettingsUtil {

	public static ExperimentSettings toExperimentSettings(
		double confidenceLevel,
		Map<String, Double> segmentsExperienceKeySplitMap,
		SegmentsExperiment segmentsExperiment) {

		ExperimentSettings experimentSettings = new ExperimentSettings();

		experimentSettings.setConfidenceLevel(confidenceLevel);

		List<DXPVariantSettings> dxpVariantsSettings = new ArrayList<>();

		segmentsExperienceKeySplitMap.forEach(
			(segmentsExperienceKey, split) -> dxpVariantsSettings.add(
				DXPVariantSettingsUtil.toDXPVariantSettings(
					segmentsExperiment.getSegmentsExperienceKey(),
					segmentsExperienceKey, split)));
		experimentSettings.setDXPVariantsSettings(dxpVariantsSettings);

		return experimentSettings;
	}

}