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

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

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
 * @author Leonardo Barros
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setDataType('predefinedValue', getValue('dataType'))",
				"setValidationDataType('validation', getValue('dataType'))",
				"setValidationFieldName('validation', getValue('name'))",
				"setVisible('tooltip', false)"
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
								"label", "placeholder", "tip", "dataType",
								"required"
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
								"name", "predefinedValue",
								"visibilityExpression", "fieldNamespace",
								"indexType", "localizable", "readOnly", "type",
								"showLabel", "repeatable", "validation",
								"tooltip"
							}
						)
					}
				)
			}
		)
	}
)
public interface NumericDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%my-numeric-type-is", optionLabels = {"%integer", "%decimal"},
		optionValues = {"integer", "double"}, predefinedValue = "integer",
		type = "radio"
	)
	@Override
	public String dataType();

	@DDMFormField(
		dataType = "string", label = "%placeholder-text",
		properties = "tooltip=%enter-text-that-assists-the-user-but-is-not-submitted-as-a-field-value",
		type = "text"
	)
	public LocalizedValue placeholder();

	@DDMFormField(
		label = "%predefined-value",
		properties = {
			"placeholder=%enter-a-default-value",
			"tooltip=%enter-a-default-value-that-is-submitted-if-no-other-value-is-entered"
		},
		type = "numeric"
	)
	@Override
	public LocalizedValue predefinedValue();

	@DDMFormField
	public LocalizedValue tooltip();

	@DDMFormField(
		dataType = "numeric", label = "%validation", type = "validation"
	)
	@Override
	public DDMFormFieldValidation validation();

}