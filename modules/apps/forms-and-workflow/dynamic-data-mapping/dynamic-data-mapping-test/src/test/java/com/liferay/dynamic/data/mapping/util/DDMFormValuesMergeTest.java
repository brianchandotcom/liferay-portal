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

		Assert.assertEquals(1, ddmFormFieldValues.size());

		Value value = ddmFormFieldValues.get(0).getValue();

		Assert.assertEquals(text, value.getString(LocaleUtil.US));

		Assert.assertEquals(textTranslator, value.getString(LocaleUtil.BRAZIL));
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

		Assert.assertEquals(1, ddmFormFieldValues.size());

		Value value = ddmFormFieldValues.get(0).getValue();

		Assert.assertEquals("", value.getString(LocaleUtil.US));

		Assert.assertEquals(textTranslator, value.getString(LocaleUtil.BRAZIL));
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

		Assert.assertEquals(1, ddmFormFieldValues.size());

		Value value = ddmFormFieldValues.get(0).getValue();

		Assert.assertEquals(newValue, value.getString(LocaleUtil.US));
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

		Assert.assertEquals(3, ddmFormFieldValues.size());

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Value value = ddmFormFieldValue.getValue();

			if (ddmFormFieldValue.getName().equals("text")) {
				Assert.assertEquals(text, value.getString(LocaleUtil.US));
			}
			else if (ddmFormFieldValue.getName().equals("textNested")) {
				Assert.assertEquals(textNested, value.getString(LocaleUtil.US));
			}
			else if (ddmFormFieldValue.getName().equals(("separator"))) {
				Assert.assertEquals(newText, value.getString(LocaleUtil.US));
			}else {
				Assert.fail();
			}
		}
	}

	@Test
	public void testMergeTextSeparatorDDMFormValues() {
		String text = RandomTestUtil.randomString();

		LocalizedValue textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, text);

		DDMFormValues textFormValues = createTextDDMFormValues(
			"text", textValue);

		String newtext = RandomTestUtil.randomString();

		textValue = new LocalizedValue();

		textValue.addString(LocaleUtil.US, newtext);

		DDMFormValues separatorFormValues = createSeparatorDDMFormValues(
			"separator", textValue);

		DDMFormValues mergeFormValues = _ddmFormValuesMerge.mergeDDMFormValues(
			separatorFormValues, textFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			mergeFormValues.getDDMFormFieldValues();

		Assert.assertEquals(2, ddmFormFieldValues.size());

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Value value = ddmFormFieldValue.getValue();

			if (ddmFormFieldValue.getName().equals(("text"))) {
				Assert.assertEquals(text, value.getString(LocaleUtil.US));
			}
			else if (ddmFormFieldValue.getName().equals(("separator"))) {
				Assert.assertEquals(newtext, value.getString(LocaleUtil.US));
			}else {
				Assert.fail();
			}
		}
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
			"textOther", textValue);

		DDMFormValues mergeFormValues = _ddmFormValuesMerge.mergeDDMFormValues(
			newTextFormValues, textFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			mergeFormValues.getDDMFormFieldValues();

		Assert.assertEquals(2, ddmFormFieldValues.size());

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Value value = ddmFormFieldValue.getValue();

			if (ddmFormFieldValue.getName().equals(("text"))) {
				Assert.assertEquals(text, value.getString(LocaleUtil.US));
			}
			else if (ddmFormFieldValue.getName().equals(("textOther"))) {
				Assert.assertEquals(newText, value.getString(LocaleUtil.US));
			}else {
				Assert.fail();
			}
		}
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

	private DDMForm _ddmForm;
	private DDMFormValuesMerge _ddmFormValuesMerge;

}