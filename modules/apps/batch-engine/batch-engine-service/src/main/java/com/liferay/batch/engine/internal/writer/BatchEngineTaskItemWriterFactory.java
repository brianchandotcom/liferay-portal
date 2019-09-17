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

package com.liferay.batch.engine.internal.writer;

import com.liferay.batch.engine.BatchEngineTaskField;
import com.liferay.batch.engine.BatchEngineTaskMethod;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.internal.util.ResourceMethodServiceReferenceTuple;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.PathParam;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Ivica cardic
 */
public class BatchEngineTaskItemWriterFactory {

	public BatchEngineTaskItemWriterFactory(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				"(&(api.version=*)(osgi.jaxrs.resource=true))"),
			new BatchEngineTaskMethodServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	public <T> BatchEngineTaskItemWriter<T> create(
			BatchEngineTask batchEngineTask,
			CompanyLocalService companyLocalService,
			UserLocalService userLocalService)
		throws Exception {

		BatchEngineTaskOperation batchEngineTaskOperation =
			BatchEngineTaskOperation.valueOf(batchEngineTask.getOperation());

		ResourceMethodServiceReferenceTuple tuple =
			_resourceServiceReferenceMap.get(
				StringBundler.concat(
					batchEngineTaskOperation, StringPool.POUND,
					batchEngineTask.getClassName(), StringPool.POUND,
					batchEngineTask.getVersion()));

		if (tuple == null) {
			StringBundler sb = new StringBundler(4);

			sb.append("No resource available for batchEngineTaskOperation ");
			sb.append(batchEngineTask.getOperation());
			sb.append(" and className ");
			sb.append(batchEngineTask.getClassName());

			throw new IllegalStateException(sb.toString());
		}

		return new BatchEngineTaskItemWriter<>(
			companyLocalService.getCompany(batchEngineTask.getCompanyId()),
			tuple.getMethod(), tuple.getItemClassFieldNames(),
			tuple.getServiceObjects(),
			userLocalService.getUser(batchEngineTask.getUserId()));
	}

	public void destroy() {
		_serviceTracker.close();
	}

	private final Map<String, ResourceMethodServiceReferenceTuple>
		_resourceServiceReferenceMap = new ConcurrentHashMap<>();
	private final ServiceTracker<Object, List<String>> _serviceTracker;

	private class BatchEngineTaskMethodServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, List<String>> {

		@Override
		public List<String> addingService(
			ServiceReference<Object> serviceReference) {

			Object resource = _bundleContext.getService(serviceReference);

			Class<?> resourceClass = resource.getClass();

			List<String> keys = null;

			for (Method resourceMethod : resourceClass.getMethods()) {
				BatchEngineTaskMethod batchEngineTaskMethod =
					resourceMethod.getAnnotation(BatchEngineTaskMethod.class);

				if (batchEngineTaskMethod == null) {
					continue;
				}

				Class<?> itemClass = batchEngineTaskMethod.itemClass();

				String key = StringBundler.concat(
					batchEngineTaskMethod.batchEngineTaskOperation(),
					StringPool.POUND, itemClass.getName(), StringPool.POUND,
					serviceReference.getProperty("api.version"));

				try {
					_resourceServiceReferenceMap.put(
						key,
						new ResourceMethodServiceReferenceTuple(
							resourceMethod,
							_getItemClassFieldNames(
								resourceClass, resourceMethod),
							_bundleContext.getServiceObjects(
								serviceReference)));
				}
				catch (NoSuchMethodException nsme) {
					throw new IllegalStateException(nsme);
				}

				if (keys == null) {
					keys = new ArrayList<>();
				}

				keys.add(key);
			}

			return keys;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, List<String> keys) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, List<String> keys) {

			keys.forEach(_resourceServiceReferenceMap::remove);

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