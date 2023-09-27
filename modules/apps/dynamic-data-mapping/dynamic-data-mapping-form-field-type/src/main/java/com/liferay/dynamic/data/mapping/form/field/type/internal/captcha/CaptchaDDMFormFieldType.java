/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.captcha;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Captcha",
		"ddm.form.field.type.js.module=liferay-ddm-form-field-captcha",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.CAPTCHA,
		"ddm.form.field.type.system=true"
	},
	service = DDMFormFieldType.class
)
public class CaptchaDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/Captcha/Captcha.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.CAPTCHA;
	}

}