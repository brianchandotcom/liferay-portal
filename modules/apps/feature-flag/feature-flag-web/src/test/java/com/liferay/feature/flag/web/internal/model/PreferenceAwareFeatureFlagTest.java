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

import com.liferay.feature.flag.web.internal.manager.FeatureFlagPreferencesManager;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Drew Brokke
 */
public class PreferenceAwareFeatureFlagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_featureFlag = new FeatureFlagImpl(
			new String[0], RandomTestUtil.randomString(),
			RandomTestUtil.randomBoolean(), FeatureFlagType.BETA,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	@Test
	public void testGetOtherValues() {
		Locale locale = LocaleUtil.getDefault();

		withPreferenceAwareFeatureFlag(
			0L,
			preferenceAwareFeatureFlag -> {
				Assert.assertArrayEquals(
					_featureFlag.getDependencyKeys(),
					preferenceAwareFeatureFlag.getDependencyKeys());
				Assert.assertEquals(
					_featureFlag.getDescription(locale),
					preferenceAwareFeatureFlag.getDescription(locale));
				Assert.assertEquals(
					_featureFlag.getKey(), preferenceAwareFeatureFlag.getKey());
				Assert.assertEquals(
					_featureFlag.getFeatureFlagType(),
					preferenceAwareFeatureFlag.getFeatureFlagType());
				Assert.assertEquals(
					_featureFlag.getTitle(locale),
					preferenceAwareFeatureFlag.getTitle(locale));
			});
	}

	@Test
	public void testIsEnabled() throws Exception {
		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			_featureFlagPreferencesManager.isEnabled(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			null
		);

		Mockito.when(
			_featureFlagPreferencesManager.isEnabled(
				Mockito.eq(companyId), Mockito.anyString())
		).thenReturn(
			!_featureFlag.isEnabled()
		);

		withPreferenceAwareFeatureFlag(
			0L,
			preferenceAwareFeatureFlag -> Assert.assertEquals(
				_featureFlag.isEnabled(),
				preferenceAwareFeatureFlag.isEnabled()));
		withPreferenceAwareFeatureFlag(
			companyId,
			preferenceAwareFeatureFlag -> Assert.assertEquals(
				!_featureFlag.isEnabled(),
				preferenceAwareFeatureFlag.isEnabled()));
	}

	protected void withPreferenceAwareFeatureFlag(
		long companyId,
		Consumer<PreferenceAwareFeatureFlag>
			preferenceAwareFeatureFlagConsumer) {

		preferenceAwareFeatureFlagConsumer.accept(
			new PreferenceAwareFeatureFlag(
				companyId, _featureFlag, _featureFlagPreferencesManager));
	}

	private FeatureFlag _featureFlag;
	private final FeatureFlagPreferencesManager _featureFlagPreferencesManager =
		Mockito.mock(FeatureFlagPreferencesManager.class);

}