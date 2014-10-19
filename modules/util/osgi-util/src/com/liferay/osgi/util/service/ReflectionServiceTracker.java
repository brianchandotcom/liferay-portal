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

import java.io.Closeable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
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

	public ReflectionServiceTracker(Object target) {
		Class<?> targetClass = target.getClass();

		Bundle bundle = FrameworkUtil.getBundle(targetClass);

		BundleContext bundleContext = bundle.getBundleContext();

		List<InjectionPoint> injectionPoints = getInjectionPoints(target);

		for (InjectionPoint injectionPoint : injectionPoints) {
			ServiceTracker<?, ?> serviceTracker = track(
				bundleContext, target, injectionPoint);

			_serviceTrackers.add(serviceTracker);
		}
	}

	@Override
	public void close() {
		for (ServiceTracker<?, ?> serviceTracker : _serviceTrackers) {
			try {
				serviceTracker.close();
			}
			catch (Exception e) {
			}
		}

		_serviceTrackers.clear();
	}

	protected InjectionPoint createInjectionPoint(
		Object target, Method method) {

		Class<?> clazz = method.getParameterTypes()[0];

		if (clazz.isInterface()) {
			return new UnavailableProxyInjectionPoint(
				target, method, _unavailableServiceProxy);
		}

		return new InjectionPoint(target, method);
	}

	protected List<Method> getInjectionPointMethods(Object target) {
		List<Method> injectionPointMethods = new ArrayList<Method>();

		Class<?> targetClass = target.getClass();

		Method[] methods = targetClass.getDeclaredMethods();

		for (Method method : methods) {
			boolean annotationPresent = method.isAnnotationPresent(
				Reference.class);
			Class<?>[] parameterTypes = method.getParameterTypes();
			Class<?> returnType = method.getReturnType();

			if (annotationPresent && (parameterTypes.length == 1) &&
				returnType.equals(void.class)) {

				injectionPointMethods.add(method);
			}
		}

		return injectionPointMethods;
	}

	protected List<InjectionPoint> getInjectionPoints(Object target) {
		Class<?> targetClass = target.getClass();

		ClassLoader classLoader = targetClass.getClassLoader();

		List<Class<?>> interfaceClasses = new ArrayList<Class<?>>();

		List<Method> injectionPointMethods = getInjectionPointMethods(target);

		for (Method injectionPointMethod : injectionPointMethods) {
			Class<?> parameterType =
				injectionPointMethod.getParameterTypes()[0];

			if (parameterType.isInterface()) {
				interfaceClasses.add(parameterType);
			}
		}

		_unavailableServiceProxy = Proxy.newProxyInstance(
			classLoader, interfaceClasses.toArray(new Class[0]),
			_invocationHandler);

		List<InjectionPoint> injectionPoints = new ArrayList<InjectionPoint>();

		for (Method injectionPointMethod : injectionPointMethods) {
			InjectionPoint injectionPoint = createInjectionPoint(
				target, injectionPointMethod);

			if (injectionPoint != null) {
				injectionPoints.add(injectionPoint);
			}
		}

		return injectionPoints;
	}

	protected ServiceTracker<?, ?> track(
		BundleContext bundleContext, final Object target,
		final InjectionPoint injectionPoint) {

		try {
			injectionPoint.reset();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to unset " +
					injectionPoint.getName() + " on "+ target,
				e);
		}

		ServiceTracker<?, ?> serviceTracker =
			new ServiceTracker<Object, Object>(
				bundleContext, (Class<Object>)injectionPoint.getParameterType(),
				null) {

				@Override
				public Object addingService(
					ServiceReference<Object> serviceReference) {

					Object service = super.addingService(serviceReference);

					ServiceReference<Object> currentServiceReference =
						getServiceReference();

					if ((currentServiceReference == null) ||
						(serviceReference.compareTo(
							currentServiceReference) > 0)) {

						try {
							injectionPoint.inject(service);
						}
						catch (Exception e) {
							throw new RuntimeException(
								"Unable to set service reference using " +
									injectionPoint.getName() + " on "+
										target,
								e);
						}
					}

					return service;
				}

				@Override
				public void modifiedService(
					ServiceReference<Object> reference, Object service) {

					super.modifiedService(reference, service);

					ServiceReference<Object> currentServiceReference =
						getServiceReference();

					Object currentService = getService(currentServiceReference);

					try {
						injectionPoint.inject(currentService);
					}
					catch (Exception e) {
						throw new RuntimeException(
							"Unable to set injection point " +
								injectionPoint.getName() + " on " +
									target,
							e);
					}
				}

				@Override
				public void removedService(
					ServiceReference<Object> serviceReference, Object service) {

					try {
						super.removedService(serviceReference, service);

						ServiceReference<Object> currentServiceReference =
							getServiceReference();

						if (currentServiceReference == null) {
							injectionPoint.reset();
						}
						else {
							Object currentService = getService(
								currentServiceReference);

							injectionPoint.inject(currentService);
						}
					}
					catch (IllegalStateException ise) {
					}
					catch (Exception e) {
						throw new RuntimeException(
							"Unable to set injection point " +
								injectionPoint.getName() + " on " +
									target,
							e);
					}
				}

			};

		serviceTracker.open();

		return serviceTracker;
	}

	private static final InvocationHandler _invocationHandler =
		new InvocationHandler() {

			@Override
			public Object invoke(
					Object object, Method method, Object[] parameters)
				throws Throwable {

				throw new UnavailableServiceException(
					method.getDeclaringClass());
			}

		};

	private final List<ServiceTracker<?, ?>> _serviceTrackers =
		new ArrayList<ServiceTracker<?, ?>>();
	private Object _unavailableServiceProxy;

	private static class InjectionPoint {

		public String getName() {
			return _method.getName();
		}

		public Class<?> getParameterType() {
			return _method.getParameterTypes()[0];
		}

		public void inject(Object value)
			throws IllegalAccessException, InvocationTargetException {

			_method.invoke(_target, value);
		}

		public void reset()
			throws IllegalAccessException, InvocationTargetException {

			_method.invoke(_target, new Object[]{null});
		}

		private InjectionPoint(Object target, Method method) {
			_target = target;
			_method = method;
		}

		private final Method _method;
		private final Object _target;

	}

	private static class UnavailableProxyInjectionPoint extends InjectionPoint {

		public UnavailableProxyInjectionPoint(
			Object target, Method method, Object proxy) {

			super(target, method);

			_proxy = proxy;
		}

		@Override
		public void reset()
			throws IllegalAccessException, InvocationTargetException {

			super.inject(_proxy);
		}

		private final Object _proxy;

	}

}