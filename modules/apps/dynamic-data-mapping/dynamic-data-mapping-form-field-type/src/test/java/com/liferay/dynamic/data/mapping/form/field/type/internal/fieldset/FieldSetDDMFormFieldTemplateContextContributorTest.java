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

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class FieldSetDDMFormFieldTemplateContextContributorTest {

	@Test
	public void testGetColumnSizeWithNestedFields() {
		FieldSetDDMFormFieldTemplateContextContributor
			fieldSetDDMFormFieldTemplateContextContributor =
				new FieldSetDDMFormFieldTemplateContextContributor();

		int columnSize =
			fieldSetDDMFormFieldTemplateContextContributor.getColumnSize(
				2, "horizontal");

		Assert.assertEquals(6, columnSize);
	}

	@Test
	public void testGetColumnSizeWithoutNestedFields() {
		FieldSetDDMFormFieldTemplateContextContributor
			fieldSetDDMFormFieldTemplateContextContributor =
				new FieldSetDDMFormFieldTemplateContextContributor();

		int columnSize =
			fieldSetDDMFormFieldTemplateContextContributor.getColumnSize(
				0, "horizontal");

		Assert.assertEquals(0, columnSize);
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

		Map<String, Object> nestedField0 = HashMapBuilder.<String, Object>put(
			"name", "field0"
		).put(
			"type", "text"
		).build();

		Map<String, Object> nestedField1 = HashMapBuilder.<String, Object>put(
			"name", "field1"
		).put(
			"type", "checkbox"
		).build();

		Map<String, List<Object>> nestedFields =
			HashMapBuilder.<String, List<Object>>put(
				"field0", Arrays.<Object>asList(nestedField0)
			).put(
				"field1", Arrays.<Object>asList(nestedField1)
			).build();

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"nestedFields", nestedFields
		).build();

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

		Map<String, Object> nestedField0 = HashMapBuilder.<String, Object>put(
			"name", "field0"
		).put(
			"type", "text"
		).build();

		Map<String, Object> nestedField1 = HashMapBuilder.<String, Object>put(
			"name", "field1"
		).put(
			"type", "checkbox"
		).build();

		Map<String, Object> nestedField2 = HashMapBuilder.<String, Object>put(
			"name", "field2"
		).put(
			"type", "select"
		).build();

		Map<String, List<Object>> nestedFields =
			HashMapBuilder.<String, List<Object>>put(
				"field0", Arrays.<Object>asList(nestedField0)
			).put(
				"field1", Arrays.<Object>asList(nestedField1)
			).put(
				"field2", Arrays.<Object>asList(nestedField2)
			).build();

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"nestedFields", nestedFields
		).build();

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