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

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Lancha
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.data.domain=fieldset",
		"ddm.form.field.type.description=fieldset-field-type-description",
		"ddm.form.field.type.display.order:Integer=8",
		"ddm.form.field.type.group=basic", "ddm.form.field.type.icon=adjust",
		"ddm.form.field.type.label=fieldset-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.FIELDSET,
		"ddm.form.field.type.scope=app-builder,forms",
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