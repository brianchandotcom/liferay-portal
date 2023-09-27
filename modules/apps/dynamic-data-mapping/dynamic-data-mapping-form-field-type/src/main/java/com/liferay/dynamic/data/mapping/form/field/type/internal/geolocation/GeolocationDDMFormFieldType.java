/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.geolocation;

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
		"ddm.form.field.type.data.domain=geolocation",
		"ddm.form.field.type.description=geolocation-field-type-description",
		"ddm.form.field.type.display.order:Integer=10",
		"ddm.form.field.type.group=basic", "ddm.form.field.type.icon=globe",
		"ddm.form.field.type.label=geolocation-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.GEOLOCATION,
		"ddm.form.field.type.scope=document-library,forms,journal"
	},
	service = DDMFormFieldType.class
)
public class GeolocationDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return GeolocationDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/Geolocation" +
			"/Geolocation.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.GEOLOCATION;
	}

}