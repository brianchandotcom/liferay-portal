/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.localizable.text;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gabriel Ibson
 */
public class LocalizableTextDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpJSONFactory();
		_setUpJSONFactoryUtil();
		_setUpLanguage();
		_setUpPortal();
	}

	@Test
	public void testGetAvailableLocales() {
		Map<String, Object> parameters = _getParameters();

		JSONArray availableLocalesJSONArray = (JSONArray)parameters.get(
			"availableLocales");

		Assert.assertEquals(
			_availableLocales.length, availableLocalesJSONArray.length());
	}

	@Test
	public void testGetNotDefinedPredefinedValue() {
		Map<String, Object> parameters = _getParameters();

		Assert.assertNull(parameters.get("predefinedValue"));
	}

	@Test
	public void testGetPlaceholdersSubmitLabel() {
		_mockLanguageGet();

		Map<String, Object> parameters = _getParameters();

		JSONArray placeholdersSubmitLabelJSONArray = (JSONArray)parameters.get(
			"placeholdersSubmitLabel");

		for (Locale availableLocale : _availableLocales) {
			Assert.assertThat(
				placeholdersSubmitLabelJSONArray.toString(),
				CoreMatchers.containsString(
					JSONUtil.put(
						"localeId", LocaleUtil.toLanguageId(availableLocale)
					).put(
						"placeholderSubmitLabel", "submit-form"
					).toString()));
		}
	}

	@Test
	public void testGetPredefinedValue() {
		String expectedString = StringUtil.randomString();

		LocalizedValue predefinedValue = new LocalizedValue(LocaleUtil.US);

		predefinedValue.addString(LocaleUtil.US, expectedString);

		_ddmFormField.setProperty("predefinedValue", predefinedValue);

		Map<String, Object> parameters = _getParameters();

		String actualPredefinedValue = (String)parameters.get(
			"predefinedValue");

		Assert.assertEquals(expectedString, actualPredefinedValue);
	}

	private DDMForm _getDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	private Map<String, Object> _getParameters() {
		_ddmFormField.setDDMForm(_getDDMForm());

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		return _localizableTextDDMFormFieldTemplateContextContributor.
			getParameters(_ddmFormField, ddmFormFieldRenderingContext);
	}

	private void _mockLanguageGet() {
		Mockito.when(
			language.get(Mockito.any(ResourceBundle.class), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArguments()[1]
		);
	}

	private void _setUpJSONFactory() {
		ReflectionTestUtil.setFieldValue(
			_localizableTextDDMFormFieldTemplateContextContributor,
			"jsonFactory", _jsonFactory);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguage() {
		ReflectionTestUtil.setFieldValue(
			_localizableTextDDMFormFieldTemplateContextContributor, "_language",
			language);

		Mockito.when(
			language.getAvailableLocales()
		).thenReturn(
			SetUtil.fromArray(_availableLocales)
		);
	}

	private void _setUpPortal() {
		ReflectionTestUtil.setFieldValue(
			_localizableTextDDMFormFieldTemplateContextContributor, "portal",
			_portal);
	}

	private final Locale[] _availableLocales = {
		LocaleUtil.BRAZIL, LocaleUtil.CANADA, LocaleUtil.FRANCE,
		LocaleUtil.SPAIN, LocaleUtil.US
	};
	private final DDMFormField _ddmFormField = new DDMFormField(
		"field", "localizableText");
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final LocalizableTextDDMFormFieldTemplateContextContributor
		_localizableTextDDMFormFieldTemplateContextContributor =
			new LocalizableTextDDMFormFieldTemplateContextContributor();
	private final Portal _portal = Mockito.mock(Portal.class);

}