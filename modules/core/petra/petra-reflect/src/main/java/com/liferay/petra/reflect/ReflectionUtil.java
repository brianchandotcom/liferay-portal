/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
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

	public static Field fetchDeclaredField(Class<?> clazz, String name) {
		if (_fetchDeclaredFieldMethodHandle == null) {
			try {
				return getDeclaredField(clazz, name);
			}
			catch (Exception exception) {
				return null;
			}
		}

		try {
			return (Field)_fetchDeclaredFieldMethodHandle.invokeExact(
				clazz, name);
		}
		catch (Throwable throwable) {
			return throwException(throwable);
		}
	}

	public static Method fetchDeclaredMethod(
		Class<?> clazz, String name, Class<?>... parameterTypes) {

		if (_fetchDeclaredMethodMethodHandle == null) {
			try {
				return getDeclaredMethod(clazz, name, parameterTypes);
			}
			catch (Exception exception) {
				return null;
			}
		}

		try {
			return (Method)_fetchDeclaredMethodMethodHandle.invokeExact(
				clazz, name, parameterTypes);
		}
		catch (Throwable throwable) {
			return throwException(throwable);
		}
	}

	public static Field fetchField(Class<?> clazz, String name) {
		if (_fetchFieldMethodHandle == null) {
			try {
				Field field = clazz.getField(name);

				field.setAccessible(true);

				return field;
			}
			catch (Exception exception) {
				return null;
			}
		}

		try {
			return (Field)_fetchFieldMethodHandle.invokeExact(clazz, name);
		}
		catch (Throwable throwable) {
			return throwException(throwable);
		}
	}

	public static Method fetchMethod(
		Class<?> clazz, String name, Class<?>... parameterTypes) {

		if (_fetchMethodMethodHandle == null) {
			try {
				Method method = clazz.getMethod(name, parameterTypes);

				method.setAccessible(true);

				return method;
			}
			catch (Exception exception) {
				return null;
			}
		}

		try {
			return (Method)_fetchMethodMethodHandle.invokeExact(
				clazz, name, parameterTypes);
		}
		catch (Throwable throwable) {
			return throwException(throwable);
		}
	}

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

	private static Field _fetchDeclaredField(
			MethodHandle privateGetDeclaredFieldsMethodHandle,
			MethodHandle searchFieldsMethodHandle,
			MethodHandle copyFieldMethodHandle, Class<?> clazz, String name)
		throws Throwable {

		Field field = (Field)searchFieldsMethodHandle.invokeExact(
			(Field[])privateGetDeclaredFieldsMethodHandle.invokeExact(
				clazz, false),
			name);

		if (field == null) {
			return null;
		}

		field = (Field)copyFieldMethodHandle.invokeExact(field);

		field.setAccessible(true);

		return field;
	}

	private static Method _fetchDeclaredMethod(
			MethodHandle privateGetDeclaredMethodsMethodHandle,
			MethodHandle searchMethodsMethodHandle,
			MethodHandle copyMethodMethodHandle, Class<?> clazz, String name,
			Class<?>... parameterTypes)
		throws Throwable {

		Method method = (Method)searchMethodsMethodHandle.invokeExact(
			(Method[])privateGetDeclaredMethodsMethodHandle.invokeExact(
				clazz, false),
			name, parameterTypes);

		if (method == null) {
			return null;
		}

		method = (Method)copyMethodMethodHandle.invokeExact(method);

		method.setAccessible(true);

		return method;
	}

	private static Field _fetchField(
			MethodHandle getField0MethodHandle,
			MethodHandle copyFieldMethodHandle, Class<?> clazz, String name)
		throws Throwable {

		Field field = (Field)getField0MethodHandle.invokeExact(clazz, name);

		if (field == null) {
			return null;
		}

		field = (Field)copyFieldMethodHandle.invokeExact(field);

		field.setAccessible(true);

		return field;
	}

	private static Method _fetchMethod(
			MethodHandle getMethod0MethodHandle,
			MethodHandle copyMethodMethodHandle, Class<?> clazz, String name,
			Class<?>... parameterTypes)
		throws Throwable {

		Method method = (Method)getMethod0MethodHandle.invokeExact(
			clazz, name, parameterTypes);

		if (method == null) {
			return null;
		}

		method = (Method)copyMethodMethodHandle.invokeExact(method);

		method.setAccessible(true);

		return method;
	}

	@SuppressWarnings("unchecked")
	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

	private static final MethodHandle _fetchDeclaredFieldMethodHandle;
	private static final MethodHandle _fetchDeclaredMethodMethodHandle;
	private static final MethodHandle _fetchFieldMethodHandle;
	private static final MethodHandle _fetchMethodMethodHandle;
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

		MethodHandle fetchDeclaredFieldMethodHandle;
		MethodHandle fetchDeclaredMethodMethodHandle;
		MethodHandle fetchFieldMethodHandle;
		MethodHandle fetchMethodMethodHandle;

		try {
			Method method = Class.class.getDeclaredMethod(
				"getReflectionFactory");

			method.setAccessible(true);

			Object reflectionFactory = method.invoke(null);

			MethodHandle copyFieldMethodHandle = _lookup.findVirtual(
				reflectionFactory.getClass(), "copyField",
				MethodType.methodType(Field.class, Field.class));

			copyFieldMethodHandle = copyFieldMethodHandle.bindTo(
				reflectionFactory);

			MethodHandle copyMethodMethodHandle = _lookup.findVirtual(
				reflectionFactory.getClass(), "copyMethod",
				MethodType.methodType(Method.class, Method.class));

			copyMethodMethodHandle = copyMethodMethodHandle.bindTo(
				reflectionFactory);

			fetchDeclaredFieldMethodHandle = _lookup.findStatic(
				ReflectionUtil.class, "_fetchDeclaredField",
				MethodType.methodType(
					Field.class, MethodHandle.class, MethodHandle.class,
					MethodHandle.class, Class.class, String.class));

			fetchDeclaredFieldMethodHandle = MethodHandles.insertArguments(
				fetchDeclaredFieldMethodHandle, 0,
				_lookup.findSpecial(
					Class.class, "privateGetDeclaredFields",
					MethodType.methodType(Field[].class, boolean.class),
					Class.class),
				_lookup.findStatic(
					Class.class, "searchFields",
					MethodType.methodType(
						Field.class, Field[].class, String.class)),
				copyFieldMethodHandle);

			fetchDeclaredMethodMethodHandle = _lookup.findStatic(
				ReflectionUtil.class, "_fetchDeclaredMethod",
				MethodType.methodType(
					Method.class, MethodHandle.class, MethodHandle.class,
					MethodHandle.class, Class.class, String.class,
					Class[].class));

			fetchDeclaredMethodMethodHandle = MethodHandles.insertArguments(
				fetchDeclaredMethodMethodHandle, 0,
				_lookup.findSpecial(
					Class.class, "privateGetDeclaredMethods",
					MethodType.methodType(Method[].class, boolean.class),
					Class.class),
				_lookup.findStatic(
					Class.class, "searchMethods",
					MethodType.methodType(
						Method.class, Method[].class, String.class,
						Class[].class)),
				copyMethodMethodHandle);

			fetchFieldMethodHandle = _lookup.findStatic(
				ReflectionUtil.class, "_fetchField",
				MethodType.methodType(
					Field.class, MethodHandle.class, MethodHandle.class,
					Class.class, String.class));

			fetchFieldMethodHandle = MethodHandles.insertArguments(
				fetchFieldMethodHandle, 0,
				_lookup.findSpecial(
					Class.class, "getField0",
					MethodType.methodType(Field.class, String.class),
					Class.class),
				copyFieldMethodHandle);

			fetchMethodMethodHandle = _lookup.findStatic(
				ReflectionUtil.class, "_fetchMethod",
				MethodType.methodType(
					Method.class, MethodHandle.class, MethodHandle.class,
					Class.class, String.class, Class[].class));

			fetchMethodMethodHandle = MethodHandles.insertArguments(
				fetchMethodMethodHandle, 0,
				_lookup.findSpecial(
					Class.class, "getMethod0",
					MethodType.methodType(
						Method.class, String.class, Class[].class),
					Class.class),
				copyMethodMethodHandle);
		}
		catch (Exception exception) {
			fetchDeclaredFieldMethodHandle = null;
			fetchDeclaredMethodMethodHandle = null;
			fetchFieldMethodHandle = null;
			fetchMethodMethodHandle = null;
		}

		_fetchDeclaredFieldMethodHandle = fetchDeclaredFieldMethodHandle;
		_fetchDeclaredMethodMethodHandle = fetchDeclaredMethodMethodHandle;
		_fetchFieldMethodHandle = fetchFieldMethodHandle;
		_fetchMethodMethodHandle = fetchMethodMethodHandle;
	}

}