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
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.language.LanguageImpl;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.service.cm.Configuration;

/**
 * @author Marcellus Tavares
 */
public class ConfigurationModelToDDMFormValuesConverterTest extends Mockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	@Test
	public void testGetValuesByConfigurationAndNegativeCardinalityWithTextField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, -2);
		whenGetDefaultValue(extendedAttributeDefinition, null);
		whenGetID(extendedAttributeDefinition, "Text");

		Configuration configuration = mock(Configuration.class);

		Dictionary<String, Object> properties = new Hashtable<>();

		Vector<String> vector = new Vector<>();

		vector.add("Joe Bloggs");
		vector.add("Ella Fitzgerald");

		properties.put("Text", vector);

		whenGetProperties(configuration, properties);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, configuration, null, null, false);

		DDMFormValues ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 2, ddmFormFieldValues.size());
		Assert.assertEquals(
			"Joe Bloggs", getValueString(ddmFormFieldValues.get(0)));
		Assert.assertEquals(
			"Ella Fitzgerald", getValueString(ddmFormFieldValues.get(1)));
	}

	@Test
	public void testGetValuesByConfigurationAndPositiveCardinalityWithTextField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 2);
		whenGetDefaultValue(extendedAttributeDefinition, null);
		whenGetID(extendedAttributeDefinition, "Text");

		Configuration configuration = mock(Configuration.class);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("Text", new String[] {"Joe Bloggs", "Ella Fitzgerald"});

		whenGetProperties(configuration, properties);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, configuration, null, null, false);

		DDMFormValues ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 2, ddmFormFieldValues.size());
		Assert.assertEquals(
			"Joe Bloggs", getValueString(ddmFormFieldValues.get(0)));
		Assert.assertEquals(
			"Ella Fitzgerald", getValueString(ddmFormFieldValues.get(1)));
	}

	@Test
	public void testGetValuesByConfigurationWithCheckboxField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Boolean");

		Configuration configuration = mock(Configuration.class);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("Boolean", Boolean.TRUE);

		whenGetProperties(configuration, properties);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, configuration, null, null, false);

		DDMFormValues ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());
		Assert.assertEquals("true", getValueString(ddmFormFieldValues.get(0)));
	}

	@Test
	public void testGetValuesByConfigurationWithPresentKey() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetDefaultValue(extendedAttributeDefinition, new String[] {"9999"});
		whenGetID(extendedAttributeDefinition, "Long");

		Configuration configuration = mock(Configuration.class);

		Dictionary<String, Object> properties = new Hashtable<>();

		whenGetProperties(configuration, properties);

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, configuration, null, null, false);

		DDMFormValues ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());
		Assert.assertEquals(
			"It should return the default value when no key is set", "9999",
			getValueString(ddmFormFieldValues.get(0)));

		properties.put("Long", 0L);

		ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		ddmFormFieldValues = ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());
		Assert.assertEquals(
			"It should return the configuration value if they key is set", "0",
			getValueString(ddmFormFieldValues.get(0)));
	}

	@Test
	public void testGetValuesByDefaultValueWithCheckboxField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Boolean");

		whenGetDefaultValue(
			extendedAttributeDefinition, new String[] {"false"});

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		DDMFormValues ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());
		Assert.assertEquals("false", getValueString(ddmFormFieldValues.get(0)));
	}

	@Test
	public void testGetValuesByDefaultValueWithSelectField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetDefaultValue(
			extendedAttributeDefinition, new String[] {"REQUEST_HEADER"});
		whenGetID(extendedAttributeDefinition, "Select");
		whenGetOptionLabels(
			extendedAttributeDefinition,
			new String[] {"COOKIE", "REQUEST_HEADER"});
		whenGetOptionValues(
			extendedAttributeDefinition,
			new String[] {"COOKIE", "REQUEST_HEADER"});

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		DDMFormValues ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());
		Assert.assertEquals(
			"[\"REQUEST_HEADER\"]", getValueString(ddmFormFieldValues.get(0)));
	}

	@Test
	public void testGetValuesByDefaultValueWithTextField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 2);
		whenGetDefaultValue(
			extendedAttributeDefinition,
			new String[] {"Joe Bloggs|Ella Fitzgerald"});
		whenGetID(extendedAttributeDefinition, "Text");

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		DDMFormValues ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 2, ddmFormFieldValues.size());
		Assert.assertEquals(
			"Joe Bloggs", getValueString(ddmFormFieldValues.get(0)));
		Assert.assertEquals(
			"Ella Fitzgerald", getValueString(ddmFormFieldValues.get(1)));
	}

	@Test
	public void testGetValuesByEmptyDefaultValueWithTextField() {
		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetDefaultValue(extendedAttributeDefinition, null);
		whenGetID(extendedAttributeDefinition, "Text");

		ConfigurationModel configurationModel = new ConfigurationModel(
			extendedObjectClassDefinition, null, null, null, false);

		DDMFormValues ddmFormValues = getDDMFormValues(
			configurationModel, getDDMForm(configurationModel));

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());
		Assert.assertEquals(
			StringPool.BLANK, getValueString(ddmFormFieldValues.get(0)));
	}

	protected DDMForm getDDMForm(ConfigurationModel configurationModel) {
		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter = spy(
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale, new EmptyResourceBundle()));

		Mockito.doReturn(
			null
		).when(
			configurationModelToDDMFormConverter
		).getConfigurationDDMForm();

		return configurationModelToDDMFormConverter.getDDMForm();
	}

	protected DDMFormValues getDDMFormValues(
		ConfigurationModel configurationModel, DDMForm ddmForm) {

		ConfigurationModelToDDMFormValuesConverter
			configurationModelToDDMFormValuesConverter =
				new ConfigurationModelToDDMFormValuesConverter(
					configurationModel, ddmForm, _enLocale,
					new EmptyResourceBundle());

		return configurationModelToDDMFormValuesConverter.getDDMFormValues();
	}

	protected String getValueString(DDMFormFieldValue ddmFormFieldValue) {
		Value value = ddmFormFieldValue.getValue();

		return value.getString(_enLocale);
	}

	protected void whenGetAttributeDefinitions(
		ExtendedObjectClassDefinition objectClassDefinition,
		ExtendedAttributeDefinition[] extendedAttributeDefinitions) {

		when(
			objectClassDefinition.getAttributeDefinitions(Matchers.anyInt())
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

	protected void whenGetDefaultValue(
		ExtendedAttributeDefinition extendedAttributeDefinition,
		String[] defaultValue) {

		when(
			extendedAttributeDefinition.getDefaultValue()
		).thenReturn(
			defaultValue
		);
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
		String[] optionLabels) {

		when(
			extendedAttributeDefinition.getOptionLabels()
		).thenReturn(
			optionLabels
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

	protected void whenGetProperties(
		Configuration configuration, Dictionary<String, Object> properties) {

		when(
			configuration.getProperties()
		).thenReturn(
			properties
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