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

package com.liferay.feature.flag.web.internal.util;

import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlagImpl;
import com.liferay.feature.flag.web.internal.model.FeatureFlagStatus;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class FeatureFlagJSONUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSON() throws Exception {
		FeatureFlag[] featureFlags = {create(true), create(false)};

		String json = FeatureFlagJSONUtil.toJSON(featureFlags);

		JSONObject jsonObject = new JSONObjectImpl(json);

		Set<String> keySet = jsonObject.keySet();

		Assert.assertEquals(
			keySet.toString(), featureFlags.length, keySet.size());

		for (FeatureFlag featureFlag : featureFlags) {
			Assert.assertEquals(
				featureFlag.isEnabled(),
				jsonObject.getBoolean(featureFlag.getKey()));
		}
	}

	protected FeatureFlag create(boolean enabled) {
		return new FeatureFlagImpl(
			RandomTestUtil.randomString(), enabled, FeatureFlagStatus.DEV,
			"ABC-" + RandomTestUtil.randomInt(1, 999),
			RandomTestUtil.randomString());
	}

}