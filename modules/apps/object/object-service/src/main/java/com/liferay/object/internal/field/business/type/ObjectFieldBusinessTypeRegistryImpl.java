/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.business.type;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcela Cunha
 */
@Component(service = ObjectFieldBusinessTypeRegistry.class)
public class ObjectFieldBusinessTypeRegistryImpl
	implements ObjectFieldBusinessTypeRegistry {

	@Override
	public ObjectFieldBusinessType getObjectFieldBusinessType(String key) {
		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<ObjectFieldBusinessType> getObjectFieldBusinessTypes() {
		return new ArrayList(_serviceTrackerMap.values());
	}

	@Override
	public Set<String> getObjectFieldDBTypes() {
		Set<String> objectFieldDBTypes = new HashSet<>();

		for (ObjectFieldBusinessType objectFieldBusinessType :
				_serviceTrackerMap.values()) {

			objectFieldDBTypes.add(objectFieldBusinessType.getDBType());
		}

		return objectFieldDBTypes;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectFieldBusinessType.class,
			"object.field.business.type.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectFieldBusinessType>
		_serviceTrackerMap;

}