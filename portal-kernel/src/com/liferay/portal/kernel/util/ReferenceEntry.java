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

import com.liferay.petra.string.StringPool;

import java.lang.reflect.Field;

/**
 * @author Shuyang Zhou
 */
public class ReferenceEntry {

	public ReferenceEntry(Field field) {
		this(null, field);
	}

	public ReferenceEntry(Object object, Field field) {
		_object = object;

		_field = field;

		_field.setAccessible(true);
	}

	public Field getField() {
		return _field;
	}

	public Object getObject() {
		return _object;
	}

	public void setValue(Object value)
		throws IllegalAccessException, IllegalArgumentException {

		_field.set(_object, value);
	}

	@Override
	public String toString() {
		return _object.toString(
		).concat(
			StringPool.POUND
		).concat(
			_field.toString()
		);
	}

	private final Field _field;
	private final Object _object;

}