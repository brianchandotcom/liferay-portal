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

import com.liferay.calendar.constants.CalendarConstants;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.calendar.model.CalendarResource",
	service = ModelResourcePermission.class
)
public class CalendarResourceModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<CalendarResource> {

	@Override
	protected ModelResourcePermission<CalendarResource>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			CalendarResource.class, CalendarResource::getCalendarResourceId,
			_calendarResourceLocalService::getCalendarResource,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new StagedModelPermissionLogic<>(
					_stagingPermission, CalendarPortletKeys.CALENDAR_ADMIN,
					CalendarResource::getCalendarResourceId)));
	}

	@Reference
	private CalendarResourceLocalService _calendarResourceLocalService;

	@Reference(
		target = "(resource.name=" + CalendarConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StagingPermission _stagingPermission;

}