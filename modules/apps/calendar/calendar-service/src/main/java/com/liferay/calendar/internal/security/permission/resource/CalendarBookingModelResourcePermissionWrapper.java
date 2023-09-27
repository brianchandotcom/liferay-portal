/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.security.permission.resource;

import com.liferay.calendar.constants.CalendarConstants;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan McCann
 */
@Component(
	property = "model.class.name=com.liferay.calendar.model.CalendarBooking",
	service = ModelResourcePermission.class
)
public class CalendarBookingModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<CalendarBooking> {

	@Override
	protected ModelResourcePermission<CalendarBooking>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			CalendarBooking.class, CalendarBooking::getCalendarBookingId,
			_calendarBookingLocalService::getCalendarBooking,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
				consumer.accept(
					new CalendarBookingInheritanceModelResourcePermissionLogic());
				consumer.accept(
					new StagedModelPermissionLogic<>(
						_stagingPermission, CalendarPortletKeys.CALENDAR,
						CalendarBooking::getCalendarBookingId));
			});
	}

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.calendar.model.Calendar)"
	)
	private ModelResourcePermission<Calendar> _calendarModelResourcePermission;

	@Reference(
		target = "(resource.name=" + CalendarConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StagingPermission _stagingPermission;

	private class CalendarBookingInheritanceModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<CalendarBooking> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				CalendarBooking calendarBooking, String actionId)
			throws PortalException {

			if (!actionId.equals(ActionKeys.VIEW)) {
				return null;
			}

			return _calendarModelResourcePermission.contains(
				permissionChecker, calendarBooking.getCalendar(), actionId);
		}

	}

}