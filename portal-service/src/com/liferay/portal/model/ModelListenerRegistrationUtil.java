package com.liferay.portal.model;

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Peter Fellwock
 */
public class ModelListenerRegistrationUtil {

	public static void register(String key, ModelListener<?> modelListener) {
		_instance._register(key, modelListener);
	}

	public static void register(ModelListener<?> modelListener) {
		_instance._register(modelListener.getClass().getName(), modelListener);
	}

	public static void unregister(String key) {
		_instance._unregister(key);
	}

	public static void unregister(ModelListener<?> modelListener) {
		_instance._unregister(modelListener.getClass().getName());
	}

	public static <T> ModelListener<T>[] getModelListeners(
		BasePersistence< ? extends BaseModel<T>> persistenceImpl) {

		Class<?> superClass = ReflectionUtil.getGenericSuperType(
			persistenceImpl.getClass());
		Class<?> key = ReflectionUtil.getGenericSuperType(
			superClass.getClass());

		if (key == null) {
			return 
				(ModelListener<T>[]) _instance._getModelListeners(superClass);
		}

		return (ModelListener<T>[]) _instance._getModelListeners(key);
	}

	private <T> ModelListener<T>[] _getModelListeners(Class<T> key) {

		List<ModelListener<?>> list = _modelListenerMap.get(key);

		if (list == null) {
			list = new ArrayList<ModelListener<?>>();
			List<ModelListener<?>> previousList = 
				_modelListenerMap.putIfAbsent(key, list);

			if (previousList != null) {
				list = previousList;
			}
		}

		ModelListener<T>[] array = (ModelListener<T>[])list.toArray(
			new ModelListener[list.size()]);

		return array;
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private ModelListenerRegistrationUtil() {

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" + ModelListener.class.getName() + ")");

		_serviceTracker = registry.trackServices(
			filter, new ModelListenerTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _register(String key, ModelListener<?> modelListener) {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(key, modelListener);
	}

	private void _unregister(String key) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceReference<ModelListener<?>> serviceReference = 
			registry.getServiceReference(key);

		if (serviceReference != null) {
			registry.ungetService(serviceReference);
		}
	}

	private static ModelListenerRegistrationUtil _instance = 
		new ModelListenerRegistrationUtil();

	private ServiceTracker<ModelListener<?>, ModelListener<?>> _serviceTracker;

	private ConcurrentMap<Class<?>, List<ModelListener<?>>> _modelListenerMap =
		new ConcurrentHashMap<Class<?>, List<ModelListener<?>>>();

	private class ModelListenerTrackerCustomizer
	implements ServiceTrackerCustomizer<ModelListener<?>, ModelListener<?>> {

		@Override
		public ModelListener<?> addingService(
			ServiceReference<ModelListener<?>> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			ModelListener<?> service = registry.getService(serviceReference);

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

			list.add(service);

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference<ModelListener<?>> serviceReference, 
			ModelListener<?> service) {
		}

		@Override
		public void removedService(
			ServiceReference<ModelListener<?>> serviceReference, 
			ModelListener<?> service) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String key = service.getClass().getName();

			_modelListenerMap.remove(key);
		}
	}
}
