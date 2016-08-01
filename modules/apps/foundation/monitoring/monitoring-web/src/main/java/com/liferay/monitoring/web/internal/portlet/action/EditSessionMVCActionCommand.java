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

package com.liferay.monitoring.web.internal.portlet.action;

import com.liferay.monitoring.web.internal.constants.MonitoringPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + MonitoringPortletKeys.MONITORING,
		"mvc.command.name=/monitoring/edit_session"
	},
	service = MVCActionCommand.class
)
public class EditSessionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin()) {
			SessionErrors.add(
				actionRequest,
				PrincipalException.MustBeCompanyAdmin.class.getName());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		invalidateSession(actionRequest);

		sendRedirect(actionRequest, actionResponse);
	}

	protected void invalidateSession(ActionRequest actionRequest)
		throws Exception {

		String sessionId = ParamUtil.getString(actionRequest, "sessionId");

		HttpSession userSession = PortalSessionContext.get(sessionId);

		if (userSession != null) {
			try {
				if (!actionRequest.getPortletSession().getId().equals(
						sessionId)) {

					userSession.invalidate();
				}
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditSessionMVCActionCommand.class);

}