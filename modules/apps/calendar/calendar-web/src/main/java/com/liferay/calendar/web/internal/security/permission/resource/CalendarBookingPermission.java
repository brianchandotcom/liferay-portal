/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.security.permission.resource;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Jonathan McCann
 */
public class CalendarBookingPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			CalendarBooking calendarBooking, String actionId)
		throws PortalException {

		ModelResourcePermission<CalendarBooking> modelResourcePermission =
			_calendarBookingeModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, calendarBooking, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long calendarBookingId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CalendarBooking> modelResourcePermission =
			_calendarBookingeModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, calendarBookingId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CalendarBooking>>
		_calendarBookingeModelResourcePermissionSnapshot = new Snapshot<>(
			CalendarBookingPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.calendar.model.CalendarBooking)");

}