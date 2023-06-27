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

package com.liferay.dynamic.data.mapping.form.field.type.internal.security.permission;

import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionChecker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Roberto Díaz
 */
@Component(service = DDMPermissionCheckerRegistry.class)
public class DDMPermissionCheckerRegistry {

	public DDMPermissionChecker getDDMPermissionChecker(String portletId) {
		DDMPermissionChecker ddmPermissionChecker =
			_serviceTrackerMap.getService(portletId);

		if (ddmPermissionChecker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No DDM permission checker specified for " + portletId);
			}
		}

		return ddmPermissionChecker;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMPermissionChecker.class, "javax.portlet.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMPermissionCheckerRegistry.class);

	private volatile ServiceTrackerMap<String, DDMPermissionChecker>
		_serviceTrackerMap;

}