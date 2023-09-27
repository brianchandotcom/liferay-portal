/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.document.library;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;

/**
 * @author Pedro Queiroz
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setVisible('dataType', false)",
				"setVisible('predefinedValue', false)",
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
								"requiredErrorMessage", "allowGuestUsers"
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
								"readOnly", "dataType", "type", "showLabel",
								"repeatable"
							}
						)
					}
				)
			}
		)
	}
)
public interface DocumentLibraryDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		dataType = "boolean", label = "%allow-guest-users-to-send-files",
		properties = "showAsSwitcher=true", type = "checkbox"
	)
	public boolean allowGuestUsers();

	@DDMFormField(predefinedValue = "document-library", required = true)
	@Override
	public String dataType();

	@DDMFormField(
		label = "%repeatable",
		properties = {"showAsSwitcher=true", "showMaximumRepetitionsInfo=true"}
	)
	@Override
	public boolean repeatable();

}