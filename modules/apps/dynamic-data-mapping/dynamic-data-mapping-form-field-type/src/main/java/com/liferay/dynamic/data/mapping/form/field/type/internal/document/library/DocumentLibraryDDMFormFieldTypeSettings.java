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