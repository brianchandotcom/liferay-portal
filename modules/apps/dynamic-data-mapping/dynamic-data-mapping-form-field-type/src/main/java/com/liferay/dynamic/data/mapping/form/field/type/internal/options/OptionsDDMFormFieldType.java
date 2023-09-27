/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.options;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Renato Rego
 */
@Component(
	property = {
		"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Options",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.OPTIONS,
		"ddm.form.field.type.system=true"
	},
	service = DDMFormFieldType.class
)
public class OptionsDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/Options/Options.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.OPTIONS;
	}

}