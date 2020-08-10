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

package com.liferay.commerce.account.web.internal.portlet.action;

import com.liferay.commerce.account.constants.CommerceAccountPortletKeys;
import com.liferay.commerce.account.exception.NoSuchAccountUserRelException;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAccountPortletKeys.COMMERCE_ACCOUNT,
		"mvc.command.name=inviteUser"
	},
	service = MVCActionCommand.class
)
public class InviteUserMVCActionCommand extends BaseMVCActionCommand {

	protected void assignUser(ActionRequest actionRequest)
		throws PortalException {

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		long[] userIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "userIds"), 0L);
		String[] emailAddresses = ParamUtil.getStringValues(
			actionRequest, "emailAddresses");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAccountUserRel.class.getName(), actionRequest);

		_commerceAccountUserRelService.addCommerceAccountUserRels(
			commerceAccountId, userIds, emailAddresses, null, serviceContext);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.REMOVE)) {
				removeUsers(actionRequest);
			}
			else if (cmd.equals(Constants.ASSIGN)) {
				assignUser(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchAccountUserRelException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof UserEmailAddressException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				String redirect = _portal.getCurrentURL(actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw e;
			}
		}
	}

	protected void removeUsers(ActionRequest actionRequest)
		throws PortalException {

		long[] removeUserIds = null;

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		long userId = ParamUtil.getLong(actionRequest, "userId");

		if (userId > 0) {
			removeUserIds = new long[] {userId};
		}
		else {
			removeUserIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "removeUserIds"), 0L);
		}

		_commerceAccountUserRelService.deleteCommerceAccountUserRels(
			commerceAccountId, removeUserIds);
	}

	@Reference
	private CommerceAccountUserRelService _commerceAccountUserRelService;

	@Reference
	private Portal _portal;

}