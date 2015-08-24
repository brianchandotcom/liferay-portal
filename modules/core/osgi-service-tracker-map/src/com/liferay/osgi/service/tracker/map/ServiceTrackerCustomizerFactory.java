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

package com.liferay.osgi.service.tracker.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerCustomizerFactory {

	public static <S> ServiceTrackerCustomizer<S, ServiceWithProperties<S>>
		serviceWithProperties(final BundleContext bundleContext) {

		return new ServiceTrackerCustomizer<S, ServiceWithProperties<S>>() {

			@Override
			public ServiceWithProperties<S> addingService(
				final ServiceReference<S> reference) {

				final S service = bundleContext.getService(reference);

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
					bundleContext.ungetService(reference);

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

				bundleContext.ungetService(reference);
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