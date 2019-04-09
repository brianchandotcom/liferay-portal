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

package com.liferay.connected.app.web.internal.portlet.action;

import com.liferay.connected.app.ConnectedApp;
import com.liferay.connected.app.ConnectedAppManager;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/revoke_connected_app"
	},
	service = MVCActionCommand.class
)
public class RevokeConnectedAppMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		User user = _portal.getSelectedUser(actionRequest);

		String connectedAppKey = ParamUtil.getString(
			actionRequest, "connectedAppKey");

		ConnectedApp connectedApp = _connectedAppManager.getConnectedApp(
			user, connectedAppKey);

		if (connectedApp != null) {
			connectedApp.revoke();
		}
	}

	@Reference
	private ConnectedAppManager _connectedAppManager;

	@Reference
	private Portal _portal;

}