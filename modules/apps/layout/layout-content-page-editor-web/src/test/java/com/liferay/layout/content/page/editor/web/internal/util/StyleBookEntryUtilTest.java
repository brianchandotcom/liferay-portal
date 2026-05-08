/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.style.book.model.StyleBookEntry;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gabriel Lima
 */
public class StyleBookEntryUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetFrontendTokensValuesLegacyBareNameOverride()
		throws Exception {

		Map<String, Object> tokenValues =
			StyleBookEntryUtil.getFrontendTokensValues(
				_mockFrontendTokenDefinition(_THEME_ID),
				LocaleUtil.getDefault(),
				_mockStyleBookEntry(
					JSONUtil.put(
						"successColor",
						JSONUtil.put(
							"cssVariableMapping", "success"
						).put(
							"value", "#34F787"
						)
					).toString()));

		_assertTokenValue("#34F787", "successColor", tokenValues);
	}

	@Test
	public void testGetFrontendTokensValuesNamespacedOverride()
		throws Exception {

		Map<String, Object> tokenValues =
			StyleBookEntryUtil.getFrontendTokensValues(
				_mockFrontendTokenDefinition(_THEME_ID),
				LocaleUtil.getDefault(),
				_mockStyleBookEntry(
					JSONUtil.put(
						_THEME_ID + ":successColor",
						JSONUtil.put(
							"cssVariableMapping", "success"
						).put(
							"tokenDefinitionId", _THEME_ID
						).put(
							"value", "#34F787"
						)
					).toString()));

		_assertTokenValue("#34F787", "successColor", tokenValues);
	}

	@Test
	public void testGetFrontendTokensValuesNamespacedTakesPrecedenceOverLegacy()
		throws Exception {

		Map<String, Object> tokenValues =
			StyleBookEntryUtil.getFrontendTokensValues(
				_mockFrontendTokenDefinition(_THEME_ID),
				LocaleUtil.getDefault(),
				_mockStyleBookEntry(
					JSONUtil.put(
						_THEME_ID + ":successColor",
						JSONUtil.put(
							"cssVariableMapping", "success"
						).put(
							"value", "#NEWVAL"
						)
					).put(
						"successColor",
						JSONUtil.put(
							"cssVariableMapping", "success"
						).put(
							"value", "#OLDVAL"
						)
					).toString()));

		_assertTokenValue("#NEWVAL", "successColor", tokenValues);
	}

	@Test
	public void testGetFrontendTokensValuesNoOverrideReturnsDefault()
		throws Exception {

		Map<String, Object> tokenValues =
			StyleBookEntryUtil.getFrontendTokensValues(
				_mockFrontendTokenDefinition(_THEME_ID),
				LocaleUtil.getDefault(), _mockStyleBookEntry("{}"));

		_assertTokenValue(_DEFAULT_VALUE, "successColor", tokenValues);
	}

	@Test
	public void testGetFrontendTokensValuesNullDefinitionReturnsEmptyMap()
		throws Exception {

		Map<String, Object> tokenValues =
			StyleBookEntryUtil.getFrontendTokensValues(
				null, LocaleUtil.getDefault(), _mockStyleBookEntry("{}"));

		Assert.assertTrue(tokenValues.isEmpty());
	}

	@Test
	public void testGetFrontendTokensValuesNullStyleBookEntryReturnsDefaults()
		throws Exception {

		Map<String, Object> tokenValues =
			StyleBookEntryUtil.getFrontendTokensValues(
				_mockFrontendTokenDefinition(_THEME_ID),
				LocaleUtil.getDefault(), null);

		_assertTokenValue(_DEFAULT_VALUE, "successColor", tokenValues);
	}

	@Test
	public void testGetFrontendTokensValuesNullThemeIdFallsBackToBareName()
		throws Exception {

		Map<String, Object> tokenValues =
			StyleBookEntryUtil.getFrontendTokensValues(
				_mockFrontendTokenDefinition(null), LocaleUtil.getDefault(),
				_mockStyleBookEntry(
					JSONUtil.put(
						"successColor",
						JSONUtil.put(
							"cssVariableMapping", "success"
						).put(
							"value", "#34F787"
						)
					).toString()));

		_assertTokenValue("#34F787", "successColor", tokenValues);
	}

	private void _assertTokenValue(
		String expectedValue, String name, Map<String, Object> tokenValues) {

		Assert.assertNotNull("Missing token: " + name, tokenValues.get(name));

		@SuppressWarnings("unchecked")
		Map<String, Object> tokenValue = (Map<String, Object>)tokenValues.get(
			name);

		Assert.assertEquals(expectedValue, tokenValue.get("value"));
	}

	private FrontendTokenDefinition _mockFrontendTokenDefinition(String themeId)
		throws Exception {

		FrontendTokenDefinition frontendTokenDefinition = Mockito.mock(
			FrontendTokenDefinition.class);

		Mockito.when(
			frontendTokenDefinition.getThemeId()
		).thenReturn(
			themeId
		);

		JSONObject frontendTokenDefinitionJSONObject = JSONUtil.put(
			"frontendTokenCategories",
			JSONUtil.putAll(
				JSONUtil.put(
					"frontendTokenSets",
					JSONUtil.putAll(
						JSONUtil.put(
							"frontendTokens",
							JSONUtil.putAll(
								JSONUtil.put(
									"defaultValue", _DEFAULT_VALUE
								).put(
									"editorType", "ColorPicker"
								).put(
									"label", "success"
								).put(
									"mappings",
									JSONUtil.putAll(
										JSONUtil.put(
											"type", "cssVariable"
										).put(
											"value", "success"
										))
								).put(
									"name", "successColor"
								))
						).put(
							"label", "Theme Colors"
						))
				).put(
					"label", "Color System"
				)));

		Mockito.when(
			frontendTokenDefinition.getJSONObject(Mockito.any(Locale.class))
		).thenReturn(
			frontendTokenDefinitionJSONObject
		);

		return frontendTokenDefinition;
	}

	private StyleBookEntry _mockStyleBookEntry(String frontendTokensValues) {
		StyleBookEntry styleBookEntry = Mockito.mock(StyleBookEntry.class);

		Mockito.when(
			styleBookEntry.getFrontendTokensValues()
		).thenReturn(
			frontendTokensValues
		);

		return styleBookEntry;
	}

	private static final String _DEFAULT_VALUE = "#287d3c";

	private static final String _THEME_ID = "classic_WAR_classictheme";

}