/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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