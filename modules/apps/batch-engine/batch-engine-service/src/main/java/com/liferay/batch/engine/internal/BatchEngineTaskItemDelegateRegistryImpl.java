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
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
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
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Igor Beslic
 */
@Component(service = BatchEngineTaskItemDelegateRegistry.class)
public class BatchEngineTaskItemDelegateRegistryImpl
	implements BatchEngineTaskItemDelegateRegistry {

	public BatchEngineTaskItemDelegate<?> getBatchEngineTaskItemDelegate(
		String itemClassName, String taskItemDelegateName) {

		if (Validator.isNull(taskItemDelegateName)) {
			taskItemDelegateName = "DEFAULT";
		}

		Map<String, BatchEngineTaskItemDelegate<?>>
			batchEngineTaskItemDelegateMap = _batchEngineTaskItemDelegates.get(
				itemClassName);

		return batchEngineTaskItemDelegateMap.get(taskItemDelegateName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, BatchEngineTaskItemDelegate.class.getName(),
			new BatchEngineTaskItemDelegateServiceTrackerCustomizer(
				bundleContext));

		_serviceTracker.open();
	}

	private String _getBatchEngineTaskItemDelegateName(
		ServiceReference<BatchEngineTaskItemDelegate<Object>>
			serviceReference) {

		return GetterUtil.getString(
			serviceReference.getProperty(
				"batch.engine.task.item.delegate.name"),
			"DEFAULT");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineTaskItemDelegateRegistryImpl.class);

	private final Map<String, Map<String, BatchEngineTaskItemDelegate<?>>>
		_batchEngineTaskItemDelegates = new ConcurrentHashMap<>();
	private ServiceTracker<BatchEngineTaskItemDelegate<Object>, Class<?>>
		_serviceTracker;

	private class BatchEngineTaskItemDelegateServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<BatchEngineTaskItemDelegate<Object>, Class<?>> {

		public BatchEngineTaskItemDelegateServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public Class<?> addingService(
			ServiceReference<BatchEngineTaskItemDelegate<Object>>
				serviceReference) {

			BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
				_bundleContext.getService(serviceReference);

			Class<?> itemClass = _getItemClass(batchEngineTaskItemDelegate);

			Map<String, BatchEngineTaskItemDelegate<?>>
				batchEngineTaskItemDelegateMap =
					_batchEngineTaskItemDelegates.computeIfAbsent(
						itemClass.getName(), key -> new ConcurrentHashMap<>());

			String batchEngineTaskItemDelegateName =
				_getBatchEngineTaskItemDelegateName(serviceReference);

			if (batchEngineTaskItemDelegateMap.containsKey(
					batchEngineTaskItemDelegateName)) {

				throw new IllegalStateException(
					batchEngineTaskItemDelegateName + " is already registered");
			}

			batchEngineTaskItemDelegateMap.put(
				batchEngineTaskItemDelegateName, batchEngineTaskItemDelegate);

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

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Batch engine task item delegate modified for item class " +
						itemClass);
			}
		}

		@Override
		public void removedService(
			ServiceReference<BatchEngineTaskItemDelegate<Object>>
				serviceReference,
			Class<?> itemClass) {

			Map<String, BatchEngineTaskItemDelegate<?>>
				batchEngineTaskItemDelegateMap =
					_batchEngineTaskItemDelegates.get(itemClass.getName());

			batchEngineTaskItemDelegateMap.remove(
				_getBatchEngineTaskItemDelegateName(serviceReference));

			if (batchEngineTaskItemDelegateMap.isEmpty()) {
				_batchEngineTaskItemDelegates.remove(itemClass.getName());
			}

			_bundleContext.ungetService(serviceReference);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Batch engine task item delegate unregistered for item " +
						"class " + itemClass);
			}
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