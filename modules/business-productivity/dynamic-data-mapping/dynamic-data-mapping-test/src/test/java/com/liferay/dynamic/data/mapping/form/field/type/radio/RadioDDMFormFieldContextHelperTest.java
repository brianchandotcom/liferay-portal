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

package com.liferay.dynamic.data.mapping.form.field.type.radio;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.type.radio.RadioDDMFormFieldContextHelper;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class RadioDDMFormFieldContextHelperTest {

	@Before
	public void setUp() {
		setUpJSONFactoryUtil();
	}

	@Test
	public void testGetOptionsWithNoCheckedValueAndOnePredefinedValue() {
		List<Object> expectedOptions = new ArrayList<>();

		expectedOptions.add(createUncheckedOption("Label 1", "value 1"));
		expectedOptions.add(createUncheckedOption("Label 2", "value 2"));
		expectedOptions.add(createCheckedOption("Label 3", "value 3"));

		DDMFormFieldOptions ddmFormFieldOptions = createDDMFormFieldOptions();

		List<Object> actualOptions = getActualOptions(
			ddmFormFieldOptions, "", createPredefinedValue("[\"value 3\"]"),
			LocaleUtil.US);

		Assert.assertEquals(expectedOptions, actualOptions);
	}

	@Test
	public void testGetOptionsWithOneCheckedValueAndNoPredefinedValue() {
		List<Object> expectedOptions = new ArrayList<>();

		expectedOptions.add(createUncheckedOption("Label 1", "value 1"));
		expectedOptions.add(createCheckedOption("Label 2", "value 2"));
		expectedOptions.add(createUncheckedOption("Label 3", "value 3"));

		DDMFormFieldOptions ddmFormFieldOptions = createDDMFormFieldOptions();

		List<Object> actualOptions = getActualOptions(
			ddmFormFieldOptions, "[\"value 2\"]", createPredefinedValue(""),
			LocaleUtil.US);

		Assert.assertEquals(expectedOptions, actualOptions);
	}

	protected static void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected Map<String, String> createCheckedOption(
		String label, String value) {

		return createOption(label, "checked", value);
	}

	protected DDMFormFieldOptions createDDMFormFieldOptions() {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("value 1", LocaleUtil.US, "Label 1");
		ddmFormFieldOptions.addOptionLabel("value 2", LocaleUtil.US, "Label 2");
		ddmFormFieldOptions.addOptionLabel("value 3", LocaleUtil.US, "Label 3");

		return ddmFormFieldOptions;
	}

	protected Map<String, String> createOption(
		String label, String status, String value) {

		Map<String, String> option = new HashMap<>();

		option.put("label", label);
		option.put("status", status);
		option.put("value", value);

		return option;
	}

	protected LocalizedValue createPredefinedValue(String string) {
		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(LocaleUtil.US, string);

		return localizedValue;
	}

	protected Map<String, String> createUncheckedOption(
		String label, String value) {

		return createOption(label, StringPool.BLANK, value);
	}

	protected List<Object> getActualOptions(
		DDMFormFieldOptions ddmFormFieldOptions, String value,
		LocalizedValue predefinedValue, Locale locale) {

		RadioDDMFormFieldContextHelper radioDDMFormFieldContextHelper =
			new RadioDDMFormFieldContextHelper(
				ddmFormFieldOptions, value, predefinedValue, locale);

		return radioDDMFormFieldContextHelper.getOptions();
	}

}