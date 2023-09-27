/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					"No dynamic data mapping permission checker found for " +
						"portlet " + portletId);
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