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

package com.liferay.dynamic.data.mapping.form.renderer.internal.servlet;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@PrepareForTest(LanguageUtil.class)
@RunWith(PowerMockRunner.class)
public class DDMFormTemplateContextProcessorTest extends PowerMockito {

	@Before
	public void setUp() {
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();

		_ddmFormTemplateContextProcessor = new DDMFormTemplateContextProcessor(
			JSONUtil.put(
				"pages", JSONFactoryUtil.createJSONArray()
			).put(
				"rules", JSONFactoryUtil.createJSONArray()
			),
			LocaleUtil.toLanguageId(_defaultLocale));
	}

	@Test
	public void testGetDDMFormCustomField() {
		DDMFormField ddmFormField =
			_ddmFormTemplateContextProcessor.getDDMFormField(
				JSONUtil.put(
					"customProperty", 10.5
				).put(
					"fieldName", "Custom12345678"
				).put(
					"type", "custom_field"
				));

		Assert.assertEquals("Custom12345678", ddmFormField.getName());
		Assert.assertEquals(10.5, ddmFormField.getProperty("customProperty"));
		Assert.assertEquals("custom_field", ddmFormField.getType());
	}

	@Test
	public void testGetDDMFormFieldsGroup() {
		long ddmStructureId = RandomTestUtil.randomLong();
		long ddmStructureLayoutId = RandomTestUtil.randomLong();

		JSONArray rowsJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"columns",
				JSONUtil.putAll(
					JSONUtil.put(
						"fields", JSONUtil.putAll("nestedField1")
					).put(
						"size", 12
					))),
			JSONUtil.put(
				"columns",
				JSONUtil.putAll(
					JSONUtil.put(
						"fields", JSONUtil.putAll("nestedField2")
					).put(
						"size", 12
					))));

		DDMFormField ddmFormField =
			_ddmFormTemplateContextProcessor.getDDMFormField(
				JSONUtil.put(
					"ddmStructureId", ddmStructureId
				).put(
					"ddmStructureLayoutId", ddmStructureLayoutId
				).put(
					"fieldName", "FieldsGroup12345678"
				).put(
					"nestedFields",
					JSONUtil.putAll(
						JSONUtil.put("fieldName", "nestedField1"),
						JSONUtil.put("fieldName", "nestedField2"))
				).put(
					"rows", rowsJSONArray
				).put(
					"type", "fieldset"
				).put(
					"upgradedStructure", true
				));

		Assert.assertEquals("FieldsGroup12345678", ddmFormField.getName());

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		Stream<DDMFormField> nestedDDMFormFieldStream =
			nestedDDMFormFields.stream();

		Set<String> nestedDDMFormFieldNames = nestedDDMFormFieldStream.map(
			DDMFormField::getName
		).collect(
			Collectors.toSet()
		);

		Assert.assertEquals(
			nestedDDMFormFieldNames.toString(), 2,
			nestedDDMFormFieldNames.size());

		Assert.assertTrue(
			nestedDDMFormFieldNames.toString(),
			nestedDDMFormFieldNames.contains("nestedField1"));
		Assert.assertTrue(
			nestedDDMFormFieldNames.toString(),
			nestedDDMFormFieldNames.contains("nestedField2"));

		Assert.assertEquals(
			ddmStructureId, ddmFormField.getProperty("ddmStructureId"));
		Assert.assertEquals(
			ddmStructureLayoutId,
			ddmFormField.getProperty("ddmStructureLayoutId"));
		Assert.assertEquals(
			rowsJSONArray.toString(), ddmFormField.getProperty("rows"));
		Assert.assertEquals(
			true, ddmFormField.getProperty("upgradedStructure"));
		Assert.assertEquals("fieldset", ddmFormField.getType());
	}

	@Test
	public void testGetDDMFormMultipleSelectionField() {
		DDMFormField ddmFormField =
			_ddmFormTemplateContextProcessor.getDDMFormField(
				JSONUtil.put(
					"fieldName", "MultipleSelection12345678"
				).put(
					"inline", true
				).put(
					"options",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "Option 1"
						).put(
							"reference", "OptionReference1"
						).put(
							"value", "OptionValue1"
						),
						JSONUtil.put(
							"label", "Option 2"
						).put(
							"reference", "OptionReference2"
						).put(
							"value", "OptionValue2"
						))
				).put(
					"showAsSwitcher", false
				).put(
					"type", "checkbox_multiple"
				));

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		Assert.assertEquals(optionsValues.toString(), 2, optionsValues.size());

		Assert.assertTrue(
			optionsValues.toString(), optionsValues.contains("OptionValue1"));
		Assert.assertTrue(
			optionsValues.toString(), optionsValues.contains("OptionValue2"));

		Assert.assertEquals(
			"OptionReference1",
			ddmFormFieldOptions.getOptionReference("OptionValue1"));
		Assert.assertEquals(
			"OptionReference2",
			ddmFormFieldOptions.getOptionReference("OptionValue2"));

		LocalizedValue optionValue1Labels = ddmFormFieldOptions.getOptionLabels(
			"OptionValue1");

		Assert.assertEquals(
			"Option 1", optionValue1Labels.getString(_defaultLocale));

		LocalizedValue optionValue2Labels = ddmFormFieldOptions.getOptionLabels(
			"OptionValue2");

		Assert.assertEquals(
			"Option 2", optionValue2Labels.getString(_defaultLocale));

		Assert.assertEquals(
			"MultipleSelection12345678", ddmFormField.getName());
		Assert.assertEquals(true, ddmFormField.getProperty("inline"));
		Assert.assertEquals(false, ddmFormField.getProperty("showAsSwitcher"));
		Assert.assertEquals("checkbox_multiple", ddmFormField.getType());
	}

	@Test
	public void testGetDDMFormTextField() {
		DDMFormField ddmFormField =
			_ddmFormTemplateContextProcessor.getDDMFormField(
				JSONUtil.put(
					"dataType", "string"
				).put(
					"fieldName", "Text12345678"
				).put(
					"fieldReference", "TextFieldReference"
				).put(
					"label", "Text Field"
				).put(
					"localizable", true
				).put(
					"placeholder", "Placeholder"
				).put(
					"readOnly", false
				).put(
					"repeatable", true
				).put(
					"required", false
				).put(
					"tooltip", "Tooltip"
				).put(
					"type", "text"
				).put(
					"valid", false
				).put(
					"validation",
					JSONUtil.put(
						"errorMessage", "This field must not contain Test."
					).put(
						"expression",
						JSONUtil.put(
							"name", "notContains"
						).put(
							"value",
							"NOT(contains(Text12345678, \"{parameter}\"))"
						)
					).put(
						"parameter", "Test"
					)
				).put(
					"visibilityExpression", ""
				));

		Assert.assertEquals("string", ddmFormField.getDataType());

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setName("notContains");
					setValue("NOT(contains(Text12345678, \"{parameter}\"))");
				}
			});
		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			DDMFormValuesTestUtil.createLocalizedValue(
				"This field must not contain Test.", _defaultLocale));
		ddmFormFieldValidation.setParameterLocalizedValue(
			DDMFormValuesTestUtil.createLocalizedValue("Test", _defaultLocale));

		Assert.assertEquals(
			ddmFormFieldValidation, ddmFormField.getDDMFormFieldValidation());

		Assert.assertEquals(
			"TextFieldReference", ddmFormField.getFieldReference());
		Assert.assertEquals(
			_getLocalizedValue("Text Field"), ddmFormField.getLabel());
		Assert.assertEquals("Text12345678", ddmFormField.getName());
		Assert.assertEquals(
			_getLocalizedValue("Placeholder"),
			ddmFormField.getProperty("placeholder"));
		Assert.assertEquals(
			_getLocalizedValue("Tooltip"), ddmFormField.getProperty("tooltip"));
		Assert.assertEquals(false, ddmFormField.getProperty("valid"));
		Assert.assertEquals("text", ddmFormField.getType());
		Assert.assertEquals("", ddmFormField.getVisibilityExpression());
		Assert.assertEquals(true, ddmFormField.isLocalizable());
		Assert.assertEquals(false, ddmFormField.isReadOnly());
		Assert.assertEquals(true, ddmFormField.isRepeatable());
		Assert.assertEquals(false, ddmFormField.isRequired());
	}

	@Test
	public void testGetDDMFormUploadField() {
		long folderId = RandomTestUtil.randomLong();

		String guestUploadURL = RandomTestUtil.randomString();

		DDMFormField ddmFormField =
			_ddmFormTemplateContextProcessor.getDDMFormField(
				JSONUtil.put(
					"allowGuestUsers", true
				).put(
					"fieldName", "Upload12345678"
				).put(
					"folderId", folderId
				).put(
					"guestUploadURL", guestUploadURL
				).put(
					"maximumRepetitions", 7
				).put(
					"maximumSubmissionLimitReached", false
				).put(
					"type", "document_library"
				));

		Assert.assertEquals("Upload12345678", ddmFormField.getName());
		Assert.assertEquals(true, ddmFormField.getProperty("allowGuestUsers"));
		Assert.assertEquals(
			folderId, GetterUtil.getLong(ddmFormField.getProperty("folderId")));
		Assert.assertEquals(
			guestUploadURL, ddmFormField.getProperty("guestUploadURL"));
		Assert.assertEquals(7, ddmFormField.getProperty("maximumRepetitions"));
		Assert.assertEquals(
			false, ddmFormField.getProperty("maximumSubmissionLimitReached"));
		Assert.assertEquals(true, ddmFormField.getProperty("valid"));
		Assert.assertEquals("document_library", ddmFormField.getType());
		Assert.assertEquals(false, ddmFormField.isLocalizable());
		Assert.assertEquals(false, ddmFormField.isReadOnly());
		Assert.assertEquals(false, ddmFormField.isRepeatable());
		Assert.assertEquals(false, ddmFormField.isRequired());
	}

	private LocalizedValue _getLocalizedValue(String value) {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		localizedValue.addString(_defaultLocale, value);

		return localizedValue;
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguageUtil() {
		mockStatic(LanguageUtil.class);

		when(
			LanguageUtil.isAvailableLocale(Matchers.any(Locale.class))
		).thenReturn(
			Boolean.TRUE
		);
	}

	private DDMFormTemplateContextProcessor _ddmFormTemplateContextProcessor;
	private final Locale _defaultLocale = LocaleUtil.US;

}