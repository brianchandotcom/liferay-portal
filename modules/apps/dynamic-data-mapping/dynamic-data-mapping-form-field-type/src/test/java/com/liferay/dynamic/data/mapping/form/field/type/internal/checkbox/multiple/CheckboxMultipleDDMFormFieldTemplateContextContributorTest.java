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
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class CheckboxMultipleDDMFormFieldTemplateContextContributorTest {

	@Before
	public void setUp() throws Exception {
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

	private void _setUpJSONFactory() throws Exception {
		PowerMockito.field(
			CheckboxMultipleDDMFormFieldTemplateContextContributor.class,
			"jsonFactory"
		).set(
			_checkboxMultipleDDMFormFieldTemplateContextContributor,
			new JSONFactoryImpl()
		);
	}

	private final CheckboxMultipleDDMFormFieldTemplateContextContributor
		_checkboxMultipleDDMFormFieldTemplateContextContributor =
			new CheckboxMultipleDDMFormFieldTemplateContextContributor();

}