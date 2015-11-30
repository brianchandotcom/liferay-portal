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

package com.liferay.dynamic.data.mapping.type.select;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;

/**
 * @author Marcellus Tavares
 */
@DDMForm
public interface SelectDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%create-list",
		optionLabels = {"%manually", "%from-data-provider"},
		optionValues = {"manual", "data-provider"}, predefinedValue = "manual",
		properties = {"setting.category=basic", "setting.weight=1"},
		type = "radio"
	)
	public String dataSourceType();

	@DDMFormField(
		properties = {
			"setting.category=basic", "setting.weight=0", "showLabel=false"
		},
		type = "select",
		visibilityExpression = "dataSourceType.equals(\"data-provider\")"

	)
	public long ddmDataProviderInstanceId();

	@DDMFormField(
		label = "%multiple",
		properties = {
			"setting.category=advanced", "setting.weight=2",
			"showAsSwitcher=true"
		}
	)
	public boolean multiple();

	@DDMFormField(
		dataType = "ddm-options", label = "%options",
		properties = {
			"setting.category=basic", "setting.weight=0", "showLabel=false"
		},
		required = true, type = "options",
		visibilityExpression = "dataSourceType.equals(\"manual\")"
	)
	public DDMFormFieldOptions options();

}