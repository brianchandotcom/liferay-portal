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

package com.liferay.dynamic.data.mapping.type.paragraph;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Bruno Basto
 */
@DDMForm
public interface ParagraphDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%title",
		properties = {
			"placeholder=%enter-title-sentence", "setting.category=basic",
			"setting.weight=4"
		},
		required = true, tip = "%enter-title-sentence", type = "key-value"
	)
	public LocalizedValue label();

	@DDMFormField(visibilityExpression = "false")
	public LocalizedValue predefinedValue();

	@DDMFormField(visibilityExpression = "false")
	public boolean repeatable();

	@DDMFormField(visibilityExpression = "false")
	public boolean required();

	@DDMFormField(visibilityExpression = "false")
	public boolean showLabel();

	@DDMFormField(
		dataType = "string", label = "%body-text",
		properties = {"setting.category=basic", "displayStyle=multiline"},
		required = true, tip = "%enter-body-text", type = "text"
	)
	public String text();

	@DDMFormField(visibilityExpression = "false")
	public LocalizedValue tip();

	@DDMFormField(
		dataType = "ddm-validation", type = "validation",
		visibilityExpression = "false"
	)
	public DDMFormFieldValidation validation();

}