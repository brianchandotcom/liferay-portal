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

package com.liferay.portal.adaptors;

import com.liferay.portal.kernel.adaptors.AdapterBuilder;
import com.liferay.portal.kernel.adaptors.AdapterBuilderLocator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.java8.util.Optional;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapAdapterBuilderLocator
	implements AdapterBuilderLocator, Closeable {

	public ServiceTrackerMapAdapterBuilderLocator() {
		_adaptorsMap.open();
	}

	@Override
	public <R, T> Optional<AdapterBuilder<R, T>> locate(Class<R> from, Class<T> to) {
		return Optional.<AdapterBuilder<R,T>>ofNullable(
			_adaptorsMap.getService(getKey(from, to)));
	}

	private <R, T> String getKey(Class<R> from, Class<T> to) {
		return from.getName() + "->" + to.getName();
	}

	private ServiceTrackerMap<String, AdapterBuilder> _adaptorsMap =
		ServiceTrackerCollections.singleValueMap(
			AdapterBuilder.class, null,
			new ServiceReferenceMapper<String, AdapterBuilder>() {
				
				@Override
				public void map(
					ServiceReference<AdapterBuilder> serviceReference,
					Emitter<String> emitter) {

					Registry registry = RegistryUtil.getRegistry();

					AdapterBuilder adapterBuilder = registry.getService(serviceReference);

					Type[] genericInterfaces =
						adapterBuilder.getClass().getGenericInterfaces();

					ParameterizedType type =
						(ParameterizedType) genericInterfaces[0];

					Type[] typeArguments = type.getActualTypeArguments();

					Class from = (Class) typeArguments[0];
					Class to = (Class) typeArguments[1];

					emitter.emit(getKey(from, to));
				}
			});

	@Override
	public void close() throws IOException {
		_adaptorsMap.close();
	}
}
