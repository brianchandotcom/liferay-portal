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

package com.liferay.message.boards.test.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

/**
 * @author Adolfo Pérez
 */
public class PropsValuesReplacer implements AutoCloseable {

	public PropsValuesReplacer(String name, Object value) throws Exception {
		Field propsKeysField = ReflectionUtil.getDeclaredField(
			PropsKeys.class, name);

		_propsKeysName = (String)propsKeysField.get(null);

		_propsKeysOldValue = PropsUtil.get(_propsKeysName);

		PropsUtil.set(_propsKeysName, String.valueOf(value));

		_propsValuesField = ReflectionUtil.getDeclaredField(
			PropsValues.class, name);

		_propsValuesOldValue = _propsValuesField.get(null);

		_propsValuesField.set(null, value);
	}

	@Override
	public void close() throws Exception {
		PropsUtil.set(_propsKeysName, _propsKeysOldValue);

		_propsValuesField.set(null, _propsValuesOldValue);
	}

	private final String _propsKeysName;
	private final String _propsKeysOldValue;
	private final Field _propsValuesField;
	private final Object _propsValuesOldValue;

}