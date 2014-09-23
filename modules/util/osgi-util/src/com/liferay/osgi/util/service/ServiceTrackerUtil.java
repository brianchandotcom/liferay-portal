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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 * @deprecated As of release 1.0.2.
 * This class makes excessive use of getService and ungetService which,
 * depending on the OSGi framework implementation or use case, can translate
 * into excessive object instantiation and destruction, possibly leading to
 * performance issues. Use
 * {@link com.liferay.osgi.util.service.ReflectionServiceTracker} instead.
 */
@Deprecated
public class ServiceTrackerUtil {

	public static <T> T getService(
		final Class<T> clazz, final BundleContext bundleContext) {

		ClassLoader classLoader = clazz.getClassLoader();

		Object serviceProxy = Proxy.newProxyInstance(
			classLoader, new Class[] {clazz},
			new InvocationHandler() {

				@Override
				public Object invoke(
						Object object, Method method, Object[] arguments)
					throws Throwable {

					ServiceReference<T> serviceReference =
						bundleContext.getServiceReference(clazz);

					if (serviceReference == null) {
						throw new UnavailableServiceException(clazz);
					}

					T service = bundleContext.getService(serviceReference);

					if (service == null) {
						throw new UnavailableServiceException(clazz);
					}

					try {
						return method.invoke(service, arguments);
					}
					catch (InvocationTargetException ite) {
						throw ite.getTargetException();
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				}

			});

		return (T)serviceProxy;
	}

}