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
				"setValue('requireConfirmation', FALSE)",
				"setVisible('autocomplete', FALSE)",
				"setVisible('requireConfirmation', FALSE)"
			},
			condition = "not(equals(getValue('displayStyle'), 'singleline'))"
		),
		@DDMFormRule(
			actions = {
				"setRequired('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), \"data-provider\"))",
				"setRequired('ddmDataProviderInstanceOutput', equals(getValue('dataSourceType'), \"data-provider\"))",
				"setValidationDataType('validation', getValue('dataType'))",
				"setValidationFieldName('validation', getValue('name'))",
				"setVisible('confirmationErrorMessage', getValue('requireConfirmation'))",
				"setVisible('confirmationLabel', getValue('requireConfirmation'))",
				"setVisible('dataSourceType', getValue('autocomplete'))",
				"setVisible('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), \"data-provider\") and getValue('autocomplete'))",
				"setVisible('ddmDataProviderInstanceOutput', equals(getValue('dataSourceType'), \"data-provider\") and getValue('autocomplete'))",
				"setVisible('direction', getValue('requireConfirmation'))",
				"setVisible('options', contains(getValue('dataSourceType'), \"manual\") and getValue('autocomplete'))"
			},
			condition = "TRUE"
		),
		@DDMFormRule(
			actions = {
				"setValue('ddmDataProviderInstanceId', '')",
				"setValue('ddmDataProviderInstanceOutput', '')"
			},
			condition = "not(equals(getValue('dataSourceType'), \"data-provider\")) or not(getValue('autocomplete'))"
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
								"name", "fieldReference", "predefinedValue",
								"visibilityExpression", "fieldNamespace",
								"indexType", "labelAtStructureLevel",
								"localizable", "nativeField", "readOnly",
								"dataType", "type", "showLabel", "repeatable",
								"requireConfirmation", "direction",
								"confirmationLabel", "confirmationErrorMessage",
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
		dataType = "string", label = "%error-message",
		properties = "initialValue=%the-information-does-not-match",
		type = "text"
	)
	public LocalizedValue confirmationErrorMessage();

	@DDMFormField(
		dataType = "string", label = "%label",
		properties = "initialValue=%confirm", type = "text"
	)
	public LocalizedValue confirmationLabel();

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
		label = "%direction", optionLabels = {"%horizontal", "%vertical"},
		optionValues = {"horizontal", "vertical"},
		predefinedValue = "[\"vertical\"]",
		properties = "showEmptyOption=false", type = "select"
	)
	public String direction();

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
			"tooltip=%enter-text-that-assists-the-user-but-is-not-submitted-as-a-field-value",
			"visualProperty=true"
		},
		type = "text"
	)
	public LocalizedValue placeholder();

	@DDMFormField(
		label = "%require-confirmation", properties = "showAsSwitcher=true"
	)
	public boolean requireConfirmation();

	@DDMFormField(visibilityExpression = "FALSE")
	public LocalizedValue tooltip();

	@DDMFormField(
		dataType = "string", label = "%validation", type = "validation"
	)
	@Override
	public DDMFormFieldValidation validation();

}