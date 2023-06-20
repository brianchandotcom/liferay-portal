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

package com.liferay.feature.flag.web.internal.company.feature.flags;

import com.liferay.feature.flag.web.internal.constants.FeatureFlagConstants;
import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Drew Brokke
 */
public class CompanyFeatureFlags {

	public CompanyFeatureFlags(Map<String, FeatureFlag> featureFlagsMap) {
		_featureFlagsMap = featureFlagsMap;
	}

	public List<FeatureFlag> getFeatureFlags(Predicate<FeatureFlag> predicate) {
		List<FeatureFlag> featureFlags = new ArrayList<>();

		if (predicate == null) {
			predicate = featureFlag -> true;
		}

		for (FeatureFlag featureFlag : _featureFlagsMap.values()) {
			if (predicate.test(featureFlag)) {
				featureFlags.add(featureFlag);
			}
		}

		featureFlags.sort(Comparator.comparing(FeatureFlag::getKey));

		return featureFlags;
	}

	public String getJSON() {
		if (_featureFlagsMap.isEmpty()) {
			return PropsValues.FEATURE_FLAGS_JSON;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (FeatureFlag featureFlag : _featureFlagsMap.values()) {
			jsonObject.put(featureFlag.getKey(), featureFlag.isEnabled());
		}

		return jsonObject.toString();
	}

	public boolean isEnabled(String key) {
		if (_featureFlagsMap.containsKey(key)) {
			FeatureFlag featureFlag = _featureFlagsMap.get(key);

			return featureFlag.isEnabled();
		}

		return GetterUtil.getBoolean(
			PropsUtil.get(FeatureFlagConstants.getKey(key)));
	}

	private final Map<String, FeatureFlag> _featureFlagsMap;

}