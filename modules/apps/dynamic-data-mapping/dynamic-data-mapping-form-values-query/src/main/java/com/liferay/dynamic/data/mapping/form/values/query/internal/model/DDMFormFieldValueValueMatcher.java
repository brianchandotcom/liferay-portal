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

package com.liferay.dynamic.data.mapping.form.values.query.internal.model;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;

import java.util.Locale;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldValueValueMatcher implements DDMFormFieldValueMatcher {

	@Override
	public boolean matches(DDMFormFieldValue ddmFormFieldValue) {
		if (_locale != null) {
			return performLocalizedMatch(ddmFormFieldValue.getValue());
		}

		return performUnlocalizedMatch(ddmFormFieldValue.getValue());
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setValue(String value) {
		_value = value;
	}

	protected boolean performLocalizedMatch(Value value) {
		return _value.equals(value.getString(_locale));
	}

	protected boolean performUnlocalizedMatch(Value value) {
		for (Locale locale : value.getAvailableLocales()) {
			if (_value.equals(value.getString(locale))) {
				return true;
			}
		}

		return false;
	}

	private Locale _locale;
	private String _value;

}