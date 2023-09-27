/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"ddm.form.field.type.data.domain=link_to_layout",
		"ddm.form.field.type.description=link-to-layout-field-description",
		"ddm.form.field.type.display.order:Integer=11",
		"ddm.form.field.type.group=basic", "ddm.form.field.type.icon=link",
		"ddm.form.field.type.label=link-to-layout-field-label",
		"ddm.form.field.type.name=" + LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT,
		"ddm.form.field.type.scope=document-library,journal,layout"
	},
	service = DDMFormFieldType.class
)
public class LayoutDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return LayoutDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		JSPackage jsPackage = _npmResolver.getJSPackage();

		return jsPackage.getResolvedId() + "/LayoutSelector";
	}

	@Override
	public String getName() {
		return LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT;
	}

	@Override
	public boolean isCustomDDMFormFieldType() {
		return true;
	}

	@Reference
	private NPMResolver _npmResolver;

}