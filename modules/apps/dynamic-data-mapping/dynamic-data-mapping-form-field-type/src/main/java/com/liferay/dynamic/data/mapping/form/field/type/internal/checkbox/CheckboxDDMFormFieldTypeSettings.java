/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
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
		dataType = "ddm-options", label = "%options", type = "options"
	)
	public DDMFormFieldOptions options();

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