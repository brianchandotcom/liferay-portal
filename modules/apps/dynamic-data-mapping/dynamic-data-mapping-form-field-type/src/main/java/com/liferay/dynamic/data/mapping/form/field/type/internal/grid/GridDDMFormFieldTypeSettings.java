/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

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
 * @author Pedro Queiroz
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setVisible('indexType', false)",
				"setVisible('predefinedValue', false)",
				"setVisible('repeatable', false)",
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
								"requiredErrorMessage", "predefinedValue",
								"rows", "columns"
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
								"name", "fieldReference", "objectFieldName",
								"visibilityExpression", "showLabel",
								"repeatable", "fieldNamespace", "indexType",
								"localizable", "readOnly", "dataType", "type"
							}
						)
					}
				)
			}
		)
	}
)
public interface GridDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		dataType = "ddm-options", label = "%columns", required = true,
		type = "options"
	)
	public DDMFormFieldOptions columns();

	@DDMFormField
	@Override
	public LocalizedValue predefinedValue();

	@DDMFormField
	@Override
	public boolean repeatable();

	@DDMFormField(
		dataType = "ddm-options", label = "%rows", required = true,
		type = "options"
	)
	public DDMFormFieldOptions rows();

	@DDMFormField
	@Override
	public DDMFormFieldValidation validation();

}