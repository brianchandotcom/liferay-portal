/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.validation;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 * @author Matheus Almeida
 */
public class ValidationDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

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

	private void _setUpJSONFactory() {
		ReflectionTestUtil.setFieldValue(
			_validationDDMFormFieldTemplateContextContributor, "jsonFactory",
			new JSONFactoryImpl());
	}

	private final ValidationDDMFormFieldTemplateContextContributor
		_validationDDMFormFieldTemplateContextContributor =
			new ValidationDDMFormFieldTemplateContextContributor();

}