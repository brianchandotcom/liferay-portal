/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormFieldValueAccessor<T> {

	public default T[] getArrayGenericType() {
		throw new UnsupportedOperationException(
			"Cannot create an array of a generic type");
	}

	public T getValue(DDMFormFieldValue ddmFormFieldValue, Locale locale);

	public T getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale);

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