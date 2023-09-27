/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.paragraph;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Queiroz
 */
public class ParagraphDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetParameters() {
		DDMFormField ddmFormField = new DDMFormField("field", "paragraph");

		LocalizedValue text = new LocalizedValue();

		text.addString(text.getDefaultLocale(), "<b>This is a header</b>\n");

		ddmFormField.setProperty("text", text);

		Map<String, Object> parameters =
			_paragraphDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, new DDMFormFieldRenderingContext());

		Assert.assertEquals(
			text.getString(text.getDefaultLocale()), parameters.get("text"));
	}

	@Test
	public void testGetParametersWhenInViewMode() {
		DDMFormField ddmFormField = new DDMFormField("field", "paragraph");

		LocalizedValue text = new LocalizedValue();

		text.addString(text.getDefaultLocale(), "<p>This is a paragraph</p>\n");

		ddmFormField.setProperty("text", text);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setViewMode(true);

		Map<String, Object> parameters =
			_paragraphDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals(
			text.getString(text.getDefaultLocale()), parameters.get("text"));
	}

	private final ParagraphDDMFormFieldTemplateContextContributor
		_paragraphDDMFormFieldTemplateContextContributor =
			new ParagraphDDMFormFieldTemplateContextContributor();

}