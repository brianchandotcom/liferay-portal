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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.RequiredRoleException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ORGANIZATIONS,
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/update_user_roles"
	},
	service = MVCActionCommand.class
)
public class UpdateUserRolesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			User user = _portal.getSelectedUser(actionRequest);

			Contact contact = user.getContact();

			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

			birthdayCal.setTime(user.getBirthday());

			long[] roleIds = _usersAdmin.getRoleIds(actionRequest);

			List<UserGroupRole> userGroupRoles = null;

			String addGroupRolesGroupIds = actionRequest.getParameter(
				"addGroupRolesGroupIds");
			String addGroupRolesRoleIds = actionRequest.getParameter(
				"addGroupRolesRoleIds");
			String deleteGroupRolesGroupIds = actionRequest.getParameter(
				"deleteGroupRolesGroupIds");
			String deleteGroupRolesRoleIds = actionRequest.getParameter(
				"deleteGroupRolesRoleIds");

			if ((addGroupRolesGroupIds != null) ||
				(addGroupRolesRoleIds != null) ||
				(deleteGroupRolesGroupIds != null) ||
				(deleteGroupRolesRoleIds != null)) {

				userGroupRoles = _usersAdmin.getUserGroupRoles(actionRequest);
			}

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				User.class.getName(), actionRequest);

			Role administratorRole = _roleService.getRole(
				user.getCompanyId(), RoleConstants.ADMINISTRATOR);

			long[] administratorUserIds = _userService.getRoleUserIds(
				administratorRole.getRoleId());

			if ((administratorUserIds.length == 1) &&
				ArrayUtil.contains(administratorUserIds, user.getUserId()) &&
				!ArrayUtil.contains(roleIds, administratorRole.getRoleId())) {

				throw new RequiredRoleException.MustNotRemoveLastAdministator();
			}

			user = _userService.updateUser(
				user.getUserId(), user.getPassword(), null, null,
				user.isPasswordReset(), null, null, user.getScreenName(),
				user.getEmailAddress(), user.getFacebookId(), user.getOpenId(),
				user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
				user.getComments(), user.getFirstName(), user.getMiddleName(),
				user.getLastName(), contact.getPrefixId(),
				contact.getSuffixId(), user.isMale(),
				birthdayCal.get(Calendar.MONTH), birthdayCal.get(Calendar.DATE),
				birthdayCal.get(Calendar.YEAR), contact.getSmsSn(),
				contact.getFacebookSn(), contact.getJabberSn(),
				contact.getSkypeSn(), contact.getTwitterSn(),
				user.getJobTitle(), null, user.getOrganizationIds(), roleIds,
				userGroupRoles, user.getUserGroupIds(), serviceContext);

			User currentUser = _userService.getCurrentUser();

			if (currentUser.getUserId() == user.getUserId()) {
				String redirect = _getRedirect(actionRequest, currentUser);

				if (Validator.isNotNull(redirect)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException ||
				e instanceof
					RequiredRoleException.MustNotRemoveLastAdministator) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof MembershipPolicyException) {
				SessionErrors.add(actionRequest, e.getClass(), e);

				actionResponse.setRenderParameter("mvcPath", "/edit_user.jsp");
			}
			else {
				throw e;
			}
		}
	}

	private String _getRedirect(ActionRequest actionRequest, User currentUser)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		String portletName = portletConfig.getPortletName();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(currentUser);

		if (!PortletPermissionUtil.contains(
				permissionChecker, portletName,
				ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

			return _portal.getHomeURL(
				_portal.getHttpServletRequest(actionRequest));
		}

		if (portletName.equals(UsersAdminPortletKeys.MY_ORGANIZATIONS)) {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			String backURL = null;
			long organizationId = 0;
			String portletNameSpace = _portal.getPortletNamespace(
				UsersAdminPortletKeys.MY_ORGANIZATIONS);
			String redirect = ParamUtil.getString(
				httpServletRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				Map<String, String[]> parameterMap = _http.getParameterMap(
					redirect);

				backURL = parameterMap.get(portletNameSpace + "backURL")[0];
			}

			if (Validator.isNotNull(backURL)) {
				Map<String, String[]> parameterMap = _http.getParameterMap(
					backURL);

				organizationId = GetterUtil.getLong(
					parameterMap.get(portletNameSpace + "organizationId")[0]);
			}

			if ((organizationId > 0) &&
				!OrganizationPermissionUtil.contains(
					permissionChecker, organizationId, ActionKeys.VIEW)) {

				PortletURL portletURL = _portal.getControlPanelPortletURL(
					httpServletRequest, portletName,
					PortletRequest.RENDER_PHASE);

				return portletURL.toString();
			}
		}

		return StringPool.BLANK;
	}

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference
	private RoleService _roleService;

	@Reference
	private UsersAdmin _usersAdmin;

	@Reference
	private UserService _userService;

}