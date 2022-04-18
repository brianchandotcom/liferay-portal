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

package com.liferay.dynamic.data.mapping.form.field.type.internal.text;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldOptionsFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormFieldOptionsTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class TextDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpDDMFormFieldOptionsFactory();
	}

	@Test
	public void testGetConfirmationFieldProperties() {
		Map<String, Object> parameters =
			_textDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "text"),
				new DDMFormFieldRenderingContext());

		Assert.assertTrue(parameters.containsKey("confirmationErrorMessage"));
		Assert.assertTrue(parameters.containsKey("confirmationLabel"));
		Assert.assertTrue(parameters.containsKey("direction"));
		Assert.assertTrue(parameters.containsKey("requireConfirmation"));
	}

	@Test
	public void testGetParameters() {
		Map<String, Object> parameters =
			_textDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "text"),
				new DDMFormFieldRenderingContext());

		Assert.assertTrue(parameters.containsKey("autocompleteEnabled"));
		Assert.assertTrue(parameters.containsKey("displayStyle"));
		Assert.assertTrue(parameters.containsKey("invalidCharacters"));
		Assert.assertTrue(parameters.containsKey("normalizeField"));
		Assert.assertTrue(parameters.containsKey("placeholder"));
		Assert.assertTrue(parameters.containsKey("tooltip"));
	}

	private void _setUpDDMFormFieldOptionsFactory() {
		ReflectionTestUtil.setFieldValue(
			_textDDMFormFieldTemplateContextContributor,
			"ddmFormFieldOptionsFactory", _ddmFormFieldOptionsFactory);

		DDMFormFieldOptions ddmFormFieldOptions =
			DDMFormFieldOptionsTestUtil.createDDMFormFieldOptions();

		Mockito.when(
			_ddmFormFieldOptionsFactory.create(
				Mockito.any(DDMFormField.class),
				Mockito.any(DDMFormFieldRenderingContext.class))
		).thenReturn(
			ddmFormFieldOptions
		);
	}

	private final DDMFormFieldOptionsFactory _ddmFormFieldOptionsFactory =
		Mockito.mock(DDMFormFieldOptionsFactory.class);
	private final TextDDMFormFieldTemplateContextContributor
		_textDDMFormFieldTemplateContextContributor =
			new TextDDMFormFieldTemplateContextContributor();

}