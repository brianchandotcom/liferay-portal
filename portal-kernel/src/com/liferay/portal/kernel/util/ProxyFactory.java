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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 */
public class ProxyFactory {

	public static <T> T newDummyInstance(Class<T> interfaceClass) {
		return (T)ProxyUtil.newProxyInstance(
			interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass},
			new DummyInvocationHandler<T>());
	}

	public static Object newInstance(
			ClassLoader classLoader, Class<?> interfaceClass,
			String implClassName)
		throws Exception {

		return newInstance(
			classLoader, new Class<?>[] {interfaceClass}, implClassName);
	}

	public static Object newInstance(
			ClassLoader classLoader, Class<?>[] interfaceClasses,
			String implClassName)
		throws Exception {

		Object instance = InstanceFactory.newInstance(
			classLoader, implClassName);

		return ProxyUtil.newProxyInstance(
			classLoader, interfaceClasses,
			new ClassLoaderBeanHandler(instance, classLoader));
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #newServiceTrackedInstance(Class, Class, String)}
	 */
	@Deprecated
	public static <T> T newServiceTrackedInstance(Class<T> interfaceClass) {
		return (T)ProxyUtil.newProxyInstance(
			interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass},
			new ServiceTrackedInvocationHandler<>(interfaceClass));
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             ServiceProxyFactory#newServiceTrackedInstance(Class, Class,
	 *             String, boolean)}
	 */
	@Deprecated
	public static <T> T newServiceTrackedInstance(
		Class<T> serviceClass, Class<?> declaringClass, String fieldName) {

		return ServiceProxyFactory.newServiceTrackedInstance(
			serviceClass, declaringClass, fieldName, false);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             ServiceProxyFactory#newServiceTrackedInstance(Class, Class,
	 *             String, String, boolean)}
	 */
	@Deprecated
	public static <T> T newServiceTrackedInstance(
		Class<T> serviceClass, Class<?> declaringClass, String fieldName,
		String filterString) {

		return ServiceProxyFactory.newServiceTrackedInstance(
			serviceClass, declaringClass, fieldName, filterString, false);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #newServiceTrackedInstance(Class, Class, String, String)}
	 */
	@Deprecated
	public static <T> T newServiceTrackedInstance(
		Class<T> interfaceClass, String filterString) {

		return (T)ProxyUtil.newProxyInstance(
			interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass},
			new ServiceTrackedInvocationHandler<>(
				interfaceClass, filterString));
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             ServiceProxyFactory#newServiceTrackedInstance(Class, Class,
	 *             String, boolean)}
	 */
	@Deprecated
	public static <T> T newServiceTrackedInstanceWithoutDummyService(
		Class<T> serviceClass, Class<?> declaringClass, String fieldName) {

		return ServiceProxyFactory.newServiceTrackedInstance(
			serviceClass, declaringClass, fieldName, true);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             ServiceProxyFactory#newServiceTrackedInstance(Class, Class,
	 *             String, String, boolean)}
	 */
	@Deprecated
	public static <T> T newServiceTrackedInstanceWithoutDummyService(
		Class<T> serviceClass, Class<?> declaringClass, String fieldName,
		String filterString) {

		return ServiceProxyFactory.newServiceTrackedInstance(
			serviceClass, declaringClass, fieldName, filterString, true);
	}

	private static final Log _log = LogFactoryUtil.getLog(ProxyFactory.class);

	private static class DummyInvocationHandler<T>
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			Class<?> returnType = method.getReturnType();

			if (returnType.equals(boolean.class)) {
				return GetterUtil.DEFAULT_BOOLEAN;
			}
			else if (returnType.equals(byte.class)) {
				return GetterUtil.DEFAULT_BYTE;
			}
			else if (returnType.equals(double.class)) {
				return GetterUtil.DEFAULT_DOUBLE;
			}
			else if (returnType.equals(float.class)) {
				return GetterUtil.DEFAULT_FLOAT;
			}
			else if (returnType.equals(int.class)) {
				return GetterUtil.DEFAULT_INTEGER;
			}
			else if (returnType.equals(long.class)) {
				return GetterUtil.DEFAULT_LONG;
			}
			else if (returnType.equals(short.class)) {
				return GetterUtil.DEFAULT_SHORT;
			}

			return method.getDefaultValue();
		}

	}

	private static class ServiceTrackedInvocationHandler<T>
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			T service = _serviceTracker.getService();

			if (service != null) {
				try {
					return method.invoke(service, arguments);
				}
				catch (InvocationTargetException ite) {
					throw ite.getTargetException();
				}
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Skipping ", method.getName(), " because ",
						_interfaceClassName, " is not registered"));
			}

			Class<?> returnType = method.getReturnType();

			if (returnType.equals(boolean.class)) {
				return GetterUtil.DEFAULT_BOOLEAN;
			}
			else if (returnType.equals(byte.class)) {
				return GetterUtil.DEFAULT_BYTE;
			}
			else if (returnType.equals(double.class)) {
				return GetterUtil.DEFAULT_DOUBLE;
			}
			else if (returnType.equals(float.class)) {
				return GetterUtil.DEFAULT_FLOAT;
			}
			else if (returnType.equals(int.class)) {
				return GetterUtil.DEFAULT_INTEGER;
			}
			else if (returnType.equals(long.class)) {
				return GetterUtil.DEFAULT_LONG;
			}
			else if (returnType.equals(short.class)) {
				return GetterUtil.DEFAULT_SHORT;
			}

			return method.getDefaultValue();
		}

		private ServiceTrackedInvocationHandler(Class<T> interfaceClass) {
			this(interfaceClass, null);
		}

		private ServiceTrackedInvocationHandler(
			Class<T> interfaceClass, String filterString) {

			_interfaceClassName = interfaceClass.getName();

			Registry registry = RegistryUtil.getRegistry();

			if (Validator.isNull(filterString)) {
				_serviceTracker = registry.trackServices(interfaceClass);
			}
			else {
				StringBundler sb = new StringBundler(7);

				sb.append("(&(objectClass=");
				sb.append(_interfaceClassName);
				sb.append(StringPool.CLOSE_PARENTHESIS);

				if (!filterString.startsWith(StringPool.OPEN_PARENTHESIS)) {
					sb.append(StringPool.OPEN_PARENTHESIS);
				}

				sb.append(filterString);

				if (!filterString.endsWith(StringPool.CLOSE_PARENTHESIS)) {
					sb.append(StringPool.CLOSE_PARENTHESIS);
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);

				Filter filter = registry.getFilter(sb.toString());

				_serviceTracker = registry.trackServices(filter);
			}

			_serviceTracker.open();
		}

		private final String _interfaceClassName;
		private final ServiceTracker<T, T> _serviceTracker;

	}

}