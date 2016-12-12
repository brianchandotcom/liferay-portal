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

		Assert.assertEquals(2, dynamicFormWithPrimitiveArrayTypes.length);

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