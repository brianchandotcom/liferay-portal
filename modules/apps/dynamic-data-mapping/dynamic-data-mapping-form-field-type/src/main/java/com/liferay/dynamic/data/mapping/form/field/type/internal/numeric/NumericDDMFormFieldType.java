/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	property = {
		"ddm.form.field.type.data.domain=number",
		"ddm.form.field.type.description=it-accepts-only-numbers",
		"ddm.form.field.type.display.order:Integer=7",
		"ddm.form.field.type.group=basic", "ddm.form.field.type.icon=integer",
		"ddm.form.field.type.label=numeric-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.NUMERIC,
		"ddm.form.field.type.scope=document-library,forms,journal"
	},
	service = DDMFormFieldType.class
)
public class NumericDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return NumericDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/Numeric/Numeric";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.NUMERIC;
	}

}