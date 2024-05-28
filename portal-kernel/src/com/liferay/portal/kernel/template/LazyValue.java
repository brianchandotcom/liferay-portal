/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import java.util.function.Supplier;

/**
 * @author Shuyang Zhou
 */
public class LazyValue {

	public LazyValue(Supplier<?> supplier) {
		_supplier = supplier;
	}

	public Object getValue() {
		if (_value == null) {
			_value = _supplier.get();
		}

		return _value;
	}

	private final Supplier<?> _supplier;
	private Object _value;

}