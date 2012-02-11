/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 */
public class ReflectionUtil {

	public static Class<?> findDeclaringClassAnnotation(
		Class<? extends Annotation> annotationType, Class<?> clazz) {

		if (clazz == null || clazz.equals(Object.class)) {
			return null;
		}

		if (isAnnotationDeclaredInClass(annotationType, clazz)) {
			return clazz;
		}
		else {
			return findDeclaringClassAnnotation(
				annotationType, clazz.getSuperclass());
		}
	}

	public static Field getDeclaredField(Class<?> clazz, String name)
		throws Exception {

		Field field = clazz.getDeclaredField(name);

		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		return field;
	}

	public static Method getDeclaredMethod(
			Class<?> clazz, String name, Class<?> ... parameterTypes)
		throws Exception {

		Method method = clazz.getDeclaredMethod(name, parameterTypes);

		if (!method.isAccessible()) {
			method.setAccessible(true);
		}

		return method;
	}

	public static Class<?>[] getParameterTypes(Object[] arguments) {
		if (arguments == null) {
			return null;
		}

		Class<?>[] parameterTypes = new Class<?>[arguments.length];

		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == null) {
				parameterTypes[i] = null;
			}
			else if (arguments[i] instanceof Boolean) {
				parameterTypes[i] = Boolean.TYPE;
			}
			else if (arguments[i] instanceof Byte) {
				parameterTypes[i] = Byte.TYPE;
			}
			else if (arguments[i] instanceof Character) {
				parameterTypes[i] = Character.TYPE;
			}
			else if (arguments[i] instanceof Double) {
				parameterTypes[i] = Double.TYPE;
			}
			else if (arguments[i] instanceof Float) {
				parameterTypes[i] = Float.TYPE;
			}
			else if (arguments[i] instanceof Integer) {
				parameterTypes[i] = Integer.TYPE;
			}
			else if (arguments[i] instanceof Long) {
				parameterTypes[i] = Long.TYPE;
			}
			else if (arguments[i] instanceof Short) {
				parameterTypes[i] = Short.TYPE;
			}
			else {
				parameterTypes[i] = arguments[i].getClass();
			}
		}

		return parameterTypes;
	}

	public static Object instantiateClass(Class<?> clazz) throws Exception {
		if (clazz.isInterface()) {
			throw new IllegalArgumentException(
				clazz + " is an interface. This cannot be instanciated");
		}

		return clazz.newInstance();
	}

	public static boolean isAnnotationDeclaredInClass(
		Class<? extends Annotation> annotationType, Class<?> clazz) {

		if (annotationType == null || clazz == null) {
			throw new IllegalArgumentException();
		}

		Annotation[] annotations = clazz.getAnnotations();

		for (Annotation annotation:annotations) {
			if (annotation.annotationType().equals(annotationType)) {
				return true;
			}
		}

		return false;
	}

}