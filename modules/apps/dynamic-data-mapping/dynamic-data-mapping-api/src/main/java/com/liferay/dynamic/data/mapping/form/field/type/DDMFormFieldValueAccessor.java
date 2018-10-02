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

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.function.IntFunction;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormFieldValueAccessor<T> {

	public default IntFunction<T[]> getArrayGeneratorIntFunction() {
		throw new UnsupportedOperationException(
			"Cannot create an array of a generic type");
	}

	public T getValue(DDMFormFieldValue ddmFormFieldValue, Locale locale);

	public default boolean isEmpty(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return true;
		}

		String valueString = StringUtil.trim(value.getString(locale));

		return Validator.isNull(valueString);
	}

	public default Object map(Object value) {
		return value;
	}

}