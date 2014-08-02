package com.liferay.portal.model;

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Peter Fellwock
 */
public class ModelListenerRegistrationUtil {

	public static void register(ModelListener<?> modelListener) {
		_instance._register(modelListener.getClass().getName(), modelListener);
	}

	public static void unregister(ModelListener<?> modelListener) {
		_instance._unregister(modelListener.getClass().getName());
	}

	public static <T> ModelListener<T>[] getModelListeners(Class<T> clazz) {
			return _instance._getModelListeners(clazz);
	}

	private <T> ModelListener<T>[] _getModelListeners(Class<T> clazz) {
		List<ModelListener<?>> list = _modelListenerMap.get(clazz);

		if (list == null) {
			list = new ArrayList<ModelListener<?>>();

			List<ModelListener<?>> previousList = _modelListenerMap.putIfAbsent(
				clazz, list);

			if (previousList != null) {
				list = previousList;
			}
		}

		return list.toArray(new ModelListener[list.size()]);
	}

	private ModelListenerRegistrationUtil() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" + ModelListener.class.getName() + ")");

		_serviceTracker = registry.trackServices(
			filter, new ModelListenerTrackerCustomizer());

		_serviceTracker.open();
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

	private static ModelListenerRegistrationUtil _instance =
		new ModelListenerRegistrationUtil();

	private ConcurrentMap<Class<?>, List<ModelListener<?>>> _modelListenerMap =
		new ConcurrentHashMap<Class<?>, List<ModelListener<?>>>();
	private Map<String, ServiceRegistration<?>> _serviceRegistrations =
		new ConcurrentHashMap<String, ServiceRegistration<?>>();
	private ServiceTracker<ModelListener<?>, ModelListener<?>> _serviceTracker;

	private class ModelListenerTrackerCustomizer
		implements
			ServiceTrackerCustomizer<ModelListener<?>, ModelListener<?>> {

		@Override
		public ModelListener<?> addingService(
			ServiceReference<ModelListener<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ModelListener<?> modelListener = registry.getService(
				serviceReference);

			Class<?> key = ReflectionUtil.getGenericSuperType(
				service.getClass());

			List<ModelListener<?>> list = _modelListenerMap.get(key);

			if (list == null) {
				list = new ArrayList<ModelListener<?>>();

				List<ModelListener<?>> previousList =
					_modelListenerMap.putIfAbsent(key, list);

				if (previousList != null) {
					list = previousList;
				}
			}

			list.add(modelListener);

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

			String key = service.getClass().getName();

			_modelListenerMap.remove(key);
		}
	}
}