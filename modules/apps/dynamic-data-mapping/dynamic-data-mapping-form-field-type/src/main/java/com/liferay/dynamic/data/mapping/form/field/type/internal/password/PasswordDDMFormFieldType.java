/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.password;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"ddm.form.field.type.display.order:Integer=9",
		"ddm.form.field.type.icon=password-policies",
		"ddm.form.field.type.label=password-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.PASSWORD,
		"ddm.form.field.type.system=true"
	},
	service = DDMFormFieldType.class
)
public class PasswordDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return PasswordDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/Password/Password.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.PASSWORD;
	}

}