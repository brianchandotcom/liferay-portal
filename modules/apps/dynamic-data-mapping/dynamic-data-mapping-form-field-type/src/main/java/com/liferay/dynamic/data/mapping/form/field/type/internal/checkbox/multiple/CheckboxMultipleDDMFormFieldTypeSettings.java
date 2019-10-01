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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Dylan Rebelak
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setOptions('predefinedValue', getValue('options'))",
				"setRequired('options', true)",
				"setVisible('repeatable', false)",
				"setVisible('validation', false)"
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
								"label", "tip", "required", "showAsSwitcher",
								"options"
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
								"name", "visibilityExpression",
								"predefinedValue", "validation",
								"fieldNamespace", "indexType", "localizable",
								"readOnly", "dataType", "type", "showLabel",
								"repeatable", "inline"
							}
						)
					}
				)
			}
		)
	}
)
public interface CheckboxMultipleDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(label = "%inline", properties = "showAsSwitcher=true")
	public boolean inline();

	@DDMFormField(
		dataType = "ddm-options", label = "%options", type = "options"
	)
	public DDMFormFieldOptions options();

	@DDMFormField(
		label = "%predefined-value",
		properties = {
			"multiple=true", "placeholder=%enter-a-default-value",
			"tooltip=%enter-a-default-value-that-is-submitted-if-no-other-value-is-entered"
		},
		type = "select"
	)
	@Override
	public LocalizedValue predefinedValue();

	@DDMFormField(
		dataType = "boolean", label = "%show-as-a-switcher",
		properties = "showAsSwitcher=true", type = "checkbox"
	)
	public boolean showAsSwitcher();

}