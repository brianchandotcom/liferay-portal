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

package com.liferay.layout.internal.security.permission.resource;

import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = LayoutContentModelResourcePermission.class)
public class LayoutContentModelResourcePermissionImpl
	implements LayoutContentModelResourcePermission {

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long plid, String actionId) {

		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(plid);

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			if (contains(
					permissionChecker, layoutClassedModelUsage.getClassName(),
					layoutClassedModelUsage.getClassPK(), actionId)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, String className, long classPK,
		String actionId) {

		ModelResourcePermission<?> modelResourcePermission =
			_modelResourcePermissionServiceTrackerMap.getService(className);

		if (modelResourcePermission == null) {
			return false;
		}

		try {
			if (modelResourcePermission.contains(
					permissionChecker, classPK, actionId)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(
				"An error occurred while checking permissions",
				portalException);
		}

		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_modelResourcePermissionServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<ModelResourcePermission<?>>)
					(Class<?>)ModelResourcePermission.class,
				"model.class.name");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutContentModelResourcePermissionImpl.class);

	private static ServiceTrackerMap<String, ModelResourcePermission<?>>
		_modelResourcePermissionServiceTrackerMap;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

}