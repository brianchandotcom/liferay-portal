/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.function;

/**
 * @author Shuyang Zhou
 */
public class UnsafeSupplierValue<T, E extends Throwable> {

	public UnsafeSupplierValue(UnsafeSupplier<T, E> unsafeSupplier) {
		_unsafeSupplier = unsafeSupplier;
	}

	public T getValue() throws E {
		if (_value == null) {
			_value = _unsafeSupplier.get();
		}

		return _value;
	}

	private final UnsafeSupplier<T, E> _unsafeSupplier;
	private T _value;

}