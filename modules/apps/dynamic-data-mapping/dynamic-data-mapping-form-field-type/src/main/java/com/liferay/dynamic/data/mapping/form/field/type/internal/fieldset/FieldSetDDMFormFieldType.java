/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Lancha
 */
@Component(
	property = {
		"ddm.form.field.type.data.domain=fieldset",
		"ddm.form.field.type.description=fieldset-field-type-description",
		"ddm.form.field.type.display.order:Integer=8",
		"ddm.form.field.type.group=basic", "ddm.form.field.type.icon=adjust",
		"ddm.form.field.type.label=fieldset-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.FIELDSET,
		"ddm.form.field.type.scope=document-library,forms,journal",
		"ddm.form.field.type.system=true"
	},
	service = DDMFormFieldType.class
)
public class FieldSetDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return FieldSetDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/FieldSet/FieldSet.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.FIELDSET;
	}

}