/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.batch.engine.internal.writer.BatchEngineTaskItemDelegateProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

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
 * @author Igor Beslic
 */
@Component(service = BatchEngineTaskItemDelegateRegistry.class)
public class BatchEngineTaskItemDelegateRegistryImpl
	implements BatchEngineTaskItemDelegateRegistry {

	@Override
	public BatchEngineTaskItemDelegate<?> getBatchEngineTaskItemDelegate(
		String itemClassName, String taskItemDelegateName) {

		return _serviceTrackerMap.getService(
			_encodeKey(itemClassName, taskItemDelegateName));
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

					emitter.emit(
						_encodeKey(
							itemClass.getName(),
							(String)serviceReference.getProperty(
								"batch.engine.task.item.delegate.name")));
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			},
			new ServiceTrackerCustomizer
				<String, BatchEngineTaskItemDelegate<?>>() {

				@Override
				public BatchEngineTaskItemDelegate<?> addingService(
					ServiceReference<String> serviceReference) {

					return _batchEngineTaskItemDelegateProvider.
						toBatchEngineTaskItemDelegate(
							bundleContext.getService(serviceReference));
				}

				@Override
				public void modifiedService(
					ServiceReference<String> serviceReference,
					BatchEngineTaskItemDelegate<?>
						batchEngineTaskItemDelegate) {
				}

				@Override
				public void removedService(
					ServiceReference<String> serviceReference,
					BatchEngineTaskItemDelegate<?>
						batchEngineTaskItemDelegate) {

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
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
		BundleContext bundleContext, ServiceReference<?> serviceReference) {

		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
			_batchEngineTaskItemDelegateProvider.toBatchEngineTaskItemDelegate(
				bundleContext.getService(serviceReference));

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

	@Reference
	private BatchEngineTaskItemDelegateProvider
		_batchEngineTaskItemDelegateProvider;

	private ServiceTrackerMap<String, BatchEngineTaskItemDelegate<?>>
		_serviceTrackerMap;

}