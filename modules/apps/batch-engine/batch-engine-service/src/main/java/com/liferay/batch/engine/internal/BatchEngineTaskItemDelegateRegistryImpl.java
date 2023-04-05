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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(service = BatchEngineTaskItemDelegateRegistry.class)
public class BatchEngineTaskItemDelegateRegistryImpl
	implements BatchEngineTaskItemDelegateRegistry {

	@Override
	public BatchEngineTaskItemDelegate<?> getBatchEngineTaskItemDelegate(
		String itemClassName, String taskItemDelegateName) {

		return _batchEngineTaskItemDelegateServiceTrackerMap.getService(
			_encodeKey(itemClassName, taskItemDelegateName));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_batchEngineTaskItemDelegateServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<BatchEngineTaskItemDelegate<?>>)
					(Class<?>)BatchEngineTaskItemDelegate.class,
				null,
				(serviceReference, emitter) -> {
					BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
						bundleContext.getService(serviceReference);

					Class<?> itemClass = _getItemClass(
						batchEngineTaskItemDelegate);

					emitter.emit(
						_encodeKey(
							itemClass.getName(),
							(String)serviceReference.getProperty(
								"batch.engine.task.item.delegate.name")));
				});
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

	private ServiceTrackerMap<String, BatchEngineTaskItemDelegate<?>>
		_batchEngineTaskItemDelegateServiceTrackerMap;

}