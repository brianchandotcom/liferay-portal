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

package com.liferay.portal.model;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Peter Fellwock
 */
public class ModelListenerRegistrationUtil {

	public static <T> ModelListener<T>[] getModelListeners(Class<T> clazz) {
		return _instance._getModelListeners(clazz);
	}

	public static void register(ModelListener<?> modelListener) {
		_instance._register(modelListener.getClass().getName(), modelListener);
	}

	public static void unregister(ModelListener<?> modelListener) {
		_instance._unregister(modelListener.getClass().getName());
	}

	private ModelListenerRegistrationUtil() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" + ModelListener.class.getName() + ")");

		_serviceTracker = registry.trackServices(
			filter, new ModelListenerTrackerCustomizer());

		_serviceTracker.open();
	}

	private <T> ModelListener<T>[] _getModelListeners(Class<T> clazz) {
		List<ModelListener<?>> modelListeners = _modelListeners.get(clazz);

		if (modelListeners == null) {
			modelListeners = new ArrayList<ModelListener<?>>();

			List<ModelListener<?>> previousList = _modelListeners.putIfAbsent(
				clazz, modelListeners);

			if (previousList != null) {
				modelListeners = previousList;
			}
		}

		return modelListeners.toArray(new ModelListener[modelListeners.size()]);
	}

	private <T> void _register(
		String className, ModelListener<T> modelListener) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<?> serviceRegistration =
			registry.registerService(
				ModelListener.class.getName(), modelListener);

		_serviceRegistrations.put(className, serviceRegistration);
	}

	private void _unregister(String className) {
		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(className);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final ModelListenerRegistrationUtil _instance =
		new ModelListenerRegistrationUtil();

	private final ConcurrentMap<Class<?>, List<ModelListener<?>>>
		_modelListeners =
			new ConcurrentHashMap<Class<?>, List<ModelListener<?>>>();
	private final Map<String, ServiceRegistration<?>> _serviceRegistrations =
		new ConcurrentHashMap<String, ServiceRegistration<?>>();
	private final ServiceTracker<ModelListener<?>, ModelListener<?>>
		_serviceTracker;

	private class ModelListenerTrackerCustomizer
		implements
			ServiceTrackerCustomizer<ModelListener<?>, ModelListener<?>> {

		@Override
		public ModelListener<?> addingService(
			ServiceReference<ModelListener<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ModelListener<?> modelListener = registry.getService(
				serviceReference);

			Class<?> clazz = _getModelListeners(modelListener);

			if (clazz == null) {
				return null;
			}

			List<ModelListener<?>> modelListeners = _modelListeners.get(clazz);

			if (modelListeners == null) {
				modelListeners = new ArrayList<ModelListener<?>>();

				List<ModelListener<?>> previousModelListeners =
					_modelListeners.putIfAbsent(clazz, modelListeners);

				if (previousModelListeners != null) {
					modelListeners = previousModelListeners;
				}
			}

			modelListeners.add(modelListener);

			return modelListener;
		}

		@Override
		public void modifiedService(
			ServiceReference<ModelListener<?>> serviceReference,
			ModelListener<?> modelListener) {
		}

		@Override
		public void removedService(
			ServiceReference<ModelListener<?>> serviceReference,
			ModelListener<?> modelListener) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			Class<?> clazz = _getModelListeners(modelListener);

			List<ModelListener<?>> modelListeners = _modelListeners.get(clazz);

			if (modelListeners != null) {
				modelListeners.remove(modelListener);
			}
		}

		private Class<?> _getModelListeners(ModelListener<?> modelListener) {
			Class<?> clazz = modelListener.getClass();

			if (ProxyUtil.isProxyClass(clazz)) {
				InvocationHandler invocationHandler =
					ProxyUtil.getInvocationHandler(modelListener);

				if (invocationHandler instanceof ClassLoaderBeanHandler) {
					ClassLoaderBeanHandler classLoaderBeanHandler =
						(ClassLoaderBeanHandler)invocationHandler;

					Object bean = classLoaderBeanHandler.getBean();

					clazz = bean.getClass();
				}
			}

			return ReflectionUtil.getGenericSuperType(clazz);
		}

	}

}