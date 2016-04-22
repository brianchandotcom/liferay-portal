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

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.impl.DDMFormValuesMergeImpl;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Inácio Nery
 */
public class DDMFormValuesMergeTest extends BaseDDMTestCase {

	@Before
	public void setUp() throws Exception {
		_ddmForm = DDMFormTestUtil.createDDMForm();
		_ddmFormValuesMerge = new DDMFormValuesMergeImpl();
	}

	@Test
	public void testMergeAddLocaleTextDDMFormValues() {
		String text = RandomTestUtil.randomString();

		LocalizedValue textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, text);

		DDMFormValues textFormValues = createTextDDMFormValues(
			"text", textValue);

		String textTranslator = RandomTestUtil.randomString();

		textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, text);

		textValue.addString(LocaleUtil.BRAZIL, textTranslator);

		DDMFormValues newTextFormValues = createTextDDMFormValues(
			"text", textValue);

		DDMFormValues mergeFormValues = _ddmFormValuesMerge.mergeDDMFormValues(
			newTextFormValues, textFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			mergeFormValues.getDDMFormFieldValues();

		testValues(ddmFormFieldValues, LocaleUtil.US, text);
		testValues(ddmFormFieldValues, LocaleUtil.BRAZIL, textTranslator);
	}

	@Test
	public void testMergeLocaleTextDDMFormValues() {
		String text = RandomTestUtil.randomString();

		LocalizedValue textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, text);

		DDMFormValues textFormValues = createTextDDMFormValues(
			"text", textValue);

		String textTranslator = RandomTestUtil.randomString();

		textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, "");

		textValue.addString(LocaleUtil.BRAZIL, textTranslator);

		DDMFormValues newTextFormValues = createTextDDMFormValues(
			"text", textValue);

		DDMFormValues mergeFormValues = _ddmFormValuesMerge.mergeDDMFormValues(
			newTextFormValues, textFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			mergeFormValues.getDDMFormFieldValues();

		testValues(ddmFormFieldValues, LocaleUtil.US, "");
		testValues(ddmFormFieldValues, LocaleUtil.BRAZIL, textTranslator);
	}

	@Test
	public void testMergeTextDDMFormValues() {
		String text = RandomTestUtil.randomString();

		LocalizedValue textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, text);

		DDMFormValues textFormValues = createTextDDMFormValues(
			"text", textValue);

		String newValue = RandomTestUtil.randomString();

		textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, newValue);

		DDMFormValues newTextFormValues = createTextDDMFormValues(
			"text", textValue);

		DDMFormValues mergeFormValues = _ddmFormValuesMerge.mergeDDMFormValues(
			newTextFormValues, textFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			mergeFormValues.getDDMFormFieldValues();

		testValues(ddmFormFieldValues, LocaleUtil.US, newValue);
	}

	@Test
	public void testMergeTextNestedDDMFormValues() {
		String text = RandomTestUtil.randomString();

		LocalizedValue textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, text);

		String textNested = RandomTestUtil.randomString();

		LocalizedValue textNestedValue = new LocalizedValue();

		textNestedValue.addString(LocaleUtil.US, textNested);

		DDMFormValues textFormValues = createTextWithNestedDDMFormValues(
			"text", textValue, "textNested", textNestedValue, LocaleUtil.US);

		String newText = RandomTestUtil.randomString();

		textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, newText);

		DDMFormValues separatorFormValues = createSeparatorDDMFormValues(
			"separator", textValue);

		DDMFormValues mergeFormValues = _ddmFormValuesMerge.mergeDDMFormValues(
			separatorFormValues, textFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			mergeFormValues.getDDMFormFieldValues();

		testValues(
			ddmFormFieldValues, LocaleUtil.US, text, textNested, newText);
	}

	@Test
	public void testMergeTextSeparatorDDMFormValues() {
		String text = RandomTestUtil.randomString();

		LocalizedValue textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, text);

		DDMFormValues textFormValues = createTextDDMFormValues(
			"text", textValue);

		String separatorText = RandomTestUtil.randomString();

		textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, separatorText);

		DDMFormValues separatorFormValues = createSeparatorDDMFormValues(
			"separator", textValue);

		DDMFormValues mergeFormValues = _ddmFormValuesMerge.mergeDDMFormValues(
			separatorFormValues, textFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			mergeFormValues.getDDMFormFieldValues();

		testValues(ddmFormFieldValues, LocaleUtil.US, text, separatorText);
	}

	@Test
	public void testMergeTwoTextDDMFormValues() {
		String text = RandomTestUtil.randomString();

		LocalizedValue textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, text);

		DDMFormValues textFormValues = createTextDDMFormValues(
			"text", textValue);

		String newText = RandomTestUtil.randomString();

		textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, newText);

		DDMFormValues newTextFormValues = createTextDDMFormValues(
			"newText", textValue);

		DDMFormValues mergeFormValues = _ddmFormValuesMerge.mergeDDMFormValues(
			newTextFormValues, textFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			mergeFormValues.getDDMFormFieldValues();

		testValues(ddmFormFieldValues, LocaleUtil.US, text, newText);
	}

	protected DDMFormValues createSeparatorDDMFormValues(
		String name, Value value) {

		DDMFormField ddmFormField = DDMFormTestUtil.createSeparatorDDMFormField(
			name, false);

		_ddmForm.addDDMFormField(ddmFormField);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			name, value);

		DDMFormValues formValues = DDMFormValuesTestUtil.createDDMFormValues(
			_ddmForm);

		formValues.addDDMFormFieldValue(ddmFormFieldValue);

		return formValues;
	}

	protected DDMFormValues createTextDDMFormValues(String name, Value value) {
		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			name, false, false, true);

		_ddmForm.addDDMFormField(ddmFormField);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			name, value);

		DDMFormValues formValues = DDMFormValuesTestUtil.createDDMFormValues(
			_ddmForm);

		formValues.addDDMFormFieldValue(ddmFormFieldValue);

		return formValues;
	}

	protected DDMFormValues createTextWithNestedDDMFormValues(
		String name, Value value, String nestedName, Value nestedValue,
		Locale locale) {

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			name, false, false, true);

		DDMFormField nestedDDMFormField =
			DDMFormTestUtil.createTextDDMFormField(
				nestedName, false, false, true);

		ddmFormField.addNestedDDMFormField(nestedDDMFormField);

		_ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues formValues = DDMFormValuesTestUtil.createDDMFormValues(
			_ddmForm);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			name, value);

		formValues.addDDMFormFieldValue(ddmFormFieldValue);

		ddmFormFieldValue = createDDMFormFieldValue(nestedName, nestedValue);

		formValues.addDDMFormFieldValue(ddmFormFieldValue);

		return formValues;
	}

	protected void testValues(
		List<DDMFormFieldValue> ddmFormFieldValues, Locale locale,
		String... expectedValues) {

		Assert.assertEquals(expectedValues.length, ddmFormFieldValues.size());

		for (int i = 0; i < expectedValues.length; i++) {
			Value value = ddmFormFieldValues.get(i).getValue();

			Assert.assertEquals(expectedValues[i], value.getString(locale));
		}
	}

	private DDMForm _ddmForm;
	private DDMFormValuesMerge _ddmFormValuesMerge;

}