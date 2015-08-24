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

package com.liferay.registry.collections;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerCustomizers {

	public static <S> ServiceTrackerCustomizer<S, ServiceWithProperties<S>>
		serviceWithProperties() {

		return new ServiceTrackerCustomizer<S, ServiceWithProperties<S>>() {

			@Override
			public ServiceWithProperties<S> addingService(
				final ServiceReference<S> reference) {

				Registry registry = RegistryUtil.getRegistry();

				final S service = registry.getService(reference);

				if (service == null) {
					return null;
				}

				try {
					final Map<String, Object> properties =
						Collections.unmodifiableMap(
							createPropertyMap(reference));

					return new ServiceWithProperties<S>() {
						@Override
						public Map<String, Object> getProperties() {
							return properties;
						}

						@Override
						public S getService() {
							return service;
						}
					};
				}

				catch (Throwable t) {
					registry.ungetService(reference);

					throw t;
				}
			}

			@Override
			public void modifiedService(
				ServiceReference<S> reference,
				ServiceWithProperties<S> service) {
			}

			@Override
			public void removedService(
				ServiceReference<S> reference,
				ServiceWithProperties<S> service) {

				Registry registry = RegistryUtil.getRegistry();

				registry.ungetService(reference);
			}
		};
	}

	public interface ServiceWithProperties<S> {
		public Map<String, Object> getProperties();

		public S getService();

	}

	private static <S> Map<String, Object> createPropertyMap(
		final ServiceReference<S> serviceReference) {

		Map<String, Object> properties = new HashMap<>();

		String[] propertyKeys = serviceReference.getPropertyKeys();

		for (String propertyKey : propertyKeys) {
			properties.put(
				propertyKey, serviceReference.getProperty(propertyKey));
		}

		return properties;
	}

}