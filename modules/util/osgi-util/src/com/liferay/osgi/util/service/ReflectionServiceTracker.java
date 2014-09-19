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

	public ReflectionServiceTracker(Object targetObject) {
		Class<?> targetObjectClass = targetObject.getClass();

		Bundle bundle = FrameworkUtil.getBundle(targetObjectClass);

		BundleContext bundleContext = bundle.getBundleContext();

		List<InjectionPoint> injectionPoints = getInjectionPoints(targetObject);

		_serviceTrackers = new ArrayList<ServiceTracker>();

		for (InjectionPoint injectionPoint : injectionPoints) {
			ServiceTracker serviceTracker = track(
				bundleContext, targetObject, injectionPoint);

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
				//Nothing to do... trying to keep it clean
			}
		}

		_serviceTrackers.clear();
	}

	protected InjectionPoint createInjectionPoint(
		final Object target, final Method method) {

		Class<?> _injectionPointType = method.getParameterTypes()[0];

		if (_injectionPointType.isInterface()) {
			return new ProxyUnavailableInjectionPoint(
				target, method, _unavailableServiceProxy);
		}
		else {
			return new InjectionPoint(target, method);
		}
	}

	protected List<Method> getInjectionPointMethods(Object target) {
		Method[] declaredMethods = target.getClass().getDeclaredMethods();

		ArrayList<Method> injectionPointMethods = new ArrayList<Method>();

		for (Method declaredMethod : declaredMethods) {
			boolean annotationPresent = declaredMethod.isAnnotationPresent(
				Reference.class);

			Class<?>[] parameterTypes = declaredMethod.getParameterTypes();

			Class<?> returnType = declaredMethod.getReturnType();

			if (annotationPresent && (parameterTypes.length == 1) &&
				returnType.equals(void.class)) {

				injectionPointMethods.add(declaredMethod);
			}
		}

		return injectionPointMethods;
	}

	protected List<InjectionPoint> getInjectionPoints(Object target) {
		Class<?> targetObjectClass = target.getClass();

		ArrayList<Class<?>> interfaces = new ArrayList<Class<?>>();

		List<Method> injectionPointMethods = getInjectionPointMethods(target);

		for (Method injectionPointMethod : injectionPointMethods) {
			Class<?> parameterType =
				injectionPointMethod.getParameterTypes()[0];

			if (parameterType.isInterface()) {
				interfaces.add(parameterType);
			}
		}

		ClassLoader classLoader = targetObjectClass.getClassLoader();

		_unavailableServiceProxy = Proxy.newProxyInstance(
			classLoader, interfaces.toArray(new Class[0]), _INVOCATION_HANDLER
		);

		ArrayList<InjectionPoint> injectionPoints =
			new ArrayList<InjectionPoint>();

		for (Method injectionPointMethod : injectionPointMethods) {
			InjectionPoint injectionPoint = createInjectionPoint(
				target, injectionPointMethod);

			if (injectionPoint != null) {
				injectionPoints.add(injectionPoint);
			}
		}

		return injectionPoints;
	}

	protected ServiceTracker track(
		final BundleContext bundleContext, final Object target,
		final InjectionPoint injectionPoint) {

		try {
			injectionPoint.reset();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Could not unset " +
					injectionPoint.getName() + " on "+ target, e);
		}

		ServiceTracker serviceTracker = new ServiceTracker(
			bundleContext, injectionPoint.getParameterType(), null) {

			@Override
			public Object addingService(ServiceReference reference) {
				Object service = super.addingService(reference);

				ServiceReference currentServiceReference =
					getServiceReference();

				if ((currentServiceReference == null) ||
					(reference.compareTo(currentServiceReference) > 0)) {

					try {
						injectionPoint.inject(service);
					}
					catch (Exception e) {
						throw new RuntimeException(
							"Could not set service reference using " +
								injectionPoint.getName() + " on "+ target, e);
					}
				}

				return service;
			}

			@Override
			public void modifiedService(
				ServiceReference reference, Object service) {

				super.modifiedService(reference, service);

				ServiceReference currentServiceReference =
					getServiceReference();

				Object currentService = getService(currentServiceReference);

				try {
					injectionPoint.inject(currentService);
				}
				catch (Exception e) {
					throw new RuntimeException(
						"Could not set injection point " +
							injectionPoint.getName() + " on " + target, e);
				}
			}

			@Override
			public void removedService(
				ServiceReference serviceReference, Object service) {

				try {
					super.removedService(serviceReference, service);

					ServiceReference currentServiceReference =
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
					//BundleContext is invalidated... nothing to do
				}
				catch (Exception e) {
					throw new RuntimeException(
						"Could not set injection point " +
							injectionPoint.getName() + " on " + target, e);
				}
			}
		};

		serviceTracker.open();

		return serviceTracker;
	}

	protected static class InjectionPoint {

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

		protected InjectionPoint(Object target, Method method) {
			_target = target;
			_method = method;
		}

		private Method _method;
		private Object _target;

	}

	protected static class ProxyUnavailableInjectionPoint
		extends InjectionPoint {

		public ProxyUnavailableInjectionPoint(
			Object target, Method method, Object proxy) {

			super(target, method);

			_proxy = proxy;
		}

		@Override
		public void reset()
			throws IllegalAccessException, InvocationTargetException {

			super.inject(_proxy);
		}

		private Object _proxy;
	}

	private static final InvocationHandler _INVOCATION_HANDLER =
		new InvocationHandler() {

			@Override
			public Object invoke(
				Object object, Method method, Object[] parameters)
			throws Throwable {

				throw new ServiceUnavailableException();
			}
	};

	private final ArrayList<ServiceTracker> _serviceTrackers;
	private Object _unavailableServiceProxy;

}