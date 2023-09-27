/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.radio;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Renato Rego
 */
@Component(
	property = {
		"ddm.form.field.type.data.domain=list",
		"ddm.form.field.type.description=radio-field-type-description",
		"ddm.form.field.type.display.order:Integer=3",
		"ddm.form.field.type.group=basic",
		"ddm.form.field.type.icon=radio-button",
		"ddm.form.field.type.label=radio-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.RADIO,
		"ddm.form.field.type.scope=document-library,forms,journal"
	},
	service = DDMFormFieldType.class
)
public class RadioDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return RadioDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/Radio/Radio.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.RADIO;
	}

}