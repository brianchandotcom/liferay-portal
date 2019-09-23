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
import com.liferay.batch.engine.internal.writer.BatchEngineTaskItemWriter;
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
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Ivica Cardic
 */
public class BatchEngineTaskMethodServiceTracker {

	public BatchEngineTaskMethodServiceTracker(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				"(&(api.version=*)(osgi.jaxrs.resource=true))"),
			new BatchEngineTaskMethodServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	public UnsafeBiFunction
		<Company, User, BatchEngineTaskItemWriter, ReflectiveOperationException>
			getBatchEngineTaskItemWriterFactory(
				BatchEngineTaskOperation batchEngineTaskOperation,
				String itemClassName, String apiVersion) {

		return _batchEngineTaskItemWriterFactories.get(
			new FactoryKey(
				batchEngineTaskOperation, itemClassName, apiVersion));
	}

	public Class<?> getItemClass(String itemClassName) {
		Map.Entry<Class<?>, AtomicInteger> entry = _itemClasses.get(
			itemClassName);

		if (entry == null) {
			throw new IllegalStateException("Unknown class :" + itemClassName);
		}

		return entry.getKey();
	}

	private final Map
		<FactoryKey,
		 UnsafeBiFunction
			 <Company, User, BatchEngineTaskItemWriter,
			  ReflectiveOperationException>>
				_batchEngineTaskItemWriterFactories = new ConcurrentHashMap<>();
	private final Map<String, Map.Entry<Class<?>, AtomicInteger>> _itemClasses =
		new ConcurrentHashMap<>();
	private final ServiceTracker<Object, List<FactoryKey>> _serviceTracker;

	private static class FactoryKey {

		@Override
		public boolean equals(Object obj) {
			FactoryKey factoryKey = (FactoryKey)obj;

			if ((factoryKey._batchEngineTaskOperation ==
					_batchEngineTaskOperation) &&
				Objects.equals(factoryKey._itemClassName, _itemClassName) &&
				Objects.equals(factoryKey._apiVersion, _apiVersion)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _batchEngineTaskOperation);

			hashCode = HashUtil.hash(hashCode, _itemClassName);
			hashCode = HashUtil.hash(hashCode, _apiVersion);

			return hashCode;
		}

		private FactoryKey(
			BatchEngineTaskOperation batchEngineTaskOperation,
			String itemClassName, String apiVersion) {

			_batchEngineTaskOperation = batchEngineTaskOperation;
			_itemClassName = itemClassName;
			_apiVersion = apiVersion;
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
					batchEngineTaskMethod.batchEngineTaskOperation(),
					itemClass.getName(),
					String.valueOf(
						serviceReference.getProperty("api.version")));

				try {
					String[] itemClassFieldNames = _getItemClassFieldNames(
						resourceClass, resourceMethod);

					ServiceObjects<Object> serviceObjects =
						_bundleContext.getServiceObjects(serviceReference);

					_batchEngineTaskItemWriterFactories.put(
						factoryKey,
						(company, user) -> new BatchEngineTaskItemWriter(
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
				_batchEngineTaskItemWriterFactories.remove(factoryKey);

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