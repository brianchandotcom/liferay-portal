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

package com.liferay.portal.kernel.security.permission;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gergely Mathe
 */
public class PermissionUpdateHandlerRegistryUtil {

	public static PermissionUpdateHandler getPermissionUpdateHandler(
		String modelClassName) {

		return _permissionUpdateHandlers.getService(modelClassName);
	}

	public static List<PermissionUpdateHandler> getPermissionUpdateHandlers() {
		return new ArrayList<>(_permissionUpdateHandlers.values());
	}

	private PermissionUpdateHandlerRegistryUtil() {
	}

	private static final ServiceTrackerMap<String, PermissionUpdateHandler>
		_permissionUpdateHandlers = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(), PermissionUpdateHandler.class,
			null,
			(serviceReference, emitter) -> {
				List<String> modelClassNames = StringUtil.asList(
					serviceReference.getProperty("model.class.name"));

				for (String modelClassName : modelClassNames) {
					emitter.emit(modelClassName);
				}
			});

}