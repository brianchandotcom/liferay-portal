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

package com.liferay.object.dynamic.data.mapping.internal.form.field.type;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.data.domain=list",
		"ddm.form.field.type.description=object-relationship-field-type-description",
		"ddm.form.field.type.display.order:Integer=13",
		"ddm.form.field.type.group=basic",
		"ddm.form.field.type.icon=relationship",
		"ddm.form.field.type.label=object-relationship-field-type-label",
		"ddm.form.field.type.name=object-relationship",
		"ddm.form.field.type.system=true"
	},
	service = DDMFormFieldType.class
)
public class ObjectRelationshipDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return ObjectRelationshipDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/ObjectRelationship" +
			"/ObjectRelationship";
	}

	@Override
	public String getName() {
		return "object-relationship";
	}

}