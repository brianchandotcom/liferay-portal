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
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Locale;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.service.cm.Configuration;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesToPropertiesConverterTest extends Mockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_jsonFactory = new JSONFactoryImpl();
	}

	@Test
	public void testArrayBooleanValues() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Boolean", "Boolean", DDMFormFieldType.CHECKBOX, "boolean", false,
			true, false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "false", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));

		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 3);
		whenGetID(extendedAttributeDefinition, "Boolean");

		ConfigurationModel configurationModel = new ConfigurationModel(
			null, null, configuration, extendedObjectClassDefinition, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Object value = properties.get("Boolean");

		Boolean[] booleanValues = (Boolean[])value;

		Assert.assertTrue(booleanValues[0]);
		Assert.assertFalse(booleanValues[1]);
		Assert.assertTrue(booleanValues[2]);
	}

	@Test
	public void testEmptyStringValue() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"String", "String", DDMFormFieldType.TEXT, "string", false, true,
			false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("String", "", _enLocale));

		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 1);
		whenGetID(extendedAttributeDefinition, "String");

		ConfigurationModel configurationModel = new ConfigurationModel(
			null, null, configuration, extendedObjectClassDefinition, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Object value = properties.get("String");

		String[] stringValues = (String[])value;

		Assert.assertEquals(
			Arrays.toString(stringValues), 0, stringValues.length);
	}

	@Test
	public void testEmptyValueArrayStringValues() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"String", "String", DDMFormFieldType.TEXT, "string", false, true,
			false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("String", "A", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("String", "", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("String", "B", _enLocale));

		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 3);
		whenGetID(extendedAttributeDefinition, "String");

		ConfigurationModel configurationModel = new ConfigurationModel(
			null, null, configuration, extendedObjectClassDefinition, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Object value = properties.get("String");

		String[] stringValues = (String[])value;

		Assert.assertEquals(
			Arrays.toString(stringValues), 2, stringValues.length);
	}

	@Test
	public void testSimpleBooleanValue() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Boolean", "Boolean", DDMFormFieldType.CHECKBOX, "boolean", false,
			false, false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));

		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Boolean");

		ConfigurationModel configurationModel = new ConfigurationModel(
			null, null, configuration, extendedObjectClassDefinition, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Assert.assertTrue((boolean)properties.get("Boolean"));
	}

	@Test
	public void testSimpleIntegerInvalidValue() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField integerDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Integer", "Integer", DDMFormFieldType.TEXT, "integer", false,
			false, false);

		ddmForm.addDDMFormField(integerDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Integer", "42f", _enLocale));

		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Integer");

		ConfigurationModel configurationModel = new ConfigurationModel(
			null, null, configuration, extendedObjectClassDefinition, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Assert.assertEquals(0, properties.get("Integer"));
	}

	@Test
	public void testSimpleIntegerValue() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField integerDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Integer", "Integer", DDMFormFieldType.TEXT, "integer", false,
			false, false);

		ddmForm.addDDMFormField(integerDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Integer", "42", _enLocale));

		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Integer");

		ConfigurationModel configurationModel = new ConfigurationModel(
			null, null, configuration, extendedObjectClassDefinition, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Assert.assertEquals(42, properties.get("Integer"));
	}

	@Test
	public void testSimpleSelectValue() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField integerDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Select", "Select", DDMFormFieldType.SELECT, "integer", false,
			false, false);

		ddmForm.addDDMFormField(integerDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Select", "[\"42\"]", _enLocale));

		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, 0);
		whenGetID(extendedAttributeDefinition, "Select");

		ConfigurationModel configurationModel = new ConfigurationModel(
			null, null, configuration, extendedObjectClassDefinition, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Assert.assertEquals(42, properties.get("Select"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testVectorBooleanValues() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Boolean", "Boolean", DDMFormFieldType.CHECKBOX, "boolean", false,
			true, false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "false", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));

		ExtendedObjectClassDefinition extendedObjectClassDefinition = mock(
			ExtendedObjectClassDefinition.class);

		ExtendedAttributeDefinition extendedAttributeDefinition = mock(
			ExtendedAttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenGetAttributeDefinitions(
			extendedObjectClassDefinition,
			new ExtendedAttributeDefinition[] {extendedAttributeDefinition});

		whenGetCardinality(extendedAttributeDefinition, -3);
		whenGetID(extendedAttributeDefinition, "Boolean");

		ConfigurationModel configurationModel = new ConfigurationModel(
			null, null, configuration, extendedObjectClassDefinition, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Object value = properties.get("Boolean");

		Vector<Boolean> booleanValues = (Vector<Boolean>)value;

		Assert.assertTrue(booleanValues.get(0));
		Assert.assertFalse(booleanValues.get(1));
		Assert.assertTrue(booleanValues.get(2));
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String name, String valueString, Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setInstanceId(StringUtil.randomString());

		Value value = new LocalizedValue(locale);

		value.addString(locale, valueString);

		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected void whenGetAttributeDefinitions(
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		ExtendedAttributeDefinition[] extendedAttributeDefinitions) {

		when(
			extendedObjectClassDefinition.getAttributeDefinitions(
				Matchers.anyInt())
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

	protected void whenGetID(
		ExtendedAttributeDefinition extendedAttributeDefinition, String id) {

		when(
			extendedAttributeDefinition.getID()
		).thenReturn(
			id
		);
	}

	private final Locale _enLocale = LocaleUtil.US;
	private JSONFactory _jsonFactory;

}