/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal.configuration;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.responsive.image.configuration.ResponsiveImageConfiguration;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageSettingsProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		Mockito.when(
			_responsiveImageConfigurationHelper.getResponsiveImageConfiguration(
				_COMPANY_ID, 0)
		).thenReturn(
			_responsiveImageConfiguration
		);

		Mockito.when(
			_responsiveImageConfigurationHelper.getScopeKey(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	@Test
	public void testGetResponsiveImageSettingsChangingAnySettingInvalidatesTheCache() {
		_mockConfiguration(new String[0], new String[0]);

		String responsiveImageURLTransformerName =
			RandomTestUtil.randomString();

		Mockito.when(
			_responsiveImageConfiguration.responsiveImageURLTransformerName()
		).thenReturn(
			responsiveImageURLTransformerName
		);

		ResponsiveImageSettings responsiveImageSettings =
			_responsiveImageSettingsProvider.getResponsiveImageSettings(
				_COMPANY_ID, 0);

		Assert.assertEquals(
			responsiveImageURLTransformerName,
			responsiveImageSettings.getResponsiveImageURLTransformerName());

		Mockito.when(
			_responsiveImageConfiguration.responsiveImageURLTransformerName()
		).thenReturn(
			""
		);

		responsiveImageSettings =
			_responsiveImageSettingsProvider.getResponsiveImageSettings(
				_COMPANY_ID, 0);

		Assert.assertEquals(
			"", responsiveImageSettings.getResponsiveImageURLTransformerName());
	}

	@Test
	public void testGetResponsiveImageSettingsConfigurationOverridesOneKeyOfASystemPreset() {
		_mockConfiguration(
			new String[0],
			new String[] {
				StringBundler.concat(
					_SYSTEM_PRESET_NAMES[0], ".default.sizes=", _SIZES)
			});

		ResponsiveImageSettings.Variant variant = _getFirstVariant(
			_SYSTEM_PRESET_NAMES[0]);

		Assert.assertEquals(Integer.valueOf(960), variant.getMaxWidth());
		Assert.assertEquals(_SIZES, variant.getSizes());

		Assert.assertTrue(_isLazyLoading(_SYSTEM_PRESET_NAMES[0]));
	}

	@Test
	public void testGetResponsiveImageSettingsDeclaringNoDefaultMediaConditionStillYieldsOne() {
		_mockConfiguration(
			new String[] {_MEDIA_CONDITION_NAMES[0] + ".query=" + _QUERIES[0]},
			new String[] {
				StringBundler.concat(
					_PRESET_NAME, ".", _MEDIA_CONDITION_NAMES[0], ".sizes=",
					_SIZES)
			});

		List<ResponsiveImageSettings.Variant> variants = _getVariants(
			_PRESET_NAME);

		Assert.assertEquals(variants.toString(), 2, variants.size());
		Assert.assertEquals(_QUERIES[0], _getQuery(variants, 0));

		ResponsiveImageSettings.Variant variant = variants.get(1);

		Assert.assertNull(variant.getMaxWidth());
		Assert.assertNull(variant.getQuery());
		Assert.assertNull(variant.getSizes());

		Map<String, String> transformations = variant.getTransformations();

		Assert.assertTrue(
			transformations.toString(), transformations.isEmpty());
	}

	@Test
	public void testGetResponsiveImageSettingsDeclaringTheDefaultMediaConditionIsIgnored() {
		_mockConfiguration(
			new String[] {"default.query=" + _QUERIES[0]},
			new String[] {
				StringBundler.concat(_PRESET_NAME, ".default.sizes=", _SIZES)
			});

		List<ResponsiveImageSettings.Variant> variants = _getVariants(
			_PRESET_NAME);

		Assert.assertEquals(variants.toString(), 1, variants.size());
		Assert.assertNull(_getQuery(variants, 0));
	}

	@Test
	public void testGetResponsiveImageSettingsDefaultMediaConditionSortsLastAsTheCatchAll() {
		_mockConfiguration(
			new String[] {_MEDIA_CONDITION_NAMES[0] + ".query=" + _QUERIES[0]},
			new String[] {
				StringBundler.concat(_PRESET_NAME, ".default.sizes=", _SIZES),
				StringBundler.concat(
					_PRESET_NAME, ".", _MEDIA_CONDITION_NAMES[0], ".sizes=",
					_SIZES)
			});

		List<ResponsiveImageSettings.Variant> variants = _getVariants(
			_PRESET_NAME);

		Assert.assertEquals(variants.toString(), 2, variants.size());
		Assert.assertEquals(_QUERIES[0], _getQuery(variants, 0));
		Assert.assertNull(_getQuery(variants, 1));
	}

	@Test
	public void testGetResponsiveImageSettingsEnabledIsOffUntilConfigured() {
		_mockConfiguration(new String[0], new String[0]);

		ResponsiveImageSettings responsiveImageSettings =
			_responsiveImageSettingsProvider.getResponsiveImageSettings(
				_COMPANY_ID, 0);

		Assert.assertFalse(responsiveImageSettings.isEnabled());

		Mockito.when(
			_responsiveImageConfiguration.enabled()
		).thenReturn(
			true
		);

		responsiveImageSettings =
			_responsiveImageSettingsProvider.getResponsiveImageSettings(
				_COMPANY_ID, 0);

		Assert.assertTrue(responsiveImageSettings.isEnabled());
	}

	@Test
	public void testGetResponsiveImageSettingsLazyLoadingOfEachSystemPreset() {
		_mockConfiguration(new String[0], new String[0]);

		Assert.assertTrue(_isLazyLoading("card"));
		Assert.assertTrue(_isLazyLoading("content"));
		Assert.assertFalse(_isLazyLoading("default"));
		Assert.assertFalse(_isLazyLoading("fullWidth"));
		Assert.assertFalse(_isLazyLoading("hero"));
		Assert.assertTrue(_isLazyLoading("thumbnail"));
	}

	@Test
	public void testGetResponsiveImageSettingsMaxWidthIsParsed() {
		_mockConfiguration(
			new String[0],
			new String[] {
				StringBundler.concat(_PRESET_NAME, ".default.sizes=", _SIZES),
				_PRESET_NAME + ".default.maxWidth=320"
			});

		ResponsiveImageSettings.Variant variant = _getFirstVariant(
			_PRESET_NAME);

		Assert.assertEquals(Integer.valueOf(320), variant.getMaxWidth());
	}

	@Test
	public void testGetResponsiveImageSettingsMediaConditionIsResolvedFromTheNamedDeclaration() {
		_mockConfiguration(
			new String[] {_MEDIA_CONDITION_NAMES[0] + ".query=" + _QUERIES[0]},
			new String[] {
				StringBundler.concat(
					_PRESET_NAME, ".", _MEDIA_CONDITION_NAMES[0], ".sizes=",
					_SIZES),
				StringBundler.concat(
					_PRESET_NAME, ".", _MEDIA_CONDITION_NAMES[0],
					".transformations=crop=1:1,quality=80")
			});

		ResponsiveImageSettings.Variant variant = _getFirstVariant(
			_PRESET_NAME);

		Assert.assertEquals(_QUERIES[0], variant.getQuery());
		Assert.assertEquals(_SIZES, variant.getSizes());

		Map<String, String> transformations = variant.getTransformations();

		Assert.assertEquals(
			transformations.toString(), 2, transformations.size());
		Assert.assertEquals("1:1", transformations.get("crop"));
		Assert.assertEquals("80", transformations.get("quality"));
	}

	@Test
	public void testGetResponsiveImageSettingsSystemPresetsDoNotDeclareAutoSizes() {
		_mockConfiguration(new String[0], new String[0]);

		for (String systemPresetName : _SYSTEM_PRESET_NAMES) {
			ResponsiveImageSettings.Preset preset = _getPreset(
				systemPresetName);

			for (ResponsiveImageSettings.Variant variant :
					preset.getVariants()) {

				String sizes = variant.getSizes();

				Assert.assertFalse(systemPresetName, sizes.contains("auto"));
			}
		}
	}

	@Test
	public void testGetResponsiveImageSettingsSystemPresetsExistWithoutAnyConfiguration() {
		_mockConfiguration(new String[0], new String[0]);

		for (String presetName : _SYSTEM_PRESET_NAMES) {
			ResponsiveImageSettings.Preset preset = _getPreset(presetName);

			Assert.assertEquals(presetName, preset.getName());
		}
	}

	@Test
	public void testGetResponsiveImageSettingsSystemPresetsKeepTheirUnconditionalVariant() {
		for (String systemPresetName : _SYSTEM_PRESET_NAMES) {
			_mockConfiguration(
				new String[] {
					_MEDIA_CONDITION_NAMES[0] + ".query=" + _QUERIES[0]
				},
				new String[] {
					StringBundler.concat(
						systemPresetName, ".", _MEDIA_CONDITION_NAMES[0],
						".sizes=", _SIZES)
				});

			List<ResponsiveImageSettings.Variant> variants = _getVariants(
				systemPresetName);

			Assert.assertEquals(variants.toString(), 2, variants.size());
			Assert.assertEquals(_QUERIES[0], _getQuery(variants, 0));
			Assert.assertNull(_getQuery(variants, 1));
		}
	}

	@Test
	public void testGetResponsiveImageSettingsSystemPresetsSurviveAConfigurationThatOmitsThem() {
		_mockConfiguration(
			new String[0],
			new String[] {
				StringBundler.concat(_PRESET_NAME, ".default.sizes=", _SIZES)
			});

		for (String systemPresetName : _SYSTEM_PRESET_NAMES) {
			ResponsiveImageSettings.Preset preset = _getPreset(
				systemPresetName);

			Assert.assertEquals(systemPresetName, preset.getName());

			preset = _getPreset(_PRESET_NAME);

			Assert.assertEquals(_PRESET_NAME, preset.getName());
		}
	}

	@Test
	public void testGetResponsiveImageSettingsVariantOrderingComesFromMediaConditionDeclaration() {
		_mockConfiguration(
			new String[] {
				_MEDIA_CONDITION_NAMES[0] + ".query=" + _QUERIES[0],
				_MEDIA_CONDITION_NAMES[1] + ".query=" + _QUERIES[1]
			},
			new String[] {
				StringBundler.concat(
					_PRESET_NAME, ".", _MEDIA_CONDITION_NAMES[1], ".sizes=",
					_SIZES),
				StringBundler.concat(
					_PRESET_NAME, ".", _MEDIA_CONDITION_NAMES[0], ".sizes=",
					_SIZES)
			});

		List<ResponsiveImageSettings.Variant> variants = _getVariants(
			_PRESET_NAME);

		Assert.assertEquals(_QUERIES[0], _getQuery(variants, 0));
		Assert.assertEquals(_QUERIES[1], _getQuery(variants, 1));
	}

	@Test
	public void testGetResponsiveImageSettingsVariantsReferencingAnUndeclaredMediaConditionAreDropped() {
		_mockConfiguration(
			new String[] {_MEDIA_CONDITION_NAMES[0] + ".query=" + _QUERIES[0]},
			new String[] {
				StringBundler.concat(
					_PRESET_NAME, ".", _MEDIA_CONDITION_NAMES[1], ".sizes=",
					_SIZES),
				StringBundler.concat(
					_PRESET_NAME, ".", _MEDIA_CONDITION_NAMES[0], ".sizes=",
					_SIZES)
			});

		List<ResponsiveImageSettings.Variant> variants = _getVariants(
			_PRESET_NAME);

		// There are two variants rather than three, because the undeclared
		// condition is dropped and the unconditional one is synthesized.

		Assert.assertEquals(variants.toString(), 2, variants.size());
		Assert.assertEquals(_QUERIES[0], _getQuery(variants, 0));
		Assert.assertNull(_getQuery(variants, 1));
	}

	@Test
	public void testGetResponsiveImageSettingsWhenTheConfigurationIsUnreadable() {
		Mockito.when(
			_responsiveImageConfigurationHelper.getResponsiveImageConfiguration(
				_COMPANY_ID, 0)
		).thenReturn(
			null
		);

		Assert.assertNull(
			_responsiveImageSettingsProvider.getResponsiveImageSettings(
				_COMPANY_ID, 0));
		Assert.assertNull(
			_responsiveImageSettingsProvider.getResponsiveImageSettings(
				_COMPANY_ID, 0));
	}

	private ResponsiveImageSettings.Variant _getFirstVariant(
		String presetName) {

		List<ResponsiveImageSettings.Variant> variants = _getVariants(
			presetName);

		return variants.get(0);
	}

	private ResponsiveImageSettings.Preset _getPreset(String presetName) {
		ResponsiveImageSettings responsiveImageSettings =
			_responsiveImageSettingsProvider.getResponsiveImageSettings(
				_COMPANY_ID, 0);

		return responsiveImageSettings.getPreset(presetName);
	}

	private String _getQuery(
		List<ResponsiveImageSettings.Variant> variants, int index) {

		ResponsiveImageSettings.Variant variant = variants.get(index);

		return variant.getQuery();
	}

	private List<ResponsiveImageSettings.Variant> _getVariants(
		String presetName) {

		ResponsiveImageSettings.Preset preset = _getPreset(presetName);

		return preset.getVariants();
	}

	private boolean _isLazyLoading(String presetName) {
		ResponsiveImageSettings.Preset preset = _getPreset(presetName);

		return preset.isLazyLoading();
	}

	private void _mockConfiguration(
		String[] mediaConditions, String[] presets) {

		Mockito.when(
			_responsiveImageConfiguration.mediaConditions()
		).thenReturn(
			mediaConditions
		);

		Mockito.when(
			_responsiveImageConfiguration.presets()
		).thenReturn(
			presets
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String[] _MEDIA_CONDITION_NAMES = {
		StringUtil.removeChar(RandomTestUtil.randomString(), CharPool.PERIOD),
		StringUtil.removeChar(RandomTestUtil.randomString(), CharPool.PERIOD)
	};

	private static final String _PRESET_NAME = StringUtil.removeChar(
		RandomTestUtil.randomString(), CharPool.PERIOD);

	private static final String[] _QUERIES = RandomTestUtil.randomStrings(2);

	private static final String _SIZES = RandomTestUtil.randomString();

	private static final String[] _SYSTEM_PRESET_NAMES = {
		"card", "content", "default", "fullWidth", "hero", "thumbnail"
	};

	private final ResponsiveImageConfiguration _responsiveImageConfiguration =
		Mockito.mock(ResponsiveImageConfiguration.class);
	private final ResponsiveImageConfigurationHelper
		_responsiveImageConfigurationHelper = Mockito.mock(
			ResponsiveImageConfigurationHelper.class);
	private final ResponsiveImageSettingsProvider
		_responsiveImageSettingsProvider = new ResponsiveImageSettingsProvider(
			_responsiveImageConfigurationHelper);

}