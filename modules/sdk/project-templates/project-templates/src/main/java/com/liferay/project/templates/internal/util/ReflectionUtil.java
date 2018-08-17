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