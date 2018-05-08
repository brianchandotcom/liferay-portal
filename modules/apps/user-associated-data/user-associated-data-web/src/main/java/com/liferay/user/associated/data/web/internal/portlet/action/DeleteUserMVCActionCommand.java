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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/delete_user"
	},
	service = MVCActionCommand.class
)
public class DeleteUserMVCActionCommand extends BaseUADMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_userLocalService.deleteUser(getSelectedUserId(actionRequest));

		PortletRequest portletRequest =
			(PortletRequest)actionRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			portletRequest, UsersAdminPortletKeys.USERS_ADMIN,
			PortletRequest.RENDER_PHASE);

		sendRedirect(
			actionRequest, actionResponse, liferayPortletURL.toString());
	}

	@Reference
	private UserLocalService _userLocalService;

}