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

package com.liferay.object.internal.field.business.type;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
	service = {
		BooleanObjectFieldBusinessType.class, ObjectFieldBusinessType.class
	}
)
public class BooleanObjectFieldBusinessType implements ObjectFieldBusinessType {

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_BOOLEAN;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return DDMFormFieldTypeConstants.CHECKBOX;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(locale, "select-between-true-or-false");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "boolean");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.BOOLEAN;
	}

	@Reference
	private Language _language;

}