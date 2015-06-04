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

package com.liferay.portal.kernel.registry;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.Function;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceMapper;
import com.liferay.registry.collections.ServiceReferenceMapper;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceReferenceMapperFactory {

	public static <K, S> ServiceReferenceMapper<K, S> create(
		final Accessor<S, K> accessor) {

		return new ServiceReferenceMapper<K, S>() {

			@Override
			public void map(
				ServiceReference<S> serviceReference, Emitter<K> emitter) {

				com.liferay.registry.collections.ServiceReferenceMapperFactory.
					create(
						new ServiceMapper<K, S>() {

							@Override
							public void map(S service, Emitter<K> emitter) {
								emitter.emit(accessor.get(service));
							}

						});
			}

		};
	}

	public static <K, S> ServiceReferenceMapper<K, S> create(
		final Function<S, K> function) {

		return new ServiceReferenceMapper<K, S>() {

			@Override
			public void map(
				ServiceReference<S> serviceReference, Emitter<K> emitter) {

				com.liferay.registry.collections.ServiceReferenceMapperFactory.
					create(
						new ServiceMapper<K, S>() {

							@Override
							public void map(S service, Emitter<K> emitter) {
								emitter.emit(function.apply(service));
							}

						});
			}

		};
	}

}