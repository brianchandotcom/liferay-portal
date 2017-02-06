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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormInstanceFactoryTest {

	@Test
	public void testCreateDynamicFormWithFieldSet() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DynamicFormWithPrimitiveTypesFieldSet.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue primitiveTypesDDMFormFieldValue =
			new DDMFormFieldValue();

		primitiveTypesDDMFormFieldValue.setName("primitiveTypes");

		boolean expectedBooleanValue = true;

		primitiveTypesDDMFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"boolean", Boolean.toString(expectedBooleanValue)));

		double expectedDoubleValue = 2.5d;

		primitiveTypesDDMFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"double", Double.toString(expectedDoubleValue)));

		float expectedFloatValue = 3.5f;

		primitiveTypesDDMFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"float", Float.toString(expectedFloatValue)));

		int expectedIntegerValue = 2015;

		primitiveTypesDDMFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"integer", Integer.toString(expectedIntegerValue)));

		long expectedLongValue = 1000L;

		primitiveTypesDDMFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"long", Long.toString(expectedLongValue)));

		short expectedShortValue = 5;

		primitiveTypesDDMFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"short", Short.toString(expectedShortValue)));

		String expectedStringValue = "Frank Sinatra";

		primitiveTypesDDMFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"string", expectedStringValue));

		ddmFormValues.addDDMFormFieldValue(primitiveTypesDDMFormFieldValue);

		DynamicFormWithPrimitiveTypesFieldSet dynamicFormWithFieldSet =
			DDMFormInstanceFactory.create(
				DynamicFormWithPrimitiveTypesFieldSet.class, ddmFormValues);

		DynamicFormWithPrimitiveTypes dynamicFormWithPrimitiveTypes =
			dynamicFormWithFieldSet.primitiveTypes();

		Assert.assertEquals(
			expectedBooleanValue, dynamicFormWithPrimitiveTypes.booleanValue());
		Assert.assertEquals(
			expectedDoubleValue, dynamicFormWithPrimitiveTypes.doubleValue(),
			0.1);
		Assert.assertEquals(
			expectedFloatValue, dynamicFormWithPrimitiveTypes.floatValue(),
			0.1);
		Assert.assertEquals(
			expectedIntegerValue, dynamicFormWithPrimitiveTypes.integerValue());
		Assert.assertEquals(
			expectedLongValue, dynamicFormWithPrimitiveTypes.longValue());
		Assert.assertEquals(
			expectedShortValue, dynamicFormWithPrimitiveTypes.shortValue());
		Assert.assertEquals(
			expectedStringValue, dynamicFormWithPrimitiveTypes.stringValue());
	}

	@Test
	public void testCreateDynamicFormWithPrimitiveArrayTypes() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DynamicFormWithPrimitiveArrayTypes.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		Boolean[] expectedBooleanValues = {true, false, true};

		for (boolean expectedBooleanValue : expectedBooleanValues) {
			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					"boolean", Boolean.toString(expectedBooleanValue)));
		}

		String[] expectedStringValues = {"Nina Simone", "Billie Holiday"};

		for (String expectedStringValue : expectedStringValues) {
			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					"string", expectedStringValue));
		}

		DynamicFormWithPrimitiveArrayTypes dynamicFormWithPrimitiveArrayTypes =
			DDMFormInstanceFactory.create(
				DynamicFormWithPrimitiveArrayTypes.class, ddmFormValues);

		Assert.assertArrayEquals(
			expectedBooleanValues,
			dynamicFormWithPrimitiveArrayTypes.booleanValues());
		Assert.assertArrayEquals(
			expectedStringValues,
			dynamicFormWithPrimitiveArrayTypes.stringValues());
	}

	@Test
	public void testCreateDynamicFormWithPrimitiveArrayTypesFieldSet() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(
				DynamicFormWithRepeatablePrimitiveArrayTypesFieldSet.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue1 = new DDMFormFieldValue();

		ddmFormFieldValue1.setName("primitiveArrayTypes");

		ddmFormFieldValue1.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"boolean", "true"));
		ddmFormFieldValue1.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"boolean", "false"));

		ddmFormFieldValue1.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"string", "A"));
		ddmFormFieldValue1.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"string", "B"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue1);

		DDMFormFieldValue ddmFormFieldValue2 = new DDMFormFieldValue();

		ddmFormFieldValue2.setName("primitiveArrayTypes");

		ddmFormFieldValue2.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"boolean", "false"));
		ddmFormFieldValue2.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"boolean", "true"));

		ddmFormFieldValue2.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"string", "C"));
		ddmFormFieldValue2.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"string", "D"));
		ddmFormFieldValue2.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"string", "E"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue2);

		DynamicFormWithRepeatablePrimitiveArrayTypesFieldSet
			dynamicFormWithRepeatablePrimitiveArrayTypesFieldSet =
				DDMFormInstanceFactory.create(
					DynamicFormWithRepeatablePrimitiveArrayTypesFieldSet.class,
					ddmFormValues);

		DynamicFormWithPrimitiveArrayTypes[]
			dynamicFormWithPrimitiveArrayTypes =
				dynamicFormWithRepeatablePrimitiveArrayTypesFieldSet.
					primitiveArrayTypes();

		Assert.assertEquals(
			Arrays.toString(dynamicFormWithPrimitiveArrayTypes), 2,
			dynamicFormWithPrimitiveArrayTypes.length);

		Assert.assertArrayEquals(
			new Boolean[] {true, false},
			dynamicFormWithPrimitiveArrayTypes[0].booleanValues());
		Assert.assertArrayEquals(
			new String[] {"A", "B"},
			dynamicFormWithPrimitiveArrayTypes[0].stringValues());

		Assert.assertArrayEquals(
			new Boolean[] {false, true},
			dynamicFormWithPrimitiveArrayTypes[1].booleanValues());
		Assert.assertArrayEquals(
			new String[] {"C", "D", "E"},
			dynamicFormWithPrimitiveArrayTypes[1].stringValues());
	}

	@Test
	public void testCreateDynamicFormWithPrimitiveTypes() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DynamicFormWithPrimitiveTypes.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		boolean expectedBooleanValue = true;

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"boolean", Boolean.toString(expectedBooleanValue)));

		double expectedDoubleValue = 2.5d;

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"double", Double.toString(expectedDoubleValue)));

		float expectedFloatValue = 3.5f;

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"float", Float.toString(expectedFloatValue)));

		int expectedIntegerValue = 2015;

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"integer", Integer.toString(expectedIntegerValue)));

		long expectedLongValue = 1000L;

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"long", Long.toString(expectedLongValue)));

		short expectedShortValue = 5;

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"short", Short.toString(expectedShortValue)));

		String expectedStringValue = "Frank Sinatra";

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"string", expectedStringValue));

		DynamicFormWithPrimitiveTypes dynamicFormWithPrimitiveTypes =
			DDMFormInstanceFactory.create(
				DynamicFormWithPrimitiveTypes.class, ddmFormValues);

		Assert.assertEquals(
			expectedBooleanValue, dynamicFormWithPrimitiveTypes.booleanValue());
		Assert.assertEquals(
			expectedDoubleValue, dynamicFormWithPrimitiveTypes.doubleValue(),
			0.1);
		Assert.assertEquals(
			expectedFloatValue, dynamicFormWithPrimitiveTypes.floatValue(),
			0.1);
		Assert.assertEquals(
			expectedIntegerValue, dynamicFormWithPrimitiveTypes.integerValue());
		Assert.assertEquals(
			expectedLongValue, dynamicFormWithPrimitiveTypes.longValue());
		Assert.assertEquals(
			expectedShortValue, dynamicFormWithPrimitiveTypes.shortValue());
		Assert.assertEquals(
			expectedStringValue, dynamicFormWithPrimitiveTypes.stringValue());
	}

	@Test
	public void testCreateDynamicFormWithRepeatableFieldSet() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DynamicFormWithRepeatableFieldSet.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		String[][] expectedParameters = {
			{"Parameter 1", "Value 1"}, {"Parameter 2", "Value 2"}
		};

		for (int i = 0; i < expectedParameters.length; i++) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName("parameters");

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					"name", expectedParameters[i][0]));

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
					"value", expectedParameters[i][1]));

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		DynamicFormWithRepeatableFieldSet dynamicFormWithFieldSet =
			DDMFormInstanceFactory.create(
				DynamicFormWithRepeatableFieldSet.class, ddmFormValues);

		Parameter[] parameters = dynamicFormWithFieldSet.parameters();

		for (int i = 0; i < expectedParameters.length; i++) {
			Parameter parameter = parameters[i];

			Assert.assertEquals(expectedParameters[i][0], parameter.name());
			Assert.assertEquals(expectedParameters[i][1], parameter.value());
		}
	}

	@Test
	public void testGetDefaultValueDynamicFormWithFieldSet() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DynamicFormWithFieldSet.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DynamicFormWithFieldSet dynamicFormWithFieldSet =
			DDMFormInstanceFactory.create(
				DynamicFormWithFieldSet.class, ddmFormValues);

		Assert.assertEquals(null, dynamicFormWithFieldSet.parameter());
	}

	@Test
	public void testGetDefaultValueDynamicFormWithPrimitiveArrayTypes() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DynamicFormWithPrimitiveArrayTypes.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DynamicFormWithPrimitiveArrayTypes dynamicFormWithPrimitiveArrayTypes =
			DDMFormInstanceFactory.create(
				DynamicFormWithPrimitiveArrayTypes.class, ddmFormValues);

		Assert.assertArrayEquals(
			new String[0], dynamicFormWithPrimitiveArrayTypes.stringValues());
		Assert.assertArrayEquals(
			new Boolean[0], dynamicFormWithPrimitiveArrayTypes.booleanValues());
	}

	@Test
	public void testGetDefaultValueDynamicFormWithRepeatableFieldSet() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DynamicFormWithRepeatableFieldSet.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DynamicFormWithRepeatableFieldSet dynamicFormWithRepeatableFieldSet =
			DDMFormInstanceFactory.create(
				DynamicFormWithRepeatableFieldSet.class, ddmFormValues);

		Assert.assertArrayEquals(
			new Parameter[0], dynamicFormWithRepeatableFieldSet.parameters());
	}

	@Test
	public void testGetDefaultValueFromPrimitiveTypes() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DynamicFormWithPrimitiveTypes.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DynamicFormWithPrimitiveTypes dynamicFormWithPrimitiveTypes =
			DDMFormInstanceFactory.create(
				DynamicFormWithPrimitiveTypes.class, ddmFormValues);

		Assert.assertEquals(
			false, dynamicFormWithPrimitiveTypes.booleanValue());
		Assert.assertEquals(
			0.0d, dynamicFormWithPrimitiveTypes.doubleValue(), 0.1);
		Assert.assertEquals(
			0.0f, dynamicFormWithPrimitiveTypes.floatValue(), 0.1);
		Assert.assertEquals(0, dynamicFormWithPrimitiveTypes.integerValue());
		Assert.assertEquals(0, dynamicFormWithPrimitiveTypes.longValue());
		Assert.assertEquals(0, dynamicFormWithPrimitiveTypes.shortValue());
		Assert.assertEquals(null, dynamicFormWithPrimitiveTypes.stringValue());
	}

	@Test
	public void testGetDefaultValueFromPrimitiveTypesWithPredefinedValue() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(
				DynamicFormWithPrimitiveTypesWithPredefinedValue.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DynamicFormWithPrimitiveTypesWithPredefinedValue
			dynamicFormWithPrimitiveTypesWithPredefinedValue =
				DDMFormInstanceFactory.create(
					DynamicFormWithPrimitiveTypesWithPredefinedValue.class,
					ddmFormValues);

		Assert.assertEquals(
			true,
			dynamicFormWithPrimitiveTypesWithPredefinedValue.booleanValue());
		Assert.assertEquals(
			1.0d,
			dynamicFormWithPrimitiveTypesWithPredefinedValue.doubleValue(),
			0.1);
		Assert.assertEquals(
			1.0f, dynamicFormWithPrimitiveTypesWithPredefinedValue.floatValue(),
			0.1);
		Assert.assertEquals(
			1, dynamicFormWithPrimitiveTypesWithPredefinedValue.integerValue());
		Assert.assertEquals(
			1, dynamicFormWithPrimitiveTypesWithPredefinedValue.longValue());
		Assert.assertEquals(
			1, dynamicFormWithPrimitiveTypesWithPredefinedValue.shortValue());
		Assert.assertEquals(
			"Joe",
			dynamicFormWithPrimitiveTypesWithPredefinedValue.stringValue());
	}

	@DDMForm
	private interface DynamicFormWithFieldSet {

		@DDMFormField
		public Parameter parameter();

	}

	@DDMForm
	private interface DynamicFormWithPrimitiveArrayTypes {

		@DDMFormField(name = "boolean")
		public Boolean[] booleanValues();

		@DDMFormField(name = "string")
		public String[] stringValues();

	}

	@DDMForm
	private interface DynamicFormWithPrimitiveTypes {

		@DDMFormField(name = "boolean")
		public boolean booleanValue();

		@DDMFormField(name = "double")
		public double doubleValue();

		@DDMFormField(name = "float")
		public float floatValue();

		@DDMFormField(name = "integer")
		public int integerValue();

		@DDMFormField(name = "long")
		public long longValue();

		@DDMFormField(name = "short")
		public short shortValue();

		@DDMFormField(name = "string")
		public String stringValue();

	}

	@DDMForm
	private interface DynamicFormWithPrimitiveTypesFieldSet {

		@DDMFormField
		public DynamicFormWithPrimitiveTypes primitiveTypes();

	}

	@DDMForm
	private interface DynamicFormWithPrimitiveTypesWithPredefinedValue {

		@DDMFormField(name = "boolean", predefinedValue = "true")
		public boolean booleanValue();

		@DDMFormField(name = "double", predefinedValue = "1.0")
		public double doubleValue();

		@DDMFormField(name = "float", predefinedValue = "1.0")
		public float floatValue();

		@DDMFormField(name = "integer", predefinedValue = "1")
		public int integerValue();

		@DDMFormField(name = "long", predefinedValue = "1")
		public long longValue();

		@DDMFormField(name = "short", predefinedValue = "1")
		public short shortValue();

		@DDMFormField(name = "string", predefinedValue = "Joe")
		public String stringValue();

	}

	@DDMForm
	private interface DynamicFormWithRepeatableFieldSet {

		@DDMFormField
		public Parameter[] parameters();

	}

	@DDMForm
	private interface DynamicFormWithRepeatablePrimitiveArrayTypesFieldSet {

		@DDMFormField
		public DynamicFormWithPrimitiveArrayTypes[] primitiveArrayTypes();

	}

	@DDMForm
	private interface Parameter {

		@DDMFormField
		public String name();

		@DDMFormField
		public String value();

	}

}