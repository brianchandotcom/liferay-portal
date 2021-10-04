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