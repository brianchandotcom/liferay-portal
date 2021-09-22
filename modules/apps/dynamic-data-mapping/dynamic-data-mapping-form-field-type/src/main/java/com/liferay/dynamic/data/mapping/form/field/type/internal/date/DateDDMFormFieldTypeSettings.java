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

package com.liferay.dynamic.data.mapping.form.field.type.internal.date;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Bruno Basto
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = "setValue('required', isRequiredObjectField(getValue('objectFieldName')))",
			condition = "hasObjectField(getValue('objectFieldName'))"
		),
		@DDMFormRule(
			actions = {
				"setEnabled('required', not(hasObjectField(getValue('objectFieldName'))))",
				"setVisible('dataType', false)",
				"setVisible('requiredErrorMessage', getValue('required'))"
			},
			condition = "TRUE"
		)
	}
)
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
							value = {
								"label", "tip", "required",
								"requiredErrorMessage"
							}
						)
					}
				)
			}
		),
		@DDMFormLayoutPage(
			title = "%advanced",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"name", "fieldReference", "predefinedValue",
								"objectFieldName", "visibilityExpression",
								"fieldNamespace", "indexType",
								"labelAtStructureLevel", "localizable",
								"nativeField", "readOnly", "dataType", "type",
								"showLabel", "repeatable", "validation"
							}
						)
					}
				)
			}
		)
	}
)
public interface DateDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(predefinedValue = "date", required = true)
	@Override
	public String dataType();

	@DDMFormField(
		dataType = "string", label = "%predefined-value",
		properties = "visualProperty=true", type = "date"
	)
	@Override
	public LocalizedValue predefinedValue();

	@DDMFormField(dataType = "date", label = "%validation", type = "validation")
	@Override
	public DDMFormFieldValidation validation();

}