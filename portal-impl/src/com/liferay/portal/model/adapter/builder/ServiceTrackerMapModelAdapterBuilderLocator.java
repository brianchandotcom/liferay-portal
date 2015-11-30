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

package com.liferay.portal.model.adapter.builder;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.io.Closeable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapModelAdapterBuilderLocator
	implements ModelAdapterBuilderLocator, Closeable {

	@Override
	public void close() {
		_modelAdapterBuilders.close();
	}

	@Override
	public <T, V> ModelAdapterBuilder<T, V> locate(
		Class<T> adapteeModelClass, Class<V> adaptedModelClass) {

		return _modelAdapterBuilders.getService(
			getKey(adapteeModelClass, adaptedModelClass));
	}

	private <T, V> String getKey(
		Class<T> adapteeModelClass, Class<V> adaptedModelClass) {

		return adapteeModelClass.getName() + "->" + adaptedModelClass.getName();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private final ServiceTrackerMap<String, ModelAdapterBuilder>
		_modelAdapterBuilders = ServiceTrackerCollections.openSingleValueMap(
			ModelAdapterBuilder.class, null,
			new ServiceReferenceMapper<String, ModelAdapterBuilder>() {

				@Override
				public void map(
					ServiceReference<ModelAdapterBuilder> serviceReference,
					Emitter<String> emitter) {

					Registry registry = RegistryUtil.getRegistry();

					ModelAdapterBuilder modelAdapterBuilder =
						registry.getService(serviceReference);

					Type genericInterface = ReflectionUtil.getGenericInterface(
						modelAdapterBuilder, ModelAdapterBuilder.class);

					if ((genericInterface == null) ||
						!(genericInterface instanceof ParameterizedType)) {

						return;
					}

					ParameterizedType parameterizedType =
						(ParameterizedType)genericInterface;

					Type[] typeArguments =
						parameterizedType.getActualTypeArguments();

					if (ArrayUtil.isEmpty(typeArguments) ||
						(typeArguments.length != 2)) {

						return;
					}

					try {
						Class adapteeModelClass = (Class)typeArguments[0];
						Class adaptedModelClass = (Class)typeArguments[1];

						emitter.emit(
							getKey(adapteeModelClass, adaptedModelClass));
					}
					catch (ClassCastException cce) {
						return;
					}
				}

			});

}