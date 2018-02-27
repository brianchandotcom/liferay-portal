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

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/user_associated_data/delete_user"
	},
	service = MVCActionCommand.class
)
public class DeleteUserMVCActionCommand extends TransactionalMVCActionCommand {

	@Override
	@Transactional(
		isolation = Isolation.PORTAL, propagation = Propagation.REQUIRES_NEW,
		rollbackFor = {Exception.class}
	)
	protected void performProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long selUserId = ParamUtil.getLong(actionRequest, "selUserId");

		User selUser = _userLocalService.getUserById(selUserId);

		_userLocalService.deleteUser(selUser);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	@Reference
	private UserLocalService _userLocalService;

}