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

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class TextDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

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
		Assert.assertTrue(parameters.containsKey("placeholder"));
		Assert.assertTrue(parameters.containsKey("tooltip"));
	}

	private void _setUpDDMFormFieldOptionsFactory() throws Exception {
		MemberMatcher.field(
			TextDDMFormFieldTemplateContextContributor.class,
			"ddmFormFieldOptionsFactory"
		).set(
			_textDDMFormFieldTemplateContextContributor,
			_ddmFormFieldOptionsFactory
		);

		DDMFormFieldOptions ddmFormFieldOptions =
			DDMFormFieldOptionsTestUtil.createDDMFormFieldOptions();

		PowerMockito.when(
			_ddmFormFieldOptionsFactory.create(
				Mockito.any(DDMFormField.class),
				Mockito.any(DDMFormFieldRenderingContext.class))
		).thenReturn(
			ddmFormFieldOptions
		);
	}

	@Mock
	private DDMFormFieldOptionsFactory _ddmFormFieldOptionsFactory;

	private final TextDDMFormFieldTemplateContextContributor
		_textDDMFormFieldTemplateContextContributor =
			new TextDDMFormFieldTemplateContextContributor();

}