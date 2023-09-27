/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.kernel.servlet.NonSerializableObjectHandler
 */
public class TransientValue<V> implements Serializable {

	public TransientValue() {
	}

	public TransientValue(V value) {
		_value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TransientValue)) {
			return false;
		}

		TransientValue<?> transientValue = (TransientValue<?>)object;

		if (Objects.equals(_value, transientValue._value)) {
			return true;
		}

		return false;
	}

	public V getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return _value.hashCode();
	}

	public boolean isNotNull() {
		return !isNull();
	}

	public boolean isNull() {
		if (_value == null) {
			return true;
		}

		return false;
	}

	public void setValue(V value) {
		_value = value;
	}

	private transient V _value;

}