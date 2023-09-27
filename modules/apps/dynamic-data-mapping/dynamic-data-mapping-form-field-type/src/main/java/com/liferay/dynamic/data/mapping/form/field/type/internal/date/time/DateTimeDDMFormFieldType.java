/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.date.time;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodrigo Paulino
 */
@Component(
	property = {
		"ddm.form.field.type.description=date-time-field-type-description",
		"ddm.form.field.type.display.order:Double=6.1",
		"ddm.form.field.type.group=basic", "ddm.form.field.type.icon=date-time",
		"ddm.form.field.type.label=date-and-time",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE_TIME,
		"ddm.form.field.type.scope=forms,journal"
	},
	service = DDMFormFieldType.class
)
public class DateTimeDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return DateTimeDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/DatePicker/DatePicker.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.DATE_TIME;
	}

}