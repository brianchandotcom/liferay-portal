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
import com.liferay.portal.kernel.test.util.PropsTestUtil;

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

		_setFeatureFlags(description, false);
	}

	@Override
	protected void afterMethod(Description description, Void v, Object target)
		throws Throwable {

		_setFeatureFlags(description, false);
	}

	@Override
	protected Void beforeClass(Description description) throws Throwable {
		_setFeatureFlags(description, true);

		return null;
	}

	@Override
	protected Void beforeMethod(Description description, Object target)
		throws Throwable {

		_setFeatureFlags(description, true);

		return null;
	}

	private void _setFeatureFlags(Description description, boolean enabled) {
		FeatureFlags featureFlags = description.getAnnotation(
			FeatureFlags.class);

		if (featureFlags != null) {
			for (String key : featureFlags.value()) {
				PropsTestUtil.setProps(
					"feature.flag." + key, Boolean.toString(enabled));
			}
		}
	}

}