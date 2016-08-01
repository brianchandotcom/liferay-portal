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

package com.liferay.dynamic.data.mapping.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormInstanceFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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

}