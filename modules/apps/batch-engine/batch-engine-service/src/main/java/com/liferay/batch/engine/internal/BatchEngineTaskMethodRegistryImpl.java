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

import com.liferay.batch.engine.BatchEngineTaskField;
import com.liferay.batch.engine.BatchEngineTaskMethod;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.ItemClassRegistry;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemResourceDelegate;
import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.PathParam;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
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

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				"(&(api.version=*)(osgi.jaxrs.resource=true)" +
					"(!(batch.engine=true)))"),
			new BatchEngineTaskMethodServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Override
	public Class<?> getItemClass(String itemClassName) {
		Map.Entry<Class<?>, AtomicInteger> entry = _itemClasses.get(
			itemClassName);

		if (entry == null) {
			throw new IllegalStateException("Unknown class: " + itemClassName);
		}

		return entry.getKey();
	}

	@Override
	public UnsafeBiFunction
		<Company, User, BatchEngineTaskItemResourceDelegate,
		 ReflectiveOperationException> getUnsafeBiFunction(
			String apiVersion,
			BatchEngineTaskOperation batchEngineTaskOperation,
			String itemClassName) {

		return _unsafeBiFunctions.get(
			new FactoryKey(
				apiVersion, batchEngineTaskOperation, itemClassName));
	}

	private final Map<String, Map.Entry<Class<?>, AtomicInteger>> _itemClasses =
		new ConcurrentHashMap<>();
	private ServiceTracker<Object, List<FactoryKey>> _serviceTracker;
	private final Map
		<FactoryKey,
		 UnsafeBiFunction
			 <Company, User, BatchEngineTaskItemResourceDelegate,
			  ReflectiveOperationException>> _unsafeBiFunctions =
				new ConcurrentHashMap<>();

	private static class FactoryKey {

		@Override
		public boolean equals(Object obj) {
			FactoryKey factoryKey = (FactoryKey)obj;

			if (Objects.equals(factoryKey._apiVersion, _apiVersion) &&
				Objects.equals(
					factoryKey._batchEngineTaskOperation,
					_batchEngineTaskOperation) &&
				Objects.equals(factoryKey._itemClassName, _itemClassName)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _apiVersion);

			hashCode = HashUtil.hash(hashCode, _batchEngineTaskOperation);
			hashCode = HashUtil.hash(hashCode, _itemClassName);

			return hashCode;
		}

		private FactoryKey(
			String apiVersion,
			BatchEngineTaskOperation batchEngineTaskOperation,
			String itemClassName) {

			_apiVersion = apiVersion;
			_batchEngineTaskOperation = batchEngineTaskOperation;
			_itemClassName = itemClassName;
		}

		private final String _apiVersion;
		private final BatchEngineTaskOperation _batchEngineTaskOperation;
		private final String _itemClassName;

	}

	private class BatchEngineTaskMethodServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, List<FactoryKey>> {

		@Override
		public List<FactoryKey> addingService(
			ServiceReference<Object> serviceReference) {

			Object resource = _bundleContext.getService(serviceReference);

			Class<?> resourceClass = resource.getClass();

			List<FactoryKey> factoryKeys = null;

			for (Method resourceMethod : resourceClass.getMethods()) {
				BatchEngineTaskMethod batchEngineTaskMethod =
					resourceMethod.getAnnotation(BatchEngineTaskMethod.class);

				if (batchEngineTaskMethod == null) {
					continue;
				}

				Class<?> itemClass = batchEngineTaskMethod.itemClass();

				FactoryKey factoryKey = new FactoryKey(
					String.valueOf(serviceReference.getProperty("api.version")),
					batchEngineTaskMethod.batchEngineTaskOperation(),
					itemClass.getName());

				try {
					String[] itemClassFieldNames = _getItemClassFieldNames(
						resourceClass, resourceMethod);

					ServiceObjects<Object> serviceObjects =
						_bundleContext.getServiceObjects(serviceReference);

					_unsafeBiFunctions.put(
						factoryKey,
						(company, user) ->
							new BatchEngineTaskItemResourceDelegate(
								company, itemClassFieldNames, resourceMethod,
								serviceObjects, user));
				}
				catch (NoSuchMethodException nsme) {
					throw new IllegalStateException(nsme);
				}

				if (factoryKeys == null) {
					factoryKeys = new ArrayList<>();
				}

				factoryKeys.add(factoryKey);

				_itemClasses.compute(
					factoryKey._itemClassName,
					(itemClassName, entry) -> {
						if (entry == null) {
							return new AbstractMap.SimpleImmutableEntry<>(
								itemClass, new AtomicInteger(1));
						}

						AtomicInteger counter = entry.getValue();

						counter.incrementAndGet();

						return entry;
					});
			}

			return factoryKeys;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference,
			List<FactoryKey> factoryKeys) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference,
			List<FactoryKey> factoryKeys) {

			for (FactoryKey factoryKey : factoryKeys) {
				_unsafeBiFunctions.remove(factoryKey);

				_itemClasses.compute(
					factoryKey._itemClassName,
					(itemClassName, entry) -> {
						if (entry == null) {
							return null;
						}

						AtomicInteger counter = entry.getValue();

						if (counter.decrementAndGet() == 0) {
							return null;
						}

						return entry;
					});
			}

			_bundleContext.ungetService(serviceReference);
		}

		private BatchEngineTaskMethodServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private String[] _getItemClassFieldNames(
				Class<?> resourceClass, Method resourceMethod)
			throws NoSuchMethodException {

			Parameter[] resourceMethodParameters =
				resourceMethod.getParameters();

			String[] itemClassFieldNames =
				new String[resourceMethodParameters.length];

			Class<?> parentResourceClass = resourceClass.getSuperclass();

			Method parentResourceMethod = parentResourceClass.getMethod(
				resourceMethod.getName(), resourceMethod.getParameterTypes());

			Parameter[] parentResourceMethodParameters =
				parentResourceMethod.getParameters();

			for (int i = 0; i < resourceMethodParameters.length; i++) {
				Parameter parameter = resourceMethodParameters[i];

				BatchEngineTaskField batchEngineTaskField =
					parameter.getAnnotation(BatchEngineTaskField.class);

				if (batchEngineTaskField == null) {
					parameter = parentResourceMethodParameters[i];

					PathParam pathParam = parameter.getAnnotation(
						PathParam.class);

					if (pathParam != null) {
						itemClassFieldNames[i] = pathParam.value();
					}
				}
				else {
					itemClassFieldNames[i] = batchEngineTaskField.value();
				}
			}

			return itemClassFieldNames;
		}

		private final BundleContext _bundleContext;

	}

}