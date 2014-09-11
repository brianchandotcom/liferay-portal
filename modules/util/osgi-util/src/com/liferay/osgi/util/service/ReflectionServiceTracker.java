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

package com.liferay.osgi.util.service;

import com.liferay.osgi.util.exception.ServiceUnavailableException;
import com.liferay.osgi.util.service.annotations.Reference;

import java.io.Closeable;
import java.io.IOException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class ReflectionServiceTracker implements Closeable {

	public ReflectionServiceTracker(Object targetObject) {
		Class<?> targetObjectClass = targetObject.getClass();

		Bundle bundle = FrameworkUtil.getBundle(targetObjectClass);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackers = new ArrayList<ServiceTracker>();

		for (Method injectableMethod : injectableMethods) {
			ServiceTracker serviceTracker = track(
				bundleContext, targetObject, injectableMethod);

			_serviceTrackers.add(serviceTracker);
		}
	}

	@Override
	public void close() throws IOException {
		for (ServiceTracker serviceTracker : _serviceTrackers) {
			try {
				serviceTracker.close();
			}
			catch (Exception e) {
				//Nothing to do... keep trying to clean
			}
		}

		_serviceTrackers.clear();
	}

	protected Object getProxyForUnavailable(
		ClassLoader classLoader, Class serviceClass) {

		if (!serviceClass.isInterface()) {
			return null;
		}

		return Proxy.newProxyInstance(
			classLoader, new Class[]{serviceClass}, new InvocationHandler() {

			@Override
			public Object invoke(Object o, Method method, Object[] objects)
				throws Throwable {

				throw new ServiceUnavailableException();
			}
		});
	}

	protected List<Method> getInjectionMethods(Object object) {
		Class<?> clazz = object.getClass();

		Method[] declaredMethods = clazz.getDeclaredMethods();

		ArrayList<Method> setterMethods = new ArrayList<Method>();

		for (Method method : declaredMethods) {
			boolean annotationPresent = method.isAnnotationPresent(
				Reference.class);

			Class<?>[] parameterTypes = method.getParameterTypes();

			Class<?> returnType = method.getReturnType();

			if (annotationPresent && (parameterTypes.length == 1) &&
				returnType.equals(void.class)) {

				setterMethods.add(method);
			}
		}

		return setterMethods;
	}

	protected ServiceTracker track(
		final BundleContext bundleContext, final Object target,
		final Method referenceMethod) {

		final Class<?> parameterType = referenceMethod.getParameterTypes()[0];

		final Object proxyForUnavailable = getProxyForUnavailable(
			target.getClass().getClassLoader(), parameterType);

		try {
			referenceMethod.invoke(target, proxyForUnavailable);
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Could not set proxy using " +
					referenceMethod.getName() + " on "+ target, e);
		}

		ServiceTracker serviceTracker = new ServiceTracker(
			bundleContext, parameterType, null) {

			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);

				ServiceReference current = getServiceReference();

				if ((current == null) || (reference.compareTo(current) > 0)) {
					try {
						referenceMethod.invoke(target, service);
					}
					catch (Exception e) {
						throw new RuntimeException(
							"Could not set service reference using " +
								referenceMethod.getName() + " on "+ target, e);
					}
				}

				return service;
			}

			@Override
			public void removedService(
				ServiceReference reference, Object service) {

				Object highestService;

				try {
					super.removedService(reference, service);

					ServiceReference serviceReference = getServiceReference();

					highestService = serviceReference == null ?
						proxyForUnavailable : getService(serviceReference);
				}
				catch (IllegalStateException ise) {
				}

				try {
					referenceMethod.invoke(target, highestService);
				}
				catch (Exception e) {
					throw new RuntimeException(
						"Could not set service reference to null on " +
							target, e);
				}
			}
		};

		serviceTracker.open();

		return serviceTracker;
	}

	private ArrayList<ServiceTracker> _serviceTrackers;

}