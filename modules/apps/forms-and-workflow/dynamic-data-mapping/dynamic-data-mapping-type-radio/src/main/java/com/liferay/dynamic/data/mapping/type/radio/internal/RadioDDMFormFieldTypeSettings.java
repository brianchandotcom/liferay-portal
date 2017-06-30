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

package com.liferay.dynamic.data.mapping.type.radio.internal;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;

/**
 * @author Marcellus Tavares
 */
@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.TABBED_MODE,
	value = {
		@DDMFormLayoutPage(
			title = "%basic",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {"label", "tip", "required", "options"}
						)
					}
				)
			}
		),
		@DDMFormLayoutPage(
			title = "%properties",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"predefinedValue", "visibilityExpression",
								"validation", "fieldNamespace", "indexType",
								"localizable", "readOnly", "dataType", "type",
								"name", "showLabel", "repeatable", "inline"
							}
						)
					}
				)
			}
		)
	}
)
public interface RadioDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%inline", predefinedValue = "true",
		properties = {"showAsSwitcher=true"}
	)
	public boolean inline();

	@DDMFormField(
		dataType = "ddm-options", label = "%options", required = true,
		type = "options"
	)
	public DDMFormFieldOptions options();

	@DDMFormField(visibilityExpression = "FALSE")
	@Override
	public DDMFormFieldValidation validation();

}