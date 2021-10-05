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

package com.liferay.dynamic.data.mapping.form.field.type.internal.image;

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
 * @author Carlos Lancha
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
				"setVisible('requiredDescription', getValue('required'))",
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
								"requiredDescription", "requiredErrorMessage"
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
								"fieldNamespace", "indexType", "localizable",
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
public interface ImageDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(predefinedValue = "image", required = true)
	@Override
	public String dataType();

	@DDMFormField(
		label = "%searchable", optionLabels = {"%disable", "%keyword", "%text"},
		optionValues = {"none", "keyword", "text"}, predefinedValue = "text",
		type = "radio"
	)
	@Override
	public String indexType();

	@DDMFormField(
		dataType = "string", label = "%predefined-value", type = "image"
	)
	@Override
	public LocalizedValue predefinedValue();

	@DDMFormField(
		label = "%required-description", predefinedValue = "true",
		properties = {
			"showAsSwitcher=true",
			"tooltip=%an-image-description-will-be-required",
			"visualProperty=true"
		}
	)
	public boolean requiredDescription();

}