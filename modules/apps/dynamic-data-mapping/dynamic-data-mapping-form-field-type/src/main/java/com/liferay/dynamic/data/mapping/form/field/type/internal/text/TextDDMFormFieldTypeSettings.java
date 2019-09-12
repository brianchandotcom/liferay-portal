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

package com.liferay.dynamic.data.mapping.form.field.type.internal.text;

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
				"setValue('autocomplete', FALSE)",
				"setVisible('autocomplete', FALSE)"
			},
			condition = "not(equals(getValue('displayStyle'), 'singleline'))"
		),
		@DDMFormRule(
			actions = {
				"setRequired('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), \"data-provider\"))",
				"setRequired('ddmDataProviderInstanceOutput', equals(getValue('dataSourceType'), \"data-provider\"))",
				"setValidationDataType('validation', getValue('dataType'))",
				"setValidationFieldName('validation', getValue('name'))",
				"setVisible('dataSourceType', getValue('autocomplete'))",
				"setVisible('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), \"data-provider\") and getValue('autocomplete'))",
				"setVisible('ddmDataProviderInstanceOutput', equals(getValue('dataSourceType'), \"data-provider\") and getValue('autocomplete'))",
				"setVisible('options', contains(getValue('dataSourceType'), \"manual\") and getValue('autocomplete'))"
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
								"label", "placeholder", "tip", "displayStyle",
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
								"indexType", "localizable", "readOnly",
								"dataType", "type", "showLabel", "repeatable",
								"validation", "tooltip"
							}
						)
					}
				)
			}
		),
		@DDMFormLayoutPage(
			title = "%autocomplete",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"autocomplete", "dataSourceType",
								"ddmDataProviderInstanceId",
								"ddmDataProviderInstanceOutput", "options"
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
		predefinedValue = "[\"manual\"]", properties = "showLabel=false",
		type = "radio"
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
		label = "%field-type",
		optionLabels = {"%single-line", "%multiple-lines"},
		optionValues = {"singleline", "multiline"},
		predefinedValue = "singleline", type = "radio"
	)
	public String displayStyle();

	@DDMFormField(
		label = "%searchable", optionLabels = {"%disable", "%keyword", "%text"},
		optionValues = {"none", "keyword", "text"}, predefinedValue = "keyword",
		type = "radio"
	)
	@Override
	public String indexType();

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