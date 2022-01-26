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

package com.liferay.object.internal.field.business.type;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeServicesTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true, service = ObjectFieldBusinessTypeServicesTracker.class
)
public class ObjectFieldBusinessTypeServicesTrackerImpl
	implements ObjectFieldBusinessTypeServicesTracker {

	@Override
	public ObjectFieldBusinessType getObjectFieldBusinessType(String name) {
		return _objectFieldBusinessTypeServiceTrackerMap.getService(name);
	}

	@Override
	public List<HashMap<String, String>> getObjectFieldBusinessTypes(
		Locale locale) {

		List<HashMap<String, String>> objectFieldBusinessTypes =
			new ArrayList<>();

		for (ObjectFieldBusinessType objectFieldBusinessType :
				_objectFieldBusinessTypeServiceTrackerMap.values()) {

			if (!objectFieldBusinessType.isVisible()) {
				continue;
			}

			objectFieldBusinessTypes.add(
				HashMapBuilder.put(
					"businessType", objectFieldBusinessType.getName()
				).put(
					"dbType", objectFieldBusinessType.getDBType()
				).put(
					"description",
					objectFieldBusinessType.getDescription(locale)
				).put(
					"label", objectFieldBusinessType.getLabel(locale)
				).build());
		}

		return objectFieldBusinessTypes;
	}

	@Override
	public Set<String> getObjectFieldDBTypes() {
		Set<String> objectFieldDBTypes = new HashSet<>();

		for (ObjectFieldBusinessType objectFieldBusinessType :
				_objectFieldBusinessTypeServiceTrackerMap.values()) {

			objectFieldDBTypes.add(objectFieldBusinessType.getDBType());
		}

		return objectFieldDBTypes;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_objectFieldBusinessTypeServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ObjectFieldBusinessType.class,
				"object.field.business.type.name");
	}

	@Deactivate
	protected void deactivate() {
		_objectFieldBusinessTypeServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectFieldBusinessType>
		_objectFieldBusinessTypeServiceTrackerMap;

}