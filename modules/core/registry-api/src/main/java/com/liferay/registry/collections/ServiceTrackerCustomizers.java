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

import java.util.Map;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerCustomizers {

	public static <S> ServiceTrackerCustomizer<S, ServiceWrapper<S>>
		serviceWrapper() {

		return new ServiceTrackerCustomizer<S, ServiceWrapper<S>>() {

			@Override
			public ServiceWrapper<S> addingService(
				final ServiceReference<S> serviceReference) {

				Registry registry = RegistryUtil.getRegistry();

				final S service = registry.getService(serviceReference);

				if (service == null) {
					return null;
				}

				try {
					final Map<String, Object> properties =
						serviceReference.getProperties();

					return new ServiceWrapper<S>() {

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
					registry.ungetService(serviceReference);

					throw t;
				}
			}

			@Override
			public void modifiedService(
				ServiceReference<S> serviceReference,
				ServiceWrapper<S> serviceWrapper) {
			}

			@Override
			public void removedService(
				ServiceReference<S> serviceReference,
				ServiceWrapper<S> serviceWrapper) {

				Registry registry = RegistryUtil.getRegistry();

				registry.ungetService(serviceReference);
			}

		};
	}

	public interface ServiceWrapper<S> {

		public Map<String, Object> getProperties();

		public S getService();

	}

}