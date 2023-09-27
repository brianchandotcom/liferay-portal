/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select.multi.language.option;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.select.SelectDDMFormFieldType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodrigo Paulino
 */
@Component(
	property = {
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.MULTI_LANGUAGE_OPTION_SELECT,
		"ddm.form.field.type.system=true"
	},
	service = DDMFormFieldType.class
)
public class MultiLanguageOptionSelectDDMFormFieldType
	extends SelectDDMFormFieldType {

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.MULTI_LANGUAGE_OPTION_SELECT;
	}

}