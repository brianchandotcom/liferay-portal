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

package com.liferay.feature.flag.web.internal.model;

import com.liferay.petra.function.transform.TransformUtil;
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