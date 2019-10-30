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

package com.liferay.calendar.internal.security.permission.resource;

import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.constants.CalendarConstants;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class CalendarModelResourcePermissionRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", Calendar.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				Calendar.class, Calendar::getCalendarId,
				_calendarLocalService::getCalendar, _portletResourcePermission,
				(modelResourcePermission, consumer) -> consumer.accept(
					new StagingModelResourcePermissionLogic(
						_stagingPermission))),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private CalendarLocalService _calendarLocalService;

	@Reference(
		target = "(resource.name=" + CalendarConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	@Reference
	private StagingPermission _stagingPermission;

	private static class StagingModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<Calendar> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				Calendar calendar, String actionId)
			throws PortalException {

			if (CalendarActionKeys.VIEW_BOOKING_DETAILS.equals(actionId)) {
				return null;
			}

			return _stagingPermission.hasPermission(
				permissionChecker, calendar.getGroupId(),
				Calendar.class.getName(), calendar.getCalendarId(),
				CalendarPortletKeys.CALENDAR, actionId);
		}

		private StagingModelResourcePermissionLogic(
			StagingPermission stagingPermission) {

			_stagingPermission = stagingPermission;
		}

		private final StagingPermission _stagingPermission;

	}

}