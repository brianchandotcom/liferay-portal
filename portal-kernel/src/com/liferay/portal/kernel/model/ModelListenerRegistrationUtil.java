/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Peter Fellwock
 */
public class ModelListenerRegistrationUtil {

	public static <T> ModelListener<T>[] getModelListeners(Class<T> clazz) {
		List<ModelListener<?>> modelListeners = _modelListeners.getService(
			clazz.getName());

		if (modelListeners == null) {
			return new ModelListener[0];
		}

		return modelListeners.toArray(new ModelListener[0]);
	}

	public static void register(ModelListener<?> modelListener) {
		Class<?> clazz = modelListener.getClass();

		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				ModelListener.class, modelListener, null);

		_serviceRegistrations.put(clazz.getName(), serviceRegistration);
	}

	public static void unregister(ModelListener<?> modelListener) {
		Class<?> clazz = modelListener.getClass();

		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(clazz.getName());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static Class<?> _getGenericSuperType(Class<?> clazz) {
		try {
			ParameterizedType parameterizedType =
				(ParameterizedType)clazz.getGenericSuperclass();

			Type[] types = parameterizedType.getActualTypeArguments();

			if (types.length > 0) {
				return (Class<?>)types[0];
			}
		}
		catch (Throwable throwable) {
		}

		return null;
	}

	private static Class<?> _getModelClass(ModelListener<?> modelListener) {
		Class<?> clazz = modelListener.getModelClass();

		if (clazz != null) {
			return clazz;
		}

		clazz = modelListener.getClass();

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

		return _getGenericSuperType(clazz);
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTrackerMap<String, List<ModelListener<?>>>
		_modelListeners;
	private static final Map<String, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();

	static {
		_modelListeners = ServiceTrackerMapFactory.openMultiValueMap(
			_bundleContext,
			(Class<ModelListener<?>>)(Class<?>)ModelListener.class, null,
			(serviceReference, emitter) -> {
				ModelListener<?> modelListener = _bundleContext.getService(
					serviceReference);

				Class<?> modelClass = _getModelClass(modelListener);

				if (modelClass != null) {
					emitter.emit(modelClass.getName());
				}
			},
			(serviceReference1, serviceReference2) -> {
				ModelListener<?> modelListener1 = _bundleContext.getService(
					serviceReference1);

				Class<?> clazz1 = modelListener1.getClass();

				String name1 = clazz1.getName();

				ModelListener<?> modelListener2 = _bundleContext.getService(
					serviceReference2);

				Class<?> clazz2 = modelListener2.getClass();

				String name2 = clazz2.getName();

				return name1.compareTo(name2);
			});
	}

}