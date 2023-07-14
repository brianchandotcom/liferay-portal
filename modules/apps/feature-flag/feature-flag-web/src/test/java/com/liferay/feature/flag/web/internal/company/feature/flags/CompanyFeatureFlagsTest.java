/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.company.feature.flags;

import com.liferay.feature.flag.web.internal.constants.FeatureFlagConstants;
import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlagImpl;
import com.liferay.feature.flag.web.internal.model.FeatureFlagType;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class CompanyFeatureFlagsTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_expectedFeatureFlags = new FeatureFlag[] {
			_betaFeatureFlag, _deprecationFeatureFlag, _devFeatureFlag,
			_releaseFeatureFlag
		};

		Map<String, FeatureFlag> featureFlagsMap = new HashMap<>();

		for (FeatureFlag featureFlag : _expectedFeatureFlags) {
			featureFlagsMap.put(featureFlag.getKey(), featureFlag);
		}

		_companyFeatureFlags = new CompanyFeatureFlags(featureFlagsMap);
	}

	@Test
	public void testGetFeatureFlags() {
		List<FeatureFlag> actualFeatureFlags =
			_companyFeatureFlags.getFeatureFlags(null);

		Assert.assertEquals(
			actualFeatureFlags.toString(), _expectedFeatureFlags.length,
			actualFeatureFlags.size());

		actualFeatureFlags = _companyFeatureFlags.getFeatureFlags(
			FeatureFlagType.BETA.getPredicate());

		Assert.assertEquals(
			actualFeatureFlags.toString(), 1, actualFeatureFlags.size());

		FeatureFlag featureFlag = actualFeatureFlags.get(0);

		Assert.assertEquals(_betaFeatureFlag.getKey(), featureFlag.getKey());
	}

	@Test
	public void testGetJSON() throws JSONException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_companyFeatureFlags.getJSON());

		Set<String> set = jsonObject.keySet();

		Assert.assertEquals(
			set.toString(), _expectedFeatureFlags.length, set.size());

		for (FeatureFlag featureFlag : _expectedFeatureFlags) {
			Assert.assertEquals(
				featureFlag.isEnabled(),
				jsonObject.getBoolean(featureFlag.getKey()));
		}
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testIsEnabled() {
		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());

		for (FeatureFlag expectedFeatureFlag : _expectedFeatureFlags) {
			Assert.assertEquals(
				expectedFeatureFlag.isEnabled(),
				_companyFeatureFlags.isEnabled(expectedFeatureFlag.getKey()));
		}

		String randomKey = _createKey();

		Assert.assertFalse(_companyFeatureFlags.isEnabled(randomKey));

		PropsUtil.set(
			FeatureFlagConstants.getKey(randomKey), Boolean.TRUE.toString());

		Assert.assertTrue(_companyFeatureFlags.isEnabled(randomKey));
	}

	private FeatureFlag _createFeatureFlag(FeatureFlagType featureFlagType) {
		return new FeatureFlagImpl(
			new String[0], RandomTestUtil.randomString(),
			RandomTestUtil.randomBoolean(), featureFlagType, _createKey(),
			RandomTestUtil.randomString());
	}

	private String _createKey() {
		return RandomTestUtil.randomString(
			5, NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);
	}

	private final FeatureFlag _betaFeatureFlag = _createFeatureFlag(
		FeatureFlagType.BETA);
	private CompanyFeatureFlags _companyFeatureFlags;
	private final FeatureFlag _deprecationFeatureFlag = _createFeatureFlag(
		FeatureFlagType.DEPRECATION);
	private final FeatureFlag _devFeatureFlag = _createFeatureFlag(
		FeatureFlagType.DEV);
	private FeatureFlag[] _expectedFeatureFlags;
	private final FeatureFlag _releaseFeatureFlag = _createFeatureFlag(
		FeatureFlagType.RELEASE);

}