/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.adapter.builder;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilderLocator;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.Closeable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapModelAdapterBuilderLocator
	implements Closeable, ModelAdapterBuilderLocator {

	@Override
	public void close() {
		_serviceTrackerMap.close();
	}

	@Override
	public <T, V> ModelAdapterBuilder<T, V> locate(
		Class<T> adapteeModelClass, Class<V> adaptedModelClass) {

		return _serviceTrackerMap.getService(
			_getKey(adapteeModelClass, adaptedModelClass));
	}

	private Type _getGenericInterface(Class<?> clazz, Class<?> interfaceClass) {
		Type[] genericInterfaces = clazz.getGenericInterfaces();

		for (Type genericInterface : genericInterfaces) {
			if (!(genericInterface instanceof ParameterizedType)) {
				continue;
			}

			ParameterizedType parameterizedType =
				(ParameterizedType)genericInterface;

			Type rawType = parameterizedType.getRawType();

			if (rawType.equals(interfaceClass)) {
				return parameterizedType;
			}
		}

		return null;
	}

	private Type _getGenericInterface(Object object, Class<?> interfaceClass) {
		Class<?> clazz = object.getClass();

		Type genericInterface = _getGenericInterface(clazz, interfaceClass);

		if (genericInterface != null) {
			return genericInterface;
		}

		Class<?> superClass = clazz.getSuperclass();

		while (superClass != null) {
			genericInterface = _getGenericInterface(superClass, interfaceClass);

			if (genericInterface != null) {
				return genericInterface;
			}

			superClass = superClass.getSuperclass();
		}

		return null;
	}

	private <T, V> String _getKey(
		Class<T> adapteeModelClass, Class<V> adaptedModelClass) {

		return adapteeModelClass.getName() + "->" + adaptedModelClass.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceTrackerMapModelAdapterBuilderLocator.class);

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	@SuppressWarnings({"unchecked", "rawtypes"})
	private final ServiceTrackerMap<String, ModelAdapterBuilder>
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, ModelAdapterBuilder.class, null,
			new ServiceReferenceMapper<String, ModelAdapterBuilder>() {

				@Override
				public void map(
					ServiceReference<ModelAdapterBuilder> serviceReference,
					Emitter<String> emitter) {

					ModelAdapterBuilder modelAdapterBuilder =
						_bundleContext.getService(serviceReference);

					Type genericInterface = _getGenericInterface(
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
						Class<?> adapteeModelClass = (Class)typeArguments[0];
						Class<?> adaptedModelClass = (Class)typeArguments[1];

						emitter.emit(
							_getKey(adapteeModelClass, adaptedModelClass));
					}
					catch (ClassCastException classCastException) {
						if (_log.isDebugEnabled()) {
							_log.debug(classCastException);
						}
					}
				}

			});

}