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

package com.liferay.dynamic.data.mapping.type.paragraph.internal;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Bruno Basto
 */
@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SETTINGS_MODE,
	value = {
		@DDMFormLayoutPage(
			title = "basic",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {"label", "text", "tip", "required"}
						)
					}
				)
			}
		),
		@DDMFormLayoutPage(
			title = "advanced",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"validation", "showLabel", "repeatable",
								"predefinedValue", "visibilityExpression",
								"fieldNamespace", "indexType", "localizable",
								"readOnly", "dataType", "type", "name"
							}
						)
					}
				)
			}
		)
	}
)
public interface ParagraphDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%title", properties = {"placeholder=%enter-a-title"},
		required = true, type = "key_value"
	)
	@Override
	public LocalizedValue label();

	@DDMFormField(visibilityExpression = "FALSE")
	@Override
	public LocalizedValue predefinedValue();

	@DDMFormField(visibilityExpression = "FALSE")
	@Override
	public boolean repeatable();

	@DDMFormField(visibilityExpression = "FALSE")
	@Override
	public boolean required();

	@DDMFormField(visibilityExpression = "FALSE")
	@Override
	public boolean showLabel();

	@DDMFormField(
		dataType = "string", label = "%body-text",
		properties = {"placeholder=%enter-body-text"}, required = true,
		type = "editor"
	)
	public String text();

	@DDMFormField(visibilityExpression = "FALSE")
	@Override
	public LocalizedValue tip();

	@DDMFormField(
		dataType = "ddm-validation", type = "validation",
		visibilityExpression = "FALSE"
	)
	@Override
	public DDMFormFieldValidation validation();

}