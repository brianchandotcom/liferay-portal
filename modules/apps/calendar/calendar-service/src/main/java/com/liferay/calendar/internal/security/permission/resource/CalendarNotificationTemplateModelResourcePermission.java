/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.security.permission.resource;

import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.service.CalendarNotificationTemplateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "model.class.name=com.liferay.calendar.model.CalendarNotificationTemplate",
	service = ModelResourcePermission.class
)
public class CalendarNotificationTemplateModelResourcePermission
	implements ModelResourcePermission<CalendarNotificationTemplate> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CalendarNotificationTemplate calendarNotificationTemplate,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, calendarNotificationTemplate, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, CalendarNotificationTemplate.class.getName(),
				calendarNotificationTemplate.getPrimaryKey(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long calendarNotificationTemplateId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, calendarNotificationTemplateId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, CalendarNotificationTemplate.class.getName(),
				calendarNotificationTemplateId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CalendarNotificationTemplate calendarNotificationTemplate,
			String actionId)
		throws PortalException {

		return permissionChecker.hasPermission(
			null, CalendarNotificationTemplate.class.getName(),
			calendarNotificationTemplate.getPrimaryKey(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long calendarNotificationTemplateId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_calendarNotificationTemplateLocalService.
				getCalendarNotificationTemplate(calendarNotificationTemplateId),
			actionId);
	}

	@Override
	public String getModelName() {
		return CalendarNotificationTemplate.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private CalendarNotificationTemplateLocalService
		_calendarNotificationTemplateLocalService;

}