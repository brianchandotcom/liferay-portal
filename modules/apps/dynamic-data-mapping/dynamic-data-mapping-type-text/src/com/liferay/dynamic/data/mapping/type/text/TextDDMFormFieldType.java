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

package com.liferay.dynamic.data.mapping.type.text;

import com.liferay.dynamic.data.mapping.registry.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, 
	property = {
		"ddm.form.field.type.icon=icon-font",
		"ddm.form.field.type.name=text"
	}, 
	service = DDMFormFieldType.class
)
public class TextDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public String getName() {
		return "text";
	}

}
