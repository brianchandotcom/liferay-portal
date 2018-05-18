/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.web.internal.portlet.action;

import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.user.constants.CommerceUserPortletKeys;
import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceUserPortletKeys.COMMERCE_USER,
		"mvc.command.name=assignUserRoles"
	},
	service = MVCActionCommand.class
)
public class AssignUserRolesMVCActionCommand extends BaseMVCActionCommand {

	protected void assignUserRoles(ActionRequest actionRequest)
		throws PortalException {

		long userId = ParamUtil.getLong(actionRequest, "userId");

		long[] roleIds = ParamUtil.getLongValues(actionRequest, "roleIds");

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				httpServletRequest);

		long groupId = 0;

		if (organization != null) {
			groupId = organization.getGroupId();
		}

		_commerceUserService.updateUserRoles(userId, groupId, roleIds);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ASSIGN)) {
				assignUserRoles(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceUserService _commerceUserService;

	@Reference
	private Portal _portal;

}