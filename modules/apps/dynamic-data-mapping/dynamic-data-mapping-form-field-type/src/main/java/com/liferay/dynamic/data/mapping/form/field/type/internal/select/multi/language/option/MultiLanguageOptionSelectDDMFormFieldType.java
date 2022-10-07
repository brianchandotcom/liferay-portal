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