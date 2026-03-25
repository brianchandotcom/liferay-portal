/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagType;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Drew Brokke
 */
public class LanguageAwareFeatureFlagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_featureFlag = new FeatureFlagImpl(
			new String[0], RandomTestUtil.randomString(),
			RandomTestUtil.randomBoolean(), FeatureFlagType.BETA, "ABC-123",
			RandomTestUtil.randomString());

		_languageAwareFeatureFlag = new LanguageAwareFeatureFlag(
			_featureFlag, _language);
	}

	@Test
	public void testGetDescription() {
		String description = RandomTestUtil.randomString();

		Mockito.when(
			_language.get(
				Mockito.any(Locale.class),
				Mockito.eq(
					FeatureFlagConstants.getKey(
						_featureFlag.getKey(), "description")),
				Mockito.any(String.class))
		).thenReturn(
			description
		);

		Assert.assertEquals(
			description, _languageAwareFeatureFlag.getDescription(_locale));
	}

	@Test
	public void testGetOtherValues() {
		Assert.assertArrayEquals(
			_featureFlag.getDependencyKeys(),
			_languageAwareFeatureFlag.getDependencyKeys());
		Assert.assertEquals(
			_featureFlag.getKey(), _languageAwareFeatureFlag.getKey());
		Assert.assertEquals(
			_featureFlag.getFeatureFlagType(),
			_languageAwareFeatureFlag.getFeatureFlagType());
		Assert.assertEquals(
			_featureFlag.isEnabled(), _languageAwareFeatureFlag.isEnabled());
	}

	@Test
	public void testGetTitle() {
		String title = RandomTestUtil.randomString();

		Mockito.when(
			_language.get(
				Mockito.any(Locale.class),
				Mockito.eq(
					FeatureFlagConstants.getKey(
						_featureFlag.getKey(), "title")),
				Mockito.any(String.class))
		).thenReturn(
			title
		);

		Assert.assertEquals(title, _languageAwareFeatureFlag.getTitle(_locale));
	}

	private FeatureFlag _featureFlag;
	private final Language _language = Mockito.mock(Language.class);
	private FeatureFlag _languageAwareFeatureFlag;
	private final Locale _locale = LocaleUtil.getDefault();

}