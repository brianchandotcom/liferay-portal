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

package com.liferay.dynamic.data.mapping.form.field.type.internal.searchLocation;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Marcela Cunha
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setVisible('dataType', false)",
				"setVisible('layout', contains(getValue('visibleFields'), \"city\") OR contains(getValue('visibleFields'), \"country\") OR contains(getValue('visibleFields'), \"postal-code\") OR contains(getValue('visibleFields'), \"state\"))",
				"setVisible('requiredErrorMessage', false)"
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
								"label", "placeholder", "tip", "required",
								"requiredErrorMessage", "visibleFields",
								"layout"
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
								"dataType", "name", "fieldReference",
								"visibilityExpression", "fieldNamespace",
								"labelAtStructureLevel", "localizable",
								"nativeField", "readOnly", "type"
							}
						)
					}
				)
			}
		)
	}
)
public interface SearchLocationDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(predefinedValue = "search-location", required = true)
	@Override
	public String dataType();

	@DDMFormField(
		label = "%layout", optionLabels = {"%one-column", "%two-columns"},
		optionValues = {"one-column", "two-columns"},
		predefinedValue = "[\"one-column\"]", type = "select"
	)
	public LocalizedValue layout();

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
		label = "%visible-fields",
		optionLabels = {
			"%address", "%city", "%state", "%postal-code", "%country"
		},
		optionValues = {"address", "city", "state", "postal-code", "country"},
		predefinedValue = "[\"address\",\"city\",\"state\",\"postal-code\",\"country\"]",
		properties = "multiple=true", type = "select"
	)
	public LocalizedValue visibleFields();

}