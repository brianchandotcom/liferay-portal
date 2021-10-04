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

package com.liferay.object.internal.security.permission.resource;

import com.liferay.object.security.permission.resource.PortletResourcePermissionRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = PortletResourcePermissionRegistry.class)
public class PortletResourcePermissionRegistryImpl
	implements PortletResourcePermissionRegistry {

	@Override
	public PortletResourcePermission getPortletResourcePermission(
		String resourceName) {

		PortletResourcePermission portletResourcePermission =
			_serviceTrackerMap.getService(resourceName);

		if (portletResourcePermission == null) {
			throw new IllegalArgumentException(
				"No portlet resource permission found with resource name " +
					resourceName);
		}

		return portletResourcePermission;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortletResourcePermission.class, "resource.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, PortletResourcePermission>
		_serviceTrackerMap;

}