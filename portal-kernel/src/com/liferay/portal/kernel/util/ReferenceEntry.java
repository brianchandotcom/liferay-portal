/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return StringBundler.concat(
			_object.toString(), StringPool.POUND, _field.toString());
	}

	private final Field _field;
	private final Object _object;

}