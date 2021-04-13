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

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class SelectDDMFormFieldValueRendererAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testRenderMultipleValues() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Select", "Select", "select", "string", false, false, false);

		ddmFormField.setProperty("dataSourceType", "manual");

		int numberOfOptions = 2;

		DDMFormFieldOptions ddmFormFieldOptions = createDDMFormFieldOptions(
			numberOfOptions);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		JSONArray optionsValuesJSONArray = createOptionsValuesJSONArray(
			numberOfOptions);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Select",
				new UnlocalizedValue(optionsValuesJSONArray.toString()));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		SelectDDMFormFieldValueRenderer selectDDMFormFieldValueRenderer =
			createSelectDDMFormFieldValueRenderer();

		Assert.assertEquals(
			"option 1, option 2",
			selectDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testRenderSingleValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Select", "Select", "select", "string", false, false, false);

		ddmFormField.setProperty("dataSourceType", "manual");

		int numberOfOptions = 1;

		DDMFormFieldOptions ddmFormFieldOptions = createDDMFormFieldOptions(
			numberOfOptions);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		JSONArray optionsValuesJSONArray = createOptionsValuesJSONArray(
			numberOfOptions);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Select",
				new UnlocalizedValue(optionsValuesJSONArray.toString()));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		SelectDDMFormFieldValueRenderer selectDDMFormFieldValueRenderer =
			createSelectDDMFormFieldValueRenderer();

		Assert.assertEquals(
			"option 1",
			selectDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	protected DDMFormFieldOptions createDDMFormFieldOptions(
		int numberOfOptions) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 1; i <= numberOfOptions; i++) {
			ddmFormFieldOptions.addOptionLabel(
				"value " + i, LocaleUtil.US, "option " + i);
		}

		return ddmFormFieldOptions;
	}

	protected JSONArray createOptionsValuesJSONArray(int numberOfOptions) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (int i = 1; i <= numberOfOptions; i++) {
			jsonArray.put("value " + i);
		}

		return jsonArray;
	}

	protected SelectDDMFormFieldValueAccessor
		createSelectDDMFormFieldValueAccessor() {

		SelectDDMFormFieldValueAccessor selectDDMFormFieldValueAccessor =
			new SelectDDMFormFieldValueAccessor();

		selectDDMFormFieldValueAccessor.jsonFactory = _jsonFactory;

		return selectDDMFormFieldValueAccessor;
	}

	protected SelectDDMFormFieldValueRenderer
			createSelectDDMFormFieldValueRenderer()
		throws Exception {

		SelectDDMFormFieldValueRenderer selectDDMFormFieldValueRenderer =
			new SelectDDMFormFieldValueRenderer();

		selectDDMFormFieldValueRenderer.selectDDMFormFieldValueAccessor =
			createSelectDDMFormFieldValueAccessor();

		return selectDDMFormFieldValueRenderer;
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}