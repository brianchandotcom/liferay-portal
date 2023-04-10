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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.AbstractTestRule;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
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

		return _enableFeatureFlags(description);
	}

	@Override
	protected Map<String, String> beforeMethod(
			Description description, Object target)
		throws Throwable {

		return _enableFeatureFlags(description);
	}

	private Map<String, String> _enableFeatureFlags(Description description) {
		FeatureFlags featureFlags = description.getAnnotation(
			FeatureFlags.class);

		Map<String, String> previousValues = new HashMap<>();

		if (featureFlags != null) {
			for (String key : featureFlags.value()) {
				String featureFlagKey = "feature.flag." + key;

				String previousValue = PropsUtil.get(featureFlagKey);

				if (Validator.isNotNull(previousValue)) {
					previousValues.put(featureFlagKey, previousValue);
				}

				PropsUtil.addProperties(
					UnicodePropertiesBuilder.setProperty(
						featureFlagKey, "true"
					).build());
			}
		}

		return previousValues;
	}

	private void _restoreFeatureFlags(Map<String, String> previousValues) {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.create(
				previousValues, true
			).build());
	}

}