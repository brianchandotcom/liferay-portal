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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

		return _batchEngineTaskItemDelegateExecutorCreatorServiceTrackerMap.
			getService(_encodeKey(itemClassName, taskItemDelegateName));
	}

	@Override
	public Class<?> getItemClass(String itemClassName) {
		Class<?> itemClass = _itemClassServiceTrackerMap.getService(
			itemClassName);

		if (itemClass == null) {
			throw new IllegalStateException("Unknown class: " + itemClassName);
		}

		return itemClass;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_itemClassServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<BatchEngineTaskItemDelegate<Object>>)
					(Class<?>)BatchEngineTaskItemDelegate.class,
				null,
				(serviceReference, emitter) -> {
					Class<?> itemClass = _getItemClass(
						bundleContext, serviceReference);

					emitter.emit(itemClass.getName());
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

		_batchEngineTaskItemDelegateExecutorCreatorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<BatchEngineTaskItemDelegate<Object>>)
					(Class<?>)BatchEngineTaskItemDelegate.class,
				null,
				(serviceReference, emitter) -> {
					Class<?> itemClass = _getItemClass(
						bundleContext, serviceReference);

					emitter.emit(
						_encodeKey(
							itemClass.getName(),
							(String)serviceReference.getProperty(
								"batch.engine.task.item.delegate.name")));
				},
				new ServiceTrackerCustomizer
					<BatchEngineTaskItemDelegate<Object>,
					 BatchEngineTaskItemDelegateExecutorCreator>() {

					@Override
					public BatchEngineTaskItemDelegateExecutorCreator
						addingService(
							ServiceReference
								<BatchEngineTaskItemDelegate<Object>>
									serviceReference) {

						ServiceObjects<BatchEngineTaskItemDelegate<Object>>
							serviceObjects = bundleContext.getServiceObjects(
								serviceReference);

						return (company, expressionConvert,
								filterParserProvider, parameters,
								sortParserProvider, user) ->
									new BatchEngineTaskItemDelegateExecutor(
										company, expressionConvert,
										filterParserProvider, parameters,
										serviceObjects, sortParserProvider,
										user);
					}

					@Override
					public void modifiedService(
						ServiceReference<BatchEngineTaskItemDelegate<Object>>
							serviceReference,
						BatchEngineTaskItemDelegateExecutorCreator
							batchEngineTaskItemDelegateExecutorCreator) {
					}

					@Override
					public void removedService(
						ServiceReference<BatchEngineTaskItemDelegate<Object>>
							serviceReference,
						BatchEngineTaskItemDelegateExecutorCreator
							batchEngineTaskItemDelegateExecutorCreator) {

						bundleContext.ungetService(serviceReference);
					}

				});
	}

	@Deactivate
	protected void deactivate() {
		_batchEngineTaskItemDelegateExecutorCreatorServiceTrackerMap.close();
	}

	private String _encodeKey(
		String itemClassName, String taskItemDelegateName) {

		if (Validator.isNull(taskItemDelegateName)) {
			taskItemDelegateName = "DEFAULT";
		}

		return StringBundler.concat(
			itemClassName, StringPool.POUND, taskItemDelegateName);
	}

	private Class<?> _getItemClass(
		BatchEngineTaskItemDelegate<Object> batchEngineTaskItemDelegate) {

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

	private Class<?> _getItemClass(
		BundleContext bundleContext,
		ServiceReference<BatchEngineTaskItemDelegate<Object>>
			serviceReference) {

		BatchEngineTaskItemDelegate<Object> batchEngineTaskItemDelegate =
			bundleContext.getService(serviceReference);

		return _getItemClass(batchEngineTaskItemDelegate);
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

	private ServiceTrackerMap
		<String, BatchEngineTaskItemDelegateExecutorCreator>
			_batchEngineTaskItemDelegateExecutorCreatorServiceTrackerMap;
	private ServiceTrackerMap<String, Class<?>> _itemClassServiceTrackerMap;

}