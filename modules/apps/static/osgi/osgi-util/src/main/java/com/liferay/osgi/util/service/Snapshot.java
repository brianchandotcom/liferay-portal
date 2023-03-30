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

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
public class Snapshot<T> {

	@SuppressWarnings("unchecked")
	public static <T> Class<T> cast(Class<?> clazz) {
		return (Class<T>)clazz;
	}

	public Snapshot(Class<?> holderClass, Class<T> serviceClass) {
		this(holderClass, serviceClass, null);
	}

	public Snapshot(
		Class<?> holderClass, Class<T> serviceClass, String filterString) {

		this(holderClass, serviceClass, filterString, false);
	}

	public Snapshot(
		Class<?> holderClass, Class<T> serviceClass, String filterString,
		boolean dynamic) {

		Bundle bundle = FrameworkUtil.getBundle(holderClass);

		BundleContext bundleContext = bundle.getBundleContext();

		if (dynamic) {
			DCLSingleton<ServiceTracker<T, T>> serviceTrackerDCLSingleton =
				new DCLSingleton<>();

			Supplier<ServiceTracker<T, T>> serviceTrackerSupplier = () -> {
				ServiceTracker<T, T> serviceTracker;

				if (filterString == null) {
					serviceTracker = new ServiceTracker<>(
						bundleContext, serviceClass, null);
				}
				else {
					try {
						serviceTracker = new ServiceTracker<>(
							bundleContext,
							bundleContext.createFilter(
								StringBundler.concat(
									"(&(objectClass=", serviceClass.getName(),
									")", filterString, ")")),
							null);
					}
					catch (InvalidSyntaxException invalidSyntaxException) {
						return ReflectionUtil.throwException(
							invalidSyntaxException);
					}
				}

				serviceTracker.open();

				return serviceTracker;
			};

			_serivceSupplier = () -> {
				ServiceTracker<T, T> serviceTracker =
					serviceTrackerDCLSingleton.getSingleton(
						serviceTrackerSupplier);

				return serviceTracker.getService();
			};
		}
		else {
			DCLSingleton<T> serviceDCLSingleton = new DCLSingleton<>();

			_serivceSupplier = () -> serviceDCLSingleton.getSingleton(
				() -> {
					ServiceReference<T> serviceReference = _getServiceReference(
						bundleContext, serviceClass, filterString);

					if (serviceReference == null) {
						return null;
					}

					return bundleContext.getService(serviceReference);
				});
		}
	}

	public T get() {
		return _serivceSupplier.get();
	}

	private ServiceReference<T> _getServiceReference(
		BundleContext bundleContext, Class<T> serviceClass,
		String filterString) {

		if (filterString == null) {
			return bundleContext.getServiceReference(serviceClass);
		}

		try {
			Collection<ServiceReference<T>> serviceReferences =
				bundleContext.getServiceReferences(serviceClass, filterString);

			if (serviceReferences.isEmpty()) {
				return null;
			}

			Iterator<ServiceReference<T>> iterator =
				serviceReferences.iterator();

			return iterator.next();
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			return ReflectionUtil.throwException(invalidSyntaxException);
		}
	}

	private final Supplier<T> _serivceSupplier;

}