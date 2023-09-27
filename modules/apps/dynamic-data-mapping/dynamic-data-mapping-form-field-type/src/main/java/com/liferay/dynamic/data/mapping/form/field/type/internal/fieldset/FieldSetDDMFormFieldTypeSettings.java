/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
								"label", "collapsible", "labelAtStructureLevel",
								"repeatable", "showLabel", "rows", "type",
								"ddmStructureId", "ddmStructureLayoutId",
								"upgradedStructure"
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
							size = 12, value = {"name", "fieldReference"}
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