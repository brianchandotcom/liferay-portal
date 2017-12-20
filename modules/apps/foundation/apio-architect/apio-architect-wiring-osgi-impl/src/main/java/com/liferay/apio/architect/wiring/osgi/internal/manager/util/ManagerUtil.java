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
import com.liferay.apio.architect.wiring.osgi.internal.manager.resource.ResourceClass;
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
 * Util class for managers.
 *
 * @author Alejandro Hernández
 * @review
 */
public class ManagerUtil {

	/**
	 * Creates a {@code ServiceTracker} that will register its managed services
	 * as the classes provided in the {@code classes} parameter.
	 *
	 * @param  bundleContext the bundle context
	 * @param  clazz the managed class
	 * @param  classes the list of classes with which the service will be
	 *         registered
	 * @param  biConsumer function that can be used to alter the properties
	 *         dictionary
	 * @return the service tracker
	 * @review
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
	 * Returns the generic class stored inside the properties of a {@code
	 * ServiceReference}, if it's present and a {@code Class}. Returns the
	 * result of the provided {@code Supplier} otherwise.
	 *
	 * @param  serviceReference the service reference
	 * @param  resourceClass the resource class to retrieve
	 * @param  supplier the supplier to call if the property is not found or is
	 *         not a {@code Class}
	 * @return the generic class stored inside the properties of a {@code
	 *         ServiceReference}, if it's present and a {@code Class}; the
	 *         result of the provided {@code Supplier} otherwise.
	 * @review
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericClassFromPropertyOrElse(
		ServiceReference serviceReference, ResourceClass resourceClass,
		Supplier<Class<T>> supplier) {

		Try<Object> propertyTry = Try.success(
			serviceReference.getProperty(resourceClass.getName()));

		return propertyTry.filter(
			Objects::nonNull
		).map(
			property -> (Class<T>)property
		).orElseGet(
			supplier
		);
	}

	/**
	 * Returns a dictionary containing the properties of a {@code
	 * ServiceReference}.
	 *
	 * @param  serviceReference the service reference
	 * @return a dictionary containing the properties of a {@code
	 *         ServiceReference}
	 * @review
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
	 * Return a type param from a generic interface of the class of an object at
	 * a certain position or fail.
	 *
	 * @param  t the object
	 * @param  interfaceClass the interface class to look for
	 * @param  position the type param position
	 * @return the type param
	 * @review
	 */
	public static <T, U> Class<U> getTypeParamOrFail(
		T t, Class<T> interfaceClass, Integer position) {

		Class<?> clazz = t.getClass();

		Try<Class<U>> classTry = GenericUtil.getGenericTypeArgumentTry(
			clazz, interfaceClass, position);

		return classTry.orElseThrow(() -> new MustHaveValidGenericType(clazz));
	}

}