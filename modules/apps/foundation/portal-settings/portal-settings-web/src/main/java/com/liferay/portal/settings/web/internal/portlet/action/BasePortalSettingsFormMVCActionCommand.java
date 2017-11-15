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

package com.liferay.portal.settings.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseFormMVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Michael C. Han
 */
public abstract class BasePortalSettingsFormMVCActionCommand
	extends BaseFormMVCActionCommand {

	public BasePortalSettingsFormMVCActionCommand(
		PortalSettingsFormContributor portalSettingsFormContributor) {

		this.portalSettingsFormContributor = portalSettingsFormContributor;
	}

	protected String getSettingsId() {
		return portalSettingsFormContributor.getSettingsId();
	}

	protected boolean hasPermissions(
		ActionRequest actionRequest, ActionResponse actionResponse,
		ThemeDisplay themeDisplay) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			SessionErrors.add(actionRequest, PrincipalException.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return false;
		}

		return true;
	}

	protected final PortalSettingsFormContributor portalSettingsFormContributor;

}