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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Marcellus Tavares
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
				"setVisible('dataType', FALSE)",
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
								"requiredErrorMessage", "showAsSwitcher"
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
								"name", "fieldReference",
								"visibilityExpression", "predefinedValue",
								"objectFieldName", "fieldNamespace",
								"indexType", "labelAtStructureLevel",
								"localizable", "readOnly", "dataType", "type",
								"repeatable"
							}
						)
					}
				)
			}
		)
	}
)
public interface CheckboxDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(predefinedValue = "boolean")
	@Override
	public String dataType();

	@DDMFormField(
		label = "%predefined-value", optionLabels = {"%false", "%true"},
		optionValues = {"false", "true"}, predefinedValue = "[\"false\"]",
		properties = {
			"placeholder=%enter-a-default-value",
			"tooltip=%enter-a-default-value-that-is-submitted-if-no-other-value-is-entered",
			"showEmptyOption=false", "visualProperty=true"
		},
		type = "select"
	)
	@Override
	public LocalizedValue predefinedValue();

	@DDMFormField(visibilityExpression = "FALSE")
	@Override
	public boolean repeatable();

	@DDMFormField(
		label = "%required-field",
		properties = {
			"showAsSwitcher=true", "tooltip=%only-true-will-be-accepted",
			"visualProperty=true"
		}
	)
	@Override
	public boolean required();

	@DDMFormField(
		dataType = "boolean", label = "%show-as-a-switch",
		properties = "showAsSwitcher=true", type = "checkbox"
	)
	public boolean showAsSwitcher();

}