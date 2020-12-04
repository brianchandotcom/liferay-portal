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

package com.liferay.dynamic.data.mapping.form.field.type.internal.paragraph;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pedro Queiroz
 */
public class ParagraphDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

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