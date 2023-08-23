/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagType;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class DependencyAwareFeatureFlagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_disabledFeatureFlag = new FeatureFlagImpl(
			new String[0], RandomTestUtil.randomString(), false,
			FeatureFlagType.BETA, "ABC-123", RandomTestUtil.randomString());
		_enabledFeatureFlag = new FeatureFlagImpl(
			new String[0], RandomTestUtil.randomString(), true,
			FeatureFlagType.BETA, "ABC-234", RandomTestUtil.randomString());
		_featureFlag = new FeatureFlagImpl(
			new String[0], RandomTestUtil.randomString(), true,
			FeatureFlagType.BETA, "ABC-345", RandomTestUtil.randomString());
	}

	@Test
	public void testGetDependencyKeys() {
		_assertArrayEquals();
		_assertArrayEquals(_disabledFeatureFlag);
		_assertArrayEquals(_disabledFeatureFlag, _enabledFeatureFlag);
	}

	@Test
	public void testIsEnabled() {
		_withDependencyAwareFeatureFlag(
			featureFlag -> Assert.assertEquals(
				_featureFlag.isEnabled(), featureFlag.isEnabled()));
		_withDependencyAwareFeatureFlag(
			featureFlag -> Assert.assertFalse(featureFlag.isEnabled()),
			_disabledFeatureFlag);
		_withDependencyAwareFeatureFlag(
			featureFlag -> Assert.assertEquals(
				_featureFlag.isEnabled(), featureFlag.isEnabled()),
			_enabledFeatureFlag);
		_withDependencyAwareFeatureFlag(
			featureFlag -> Assert.assertFalse(featureFlag.isEnabled()),
			_disabledFeatureFlag, _enabledFeatureFlag);
	}

	private void _assertArrayEquals(FeatureFlag... featureFlags) {
		_withDependencyAwareFeatureFlag(
			featureFlag -> Assert.assertArrayEquals(
				TransformUtil.transform(
					featureFlags, FeatureFlag::getKey, String.class),
				featureFlag.getDependencyKeys()),
			featureFlags);
	}

	private void _withDependencyAwareFeatureFlag(
		Consumer<FeatureFlag> consumer, FeatureFlag... featureFlags) {

		consumer.accept(
			new DependencyAwareFeatureFlag(_featureFlag, featureFlags));
	}

	private FeatureFlag _disabledFeatureFlag;
	private FeatureFlag _enabledFeatureFlag;
	private FeatureFlag _featureFlag;

}