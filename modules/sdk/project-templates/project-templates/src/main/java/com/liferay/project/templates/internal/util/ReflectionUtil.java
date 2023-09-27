/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.internal.util;

import java.lang.reflect.Field;

/**
 * @author Gregory Amerson
 */
public class ReflectionUtil {

	public static Field getField(Class<?> clazz, String name) throws Exception {
		Field field = clazz.getDeclaredField(name);

		field.setAccessible(true);

		return field;
	}

	public static void setFieldValue(
			Class<?> clazz, String name, Object object, Object value)
		throws Exception {

		Field field = getField(clazz, name);

		field.set(object, value);
	}

	public static void setFieldValue(Field field, Object object, Object value)
		throws Exception {

		field.set(object, value);
	}

}