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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.Inject;
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
				registry, clazz, inject.filter(), inject.blocking());

			if (serviceReference != null) {
				_serviceReferences.add(serviceReference);

				Object service = registry.getService(serviceReference);

				field.set(_target, service);
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

	private <T> String _getFilterString(Class<T> clazz, String filter) {
		if (filter.isEmpty()) {
			return "(objectClass=" + clazz.getName() + ")";
		}

		if ((clazz != Object.class) && !filter.contains("objectClass")) {
			int index = filter.indexOf('&');

			StringBundler sb = new StringBundler(5);

			if (index < 0) {
				sb.append("(&(objectClass=");
				sb.append(clazz.getName());
				sb.append(")(");
				sb.append(filter);
				sb.append("))");
			}
			else {
				sb.append(filter.substring(0, index));
				sb.append("&(objectClass=");
				sb.append(clazz.getName());
				sb.append(")");
				sb.append(filter.substring(index + 1));
			}

			filter = sb.toString();
		}

		return filter;
	}

	private <T> ServiceReference<T> _getServiceReference(
			Registry registry, Class<T> clazz, String filter)
		throws Exception {

		Collection<ServiceReference<T>> serviceReferences =
			registry.getServiceReferences(
				clazz, _getFilterString(clazz, filter));

		Stream<ServiceReference<T>> stream = serviceReferences.stream();

		Optional<ServiceReference<T>> optional = stream.findFirst();

		return optional.orElse(null);
	}

	private <T> ServiceReference<T> _getServiceReference(
			Registry registry, Class<T> clazz, String filter, boolean blocking)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		AtomicReference<ServiceTracker<T, T>> atomicReference =
			new AtomicReference<>();

		ServiceTracker<T, T> serviceTracker = registry.trackServices(
			registry.getFilter(_getFilterString(clazz, filter)),
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

		ServiceReference<T> serviceReference = _getServiceReference(
			registry, clazz, filter);

		if (blocking) {
			int waitTime = 0;

			String className = clazz.getName();

			while (serviceReference == null) {
				waitTime += _SLEEP_TIME;

				if (waitTime >= TestPropsValues.CI_TEST_TIMEOUT_TIME) {
					throw new IllegalStateException(
						"Timed out while waiting for service " + className +
							" " + filter);
				}

				System.out.println(
					"Waiting for service " + className + " " + filter);

				try {
					countDownLatch.await(_SLEEP_TIME, TimeUnit.MILLISECONDS);
				}
				catch (InterruptedException ie) {
				}

				serviceReference = _getServiceReference(
					registry, clazz, filter);
			}
		}

		return serviceReference;
	}

	private static final int _SLEEP_TIME = 2000;

	private final List<Field> _fields = new ArrayList<>();
	private final List<ServiceReference<?>> _serviceReferences =
		new ArrayList<>();
	private final Object _target;

}