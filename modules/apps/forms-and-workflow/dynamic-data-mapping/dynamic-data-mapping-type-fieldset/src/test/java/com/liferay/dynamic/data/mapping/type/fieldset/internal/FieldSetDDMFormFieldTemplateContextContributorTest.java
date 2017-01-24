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

package com.liferay.dynamic.data.mapping.type.fieldset.internal;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class FieldSetDDMFormFieldTemplateContextContributorTest {

	@Test
	public void testGetColumnSizeWithEmptyList() {
		FieldSetDDMFormFieldTemplateContextContributor
			fieldSetDDMFormFieldTemplateContextContributor =
				new FieldSetDDMFormFieldTemplateContextContributor();

		int columnSize =
			fieldSetDDMFormFieldTemplateContextContributor.getColumnSize(
				new ArrayList<>(), "horizontal");

		Assert.assertEquals(0, columnSize);
	}

	@Test
	public void testGetColumnSizeWithList() {
		FieldSetDDMFormFieldTemplateContextContributor
			fieldSetDDMFormFieldTemplateContextContributor =
				new FieldSetDDMFormFieldTemplateContextContributor();

		int columnSize =
			fieldSetDDMFormFieldTemplateContextContributor.getColumnSize(
				Arrays.asList(new Object(), new Object()), "horizontal");

		Assert.assertEquals(6, columnSize);
	}

	@Test
	public void testGetParametersWithHorizontalFieldSet() {
		FieldSetDDMFormFieldTemplateContextContributor
			fieldSetDDMFormFieldTemplateContextContributor =
				new FieldSetDDMFormFieldTemplateContextContributor();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"field0", "Field 0", "text", "string", false, false, false);

		Map<String, Object> ddmFormFieldProperties =
			ddmFormField.getProperties();

		ddmFormFieldProperties.put("orientation", "horizontal");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		Map<String, Object> nestedField0 = new HashMap<>();

		nestedField0.put("name", "field1");
		nestedField0.put("type", "text");

		Map<String, Object> nestedField1 = new HashMap<>();

		nestedField1.put("name", "field2");
		nestedField1.put("type", "checkbox");

		List<Object> nestedFields = new ArrayList<>();

		nestedFields.add(nestedField0);
		nestedFields.add(nestedField1);

		Map<String, Object> properties = new HashMap<>();

		properties.put("nestedFields", nestedFields);

		ddmFormFieldRenderingContext.setProperties(properties);

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		Map<String, Object> parameters =
			fieldSetDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertTrue(parameters.containsKey("showLabel"));

		Assert.assertEquals(true, parameters.get("showLabel"));

		Assert.assertTrue(parameters.containsKey("columnSize"));

		Assert.assertEquals(6, parameters.get("columnSize"));

		Assert.assertTrue(parameters.containsKey("label"));

		Assert.assertEquals("Field 0", parameters.get("label"));
	}

	@Test
	public void testGetParametersWithVerticalFieldSet() {
		FieldSetDDMFormFieldTemplateContextContributor
			fieldSetDDMFormFieldTemplateContextContributor =
				new FieldSetDDMFormFieldTemplateContextContributor();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"field0", "", "text", "string", false, false, false);

		ddmFormField.setLabel(null);

		Map<String, Object> ddmFormFieldProperties =
			ddmFormField.getProperties();

		ddmFormFieldProperties.put("orientation", "vertical");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		Map<String, Object> nestedField0 = new HashMap<>();

		nestedField0.put("name", "field1");
		nestedField0.put("type", "text");

		Map<String, Object> nestedField1 = new HashMap<>();

		nestedField1.put("name", "field2");
		nestedField1.put("type", "checkbox");

		Map<String, Object> nestedField2 = new HashMap<>();

		nestedField2.put("name", "field3");
		nestedField2.put("type", "select");

		List<Object> nestedFields = new ArrayList<>();

		nestedFields.add(nestedField0);
		nestedFields.add(nestedField1);
		nestedFields.add(nestedField2);

		Map<String, Object> properties = new HashMap<>();

		properties.put("nestedFields", nestedFields);

		ddmFormFieldRenderingContext.setProperties(properties);

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		Map<String, Object> parameters =
			fieldSetDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertFalse(parameters.containsKey("showLabel"));
		Assert.assertTrue(parameters.containsKey("columnSize"));

		Assert.assertEquals(12, parameters.get("columnSize"));

		Assert.assertFalse(parameters.containsKey("label"));
	}

}