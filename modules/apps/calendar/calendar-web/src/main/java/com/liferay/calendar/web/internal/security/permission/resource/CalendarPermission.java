/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.security.permission.resource;

import com.liferay.calendar.model.Calendar;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class CalendarPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, Calendar calendar,
			String actionId)
		throws PortalException {

		ModelResourcePermission<Calendar> modelResourcePermission =
			_calendarModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, calendar, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long calendarId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<Calendar> modelResourcePermission =
			_calendarModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, calendarId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<Calendar>>
		_calendarModelResourcePermissionSnapshot = new Snapshot<>(
			CalendarPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.calendar.model.Calendar)");

}