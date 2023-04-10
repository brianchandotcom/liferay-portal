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
import com.liferay.portal.util.PropsUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.Description;

/**
 * @author Alejandro Tardín
 */
public class FeatureFlagTestRule extends AbstractTestRule<Void, Void> {

	public static final FeatureFlagTestRule INSTANCE =
		new FeatureFlagTestRule();

	@Override
	protected void afterClass(Description description, Void v)
		throws Throwable {

		_restoreInitialProperties(
			description.getAnnotation(FeatureFlags.class),
			_initialClassProperties);
	}

	@Override
	protected void afterMethod(Description description, Void v, Object target)
		throws Throwable {

		_restoreInitialProperties(
			description.getAnnotation(FeatureFlags.class),
			_initialMethodProperties);
	}

	@Override
	protected Void beforeClass(Description description) throws Throwable {
		FeatureFlags featureFlags = description.getAnnotation(
			FeatureFlags.class);

		_storeInitialProperties(featureFlags, _initialClassProperties);

		_setFeatureFlags(featureFlags, true);

		return null;
	}

	@Override
	protected Void beforeMethod(Description description, Object target)
		throws Throwable {

		FeatureFlags featureFlags = description.getAnnotation(
			FeatureFlags.class);

		_storeInitialProperties(featureFlags, _initialMethodProperties);

		_setFeatureFlags(featureFlags, true);

		return null;
	}

	private void _restoreInitialProperties(
		FeatureFlags featureFlags, Map<String, String> propertiesMap) {

		if (featureFlags != null) {
			for (String key : featureFlags.value()) {
				PropsUtil.addProperties(
					UnicodePropertiesBuilder.setProperty(
						"feature.flag." + key,
						propertiesMap.get("feature.flag." + key)
					).build());
			}
		}
	}

	private void _setFeatureFlags(FeatureFlags featureFlags, boolean enabled) {
		if (featureFlags != null) {
			for (String key : featureFlags.value()) {
				PropsUtil.addProperties(
					UnicodePropertiesBuilder.setProperty(
						"feature.flag." + key, Boolean.toString(enabled)
					).build());
			}
		}
	}

	private void _storeInitialProperties(
		FeatureFlags featureFlags, Map<String, String> propertiesMap) {

		if (featureFlags != null) {
			for (String key : featureFlags.value()) {
				propertiesMap.putIfAbsent(
					"feature.flag." + key,
					PropsUtil.get("feature.flag." + key));
			}
		}
	}

	private final Map<String, String> _initialClassProperties = new HashMap<>();
	private final Map<String, String> _initialMethodProperties =
		new HashMap<>();

}