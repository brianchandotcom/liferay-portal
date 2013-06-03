/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Raymond Augé
 */
public class StaticUtil {

	public static Field getSettableField(Class<?> clazz, String fieldName)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			clazz, fieldName);

		int modifiers = field.getModifiers();

		if ((modifiers & Modifier.FINAL) == Modifier.FINAL) {
			Field modifiersField = ReflectionUtil.getDeclaredField(
				Field.class, "modifiers");

			modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
		}

		return field;
	}

}