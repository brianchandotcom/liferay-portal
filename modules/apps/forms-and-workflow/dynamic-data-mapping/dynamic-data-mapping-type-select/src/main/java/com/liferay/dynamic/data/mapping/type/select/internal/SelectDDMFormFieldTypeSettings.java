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

package com.liferay.dynamic.data.mapping.type.select.internal;

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

/**
 * @author Marcellus Tavares
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"call('getDataProviderInstanceOutputParameters', 'dataProviderInstanceId=ddmDataProviderInstanceId', 'ddmDataProviderInstanceOutput=outputParameterNames')"
			},
			condition = "not(equals(getValue('ddmDataProviderInstanceId'), 0))"
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
								"label", "tip", "required", "dataSourceType",
								"options", "ddmDataProviderInstanceId",
								"ddmDataProviderInstanceOutput"
							}
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
								"name", "showLabel", "repeatable", "multiple"
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
		label = "%create-list",
		optionLabels = {"%manually", "%from-data-provider"},
		optionValues = {"manual", "data-provider"}, predefinedValue = "manual",
		type = "radio"
	)
	public String dataSourceType();

	@DDMFormField(
		label = "%choose-a-data-provider",
		properties = {
			"dataSourceType=data-provider",
			"ddmDataProviderInstanceId=getDataProviderInstances"
		},
		required = true, type = "select",
		visibilityExpression = "equals(dataSourceType, \"data-provider\")"
	)
	public long ddmDataProviderInstanceId();

	@DDMFormField(
		label = "%choose-an-output-parameter",
		properties = {
			"tooltip=%choose-an-output-parameter-for-a-data-provider-previously-created"
		},
		required = true, type = "select",
		visibilityExpression = "equals(dataSourceType, \"data-provider\")"
	)
	public String ddmDataProviderInstanceOutput();

	@DDMFormField(
		label = "%allow-multiple-selections",
		properties = {"showAsSwitcher=true"}
	)
	public boolean multiple();

	@DDMFormField(
		dataType = "ddm-options", label = "%options",
		properties = {"showLabel=false"}, required = true, type = "options",
		visibilityExpression = "equals(dataSourceType, \"manual\")"
	)
	public DDMFormFieldOptions options();

	@DDMFormField(visibilityExpression = "FALSE")
	@Override
	public DDMFormFieldValidation validation();

}