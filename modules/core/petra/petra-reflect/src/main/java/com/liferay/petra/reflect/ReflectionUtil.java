/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.reflect;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 * @author Shuyang Zhou
 */
public class ReflectionUtil {

	public static Field getDeclaredField(Class<?> clazz, String name)
		throws Exception {

		Field field = clazz.getDeclaredField(name);

		field.setAccessible(true);

		return field;
	}

	public static Field[] getDeclaredFields(Class<?> clazz) throws Exception {
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
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

	public static MethodHandles.Lookup getImplLookup() {
		return _lookup;
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
				catch (ClassNotFoundException classNotFoundException) {
					classNotFoundHandler.accept(classNotFoundException);
				}
			}

			superClass = superClass.getSuperclass();
		}

		return interfaceClasses.toArray(new Class<?>[0]);
	}

	public static <T> T throwException(Throwable throwable) {
		return ReflectionUtil.<T, RuntimeException>_throwException(throwable);
	}

	@SuppressWarnings("unchecked")
	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

	private static final MethodHandles.Lookup _lookup;

	static {
		try {
			Field field = MethodHandles.Lookup.class.getDeclaredField(
				"IMPL_LOOKUP");

			field.setAccessible(true);

			_lookup = (MethodHandles.Lookup)field.get(null);
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

}