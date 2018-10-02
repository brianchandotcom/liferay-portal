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

package com.liferay.dynamic.data.mapping.form.field.type.text.internal;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Lino Alves
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = "call('getDataProviderInstanceOutputParameters', 'dataProviderInstanceId=ddmDataProviderInstanceId', 'ddmDataProviderInstanceOutput=outputParameterNames')",
			condition = "not(equals(getValue('ddmDataProviderInstanceId'), ''))"
		),
		@DDMFormRule(
			actions = {
				"setRequired('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), \"data-provider\"))",
				"setRequired('ddmDataProviderInstanceOutput', equals(getValue('dataSourceType'), \"data-provider\"))",
				"setVisible('dataSourceType', getValue('autocomplete'))",
				"setVisible('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), \"data-provider\") and getValue('autocomplete'))",
				"setVisible('ddmDataProviderInstanceOutput', equals(getValue('dataSourceType'), \"data-provider\") and getValue('autocomplete'))",
				"setVisible('options', equals(getValue('dataSourceType'), \"manual\") and getValue('autocomplete'))"
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
							value = {"label", "tip", "displayStyle", "required"}
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
								"predefinedValue", "placeholder",
								"visibilityExpression", "fieldNamespace",
								"indexType", "localizable", "readOnly",
								"dataType", "type", "name", "showLabel",
								"repeatable", "validation", "autocomplete",
								"dataSourceType", "ddmDataProviderInstanceId",
								"ddmDataProviderInstanceOutput", "options",
								"tooltip"
							}
						)
					}
				)
			}
		)
	}
)
public interface TextDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(label = "%autocomplete", properties = "showAsSwitcher=true")
	public boolean autocomplete();

	@DDMFormField(
		label = "%create-list",
		optionLabels = {"%manually", "%from-data-provider"},
		optionValues = {"manual", "data-provider"},
		properties = "showLabel=false", type = "radio"
	)
	public String dataSourceType();

	@DDMFormField(
		label = "%choose-a-data-provider",
		properties = {
			"dataSourceType=data-provider",
			"ddmDataProviderInstanceId=getDataProviderInstances"
		},
		type = "select"
	)
	public long ddmDataProviderInstanceId();

	@DDMFormField(
		label = "%choose-an-output-parameter",
		properties = "tooltip=%choose-an-output-parameter-for-a-data-provider-previously-created",
		type = "select"
	)
	public String ddmDataProviderInstanceOutput();

	@DDMFormField(
		label = "%my-text-field-has",
		optionLabels = {"%a-single-line", "%multiple-lines"},
		optionValues = {"singleline", "multiline"},
		predefinedValue = "singleline", properties = "inline=true",
		type = "radio"
	)
	public String displayStyle();

	@DDMFormField(
		dataType = "ddm-options", label = "%options",
		properties = {"showLabel=false", "allowEmptyOptions=true"},
		required = false, type = "options"
	)
	public DDMFormFieldOptions options();

	@DDMFormField(
		dataType = "string", label = "%placeholder-text",
		properties = {
			"placeholder=%enter-placeholder-text",
			"tooltip=%enter-text-that-assists-the-user-but-is-not-submitted-as-a-field-value"
		},
		type = "text"
	)
	public LocalizedValue placeholder();

	@DDMFormField(visibilityExpression = "FALSE")
	public LocalizedValue tooltip();

	@DDMFormField(
		dataType = "string", label = "%validation", type = "validation"
	)
	@Override
	public DDMFormFieldValidation validation();

}