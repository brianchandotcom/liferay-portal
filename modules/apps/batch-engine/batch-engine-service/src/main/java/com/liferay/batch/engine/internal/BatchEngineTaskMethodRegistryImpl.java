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

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.ItemClassRegistry;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutor;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutorCreator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Ivica Cardic
 */
@Component(
	service = {BatchEngineTaskMethodRegistry.class, ItemClassRegistry.class}
)
public class BatchEngineTaskMethodRegistryImpl
	implements BatchEngineTaskMethodRegistry {

	@Override
	public BatchEngineTaskItemDelegateExecutorCreator
		getBatchEngineTaskItemDelegateExecutorCreator(
			String itemClassName, String taskItemDelegateName) {

		if (Validator.isNull(taskItemDelegateName)) {
			taskItemDelegateName = "DEFAULT";
		}

		Map<String, BatchEngineTaskItemDelegateExecutorCreator>
			batchEngineTaskItemDelegateExecutorCreatorMap =
				_batchEngineTaskItemDelegateExecutorCreators.get(
					_itemClasses.get(itemClassName));

		return batchEngineTaskItemDelegateExecutorCreatorMap.get(
			taskItemDelegateName);
	}

	@Override
	public Class<?> getItemClass(String itemClassName) {
		Class<?> itemClass = _itemClasses.get(itemClassName);

		if (itemClass == null) {
			throw new IllegalStateException("Unknown class: " + itemClassName);
		}

		return itemClass;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, BatchEngineTaskItemDelegate.class.getName(),
			new BatchEngineTaskMethodServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineTaskMethodRegistryImpl.class);

	private final Map
		<Class<?>, Map<String, BatchEngineTaskItemDelegateExecutorCreator>>
			_batchEngineTaskItemDelegateExecutorCreators =
				new ConcurrentHashMap<>();
	private final Map<String, Class<?>> _itemClasses =
		new ConcurrentHashMap<>();
	private ServiceTracker<BatchEngineTaskItemDelegate<Object>, Class<?>>
		_serviceTracker;

	private class BatchEngineTaskMethodServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<BatchEngineTaskItemDelegate<Object>, Class<?>> {

		@Override
		public Class<?> addingService(
			ServiceReference<BatchEngineTaskItemDelegate<Object>>
				serviceReference) {

			BatchEngineTaskItemDelegate<Object> batchEngineTaskItemDelegate =
				_bundleContext.getService(serviceReference);

			Class<?> itemClass = _getItemClass(batchEngineTaskItemDelegate);

			Map<String, BatchEngineTaskItemDelegateExecutorCreator>
				batchEngineTaskItemDelegateExecutorCreatorMap =
					_batchEngineTaskItemDelegateExecutorCreators.
						computeIfAbsent(
							itemClass, key -> new ConcurrentHashMap<>());

			String batchEngineTaskItemDelegateName =
				_getBatchEngineTaskItemDelegateName(serviceReference);

			if (batchEngineTaskItemDelegateExecutorCreatorMap.containsKey(
					batchEngineTaskItemDelegateName)) {

				throw new IllegalStateException(
					batchEngineTaskItemDelegateName + " is already registered");
			}

			ServiceObjects<BatchEngineTaskItemDelegate<Object>> serviceObjects =
				_bundleContext.getServiceObjects(serviceReference);

			batchEngineTaskItemDelegateExecutorCreatorMap.put(
				batchEngineTaskItemDelegateName,
				(company, expressionConvert, filterParserProvider, parameters,
				 sortParserProvider, user) ->
					new BatchEngineTaskItemDelegateExecutor(
						company, expressionConvert, filterParserProvider,
						parameters, serviceObjects, sortParserProvider, user));

			_itemClasses.put(itemClass.getName(), itemClass);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Batch engine task item delegate registered for item ",
						"class ", itemClass, " and delegate name ",
						batchEngineTaskItemDelegateName));
			}

			return itemClass;
		}

		@Override
		public void modifiedService(
			ServiceReference<BatchEngineTaskItemDelegate<Object>>
				serviceReference,
			Class<?> itemClass) {
		}

		@Override
		public void removedService(
			ServiceReference<BatchEngineTaskItemDelegate<Object>>
				serviceReference,
			Class<?> itemClass) {

			Map<String, BatchEngineTaskItemDelegateExecutorCreator>
				batchEngineTaskItemDelegateExecutorCreatorMap =
					_batchEngineTaskItemDelegateExecutorCreators.get(itemClass);

			String batchEngineTaskItemDelegateName =
				_getBatchEngineTaskItemDelegateName(serviceReference);

			batchEngineTaskItemDelegateExecutorCreatorMap.remove(
				batchEngineTaskItemDelegateName);

			if (batchEngineTaskItemDelegateExecutorCreatorMap.isEmpty()) {
				_batchEngineTaskItemDelegateExecutorCreators.remove(itemClass);

				_itemClasses.remove(itemClass.getName());
			}

			_bundleContext.ungetService(serviceReference);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Batch engine task item delegate unregistered for item " +
						"class " + itemClass);
			}
		}

		private BatchEngineTaskMethodServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private String _getBatchEngineTaskItemDelegateName(
			ServiceReference<BatchEngineTaskItemDelegate<Object>>
				serviceReference) {

			return GetterUtil.getString(
				serviceReference.getProperty(
					"batch.engine.task.item.delegate.name"),
				"DEFAULT");
		}

		private Class<?> _getItemClass(
			BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate) {

			Class<?> itemClass = batchEngineTaskItemDelegate.getItemClass();

			if (itemClass != null) {
				return itemClass;
			}

			Class<?> batchEngineTaskItemDelegateClass =
				batchEngineTaskItemDelegate.getClass();

			itemClass = _getItemClassFromGenericInterfaces(
				batchEngineTaskItemDelegateClass.getGenericInterfaces());

			if (itemClass == null) {
				itemClass = _getItemClassFromGenericSuperclass(
					batchEngineTaskItemDelegateClass.getGenericSuperclass());
			}

			if (itemClass == null) {
				throw new IllegalStateException(
					BatchEngineTaskItemDelegate.class.getName() +
						" is not implemented");
			}

			return itemClass;
		}

		@SuppressWarnings("unchecked")
		private Class<?> _getItemClass(ParameterizedType parameterizedType) {
			Type[] genericTypes = parameterizedType.getActualTypeArguments();

			return (Class<BatchEngineTaskItemDelegate<?>>)genericTypes[0];
		}

		private Class<?> _getItemClassFromGenericInterfaces(
			Type[] genericInterfaceTypes) {

			for (Type genericInterfaceType : genericInterfaceTypes) {
				if (genericInterfaceType instanceof ParameterizedType) {
					ParameterizedType parameterizedType =
						(ParameterizedType)genericInterfaceType;

					if (parameterizedType.getRawType() !=
							BatchEngineTaskItemDelegate.class) {

						continue;
					}

					return _getItemClass(parameterizedType);
				}
			}

			return null;
		}

		private Class<?> _getItemClassFromGenericSuperclass(
			Type genericSuperclassType) {

			if (genericSuperclassType == null) {
				return null;
			}

			return _getItemClass((ParameterizedType)genericSuperclassType);
		}

		private final BundleContext _bundleContext;

	}

}