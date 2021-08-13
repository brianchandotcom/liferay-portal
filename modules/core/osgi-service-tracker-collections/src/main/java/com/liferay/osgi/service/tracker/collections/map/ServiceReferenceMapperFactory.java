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

package com.liferay.osgi.service.tracker.collections.map;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public final class ServiceReferenceMapperFactory {

	public static <K, S> ServiceReferenceMapper<K, S> create(
		BundleContext bundleContext, ServiceMapper<K, S> serviceMapper) {

		return (serviceReference, emitter) -> {
			S service = bundleContext.getService(serviceReference);

			try {
				serviceMapper.map(service, emitter);
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		};
	}

	public static <K, S> Function<BundleContext, ServiceReferenceMapper<K, S>>
		createFromBiFunction(BiFunction<ServiceReference<S>, S, K> biFunction) {

		return bundleContext -> (serviceReference, emitter) -> {
			S service = bundleContext.getService(serviceReference);

			try {
				emitter.emit(biFunction.apply(serviceReference, service));
			}
			catch (Exception exception) {
				bundleContext.ungetService(serviceReference);
			}
		};
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #createFromBiFunction(BiFunction)}
	 */
	@Deprecated
	public static <K, S> Function<BundleContext, ServiceReferenceMapper<K, S>>
		createFromFunction(BiFunction<ServiceReference<S>, S, K> biFunction) {

		return createFromBiFunction(biFunction);
	}

	public static <K, S> ServiceReferenceMapper<K, S> createFromFunction(
		BundleContext bundleContext, Function<S, K> function) {

		return (serviceReference, emitter) -> {
			S service = bundleContext.getService(serviceReference);

			try {
				emitter.emit(function.apply(service));
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		};
	}

}