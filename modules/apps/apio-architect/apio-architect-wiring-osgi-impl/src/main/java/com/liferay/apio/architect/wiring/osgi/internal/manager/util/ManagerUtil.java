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

package com.liferay.apio.architect.wiring.osgi.internal.manager.util;

import com.liferay.apio.architect.error.ApioDeveloperError.MustHaveValidGenericType;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.wiring.osgi.internal.service.tracker.customizer.ServiceRegistrationServiceTrackerCustomizer;
import com.liferay.apio.architect.wiring.osgi.util.GenericUtil;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

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

					T t = null;

					try {
						t = bundleContext.getService(serviceReference);
					}
					catch (Exception e) {
						return null;
					}

					biConsumer.accept(properties, t);

					return bundleContext.registerService(
						classes, t, properties);
				});
	}

	/**
	 * Returns a {@code Class}, and the generic class if it's present inside the
	 * properties of a {@code ServiceReference}. Returns the result of the
	 * provided {@code Supplier} otherwise.
	 *
	 * @param  serviceReference the service reference
	 * @param  typeArgumentProperty the type argument position
	 * @param  supplier the supplier to call if the property isn't found or
	 *         isn't a {@code Class}
	 * @return the {@code Class}, and the generic class if it's present inside
	 *         the properties of a {@code ServiceReference}; the result of the
	 *         provided {@code Supplier} otherwise
	 */
	public static <T> Class<T> getGenericClassFromPropertyOrElse(
		ServiceReference serviceReference, String typeArgumentProperty,
		Supplier<Class<T>> supplier) {

		return Try.success(
			typeArgumentProperty
		).map(
			serviceReference::getProperty
		).filter(
			Objects::nonNull
		).<Class<T>>map(
			Unsafe::unsafeCast
		).orElseGet(
			supplier
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
	 * Returns a type parameter from a generic interface of the class of an
	 * object at a certain position, or fails.
	 *
	 * @param  t the object
	 * @param  interfaceClass the class's interface
	 * @param  position the position
	 * @return the type parameter
	 */
	public static <T, U> Class<U> getTypeParamOrFail(
		T t, Class<T> interfaceClass, Integer position) {

		Class<?> clazz = t.getClass();

		Try<Class<U>> classTry = GenericUtil.getGenericTypeArgumentTry(
			clazz, interfaceClass, position);

		return classTry.orElseThrow(() -> new MustHaveValidGenericType(clazz));
	}

}