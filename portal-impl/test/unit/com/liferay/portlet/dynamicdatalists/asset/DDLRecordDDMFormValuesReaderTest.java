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

package com.liferay.portlet.dynamicdatalists.asset;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.DDMFormValuesReader;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@RunWith(PowerMockRunner.class)
public class DDLRecordDDMFormValuesReaderTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDLRecord();
	}

	@Test
	public void testGetDDMFormFieldValues() throws Exception {
		DDMFormValuesReader ddmFormValuesReader =
			new DDLRecordDDMFormValuesReader(_ddlRecord);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesReader.getDDMFormFieldValues("text");

		Assert.assertEquals(3, ddmFormFieldValues.size());
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

	protected void setUpDDLRecord() throws Exception {
		DDMFormValues ddmFormValues = createDDMFormValues();

		when(
			_ddlRecord.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);
	}

	@Mock
	private DDLRecord _ddlRecord;

}