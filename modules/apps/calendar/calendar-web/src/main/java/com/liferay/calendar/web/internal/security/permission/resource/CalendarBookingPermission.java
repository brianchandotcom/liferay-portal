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