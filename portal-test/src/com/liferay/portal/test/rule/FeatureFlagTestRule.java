/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.util.PropsUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.Description;

/**
 * @author Alejandro Tardín
 */
public class FeatureFlagTestRule
	extends AbstractTestRule<Map<String, String>, Map<String, String>> {

	public static final FeatureFlagTestRule INSTANCE =
		new FeatureFlagTestRule();

	@Override
	protected void afterClass(
			Description description, Map<String, String> previousValues)
		throws Throwable {

		_restoreFeatureFlags(previousValues);
	}

	@Override
	protected void afterMethod(
			Description description, Map<String, String> previousValues,
			Object target)
		throws Throwable {

		_restoreFeatureFlags(previousValues);
	}

	@Override
	protected Map<String, String> beforeClass(Description description)
		throws Throwable {

		return _updateFeatureFlags(description);
	}

	@Override
	protected Map<String, String> beforeMethod(
			Description description, Object target)
		throws Throwable {

		return _updateFeatureFlags(description);
	}

	private void _restoreFeatureFlags(Map<String, String> previousValues) {
		Map<String, String> values = new HashMap<>();

		for (Map.Entry<String, String> entry : previousValues.entrySet()) {
			String value = entry.getValue();

			if (value == null) {
				PropsUtil.set(entry.getKey(), value);

				continue;
			}

			values.put(entry.getKey(), entry.getValue());
		}

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.create(
				values, true
			).build());
	}

	private KeyValuePair _updateFeatureFlag(FeatureFlag featureFlag) {
		String featureFlagKey = FeatureFlagConstants.getKey(
			featureFlag.value());

		KeyValuePair previousKeyValuePair = new KeyValuePair(
			featureFlagKey, PropsUtil.get(featureFlagKey));

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				featureFlagKey, String.valueOf(featureFlag.enable())
			).build());

		return previousKeyValuePair;
	}

	private Map<String, String> _updateFeatureFlags(Description description) {
		Map<String, String> previousValues = new HashMap<>();

		FeatureFlags featureFlags = description.getAnnotation(
			FeatureFlags.class);

		if (featureFlags != null) {
			for (FeatureFlag featureFlag : featureFlags.featureFlags()) {
				if (featureFlag == null) {
					continue;
				}

				KeyValuePair previousKeyValuePair = _updateFeatureFlag(
					featureFlag);

				previousValues.put(
					previousKeyValuePair.getKey(),
					previousKeyValuePair.getValue());
			}
		}

		FeatureFlag featureFlag = description.getAnnotation(FeatureFlag.class);

		if (featureFlag != null) {
			KeyValuePair previousKeyValuePair = _updateFeatureFlag(featureFlag);

			previousValues.put(
				previousKeyValuePair.getKey(), previousKeyValuePair.getValue());
		}

		return previousValues;
	}

}