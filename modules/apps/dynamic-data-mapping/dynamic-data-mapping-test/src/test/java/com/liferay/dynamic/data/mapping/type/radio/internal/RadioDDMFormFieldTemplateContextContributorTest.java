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

package com.liferay.dynamic.data.mapping.type.radio.internal;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
public class RadioDDMFormFieldTemplateContextContributorTest
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_radioDDMFormFieldTemplateContextContributor =
			new RadioDDMFormFieldTemplateContextContributor();

		setUpJSONFactory();
	}

	@Test
	public void testGetInline() {
		DDMFormField ddmFormField = createDDMFormField();

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormField.setProperty("inline", true);
		ddmFormField.setProperty("dataSourceType", "data-provider");

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals(true, parameters.get("inline"));
	}

	@Test
	public void testGetNotDefinedPredefinedValue() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		ddmFormFieldRenderingContext.setProperty(
			"options", createDDMFormOptions());

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals(
			StringPool.BLANK, parameters.get("predefinedValue"));
	}

	@Test
	public void testGetOptions() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		ddmFormFieldRenderingContext.setProperty(
			"options", createDDMFormOptions());

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertTrue(parameters.containsKey("options"));

		List<Object> options = (List<Object>)parameters.get("options");

		Assert.assertEquals(options.toString(), 2, options.size());

		Map option0 = (Map)options.get(0);

		Assert.assertEquals("Label 0", option0.get("label"));
		Assert.assertEquals("Value 0", option0.get("value"));

		Map option1 = (Map)options.get(1);

		Assert.assertEquals("Label 1", option1.get("label"));
		Assert.assertEquals("Value 1", option1.get("value"));
	}

	@Test
	public void testGetPredefinedValue() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		ddmFormFieldRenderingContext.setProperty(
			"options", createDDMFormOptions());

		LocalizedValue predefinedValue = new LocalizedValue(LocaleUtil.US);

		predefinedValue.addString(LocaleUtil.US, "value");

		ddmFormField.setProperty("predefinedValue", predefinedValue);

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("value", parameters.get("predefinedValue"));
	}

	@Test
	public void testGetPredefinedValueInJSONArrayFormat() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		List<Map<String, String>> keyValuePairs = new ArrayList<>();

		ddmFormFieldRenderingContext.setProperty("options", keyValuePairs);

		LocalizedValue predefinedValue = new LocalizedValue(LocaleUtil.US);

		predefinedValue.addString(LocaleUtil.US, "[\"value\"]");

		ddmFormField.setProperty("predefinedValue", predefinedValue);

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("value", parameters.get("predefinedValue"));
	}

	@Test
	public void testGetValue() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		ddmFormFieldRenderingContext.setProperty(
			"options", createDDMFormOptions());

		ddmFormFieldRenderingContext.setValue("value");

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("value", parameters.get("value"));
	}

	@Test
	public void testGetValueInJSONArrayFormat() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		ddmFormFieldRenderingContext.setProperty(
			"options", createDDMFormOptions());

		ddmFormFieldRenderingContext.setValue("[\"value\"]");

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("value", parameters.get("value"));
	}

	protected DDMFormField createDDMFormField() {
		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", false, false, false);

		return ddmFormField;
	}

	protected List<Map<String, String>> createDDMFormOptions() {
		Map<String, String> keyValuePair0 = new HashMap<>();

		keyValuePair0.put("label", "Label 0");
		keyValuePair0.put("value", "Value 0");

		Map<String, String> keyValuePair1 = new HashMap<>();

		keyValuePair1.put("label", "Label 1");
		keyValuePair1.put("value", "Value 1");

		return Arrays.asList(keyValuePair0, keyValuePair1);
	}

	protected void setUpJSONFactory() throws Exception {
		field(
			RadioDDMFormFieldTemplateContextContributor.class, "jsonFactory"
		).set(
			_radioDDMFormFieldTemplateContextContributor, _jsonFactory
		);
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private RadioDDMFormFieldTemplateContextContributor
		_radioDDMFormFieldTemplateContextContributor;

}