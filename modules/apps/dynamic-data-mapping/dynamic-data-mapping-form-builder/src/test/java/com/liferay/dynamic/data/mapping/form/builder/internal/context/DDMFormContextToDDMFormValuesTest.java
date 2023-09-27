/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.context;

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializerRequest;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormContextToDDMFormValuesTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_ddmFormContextToDDMFormValues = new DDMFormContextToDDMFormValues();

		ReflectionTestUtil.setFieldValue(
			_ddmFormContextToDDMFormValues, "jsonFactory",
			new JSONFactoryImpl());

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));

		Mockito.when(
			LanguageUtil.isAvailableLocale(LocaleUtil.BRAZIL)
		).thenReturn(
			true
		);
	}

	@Test
	public void testDeserializeWithLanguageDifferentThanSiteDefault()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"TextField1", true, false, false));
		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"TextField2", true, false, false));

		DDMFormContextDeserializerRequest ddmFormContextDeserializerRequest =
			DDMFormContextDeserializerRequest.with(
				ddmForm, _read("ddm-form-values.json"));

		ddmFormContextDeserializerRequest.addProperty(
			"currentLocale", LocaleUtil.BRAZIL);

		DDMFormValues ddmFormValues =
			_ddmFormContextToDDMFormValues.deserialize(
				ddmFormContextDeserializerRequest);

		Assert.assertNotEquals(LocaleUtil.getSiteDefault(), LocaleUtil.BRAZIL);
		Assert.assertEquals(
			ddmFormValues.getDefaultLocale(), LocaleUtil.BRAZIL);

		Set<Locale> availableLocales = ddmFormValues.getAvailableLocales();

		Assert.assertEquals(
			availableLocales.toString(), 1, availableLocales.size());
		Assert.assertTrue(availableLocales.contains(LocaleUtil.BRAZIL));
	}

	@Test
	public void testGetDDMFormFieldValues() throws Exception {
		List<DDMFormFieldValue> expectedDDMFormFieldValues = new ArrayList<>(2);

		expectedDDMFormFieldValues.add(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"eBvF8zup", "TextField1",
				_createLocalizedValue(LocaleUtil.BRAZIL, "Texto 1")));

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"6VYYLvfJ", "TextField2",
				_createLocalizedValue(LocaleUtil.BRAZIL, "Texto 2"));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"ZgQVb8b7", "TextField3",
				_createLocalizedValue(LocaleUtil.BRAZIL, "Texto 3")));

		expectedDDMFormFieldValues.add(ddmFormFieldValue);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"TextField1", true, false, false));

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"TextField2", true, false, false);

		ddmFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"TextField3", true, false, false));

		ddmForm.addDDMFormField(ddmFormField);

		List<DDMFormFieldValue> actualDDMFormFieldValues =
			_ddmFormContextToDDMFormValues.getDDMFormFieldValues(
				new JSONArrayImpl(_read("ddm-form-values-pages-only.json")),
				ddmForm);

		Assert.assertTrue(
			Objects.equals(
				expectedDDMFormFieldValues, actualDDMFormFieldValues));
	}

	@Test
	public void testGetLocalizedValue() {
		JSONObject jsonObject = new JSONObjectImpl();

		String enValue = StringUtil.randomString();
		String ptValue = StringUtil.randomString();

		jsonObject.put(
			"en_US", enValue
		).put(
			"pt_BR", ptValue
		);

		Value value1 = DDMFormValuesTestUtil.createLocalizedValue(
			enValue, ptValue, LocaleUtil.US);

		Value value2 = _ddmFormContextToDDMFormValues.getLocalizedValue(
			jsonObject);

		Assert.assertTrue(Objects.equals(value1, value2));
	}

	private LocalizedValue _createLocalizedValue(Locale locale, String value) {
		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(locale, value);

		return localizedValue;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private static DDMFormContextToDDMFormValues _ddmFormContextToDDMFormValues;

}