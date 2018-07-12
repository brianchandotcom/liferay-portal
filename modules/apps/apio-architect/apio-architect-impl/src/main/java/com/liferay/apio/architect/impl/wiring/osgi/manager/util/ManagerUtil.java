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

package com.liferay.apio.architect.impl.wiring.osgi.manager.util;

import static com.liferay.apio.architect.impl.wiring.osgi.util.GenericUtil.getGenericTypeArgumentTry;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.impl.unsafe.Unsafe;
import com.liferay.apio.architect.impl.wiring.osgi.tracker.customizer.ServiceRegistrationServiceTrackerCustomizer;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;
import java.util.function.BiConsumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides a util class for managers.
 *
 * @author Alejandro Hernández
 */
public class ManagerUtil {

	/**
	 * Creates and returns a {@code ServiceTracker} that registers its managed
	 * services as the classes provided in the {@code classes} parameter.
	 *
	 * @param  bundleContext the bundle context
	 * @param  clazz the managed class
	 * @param  classes the list of classes to register the service under
	 * @param  biConsumer the function that can be used to alter the properties
	 *         dictionary
	 * @return the service tracker
	 */
	public static <T> ServiceTracker<T, ServiceRegistration<?>>
		createServiceTracker(
			BundleContext bundleContext, Class<T> clazz, String[] classes,
			BiConsumer<Dictionary<String, Object>, T> biConsumer) {

		return new ServiceTracker<>(
			bundleContext, clazz,
			(ServiceRegistrationServiceTrackerCustomizer<T>)
				serviceReference -> {
					Dictionary<String, Object> properties = getProperties(
						serviceReference);

					T t = Try.fromFallible(
						() -> bundleContext.getService(serviceReference)
					).orElse(
						null
					);

					if (t == null) {
						return null;
					}

					biConsumer.accept(properties, t);

					return bundleContext.registerService(
						classes, t, properties);
				});
	}

	/**
	 * Returns a {@code Try.Success} containing the generic class if it's
	 * present inside the properties of a {@code ServiceReference}. Returns a
	 * {@code Failure} if something fails
	 *
	 * @param  serviceReference the service reference
	 * @param  typeArgumentProperty the type argument position
	 * @return a {@code Try.Success} containing the generic class if it's
	 *         present inside the properties of a {@code ServiceReference}; a
	 *         {@code Failure} with the error otherwise
	 */
	public static <T> Try<Class<T>> getGenericClassFromProperty(
		ServiceReference serviceReference, String typeArgumentProperty) {

		return Try.success(
			typeArgumentProperty
		).map(
			serviceReference::getProperty
		).filter(
			Objects::nonNull
		).map(
			Unsafe::unsafeCast
		);
	}

	/**
	 * Returns a dictionary containing the properties of a {@code
	 * ServiceReference}.
	 *
	 * @param  serviceReference the service reference
	 * @return the dictionary
	 */
	public static <T> Dictionary<String, Object> getProperties(
		ServiceReference<T> serviceReference) {

		Dictionary<String, Object> properties = new Hashtable<>();

		String[] propertyKeys = serviceReference.getPropertyKeys();

		for (String propertyKey : propertyKeys) {
			properties.put(
				propertyKey, serviceReference.getProperty(propertyKey));
		}

		return properties;
	}

	/**
	 * Returns a {@code Try.Success} containing the type parameter from a
	 * generic interface of the class of an object at a certain position, or a
	 * {@code Try.Failure} if something fails.
	 *
	 * @param  t the object
	 * @param  interfaceClass the class's interface
	 * @param  position the position
	 * @return a {@code Try.Success} containing the type parameter; otherwise a
	 *         {@code Try.Failure} containing the failure
	 */
	public static <T, U> Try<Class<U>> getTypeParamTry(
		T t, Class<T> interfaceClass, int position) {

		return Try.fromFallible(
			() -> getGenericTypeArgumentTry(
				t.getClass(), interfaceClass, position)
		).flatMap(
			classTry -> classTry
		).map(
			Unsafe::unsafeCast
		);
	}

}