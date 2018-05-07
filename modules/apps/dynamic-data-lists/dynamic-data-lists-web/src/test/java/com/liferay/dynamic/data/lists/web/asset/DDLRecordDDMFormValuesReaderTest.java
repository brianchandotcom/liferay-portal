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

package com.liferay.dynamic.data.lists.web.asset;

import com.liferay.asset.kernel.model.DDMFormValuesReader;
import com.liferay.dynamic.data.lists.web.internal.asset.DDLRecordDDMFormValuesReader;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.UnlocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordDDMFormValuesReaderTest {

	@Test
	public void testGetDDMFormFieldValues() throws Exception {
		DDMFormValuesReader ddmFormValuesReader =
			new MockDDLRecordDDMFormValuesReader(createDDMFormValues());

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesReader.getDDMFormFieldValues("text");

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 3, ddmFormFieldValues.size());
		Assert.assertEquals(
			"Text", getDDMFormFieldValueName(ddmFormFieldValues.get(0)));
		Assert.assertEquals(
			"NestedText", getDDMFormFieldValueName(ddmFormFieldValues.get(1)));
		Assert.assertEquals(
			"NestedText", getDDMFormFieldValueName(ddmFormFieldValues.get(2)));
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormField textDDMFormField = createTextDDMFormField("Text", false);

		textDDMFormField.addNestedDDMFormField(
			createTextDDMFormField("NestedText", true));

		ddmForm.addDDMFormField(textDDMFormField);

		ddmForm.addDDMFormField(createTextAreaDDMFormField("TextArea"));

		return ddmForm;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(String name) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setInstanceId(StringUtil.randomString());
		ddmFormFieldValue.setValue(
			new UnlocalizedValue(StringUtil.randomString()));

		return ddmFormFieldValue;
	}

	protected DDMFormValues createDDMFormValues() {
		DDMForm ddmForm = createDDMForm();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(LocaleUtil.US);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		DDMFormFieldValue textDDMFormFieldValue = createDDMFormFieldValue(
			"Text");

		textDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("NestedText"));
		textDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("NestedText"));

		ddmFormValues.addDDMFormFieldValue(textDDMFormFieldValue);

		ddmFormValues.addDDMFormFieldValue(createDDMFormFieldValue("TextArea"));

		return ddmFormValues;
	}

	protected DDMFormField createTextAreaDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "textarea");

		ddmFormField.setDataType("string");

		return ddmFormField;
	}

	protected DDMFormField createTextDDMFormField(
		String name, boolean repeatable) {

		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");

		return ddmFormField;
	}

	protected String getDDMFormFieldValueName(
		DDMFormFieldValue ddmFormFieldValue) {

		return ddmFormFieldValue.getName();
	}

	private static class MockDDLRecordDDMFormValuesReader
		extends DDLRecordDDMFormValuesReader {

		public MockDDLRecordDDMFormValuesReader(DDMFormValues ddmFormValues) {
			super(null);

			_ddmFormValues = ddmFormValues;
		}

		@Override
		public DDMFormValues getDDMFormValues() throws PortalException {
			return _ddmFormValues;
		}

		private final DDMFormValues _ddmFormValues;

	}

}