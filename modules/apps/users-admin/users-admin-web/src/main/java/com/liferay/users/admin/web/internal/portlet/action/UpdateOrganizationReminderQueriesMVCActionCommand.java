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

package com.liferay.users.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ORGANIZATIONS,
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/update_organization_reminder_queries"
	},
	service = MVCActionCommand.class
)
public class UpdateOrganizationReminderQueriesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_updateReminderQueries(actionRequest);
		}
		catch (Exception exception) {
			String mvcPath = "/edit_organization.jsp";

			if (exception instanceof NoSuchOrganizationException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				mvcPath = "/error.jsp";
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	private void _updateReminderQueries(PortletRequest portletRequest)
		throws Exception {

		long organizationId = ParamUtil.getLong(
			portletRequest, "organizationId");

		Organization organization = _organizationService.getOrganization(
			organizationId);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_organizationPermission.check(
			themeDisplay.getPermissionChecker(), organization,
			ActionKeys.UPDATE);

		String reminderQueries = portletRequest.getParameter("reminderQueries");

		PortletPreferences portletPreferences = organization.getPreferences();

		LocalizationUtil.setLocalizedPreferencesValues(
			portletRequest, portletPreferences, "reminderQueries");

		portletPreferences.setValue("reminderQueries", reminderQueries);

		portletPreferences.store();
	}

	@Reference
	private OrganizationPermission _organizationPermission;

	@Reference
	private OrganizationService _organizationService;

}