/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.security.permission.resource;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class CalendarResourcePermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			CalendarResource calendarResource, String actionId)
		throws PortalException {

		ModelResourcePermission<CalendarResource> modelResourcePermission =
			_calendarResourceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, calendarResource, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long calendarResourceId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CalendarResource> modelResourcePermission =
			_calendarResourceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, calendarResourceId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CalendarResource>>
		_calendarResourceModelResourcePermissionSnapshot = new Snapshot<>(
			CalendarResourcePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.calendar.model.CalendarResource)");

}