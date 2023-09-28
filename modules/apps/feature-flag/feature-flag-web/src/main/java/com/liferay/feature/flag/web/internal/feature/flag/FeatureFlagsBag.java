/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.feature.flag;

import com.liferay.feature.flag.web.internal.model.FeatureFlagWrapper;
import com.liferay.feature.flag.web.internal.model.PreferenceAwareFeatureFlag;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
public class FeatureFlagsBag {

	public FeatureFlagsBag(
		long companyId, Map<String, FeatureFlag> featureFlagsMap) {

		_companyId = companyId;
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

		String json = _json;

		if (json == null) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			for (FeatureFlag featureFlag : _featureFlagsMap.values()) {
				jsonObject.put(featureFlag.getKey(), featureFlag.isEnabled());
			}

			json = jsonObject.toString();

			_json = json;
		}

		return json;
	}

	public boolean isEnabled(String key) {
		if (_featureFlagsMap.containsKey(key)) {
			FeatureFlag featureFlag = _featureFlagsMap.get(key);

			return featureFlag.isEnabled();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Feature flag ", key, " is not available for company ",
					_companyId));
		}

		return GetterUtil.getBoolean(
			PropsUtil.get(FeatureFlagConstants.getKey(key)));
	}

	public void setEnabled(String key, boolean enabled) {
		FeatureFlag featureFlag = _featureFlagsMap.get(key);

		if ((featureFlag == null) || (enabled == featureFlag.isEnabled())) {
			return;
		}

		while (featureFlag instanceof FeatureFlagWrapper) {
			if (featureFlag instanceof PreferenceAwareFeatureFlag) {
				PreferenceAwareFeatureFlag preferenceAwareFeatureFlag =
					(PreferenceAwareFeatureFlag)featureFlag;

				preferenceAwareFeatureFlag.setEnabled(enabled);

				_json = null;

				break;
			}

			FeatureFlagWrapper featureFlagWrapper =
				(FeatureFlagWrapper)featureFlag;

			featureFlag = featureFlagWrapper.getFeatureFlag();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FeatureFlagsBag.class);

	private final long _companyId;
	private final Map<String, FeatureFlag> _featureFlagsMap;
	private volatile String _json;

}