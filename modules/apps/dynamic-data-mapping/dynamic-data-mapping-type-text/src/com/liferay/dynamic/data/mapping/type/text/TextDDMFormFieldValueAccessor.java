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

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class TextDDMFormFieldValueAccessor
	extends DDMFormFieldValueAccessor<String> {

	public TextDDMFormFieldValueAccessor(Locale locale) {
		super(locale);
	}

	@Override
	public String get(DDMFormFieldValue ddmFormFieldValue) {
		Value value = ddmFormFieldValue.getValue();

		return value.getString(locale);
	}

	@Override
	public Class<String> getAttributeClass() {
		return String.class;
	}

}