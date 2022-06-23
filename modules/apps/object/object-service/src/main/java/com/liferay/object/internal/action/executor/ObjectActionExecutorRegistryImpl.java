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

package com.liferay.object.internal.action.executor;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionExecutorRegistry.class)
public class ObjectActionExecutorRegistryImpl
	implements ObjectActionExecutorRegistry {

	@Override
	public ObjectActionExecutor getObjectActionExecutor(
		String objectActionExecutorKey) {

		ObjectActionExecutor objectActionExecutor =
			_serviceTrackerMap.getService(objectActionExecutorKey);

		if (objectActionExecutor == null) {
			throw new IllegalArgumentException(
				"No object action executor found with key " +
					objectActionExecutorKey);
		}

		return objectActionExecutor;
	}

	@Override
	public List<ObjectActionExecutor> getObjectActionExecutors() {
		Collection<ObjectActionExecutor> objectActionExecutorsCollection =
			_serviceTrackerMap.values();

		if (objectActionExecutorsCollection == null) {
			return Collections.<ObjectActionExecutor>emptyList();
		}

		List<ObjectActionExecutor> objectActionExecutors = new ArrayList<>(
			objectActionExecutorsCollection);

		objectActionExecutors.sort(
			(ObjectActionExecutor objectActionExecutor1,
			 ObjectActionExecutor objectActionExecutor2) -> {

				String key1 = objectActionExecutor1.getKey();
				String key2 = objectActionExecutor2.getKey();

				return key1.compareTo(key2);
			});

		return objectActionExecutors;
	}

	@Override
	public boolean hasObjectActionExecutor(String objectActionExecutorKey) {
		return _serviceTrackerMap.containsKey(objectActionExecutorKey);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectActionExecutor.class, null,
			(serviceReference, emitter) -> {
				ObjectActionExecutor objectActionExecutor =
					bundleContext.getService(serviceReference);

				emitter.emit(objectActionExecutor.getKey());
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectActionExecutor> _serviceTrackerMap;

}