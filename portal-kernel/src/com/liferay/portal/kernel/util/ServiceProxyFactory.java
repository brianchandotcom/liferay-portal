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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.ServiceTrackerFieldUpdaterCustomizer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Tina Tian
 */
public class ServiceProxyFactory {

	public static <T> T newServiceTrackedInstance(
		Class<T> serviceClass, Class<?> declaringClass, String fieldName,
		boolean blocking) {

		return newServiceTrackedInstance(
			serviceClass, declaringClass, fieldName, null, blocking, false);
	}

	public static <T> T newServiceTrackedInstance(
		Class<T> serviceClass, Class<?> declaringClass, String fieldName,
		boolean blocking, boolean useNullAsDummyService) {

		return newServiceTrackedInstance(
			serviceClass, declaringClass, fieldName, null, blocking,
			useNullAsDummyService);
	}

	public static <T> T newServiceTrackedInstance(
		Class<T> serviceClass, Class<?> declaringClass, String fieldName,
		String filterString, boolean blocking) {

		return newServiceTrackedInstance(
			serviceClass, declaringClass, fieldName, filterString, blocking,
			false);
	}

	public static <T> T newServiceTrackedInstance(
		Class<T> serviceClass, Class<?> declaringClass, String fieldName,
		String filterString, boolean blocking, boolean useNullAsDummyService) {

		try {
			Field field = declaringClass.getDeclaredField(fieldName);

			if (!Modifier.isStatic(field.getModifiers())) {
				throw new IllegalArgumentException(field + " is not static");
			}

			field.setAccessible(true);

			ServiceTrackerCustomizer<?, ?> serviceTrackerCustomizer = null;

			if (blocking) {
				ReentrantLock lock = new ReentrantLock();

				Condition realServiceSet = lock.newCondition();

				T awaitService = (T)ProxyUtil.newProxyInstance(
					serviceClass.getClassLoader(),
					new Class<?>[] {serviceClass},
					new AwaitServiceInvocationHandler(
						field, realServiceSet, lock));

				field.set(null, awaitService);

				serviceTrackerCustomizer =
					new AwaitServiceTrackerFieldUpdaterCustomizer<>(
						field, null, awaitService, realServiceSet, lock);
			}
			else {
				T dummyService = null;

				if (!useNullAsDummyService) {
					dummyService = ProxyFactory.newDummyInstance(serviceClass);

					field.set(null, dummyService);
				}

				serviceTrackerCustomizer =
					new ServiceTrackerFieldUpdaterCustomizer<>(
						field, null, dummyService);
			}

			_openServiceTracker(
				serviceClass, filterString, serviceTrackerCustomizer);

			return (T)field.get(null);
		}
		catch (ReflectiveOperationException roe) {
			return ReflectionUtil.throwException(roe);
		}
	}

	private static void _openServiceTracker(
			Class<?> serviceClass, String filterString,
			ServiceTrackerCustomizer<?, ?> serviceTrackerCustomizer)
		throws ReflectiveOperationException {

		ServiceTracker<?, ?> serviceTracker = null;

		String serviceName = serviceClass.getName();

		Registry registry = RegistryUtil.getRegistry();

		if (Validator.isNull(filterString)) {
			serviceTracker = registry.trackServices(
				serviceName, serviceTrackerCustomizer);
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append("(&(objectClass=");
			sb.append(serviceName);
			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(filterString);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			serviceTracker = registry.trackServices(
				registry.getFilter(sb.toString()), serviceTrackerCustomizer);
		}

		serviceTracker.open();
	}

	private static class AwaitServiceInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			while (true) {
				_lock.lock();

				try {
					Object service = _field.get(null);

					if (!ProxyUtil.isProxyClass(service.getClass()) ||
						(ProxyUtil.getInvocationHandler(service) != this)) {

						return method.invoke(service, arguments);
					}

					_realServiceSet.await();
				}
				finally {
					_lock.unlock();
				}
			}
		}

		private AwaitServiceInvocationHandler(
			Field field, Condition realServiceSet, Lock lock) {

			_field = field;
			_realServiceSet = realServiceSet;
			_lock = lock;
		}

		private final Field _field;
		private final Lock _lock;
		private final Condition _realServiceSet;

	}

	private static class AwaitServiceTrackerFieldUpdaterCustomizer<S, T>
		extends ServiceTrackerFieldUpdaterCustomizer<S, T> {

		@Override
		protected void doServiceUpdate(T newService) {
			_lock.lock();

			try {
				super.doServiceUpdate(newService);

				if (newService != _awaitService) {
					_realServiceSet.signalAll();
				}
			}
			finally {
				_lock.unlock();
			}
		}

		private AwaitServiceTrackerFieldUpdaterCustomizer(
			Field serviceField, Object serviceHolder, T awaitService,
			Condition realServiceSet, Lock lock) {

			super(serviceField, serviceHolder, awaitService);

			_awaitService = awaitService;
			_realServiceSet = realServiceSet;
			_lock = lock;
		}

		private final T _awaitService;
		private final Lock _lock;
		private final Condition _realServiceSet;

	}

}