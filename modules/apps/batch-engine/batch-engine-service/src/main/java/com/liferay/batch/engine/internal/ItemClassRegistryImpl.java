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
import com.liferay.batch.engine.internal.writer.BatchEngineTaskItemDelegateProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Ivica Cardic
 */
@Component(service = ItemClassRegistry.class)
public class ItemClassRegistryImpl implements ItemClassRegistry {

	@Override
	public Class<?> getItemClass(
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

	@Override
	public Class<?> getItemClass(String itemClassName) {
		Class<?> itemClass = _serviceTrackerMap.getService(itemClassName);

		if (itemClass == null) {
			throw new IllegalStateException("Unknown class: " + itemClassName);
		}

		return itemClass;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, null,
			"(|(batch.engine.task.item.delegate=true)(objectClass=" +
				BatchEngineTaskItemDelegate.class.getName() + "))",
			(serviceReference, emitter) -> {
				try {
					Class<?> itemClass = _getItemClass(
						bundleContext, serviceReference);

					emitter.emit(itemClass.getName());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			},
			new ServiceTrackerCustomizer
				<BatchEngineTaskItemDelegate<Object>, Class<?>>() {

				@Override
				public Class<?> addingService(
					ServiceReference<BatchEngineTaskItemDelegate<Object>>
						serviceReference) {

					return _getItemClass(bundleContext, serviceReference);
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

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private Class<?> _getItemClass(
		BundleContext bundleContext, ServiceReference<?> serviceReference) {

		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
			_batchEngineTaskItemDelegateProvider.toBatchEngineTaskItemDelegate(
				bundleContext.getService(serviceReference));

		return getItemClass(batchEngineTaskItemDelegate);
	}

	@SuppressWarnings("unchecked")
	private Class<?> _getItemClass(ParameterizedType parameterizedType) {
		Type[] genericTypes = parameterizedType.getActualTypeArguments();

		return (Class<BatchEngineTaskItemDelegate<Object>>)genericTypes[0];
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

	@Reference
	private BatchEngineTaskItemDelegateProvider
		_batchEngineTaskItemDelegateProvider;

	private ServiceTrackerMap<String, Class<?>> _serviceTrackerMap;

}