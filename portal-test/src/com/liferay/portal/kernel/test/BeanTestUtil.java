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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Objects;

/**
 * @author Guilherme Camacho
 */
public class BeanTestUtil {

	public static void copyProperties(Object source, Object target)
		throws Exception {

		Class<?> sourceClass = source.getClass();

		if (_hasSuperClass(sourceClass)) {
			sourceClass = sourceClass.getSuperclass();
		}

		Class<?> targetClass = target.getClass();

		for (Field field : sourceClass.getDeclaredFields()) {
			if (!field.isSynthetic()) {
				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source, null));
			}
		}
	}

	public static boolean hasProperty(Object bean, String name) {
		if (_hasMethod(
				bean.getClass(),
				"set" + StringUtil.upperCaseFirstLetter(name))) {

			return true;
		}

		return false;
	}

	public static void setProperty(Object bean, String name, Object value)
		throws Exception {

		Class<?> clazz = bean.getClass();

		Method getMethod = _getMethod(clazz, name, "get");

		Method setMethod = _getMethod(
			clazz, name, "set", getMethod.getReturnType());

		setMethod.invoke(
			bean, _translateValue(getMethod.getReturnType(), value));
	}

	private static Method _getMethod(
			Class<?> clazz, String fieldName, String prefix,
			Class<?>... parameterTypes)
		throws Exception {

		return clazz.getMethod(
			prefix + StringUtil.upperCaseFirstLetter(fieldName),
			parameterTypes);
	}

	private static boolean _hasMethod(Class<?> clazz, String name) {
		for (Method method : clazz.getMethods()) {
			if (name.equals(method.getName())) {
				return true;
			}
		}

		return false;
	}

	private static boolean _hasSuperClass(Class<?> clazz) {
		Class<?> superClass = clazz.getSuperclass();

		if ((superClass != null) &&
			!Objects.equals(superClass.getName(), "java.lang.Object")) {

			return true;
		}

		return false;
	}

	private static Object _translateValue(
		Class<?> parameterType, Object value) {

		if (parameterType.equals(Long.class)) {
			return GetterUtil.getLong(value);
		}

		return value;
	}

}