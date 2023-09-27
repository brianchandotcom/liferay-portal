/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.color;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcela Cunha
 */
@Component(
	property = {
		"ddm.form.field.type.data.domain=color",
		"ddm.form.field.type.description=color-field-type-description",
		"ddm.form.field.type.display.order:Integer=9",
		"ddm.form.field.type.group=basic",
		"ddm.form.field.type.icon=color-picker",
		"ddm.form.field.type.label=color-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.COLOR,
		"ddm.form.field.type.scope=document-library,forms,journal"
	},
	service = DDMFormFieldType.class
)
public class ColorDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return ColorDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/ColorPicker" +
			"/ColorPicker.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.COLOR;
	}

}