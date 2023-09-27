/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.search.location;

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
		"ddm.form.field.type.description=search-location-field-type-description",
		"ddm.form.field.type.display.order:Integer=11",
		"ddm.form.field.type.group=basic",
		"ddm.form.field.type.icon=geolocation",
		"ddm.form.field.type.label=search-location-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SEARCH_LOCATION,
		"ddm.form.field.type.scope=forms"
	},
	service = DDMFormFieldType.class
)
public class SearchLocationDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return SearchLocationDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/SearchLocation" +
			"/SearchLocation.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.SEARCH_LOCATION;
	}

}