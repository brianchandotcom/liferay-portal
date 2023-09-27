/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

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
			actions = "call('getDataProviderInstanceOutputParameters', 'dataProviderInstanceId=ddmDataProviderInstanceId', 'ddmDataProviderInstanceOutput=outputParameterNames')",
			condition = "contains(getValue('dataSourceType'), \"data-provider\")"
		),
		@DDMFormRule(
			actions = {
				"setEnabled('required', not(hasObjectField(getValue('objectFieldName'))))",
				"setMultiple('predefinedValue', getValue('multiple'))",
				"setOptions('predefinedValue', getValue('options'))",
				"setRequired('ddmDataProviderInstanceId', contains(getValue('dataSourceType'), \"data-provider\"))",
				"setRequired('ddmDataProviderInstanceOutput', contains(getValue('dataSourceType'), \"data-provider\"))",
				"setRequired('options', contains(getValue('dataSourceType'), \"manual\") OR isEmpty(getValue('dataSourceType')))",
				"setVisible('dataSourceType', not(hasObjectField(getValue('objectFieldName'))))",
				"setVisible('ddmDataProviderInstanceId', contains(getValue('dataSourceType'), \"data-provider\"))",
				"setVisible('ddmDataProviderInstanceOutput', contains(getValue('dataSourceType'), \"data-provider\"))",
				"setVisible('multiple', not(hasObjectField(getValue('objectFieldName'))))",
				"setVisible('options', (contains(getValue('dataSourceType'), \"manual\") OR isEmpty(getValue('dataSourceType'))) AND not(hasObjectField(getValue('objectFieldName'))))",
				"setVisible('predefinedValue', contains(getValue('dataSourceType'), \"manual\"))",
				"setVisible('requiredErrorMessage', getValue('required'))"
			},
			condition = "TRUE"
		),
		@DDMFormRule(
			actions = {
				"setValue('ddmDataProviderInstanceId', '')",
				"setValue('ddmDataProviderInstanceOutput', '')"
			},
			condition = "not(equals(getValue('dataSourceType'), \"data-provider\"))"
		),
		@DDMFormRule(
			actions = {
				"setValue('options', getListTypeEntries(getValue('objectFieldName')))",
				"setValue('required', isRequiredObjectField(getValue('objectFieldName')))"
			},
			condition = "hasObjectField(getValue('objectFieldName'))"
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
								"requiredErrorMessage", "dataSourceType",
								"options", "ddmDataProviderInstanceId",
								"ddmDataProviderInstanceOutput"
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
								"showLabel", "repeatable", "multiple",
								"alphabeticalOrder"
							}
						)
					}
				)
			}
		)
	}
)
public interface SelectDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%order-options-alphabetically",
		properties = "showAsSwitcher=true"
	)
	public boolean alphabeticalOrder();

	@DDMFormField(
		label = "%create-list",
		optionLabels = {"%manually", "%from-data-provider", "%from-autofill"},
		optionValues = {"manual", "data-provider", "from-autofill"},
		predefinedValue = "[\"manual\"]", properties = "showEmptyOption=false",
		type = "select"
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
		label = "%allow-multiple-selections", properties = "showAsSwitcher=true"
	)
	public boolean multiple();

	@DDMFormField(
		dataType = "ddm-options", label = "%options",
		properties = "showLabel=false", type = "options"
	)
	public DDMFormFieldOptions options();

	@DDMFormField(
		label = "%predefined-value",
		properties = {
			"placeholder=%enter-a-default-value",
			"tooltip=%enter-a-default-value-that-is-submitted-if-no-other-value-is-entered",
			"visualProperty=true"
		},
		type = "select"
	)
	@Override
	public LocalizedValue predefinedValue();

}