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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author     Brian Wing Shun Chan
 * @author     Miguel Pastor
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.petra.reflect.ReflectionUtil}
 */
@Deprecated
public class ReflectionUtil {

	public static Object arrayClone(Object array) {
		Class<?> clazz = array.getClass();

		if (!clazz.isArray()) {
			throw new IllegalArgumentException(
				"Input object is not an array: " + array);
		}

		try {
			return _cloneMethod.invoke(array);
		}
		catch (Exception e) {
			return throwException(e);
		}
	}

	public static Field getDeclaredField(Class<?> clazz, String name)
		throws Exception {

		Field field = clazz.getDeclaredField(name);

		field.setAccessible(true);

		return unfinalField(field);
	}

	public static Field[] getDeclaredFields(Class<?> clazz) throws Exception {
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			unfinalField(field);
		}

		return fields;
	}

	public static Method getDeclaredMethod(
			Class<?> clazz, String name, Class<?>... parameterTypes)
		throws Exception {

		Method method = clazz.getDeclaredMethod(name, parameterTypes);

		method.setAccessible(true);

		return method;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static Type getGenericInterface(
		Object object, Class<?> interfaceClass) {

		Class<?> clazz = object.getClass();

		Type genericInterface = _getGenericInterface(clazz, interfaceClass);

		if (genericInterface != null) {
			return genericInterface;
		}

		Class<?> superClass = clazz.getSuperclass();

		while (superClass != null) {
			genericInterface = _getGenericInterface(superClass, interfaceClass);

			if (genericInterface != null) {
				return genericInterface;
			}

			superClass = superClass.getSuperclass();
		}

		return null;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static Class<?> getGenericSuperType(Class<?> clazz) {
		try {
			ParameterizedType parameterizedType =
				(ParameterizedType)clazz.getGenericSuperclass();

			Type[] types = parameterizedType.getActualTypeArguments();

			if (types.length > 0) {
				return (Class<?>)types[0];
			}
		}
		catch (Throwable t) {
		}

		return null;
	}

	public static Class<?>[] getInterfaces(Object object) {
		return getInterfaces(object, null);
	}

	public static Class<?>[] getInterfaces(
		Object object, ClassLoader classLoader) {

		return getInterfaces(
			object, classLoader,
			cnfe -> {
			});
	}

	public static Class<?>[] getInterfaces(
		Object object, ClassLoader classLoader,
		Consumer<ClassNotFoundException> classNotFoundHandler) {

		Set<Class<?>> interfaceClasses = new LinkedHashSet<>();

		Class<?> superClass = object.getClass();

		while (superClass != null) {
			for (Class<?> interfaceClass : superClass.getInterfaces()) {
				try {
					if (classLoader == null) {
						interfaceClasses.add(interfaceClass);
					}
					else {
						interfaceClasses.add(
							classLoader.loadClass(interfaceClass.getName()));
					}
				}
				catch (ClassNotFoundException cnfe) {
					classNotFoundHandler.accept(cnfe);
				}
			}

			superClass = superClass.getSuperclass();
		}

		return interfaceClasses.toArray(new Class<?>[0]);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
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

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static Set<Method> getVisibleMethods(Class<?> clazz) {
		Set<Method> visibleMethods = new HashSet<>(
			Arrays.asList(clazz.getMethods()));

		Collections.addAll(visibleMethods, clazz.getDeclaredMethods());

		while ((clazz = clazz.getSuperclass()) != null) {
			for (Method method : clazz.getDeclaredMethods()) {
				int modifiers = method.getModifiers();

				if (!Modifier.isPrivate(modifiers) &
					!Modifier.isPublic(modifiers)) {

					visibleMethods.add(method);
				}
			}
		}

		return visibleMethods;
	}

	public static <T> T throwException(Throwable throwable) {
		return ReflectionUtil.<T, RuntimeException>_throwException(throwable);
	}

	public static Field unfinalField(Field field) throws Exception {
		int modifiers = field.getModifiers();

		if ((modifiers & Modifier.FINAL) == Modifier.FINAL) {
			_modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
		}

		return field;
	}

	private static Type _getGenericInterface(
		Class<?> clazz, Class<?> interfaceClass) {

		Type[] genericInterfaces = clazz.getGenericInterfaces();

		for (Type genericInterface : genericInterfaces) {
			if (!(genericInterface instanceof ParameterizedType)) {
				continue;
			}

			ParameterizedType parameterizedType =
				(ParameterizedType)genericInterface;

			Type rawType = parameterizedType.getRawType();

			if (rawType.equals(interfaceClass)) {
				return parameterizedType;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

	private static final Method _cloneMethod;
	private static final Field _modifiersField;

	static {
		try {
			_cloneMethod = Object.class.getDeclaredMethod("clone");

			_cloneMethod.setAccessible(true);

			_modifiersField = Field.class.getDeclaredField("modifiers");

			_modifiersField.setAccessible(true);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}