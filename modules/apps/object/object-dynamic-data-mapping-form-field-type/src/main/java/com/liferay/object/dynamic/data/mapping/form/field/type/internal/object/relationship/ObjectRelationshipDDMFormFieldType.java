/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.form.field.type.internal.object.relationship;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"ddm.form.field.type.data.domain=list",
		"ddm.form.field.type.description=object-relationship-field-type-description",
		"ddm.form.field.type.display.order:Integer=13",
		"ddm.form.field.type.group=basic",
		"ddm.form.field.type.icon=relationship",
		"ddm.form.field.type.label=object-relationship-field-type-label",
		"ddm.form.field.type.name=" + ObjectDDMFormFieldTypeConstants.OBJECT_RELATIONSHIP,
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
		JSPackage jsPackage = _npmResolver.getJSPackage();

		return jsPackage.getResolvedId() +
			"/ObjectRelationship/ObjectRelationship";
	}

	@Override
	public String getName() {
		return ObjectDDMFormFieldTypeConstants.OBJECT_RELATIONSHIP;
	}

	@Override
	public boolean isCustomDDMFormFieldType() {
		return true;
	}

	@Reference
	private NPMResolver _npmResolver;

}