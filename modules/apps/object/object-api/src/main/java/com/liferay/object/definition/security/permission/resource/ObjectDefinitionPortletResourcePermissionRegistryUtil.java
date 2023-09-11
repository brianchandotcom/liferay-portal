/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.security.permission.resource;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Joao Victor Alves
 */
public class ObjectDefinitionPortletResourcePermissionRegistryUtil {

	public static PortletResourcePermission getService(String resourceName) {
		return _objectPortletResourcePermissionServiceTracker.getService(
			resourceName);
	}

	private static final ServiceTrackerMap<String, PortletResourcePermission>
		_objectPortletResourcePermissionServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ObjectDefinitionPortletResourcePermissionRegistryUtil.class);

		_objectPortletResourcePermissionServiceTracker =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundle.getBundleContext(), PortletResourcePermission.class,
				"(&(com.liferay.object=true)(resource.name=*))",
				(serviceReference, emitter) -> emitter.emit(
					(String)serviceReference.getProperty("resource.name")));
	}

}