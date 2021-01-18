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

package com.liferay.osgi.util;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.function.Function;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Adolfo Pérez
 */
public class ServiceTrackerFactory {

	public static <T> ServiceTracker<T, T> create(
		Bundle bundle, Class<T> clazz) {

		return new ServiceTracker<>(bundle.getBundleContext(), clazz, null);
	}

	public static <S, T> ServiceTracker<S, T> create(
		BundleContext bundleContext, Class<S> clazz,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return new ServiceTracker<>(
			bundleContext, clazz, serviceTrackerCustomizer);
	}

	public static <S, T> ServiceTracker<S, T> create(
		BundleContext bundleContext, String filterString) {

		try {
			return new ServiceTracker<>(
				bundleContext, bundleContext.createFilter(filterString), null);
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throwException(invalidSyntaxException);

			return null;
		}
	}

	public static <S, T> ServiceTracker<S, T> create(
		BundleContext bundleContext, String filterString,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		try {
			return new ServiceTracker<>(
				bundleContext, bundleContext.createFilter(filterString),
				serviceTrackerCustomizer);
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throwException(invalidSyntaxException);

			return null;
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static <T> ServiceTracker<T, T> create(Class<T> clazz) {
		return create(
			FrameworkUtil.getBundle(ServiceTrackerFactory.class), clazz);
	}

	public static <T> ServiceTracker<T, T> open(Bundle bundle, Class<T> clazz) {
		ServiceTracker<T, T> serviceTracker = create(bundle, clazz);

		serviceTracker.open();

		return serviceTracker;
	}

	public static <S, T> ServiceTracker<S, T> open(
		BundleContext bundleContext, Class<S> clazz,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		ServiceTracker<S, T> serviceTracker = create(
			bundleContext, clazz, serviceTrackerCustomizer);

		serviceTracker.open();

		return serviceTracker;
	}

	public static <S, T> ServiceTracker<S, T> open(
		BundleContext bundleContext, String filterString) {

		ServiceTracker<S, T> serviceTracker = create(
			bundleContext, filterString);

		serviceTracker.open();

		return serviceTracker;
	}

	public static <S, T> ServiceTracker<S, T> open(
		BundleContext bundleContext, String filterString,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		ServiceTracker<S, T> serviceTracker = create(
			bundleContext, filterString, serviceTrackerCustomizer);

		serviceTracker.open();

		return serviceTracker;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static <T> ServiceTracker<T, T> open(Class<T> clazz) {
		ServiceTracker<T, T> serviceTracker = create(clazz);

		serviceTracker.open();

		return serviceTracker;
	}

	public static <T, W> ServiceTracker<T, ServiceRegistration<W>>
		openWrapperServiceRegistrator(
			BundleContext bundleContext, Class<T> trackedClass,
			Class<W> registeredClass, Function<T, W> wrapperFunction,
			String... propertyNames) {

		return open(
			bundleContext, trackedClass,
			new ServiceTrackerCustomizer<T, ServiceRegistration<W>>() {

				@Override
				public ServiceRegistration<W> addingService(
					ServiceReference<T> serviceReference) {

					return bundleContext.registerService(
						registeredClass,
						wrapperFunction.apply(
							bundleContext.getService(serviceReference)),
						_buildProperties(serviceReference));
				}

				@Override
				public void modifiedService(
					ServiceReference<T> serviceReference,
					ServiceRegistration<W> serviceRegistration) {

					serviceRegistration.setProperties(
						_buildProperties(serviceReference));
				}

				@Override
				public void removedService(
					ServiceReference<T> serviceReference,
					ServiceRegistration<W> serviceRegistration) {

					serviceRegistration.unregister();

					bundleContext.ungetService(serviceReference);
				}

				private Dictionary<String, Object> _buildProperties(
					ServiceReference<?> serviceReference) {

					Dictionary<String, Object> properties =
						new HashMapDictionary<>();

					for (String propertyName : propertyNames) {
						properties.put(
							propertyName,
							serviceReference.getProperty(propertyName));
					}

					properties.put(
						"original.service.id",
						serviceReference.getProperty("service.id"));

					return properties;
				}

			});
	}

	public static <T> T throwException(Throwable throwable) {
		return ServiceTrackerFactory.<T, RuntimeException>_throwException(
			throwable);
	}

	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

}