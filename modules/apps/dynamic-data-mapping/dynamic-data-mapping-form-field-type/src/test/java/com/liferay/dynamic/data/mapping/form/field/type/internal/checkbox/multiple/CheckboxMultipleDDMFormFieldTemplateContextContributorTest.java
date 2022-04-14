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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class CheckboxMultipleDDMFormFieldTemplateContextContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpJSONFactory();
	}

	@Test
	public void testGetPredefinedValue() {
		Map<String, Object> parameters =
			_checkboxMultipleDDMFormFieldTemplateContextContributor.
				getParameters(
					_createDDMFormField(),
					_createDDMFormFieldRenderingContext(false));

		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("Option1");
					add("Option2");
				}
			},
			parameters.get("predefinedValue"));
	}

	@Test
	public void testGetPredefinedValueInViewMode() {
		Map<String, Object> parameters =
			_checkboxMultipleDDMFormFieldTemplateContextContributor.
				getParameters(
					_createDDMFormField(),
					_createDDMFormFieldRenderingContext(true));

		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("Option1");
					add("Option2");
				}
			},
			parameters.get("predefinedValue"));
	}

	private static void _setUpJSONFactory() {
		ReflectionTestUtil.setFieldValue(
			_checkboxMultipleDDMFormFieldTemplateContextContributor,
			"jsonFactory", new JSONFactoryImpl());
	}

	private DDMFormField _createDDMFormField() {
		DDMFormField ddmFormField = new DDMFormField(
			"field", "checkbox_multiple");

		ddmFormField.setProperty(
			"predefinedValue",
			DDMFormValuesTestUtil.createLocalizedValue(
				"[\"Option1\", \"Option2\"]", LocaleUtil.US));

		return ddmFormField;
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext(
		boolean viewMode) {

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setViewMode(viewMode);

		return ddmFormFieldRenderingContext;
	}

	private static final CheckboxMultipleDDMFormFieldTemplateContextContributor
		_checkboxMultipleDDMFormFieldTemplateContextContributor =
			new CheckboxMultipleDDMFormFieldTemplateContextContributor();

}