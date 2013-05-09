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

package com.liferay.portal.kernel.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 * @author Shuyang Zhou
 */
public class ReflectionUtil {

	public static Class<?> getAnnotationDeclaringClass(
		Class<? extends Annotation> annotationClass, Class<?> clazz) {

		if ((clazz == null) || clazz.equals(Object.class)) {
			return null;
		}

		if (isAnnotationDeclaredInClass(annotationClass, clazz)) {
			return clazz;
		}
		else {
			return getAnnotationDeclaringClass(
				annotationClass, clazz.getSuperclass());
		}
	}

	public static Method getBridgeMethod(
			Class<?> clazz, String name, Class<?> ... parameterTypes)
		throws Exception {

		Method method = clazz.getMethod(name, parameterTypes);

		if (method.isBridge()) {
			return method;
		}

		bridge:
		for (Method currentMethod : clazz.getMethods()) {
			if (!currentMethod.isBridge() ||
				!name.equals(currentMethod.getName())) {

				continue;
			}

			Class<?>[] currentParameterTypes =
				currentMethod.getParameterTypes();

			if (currentParameterTypes.length != parameterTypes.length) {
				continue;
			}

			for (int i = 0; i < currentParameterTypes.length; i++) {
				if (!currentParameterTypes[i].isAssignableFrom(
						parameterTypes[i])) {

					continue bridge;
				}
			}

			return currentMethod;
		}

		throw new NoSuchMethodException(
			"No bridge method on " + clazz + " with name " + name +
				" and parameter types " + Arrays.toString(parameterTypes));
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

	public static Class<?>[] getInterfaces(Object object) {
		return getInterfaces(object, null);
	}

	public static Class<?>[] getInterfaces(
		Object object, ClassLoader classLoader) {

		List<Class<?>> interfaceClasses = new UniqueList<Class<?>>();

		Class<?> clazz = object.getClass();

		_getInterfaces(interfaceClasses, clazz, classLoader);

		Class<?> superClass = clazz.getSuperclass();

		while (superClass != null) {
			_getInterfaces(interfaceClasses, superClass, classLoader);

			superClass = superClass.getSuperclass();
		}

		return interfaceClasses.toArray(new Class<?>[interfaceClasses.size()]);
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

	public static boolean isAnnotationDeclaredInClass(
		Class<? extends Annotation> annotationClass, Class<?> clazz) {

		if ((annotationClass == null) || (clazz == null)) {
			throw new IllegalArgumentException();
		}

		Annotation[] annotations = clazz.getAnnotations();

		for (Annotation annotation : annotations) {
			if (annotationClass.equals(annotation.annotationType())) {
				return true;
			}
		}

		return false;
	}

	public static <T extends Enum<T>> T newEnumElement(
			Class<T> enumClass, Class<?>[] constructorParameterTypes,
			String name, int ordinal, Object... constructorParameters)
		throws Exception {

		Class<?>[] parameterTypes = null;

		if ((constructorParameterTypes != null) &&
			(constructorParameterTypes.length != 0)) {

			parameterTypes = new Class<?>[constructorParameterTypes.length + 2];

			parameterTypes[0] = String.class;
			parameterTypes[1] = int.class;

			System.arraycopy(
				constructorParameterTypes, 0, parameterTypes, 2,
				constructorParameterTypes.length);
		}
		else {
			parameterTypes = new Class<?>[2];

			parameterTypes[0] = String.class;
			parameterTypes[1] = int.class;
		}

		Constructor<T> constructor = enumClass.getDeclaredConstructor(
			parameterTypes);

		Method acquireConstructorAccessorMethod = getDeclaredMethod(
			Constructor.class, "acquireConstructorAccessor");

		acquireConstructorAccessorMethod.invoke(constructor);

		Field constructorAccessorField = getDeclaredField(
			Constructor.class, "constructorAccessor");

		Object constructorAccessor = constructorAccessorField.get(constructor);

		Method newInstanceMethod = getDeclaredMethod(
			constructorAccessor.getClass(), "newInstance", Object[].class);

		Object[] parameters = null;

		if ((constructorParameters != null) &&
			(constructorParameters.length != 0)) {

			parameters = new Object[constructorParameters.length + 2];

			parameters[0] = name;
			parameters[1] = ordinal;

			System.arraycopy(
				constructorParameters, 0, parameters, 2,
				constructorParameters.length);

		}
		else {
			parameters = new Object[2];

			parameters[0] = name;
			parameters[1] = ordinal;
		}

		return (T)newInstanceMethod.invoke(
			constructorAccessor, new Object[]{parameters});
	}

	public static <T extends Enum<T>> T newEnumElement(
			Class<T> enumClass, String name, int ordinal)
		throws Exception {

		return newEnumElement(enumClass, null, name, ordinal, (Object[])null);
	}

	private static void _getInterfaces(
		List<Class<?>> interfaceClasses, Class<?> clazz,
		ClassLoader classLoader) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			try {
				if (classLoader != null) {
					interfaceClasses.add(
						classLoader.loadClass(interfaceClass.getName()));
				}
				else {
					interfaceClasses.add(interfaceClass);
				}
			}
			catch (ClassNotFoundException cnfe) {
			}
		}
	}

}