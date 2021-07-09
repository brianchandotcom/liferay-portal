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

package com.liferay.dynamic.data.mapping.form.field.type.internal.validation;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 * @author Matheus Almeida
 */
@RunWith(PowerMockRunner.class)
public class ValidationDDMFormFieldTemplateContextContributorTest
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpJSONFactory();
	}

	@Test
	public void testGetDataTypeChanged() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties",
			HashMapBuilder.<String, Object>put(
				"validationDataType", "double"
			).build());

		Assert.assertEquals(
			"double",
			_validationDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetDataTypeDouble() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "double");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties", new HashMap<String, Object>());

		Assert.assertEquals(
			"double",
			_validationDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetDataTypeInteger() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties", new HashMap<String, Object>());

		Assert.assertEquals(
			"integer",
			_validationDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetDataTypeString() {
		DDMFormField ddmFormField = new DDMFormField("field", "text");

		ddmFormField.setProperty("dataType", "string");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties", new HashMap<String, Object>());

		Assert.assertEquals(
			"string",
			_validationDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetParameters() {
		Map<String, Object> parameters =
			_validationDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "text"),
				new DDMFormFieldRenderingContext());

		Assert.assertTrue(parameters.containsKey("dataType"));
		Assert.assertTrue(parameters.containsKey("value"));
	}

	@Test
	public void testValidationDataTypeEmpty() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties",
			HashMapBuilder.<String, Object>put(
				"validationDataType", StringPool.BLANK
			).build());

		Assert.assertEquals(
			"integer",
			_validationDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	private void _setUpJSONFactory() throws Exception {
		PowerMockito.field(
			ValidationDDMFormFieldTemplateContextContributor.class,
			"jsonFactory"
		).set(
			_validationDDMFormFieldTemplateContextContributor,
			new JSONFactoryImpl()
		);
	}

	private final ValidationDDMFormFieldTemplateContextContributor
		_validationDDMFormFieldTemplateContextContributor =
			new ValidationDDMFormFieldTemplateContextContributor();

}