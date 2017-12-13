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

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class EscapableObject<T> implements Serializable {

	public EscapableObject(T originalValue) {
		this(originalValue, true);
	}

	public EscapableObject(T originalValue, boolean escape) {
		_originalValue = originalValue;
		_escape = escape;
	}

	public String getEscapedValue() {
		if (_escapedValue == null) {
			if (_escape) {
				_escapedValue = escape(_originalValue);
			}
			else {
				_escapedValue = String.valueOf(_originalValue);
			}
		}

		return _escapedValue;
	}

	public T getOriginalValue() {
		return _originalValue;
	}

	public boolean isEscape() {
		return _escape;
	}

	@Override
	public String toString() {
		return _originalValue.toString();
	}

	protected String escape(T t) {
		return String.valueOf(t);
	}

	private final boolean _escape;
	private String _escapedValue;
	private final T _originalValue;

}