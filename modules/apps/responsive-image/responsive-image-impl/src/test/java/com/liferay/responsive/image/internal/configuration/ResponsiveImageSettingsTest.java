/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal.configuration;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.responsive.image.constants.ResponsiveImageConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageSettingsTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetPreset() {
		String presetName = RandomTestUtil.randomString();

		ResponsiveImageSettings responsiveImageSettings =
			_newResponsiveImageSettings(
				_newPreset(presetName),
				_newPreset(ResponsiveImageConstants.PRESET_DEFAULT));

		// Blank preset name

		ResponsiveImageSettings.Preset preset =
			responsiveImageSettings.getPreset(StringPool.BLANK);

		Assert.assertEquals(
			ResponsiveImageConstants.PRESET_DEFAULT, preset.getName());

		// Null preset name

		preset = responsiveImageSettings.getPreset(null);

		Assert.assertEquals(
			ResponsiveImageConstants.PRESET_DEFAULT, preset.getName());

		// The named preset

		preset = responsiveImageSettings.getPreset(presetName);

		Assert.assertEquals(presetName, preset.getName());

		// Undeclared preset

		preset = responsiveImageSettings.getPreset(
			RandomTestUtil.randomString());

		Assert.assertEquals(
			ResponsiveImageConstants.PRESET_DEFAULT, preset.getName());
	}

	@Test
	public void testGetSizesIsTakenVerbatim() {
		String sizes = RandomTestUtil.randomString();

		ResponsiveImageSettings.Variant variant = _newVariant(sizes);

		Assert.assertEquals(sizes, variant.getSizes());

		variant = _newVariant(null);

		Assert.assertNull(variant.getSizes());
	}

	@Test
	public void testIsLazyLoadingUndeclaredMeansEager() {
		ResponsiveImageSettings.Preset preset = _newPreset(
			RandomTestUtil.randomString());

		Assert.assertFalse(preset.isLazyLoading());
	}

	private ResponsiveImageSettings.Preset _newPreset(String name) {
		return new ResponsiveImageSettings.Preset(
			null, name, Collections.emptyList());
	}

	private ResponsiveImageSettings _newResponsiveImageSettings(
		ResponsiveImageSettings.Preset... presets) {

		Map<String, ResponsiveImageSettings.Preset> presetsMap =
			new HashMap<>();

		for (ResponsiveImageSettings.Preset preset : presets) {
			presetsMap.put(preset.getName(), preset);
		}

		return new ResponsiveImageSettings(
			new TreeSet<>(Arrays.asList(RandomTestUtil.randomInt())),
			RandomTestUtil.randomString(), RandomTestUtil.randomInt(),
			Collections.emptyMap(), true, presetsMap,
			RandomTestUtil.randomString());
	}

	private ResponsiveImageSettings.Variant _newVariant(String sizes) {
		return new ResponsiveImageSettings.Variant(
			null, RandomTestUtil.randomString(), sizes,
			Collections.<String, String>emptyMap());
	}

}