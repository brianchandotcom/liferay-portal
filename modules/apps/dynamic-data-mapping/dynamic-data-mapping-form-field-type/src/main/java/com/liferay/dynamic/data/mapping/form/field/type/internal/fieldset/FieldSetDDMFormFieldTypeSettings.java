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

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;

/**
 * @author Carlos Lancha
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setVisible('ddmStructureId', FALSE)",
				"setVisible('ddmStructureLayoutId', FALSE)",
				"setVisible('name', FALSE)", "setVisible('rows', FALSE)",
				"setVisible('upgradedStructure', FALSE)"
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
								"label", "name", "fieldReference",
								"collapsible", "repeatable", "showLabel",
								"rows", "type", "ddmStructureId",
								"ddmStructureLayoutId", "upgradedStructure"
							}
						)
					}
				)
			}
		)
	}
)
public interface FieldSetDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%collapsible", properties = "showAsSwitcher=true",
		type = "checkbox"
	)
	public boolean collapsible();

	@DDMFormField(dataType = "numeric")
	public long ddmStructureId();

	@DDMFormField(dataType = "numeric")
	public long ddmStructureLayoutId();

	@DDMFormField(dataType = "json", type = "text")
	public String rows();

	@DDMFormField(predefinedValue = "false")
	public boolean upgradedStructure();

}