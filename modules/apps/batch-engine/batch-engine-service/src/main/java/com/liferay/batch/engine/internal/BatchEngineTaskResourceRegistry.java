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
import com.liferay.batch.engine.internal.util.ResourceMethodServiceReferenceTuple;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.PathParam;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 * @author Ivica Cardic
 */
@Component(service = BatchEngineTaskResourceRegistry.class)
public class BatchEngineTaskResourceRegistry {

	@Activate
	public void activate(BundleContext bundleContext) throws Exception {
		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				"(&(api.version=*)(osgi.jaxrs.resource=true))"),
			new ItemClassServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	public void deactivate(BundleContext bundleContext) {
		_serviceTracker.close();
	}

	public ResourceMethodServiceReferenceTuple
		getResourceMethodServiceReferenceTuple(
			BatchEngineTaskOperation batchEngineTaskOperation,
			String itemClassName, String version) {

		return _resourceServiceReferenceMap.get(
			StringBundler.concat(
				batchEngineTaskOperation, StringPool.POUND, itemClassName,
				StringPool.POUND, version));
	}

	private final Map<String, ResourceMethodServiceReferenceTuple>
		_resourceServiceReferenceMap = new ConcurrentHashMap<>();
	private ServiceTracker<Object, Object> _serviceTracker;

	private class ItemClassServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			Object resource = _bundleContext.getService(serviceReference);

			Class<?> resourceClass = resource.getClass();

			Class<?> parentResourceClass = resourceClass.getSuperclass();

			for (Method resourceMethod : resourceClass.getMethods()) {
				BatchEngineTaskMethod batchEngineTaskMethod =
					resourceMethod.getAnnotation(BatchEngineTaskMethod.class);

				if (batchEngineTaskMethod == null) {
					continue;
				}

				Class<?> itemClass = batchEngineTaskMethod.itemClass();

				try {
					_resourceServiceReferenceMap.put(
						StringBundler.concat(
							batchEngineTaskMethod.batchEngineTaskOperation(),
							StringPool.POUND, itemClass.getName(),
							StringPool.POUND,
							serviceReference.getProperty("api.version")),
						new ResourceMethodServiceReferenceTuple(
							resourceMethod,
							_getMethodParameterNames(
								parentResourceClass.getMethod(
									resourceMethod.getName(),
									resourceMethod.getParameterTypes()),
								resourceMethod),
							serviceReference));
				}
				catch (NoSuchMethodException nsme) {
					throw new IllegalStateException(nsme);
				}
			}

			return resource;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object resource) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object resource) {

			for (Map.Entry<String, ResourceMethodServiceReferenceTuple> entry :
					_resourceServiceReferenceMap.entrySet()) {

				ResourceMethodServiceReferenceTuple tuple = entry.getValue();

				if (!Objects.equals(
						serviceReference, tuple.getServiceReference())) {

					continue;
				}

				_resourceServiceReferenceMap.remove(entry.getKey());
			}
		}

		private ItemClassServiceTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		private String[] _getMethodParameterNames(
			Method parentResourceMethod, Method resourceMethod) {

			Parameter[] parentResourceMethodParameters =
				parentResourceMethod.getParameters();

			Parameter[] resourceMethodParameters =
				resourceMethod.getParameters();

			String[] parameterNames =
				new String[resourceMethodParameters.length];

			for (int i = 0; i < resourceMethodParameters.length; i++) {
				Parameter parameter = resourceMethodParameters[i];

				BatchEngineTaskField batchEngineTaskField =
					parameter.getAnnotation(BatchEngineTaskField.class);

				if (batchEngineTaskField == null) {
					parameter = parentResourceMethodParameters[i];

					PathParam pathParam = parameter.getAnnotation(
						PathParam.class);

					if (pathParam != null) {
						parameterNames[i] = pathParam.value();
					}
				}
				else {
					parameterNames[i] = batchEngineTaskField.value();
				}
			}

			return parameterNames;
		}

		private final BundleContext _bundleContext;

	}

}