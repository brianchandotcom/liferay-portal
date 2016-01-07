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
import com.liferay.registry.collections.ServiceReferenceMapper.Emitter;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceReferenceMapperFactory {

	public static <K, S> ServiceReferenceMapper<K, S> create(
		final ServiceMapper<K, S> serviceMapper) {

		return new ServiceReferenceMapper<K, S>() {

			@Override
			public void map(
				ServiceReference<S> serviceReference, Emitter<K> emitter) {

				Registry registry = RegistryUtil.getRegistry();

				S service = registry.getService(serviceReference);

				try {
					serviceMapper.map(service, emitter);
				}
				finally {
					registry.ungetService(serviceReference);
				}
			}

		};
	}

	public static <K, S> ServiceReferenceMapper<K, S> create(
		final String property) {

		return new ServiceReferenceMapper<K, S>() {

			@Override
			public void map(
				ServiceReference<S> serviceReference, Emitter<K> emitter) {

				Object propertyValue = serviceReference.getProperty(property);

				if (propertyValue == null) {
					return;
				}

				if (propertyValue instanceof Object[]) {
					for (K k : (K[])propertyValue) {
						emitter.emit(k);
					}
				}
				else {
					emitter.emit((K)propertyValue);
				}
			}

		};
	}

}