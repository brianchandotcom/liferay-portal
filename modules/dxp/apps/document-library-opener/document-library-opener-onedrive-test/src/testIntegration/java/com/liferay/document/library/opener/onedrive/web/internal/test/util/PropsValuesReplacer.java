/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.test.util;

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