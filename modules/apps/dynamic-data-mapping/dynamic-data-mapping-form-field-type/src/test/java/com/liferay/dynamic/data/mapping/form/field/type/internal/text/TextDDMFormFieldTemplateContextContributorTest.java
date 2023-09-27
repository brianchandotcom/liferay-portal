/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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