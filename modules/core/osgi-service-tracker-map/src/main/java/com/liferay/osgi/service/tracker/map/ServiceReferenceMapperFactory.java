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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public final class ServiceReferenceMapperFactory {

	public static <K, S> ServiceReferenceMapper<K, S> create(
		final BundleContext bundleContext,
		final ServiceMapper<K, S> serviceMapper) {

		return new ServiceReferenceMapper<K, S>() {

			@Override
			public void map(
				ServiceReference<S> serviceReference, Emitter<K> emitter) {

				S service = bundleContext.getService(serviceReference);

				try {
					serviceMapper.map(service, emitter);
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			}

		};
	}

}