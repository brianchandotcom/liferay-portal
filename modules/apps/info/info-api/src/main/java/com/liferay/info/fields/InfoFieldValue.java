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

package com.liferay.info.fields;

import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringBundler;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author Jorge Ferrer
 */
public class InfoFieldValue<T> {

	public InfoFieldValue(InfoField infoField, Supplier<T> valueSupplier) {
		_infoField = infoField;
		_value = null;
		_valueSupplier = valueSupplier;
	}

	public InfoFieldValue(InfoField infoField, T value) {
		_infoField = infoField;
		_value = value;
		_valueSupplier = null;
	}

	public InfoField getInfoField() {
		return _infoField;
	}

	public T getValue(Locale locale) {
		T value = _value;

		if (_valueSupplier != null) {
			value = _valueSupplier.get();
		}

		if (value instanceof InfoLocalizedValue) {
			InfoLocalizedValue<T> infoLocalizedValue =
				(InfoLocalizedValue<T>)value;

			return infoLocalizedValue.getValue(locale);
		}

		return value;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{");
		sb.append(_infoField.getName());
		sb.append(": ");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
	}

	private final InfoField _infoField;
	private final T _value;
	private final Supplier<T> _valueSupplier;

}