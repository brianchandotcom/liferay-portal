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

package com.liferay.dynamic.data.mapping.type.radio;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormValuesTestUtil;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class RadioDDMFormFieldValueRendererAccessorTest {

	@Before
	public void setUp() {
		setUpJSONFactoryUtil();
	}

	@Test
	public void testGetRadioRenderedValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Radio", "Radio", "radio", "string", false, false, false);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"value 1", LocaleUtil.US, "option 1");
		ddmFormFieldOptions.addOptionLabel(
			"value 2", LocaleUtil.US, "option 2");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Radio", new UnlocalizedValue("[\"value 1\"]"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		RadioDDMFormFieldValueRendererAccessor
			radioDDMFormFieldValueRendererAccessor =
				createRadioDDMFormFieldValueRendererAccessor(LocaleUtil.US);

		Assert.assertEquals(
			"option 1",
			radioDDMFormFieldValueRendererAccessor.get(ddmFormFieldValue));
	}

	protected RadioDDMFormFieldValueRendererAccessor
		createRadioDDMFormFieldValueRendererAccessor(Locale locale) {

		RadioDDMFormFieldValueAccessor radioDDMFormFieldValueAccessor =
			new RadioDDMFormFieldValueAccessor(locale);

		return new RadioDDMFormFieldValueRendererAccessor(
			radioDDMFormFieldValueAccessor);
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

}