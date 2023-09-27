/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field;

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
		_valueSupplier = valueSupplier;

		_value = null;
	}

	public InfoFieldValue(InfoField infoField, T value) {
		_infoField = infoField;
		_value = value;

		_valueSupplier = null;
	}

	public InfoField getInfoField() {
		return _infoField;
	}

	public T getValue() {
		if (_valueSupplier != null) {
			return _valueSupplier.get();
		}

		return _value;
	}

	public T getValue(Locale locale) {
		T value = getValue();

		if (value instanceof InfoLocalizedValue) {
			InfoLocalizedValue<T> infoLocalizedValue =
				(InfoLocalizedValue<T>)value;

			return infoLocalizedValue.getValue(locale);
		}

		return value;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{", _infoField.getName(), ": ", _value, "}");
	}

	private final InfoField _infoField;
	private final T _value;
	private final Supplier<T> _valueSupplier;

}