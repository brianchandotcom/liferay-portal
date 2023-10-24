/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.adapter.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * @author Máté Thurzó
 */
public class ModelAdapterUtil {

	public static <T, V> List<V> adapt(
		List<T> adapteeModels, Class<T> adapteeModelClass,
		Class<V> adaptedModelClass) {

		List<V> adaptedModels = new ArrayList<>();

		for (T adapteeModel : adapteeModels) {
			adaptedModels.add(
				adapt(adapteeModel, adapteeModelClass, adaptedModelClass));
		}

		return adaptedModels;
	}

	public static <T, V> List<V> adapt(
		List<T> adapteeModels, Class<V> adaptedModelClass) {

		List<V> adaptedModels = new ArrayList<>();

		for (T adapteeModel : adapteeModels) {
			adaptedModels.add(adapt(adapteeModel, adaptedModelClass));
		}

		return adaptedModels;
	}

	public static <T, V> V adapt(
		T adapteeModel, Class<T> adapteeModelClass,
		Class<V> adaptedModelClass) {

		return _adapt(adapteeModel, adapteeModelClass, adaptedModelClass);
	}

	public static <T, V> V adapt(T adapteeModel, Class<V> adaptedModelClass) {
		Class<T> adapteeModelClass = (Class<T>)adapteeModel.getClass();

		return _adapt(adapteeModel, adapteeModelClass, adaptedModelClass);
	}

	private static <T, V> V _adapt(
		T adapteeModel, Class<T> adapteeModelClass,
		Class<V> adaptedModelClass) {

		ModelAdapterBuilder<T, V> modelAdapterBuilder =
			(ModelAdapterBuilder<T, V>)_serviceTrackerMap.getService(
				_getKey(adapteeModelClass, adaptedModelClass));

		return modelAdapterBuilder.build(adapteeModel);
	}

	private static Type _getGenericInterface(
		Class<?> clazz, Class<?> interfaceClass) {

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

	private static Type _getGenericInterface(
		Object object, Class<?> interfaceClass) {

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

	private static <T, V> String _getKey(
		Class<T> adapteeModelClass, Class<V> adaptedModelClass) {

		return adapteeModelClass.getName() + "->" + adaptedModelClass.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModelAdapterUtil.class);

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static final ServiceTrackerMap<String, ModelAdapterBuilder>
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, ModelAdapterBuilder.class, null,
			(serviceReference, emitter) -> {
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

					emitter.emit(_getKey(adapteeModelClass, adaptedModelClass));
				}
				catch (ClassCastException classCastException) {
					if (_log.isDebugEnabled()) {
						_log.debug(classCastException);
					}
				}
			});

}