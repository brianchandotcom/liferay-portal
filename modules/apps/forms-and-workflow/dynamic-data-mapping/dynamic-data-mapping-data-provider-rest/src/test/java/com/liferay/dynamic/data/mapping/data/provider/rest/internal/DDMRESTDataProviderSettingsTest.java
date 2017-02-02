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

package com.liferay.dynamic.data.mapping.data.provider.rest.internal;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class DDMRESTDataProviderSettingsTest {

	@Before
	public void setUp() throws Exception {
		setUpLanguageUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			DDMRESTDataProviderSettings.class);

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			false);

		Assert.assertEquals(ddmFormFields.toString(), 8, ddmFormFields.size());

		assertCacheable(ddmFormFields.get("cacheable"));
		assertFilterable(ddmFormFields.get("filterable"));
		assertFilterParameterName(ddmFormFields.get("filterParameterName"));
		assertInputParameters(ddmFormFields.get("inputParameters"));
		assertOutputParameters(ddmFormFields.get("outputParameters"));
		assertPassword(ddmFormFields.get("password"));
		assertURL(ddmFormFields.get("url"));
		assertUsername(ddmFormFields.get("username"));
	}

	protected void assertCacheable(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("checkbox", ddmFormField.getType());
		Assert.assertEquals("boolean", ddmFormField.getDataType());
		Assert.assertEquals("true", ddmFormField.getProperty("showAsSwitcher"));
	}

	protected void assertFilterable(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("checkbox", ddmFormField.getType());
		Assert.assertEquals("boolean", ddmFormField.getDataType());
		Assert.assertEquals("true", ddmFormField.getProperty("showAsSwitcher"));
	}

	protected void assertFilterParameterName(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("text", ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());

		Map<String, Object> properties = ddmFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));
		Assert.assertTrue(properties.containsKey("tooltip"));
	}

	protected void assertInputParameters(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("fieldset", ddmFormField.getType());
		Assert.assertEquals("", ddmFormField.getDataType());

		Map<String, DDMFormField> nestedDDMFormFieldsMap =
			ddmFormField.getNestedDDMFormFieldsMap();

		Assert.assertEquals(
			nestedDDMFormFieldsMap.toString(), 3,
			nestedDDMFormFieldsMap.size());

		DDMFormField inputParameterNameDDMFormField =
			nestedDDMFormFieldsMap.get("inputParameterName");

		Assert.assertNotNull(inputParameterNameDDMFormField);

		Assert.assertEquals("text", inputParameterNameDDMFormField.getType());
		Assert.assertEquals(
			"string", inputParameterNameDDMFormField.getDataType());

		Map<String, Object> inputParameterNameDDMFormFieldProperties =
			inputParameterNameDDMFormField.getProperties();

		Assert.assertTrue(
			inputParameterNameDDMFormFieldProperties.containsKey(
				"placeholder"));

		DDMFormField inputParameterTypeDDMFormField =
			nestedDDMFormFieldsMap.get("inputParameterType");

		Assert.assertNotNull(inputParameterTypeDDMFormField);

		Assert.assertEquals("select", inputParameterTypeDDMFormField.getType());
		Assert.assertEquals(
			"string", inputParameterTypeDDMFormField.getDataType());

		Assert.assertNotNull(
			inputParameterTypeDDMFormField.getDDMFormFieldOptions());

		DDMFormFieldOptions ddmFormFieldOptions =
			inputParameterTypeDDMFormField.getDDMFormFieldOptions();

		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		Assert.assertTrue(optionValues.contains("text"));
		Assert.assertTrue(optionValues.contains("number"));

		DDMFormField inputParameterRequiredDDMFormField =
			nestedDDMFormFieldsMap.get("inputParameterRequired");

		Assert.assertNotNull(inputParameterRequiredDDMFormField);

		Assert.assertEquals(
			"checkbox", inputParameterRequiredDDMFormField.getType());
		Assert.assertEquals(
			"boolean", inputParameterRequiredDDMFormField.getDataType());
	}

	protected void assertKey(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertTrue(ddmFormField.isRequired());

		Map<String, Object> properties = ddmFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));
		Assert.assertTrue(properties.containsKey("tooltip"));
	}

	protected void assertOutputParameters(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("fieldset", ddmFormField.getType());
		Assert.assertEquals("", ddmFormField.getDataType());

		Map<String, DDMFormField> nestedDDMFormFieldsMap =
			ddmFormField.getNestedDDMFormFieldsMap();

		Assert.assertEquals(
			nestedDDMFormFieldsMap.toString(), 3,
			nestedDDMFormFieldsMap.size());

		DDMFormField outputParameterNameDDMFormField =
			nestedDDMFormFieldsMap.get("outputParameterName");

		Assert.assertNotNull(outputParameterNameDDMFormField);

		Assert.assertEquals("text", outputParameterNameDDMFormField.getType());
		Assert.assertEquals(
			"string", outputParameterNameDDMFormField.getDataType());

		Map<String, Object> outputParameterNameDDMFormFieldProperties =
			outputParameterNameDDMFormField.getProperties();

		Assert.assertTrue(
			outputParameterNameDDMFormFieldProperties.containsKey(
				"placeholder"));

		DDMFormField outputParameterPathDDMFormField =
			nestedDDMFormFieldsMap.get("outputParameterPath");

		Assert.assertNotNull(outputParameterPathDDMFormField);

		Assert.assertEquals("text", outputParameterPathDDMFormField.getType());
		Assert.assertEquals(
			"string", outputParameterPathDDMFormField.getDataType());

		Map<String, Object> outputParameterPathDDMFormFieldProperties =
			outputParameterPathDDMFormField.getProperties();

		Assert.assertTrue(
			outputParameterPathDDMFormFieldProperties.containsKey(
				"placeholder"));

		DDMFormField outputParameterTypeDDMFormField =
			nestedDDMFormFieldsMap.get("outputParameterType");

		Assert.assertNotNull(outputParameterTypeDDMFormField);

		Assert.assertEquals(
			"select", outputParameterTypeDDMFormField.getType());
		Assert.assertEquals(
			"string", outputParameterTypeDDMFormField.getDataType());

		Assert.assertNotNull(
			outputParameterTypeDDMFormField.getDDMFormFieldOptions());

		DDMFormFieldOptions ddmFormFieldOptions =
			outputParameterTypeDDMFormField.getDDMFormFieldOptions();

		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		Assert.assertTrue(optionValues.contains("text"));
		Assert.assertTrue(optionValues.contains("number"));
	}

	protected void assertPassword(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("text", ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());

		Map<String, Object> properties = ddmFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));
		Assert.assertTrue(properties.containsKey("tooltip"));
	}

	protected void assertURL(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertTrue(ddmFormField.isRequired());

		Assert.assertEquals("text", ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());

		Map<String, Object> properties = ddmFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));
	}

	protected void assertUsername(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("text", ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());

		Map<String, Object> properties = ddmFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));
		Assert.assertTrue(properties.containsKey("tooltip"));
	}

	protected void assertValue(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertTrue(ddmFormField.isRequired());

		Assert.assertEquals("text", ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());

		Map<String, Object> properties = ddmFormField.getProperties();

		Assert.assertTrue(properties.containsKey("placeholder"));
		Assert.assertTrue(properties.containsKey("tooltip"));
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = mock(Language.class);

		languageUtil.setLanguage(language);
	}

	protected void setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

}