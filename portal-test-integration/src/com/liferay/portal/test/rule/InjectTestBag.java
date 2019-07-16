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

package com.liferay.portal.test.rule;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * @author Preston Crary
 */
public class InjectTestBag {

	public InjectTestBag(Class<?> testClass) throws Exception {
		this(testClass, null);
	}

	public InjectTestBag(Class<?> testClass, Object target) throws Exception {
		_target = target;

		while (testClass != Object.class) {
			for (Field field : ReflectionUtil.getDeclaredFields(testClass)) {
				boolean staticField = Modifier.isStatic(field.getModifiers());

				if (((_target == null) == staticField) &&
					field.isAnnotationPresent(Inject.class)) {

					_fields.add(field);
				}
			}

			testClass = testClass.getSuperclass();
		}
	}

	public void injectFields() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		for (Field field : _fields) {
			Inject inject = field.getAnnotation(Inject.class);

			Class<?> clazz = inject.type();

			if (clazz == Object.class) {
				clazz = field.getType();
			}

			ServiceReference<?> serviceReference = _getServiceReference(
				registry, clazz, field, inject.filter(), inject.blocking());

			if (serviceReference != null) {
				_serviceReferences.add(serviceReference);

				field.set(_target, registry.getService(serviceReference));
			}
		}
	}

	public void resetFields() throws Exception {
		for (Field field : _fields) {
			field.set(_target, null);
		}

		Registry registry = RegistryUtil.getRegistry();

		for (ServiceReference<?> serviceReference : _serviceReferences) {
			registry.ungetService(serviceReference);
		}
	}

	private <T> String _getFilterString(Class<T> clazz, String filterString) {
		if (filterString.isEmpty()) {
			return "(objectClass=" + clazz.getName() + ")";
		}

		if ((clazz != Object.class) && !filterString.contains("objectClass")) {
			int index = filterString.indexOf('&');

			StringBundler sb = new StringBundler(5);

			if (index < 0) {
				sb.append("(&(objectClass=");
				sb.append(clazz.getName());
				sb.append(")(");
				sb.append(filterString);
				sb.append("))");
			}
			else {
				sb.append(filterString.substring(0, index));
				sb.append("&(objectClass=");
				sb.append(clazz.getName());
				sb.append(")");
				sb.append(filterString.substring(index + 1));
			}

			filterString = sb.toString();
		}

		return filterString;
	}

	private <T> ServiceReference<T> _getServiceReference(
			Registry registry, Class<T> clazz, Field field, String filterString,
			boolean blocking)
		throws Exception {

		String filterStringString = _getFilterString(clazz, filterString);

		ServiceReference<T> serviceReference = _getServiceReference(
			registry, clazz, filterStringString);

		if ((serviceReference != null) || !blocking) {
			return serviceReference;
		}

		CountDownLatch countDownLatch = new CountDownLatch(1);

		AtomicReference<ServiceTracker<T, T>> atomicReference =
			new AtomicReference<>();

		ServiceTracker<T, T> serviceTracker = registry.trackServices(
			registry.getFilter(filterStringString),
			new ServiceTrackerCustomizer<T, T>() {

				@Override
				public T addingService(ServiceReference<T> serviceReference) {
					countDownLatch.countDown();

					ServiceTracker<T, T> serviceTracker = atomicReference.get();

					serviceTracker.close();

					return null;
				}

				@Override
				public void modifiedService(
					ServiceReference<T> serviceReference, T service) {
				}

				@Override
				public void removedService(
					ServiceReference<T> serviceReference, T service) {
				}

			});

		atomicReference.set(serviceTracker);

		serviceTracker.open();

		int waitTime = 0;

		String className = clazz.getName();

		while (serviceReference == null) {
			waitTime += _SLEEP_TIME;

			if (waitTime >= TestPropsValues.CI_TEST_TIMEOUT_TIME) {
				throw new IllegalStateException(
					StringBundler.concat(
						"Timed out while waiting for service ", className, " ",
						filterString));
			}

			Class<?> testClass = field.getDeclaringClass();

			System.out.println(
				StringBundler.concat(
					"Waiting for service ", className, " ", filterString,
					" for field ", testClass.getName(), ".", field.getName()));

			try {
				countDownLatch.await(_SLEEP_TIME, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException ie) {
				System.out.println(
					StringBundler.concat(
						"Stopped waiting for service ", className, " ",
						filterString, " for field ", testClass.getName(), ".",
						field.getName(), " due to interruption"));

				throw ie;
			}

			serviceReference = _getServiceReference(
				registry, clazz, filterStringString);
		}

		return serviceReference;
	}

	private <T> ServiceReference<T> _getServiceReference(
			Registry registry, Class<T> clazz, String filterString)
		throws Exception {

		Collection<ServiceReference<T>> serviceReferences =
			registry.getServiceReferences(clazz, filterString);

		Stream<ServiceReference<T>> stream = serviceReferences.stream();

		Optional<ServiceReference<T>> optional = stream.findFirst();

		return optional.orElse(null);
	}

	private static final int _SLEEP_TIME = 2000;

	private final List<Field> _fields = new ArrayList<>();
	private final List<ServiceReference<?>> _serviceReferences =
		new ArrayList<>();
	private final Object _target;

}