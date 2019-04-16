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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.language.LanguageImpl;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Marcellus Tavares
 */
public class ConfigurationModelToDDMFormConverterTest extends Mockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	@Test
	public void testGetWithCheckboxField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.ALL);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition, new ExtendedAttributeDefinition[0],
			ExtendedObjectClassDefinition.OPTIONAL);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.REQUIRED);
		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Boolean");
		whenGetType(
			extendedAttributeDefinition, ExtendedAttributeDefinition.BOOLEAN);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter = spy(
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale, new EmptyResourceBundle()));

		whenGetConfigurationDDMForm(configurationModelToDDMFormConverter, null);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Boolean");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.CHECKBOX, ddmFormField.getType());
		Assert.assertEquals("boolean", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertFalse(ddmFormField.isRequired());

		Value predefinedValue = ddmFormField.getPredefinedValue();

		Assert.assertEquals("false", predefinedValue.getString(_enLocale));
	}

	@Test
	public void testGetWithIntegerField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.ALL);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition, new ExtendedAttributeDefinition[0],
			ExtendedObjectClassDefinition.OPTIONAL);
		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.REQUIRED);
		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Integer");
		whenGetType(
			extendedAttributeDefinition, ExtendedAttributeDefinition.INTEGER);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter = spy(
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale, new EmptyResourceBundle()));

		whenGetConfigurationDDMForm(configurationModelToDDMFormConverter, null);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Integer");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.TEXT, ddmFormField.getType());
		Assert.assertEquals("integer", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertTrue(ddmFormField.isRequired());

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		Assert.assertEquals(_enLocale, predefinedValue.getDefaultLocale());
		Assert.assertEquals("0", predefinedValue.getString(_enLocale));
	}

	@Test
	public void testGetWithPasswordField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.ALL);
		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.OPTIONAL);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition, new ExtendedAttributeDefinition[0],
			ExtendedObjectClassDefinition.REQUIRED);
		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Password");
		whenGetType(
			extendedAttributeDefinition, ExtendedAttributeDefinition.PASSWORD);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter = spy(
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale, new EmptyResourceBundle()));

		whenGetConfigurationDDMForm(configurationModelToDDMFormConverter, null);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Password");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.PASSWORD, ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertFalse(ddmFormField.isRequired());
	}

	@Test
	public void testGetWithSelectField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.ALL);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition, new ExtendedAttributeDefinition[0],
			ExtendedObjectClassDefinition.OPTIONAL);
		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.REQUIRED);
		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Select");
		whenGetType(
			extendedAttributeDefinition, ExtendedAttributeDefinition.STRING);
		whenGetOptionLabels(
			extendedAttributeDefinition, new String[] {"Label 1", "Label 2"});
		whenGetOptionValues(
			extendedAttributeDefinition, new String[] {"Value 1", "Value 2"});

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter = spy(
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale, new EmptyResourceBundle()));

		whenGetConfigurationDDMForm(configurationModelToDDMFormConverter, null);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Select");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.SELECT, ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertTrue(ddmFormField.isRequired());

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Assert.assertEquals(_enLocale, ddmFormFieldOptions.getDefaultLocale());

		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		Assert.assertTrue(
			optionValues.toString(), optionValues.contains("Value 1"));
		Assert.assertTrue(
			optionValues.toString(), optionValues.contains("Value 2"));

		LocalizedValue value1Labels = ddmFormFieldOptions.getOptionLabels(
			"Value 1");

		Assert.assertEquals(_enLocale, value1Labels.getDefaultLocale());
		Assert.assertEquals("Label 1", value1Labels.getString(_enLocale));

		LocalizedValue value2Labels = ddmFormFieldOptions.getOptionLabels(
			"Value 2");

		Assert.assertEquals(_enLocale, value2Labels.getDefaultLocale());
		Assert.assertEquals("Label 2", value2Labels.getString(_enLocale));
	}

	@Test
	public void testGetWithTextField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.ALL);
		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.OPTIONAL);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition, new ExtendedAttributeDefinition[0],
			ExtendedObjectClassDefinition.REQUIRED);
		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Text");
		whenGetType(
			extendedAttributeDefinition, ExtendedAttributeDefinition.STRING);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter = spy(
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale, new EmptyResourceBundle()));

		whenGetConfigurationDDMForm(configurationModelToDDMFormConverter, null);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Text");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.TEXT, ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertFalse(ddmFormField.isRequired());
	}

	@Test
	public void testRequiredInputWithTextField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.ALL);
		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition},
			ExtendedObjectClassDefinition.OPTIONAL);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition, new ExtendedAttributeDefinition[0],
			ExtendedObjectClassDefinition.REQUIRED);
		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Text");
		whenGetRequiredInput(extendedAttributeDefinition, true);
		whenGetType(
			extendedAttributeDefinition, ExtendedAttributeDefinition.STRING);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter = spy(
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale, new EmptyResourceBundle()));

		whenGetConfigurationDDMForm(configurationModelToDDMFormConverter, null);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Text");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.TEXT, ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertTrue(ddmFormField.isRequired());
	}

	protected void whenGetAttributeDefinitions(
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		ExtendedAttributeDefinition[] extendedAttributeDefinitions,
		int filter) {

		when(
			extendedObjectClassDefinition.getAttributeDefinitions(
				Matchers.eq(filter))
		).thenReturn(
			extendedAttributeDefinitions
		);
	}

	protected void whenGetCardinality(
		ExtendedAttributeDefinition extendedAttributeDefinition,
		int cardinality) {

		when(
			extendedAttributeDefinition.getCardinality()
		).thenReturn(
			cardinality
		);
	}

	protected void whenGetConfigurationDDMForm(
		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter,
		DDMForm ddmForm) {

		Mockito.doReturn(
			ddmForm
		).when(
			configurationModelToDDMFormConverter
		).getConfigurationDDMForm();
	}

	protected void whenGetID(
		ExtendedAttributeDefinition extendedAttributeDefinition, String id) {

		when(
			extendedAttributeDefinition.getID()
		).thenReturn(
			id
		);
	}

	protected void whenGetOptionLabels(
		ExtendedAttributeDefinition extendedAttributeDefinition,
		String[] returnOptionLabels) {

		when(
			extendedAttributeDefinition.getOptionLabels()
		).thenReturn(
			returnOptionLabels
		);
	}

	protected void whenGetOptionValues(
		ExtendedAttributeDefinition extendedAttributeDefinition,
		String[] optionValues) {

		when(
			extendedAttributeDefinition.getOptionValues()
		).thenReturn(
			optionValues
		);
	}

	protected void whenGetRequiredInput(
		ExtendedAttributeDefinition extendedAttributeDefinition,
		boolean requiredInput) {

		when(
			extendedAttributeDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedAttributeDefinition.XML_NAMESPACE)
		).thenReturn(
			MapUtil.fromArray("required-input", String.valueOf(requiredInput))
		);
	}

	protected void whenGetType(
		ExtendedAttributeDefinition extendedAttributeDefinition, int type) {

		when(
			extendedAttributeDefinition.getType()
		).thenReturn(
			type
		);
	}

	private final Locale _enLocale = LocaleUtil.US;

	private static class EmptyResourceBundle extends ListResourceBundle {

		@Override
		protected Object[][] getContents() {
			return new Object[0][];
		}

	}

}