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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/depot/update_roles"
	},
	service = MVCActionCommand.class
)
public class UpdateRolesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		try {
			User user = _portal.getSelectedUser(actionRequest);

			Contact contact = user.getContact();

			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

			birthdayCal.setTime(user.getBirthday());

			long[] organizationIds = UsersAdminUtil.getOrganizationIds(
				actionRequest);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				User.class.getName(), actionRequest);

			serviceContext.setAssetCategoryIds(null);
			serviceContext.setAssetTagNames(null);

			_userService.updateUser(
				user.getUserId(), user.getPassword(), null, null,
				user.isPasswordReset(), null, null, user.getScreenName(),
				user.getEmailAddress(), user.getLanguageId(),
				user.getTimeZoneId(), user.getGreeting(), user.getComments(),
				user.getFirstName(), user.getMiddleName(), user.getLastName(),
				contact.getPrefixId(), contact.getSuffixId(), user.isMale(),
				birthdayCal.get(Calendar.MONTH), birthdayCal.get(Calendar.DATE),
				birthdayCal.get(Calendar.YEAR), contact.getSmsSn(),
				contact.getFacebookSn(), contact.getJabberSn(),
				contact.getSkypeSn(), contact.getTwitterSn(),
				user.getJobTitle(), user.getGroupIds(), organizationIds,
				user.getRoleIds(), _getUserGroupRoles(actionRequest),
				user.getUserGroupIds(), serviceContext);
		}
		catch (PortalException portalException) {
			SessionErrors.add(actionRequest, portalException.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	private UserGroupRole _getUserGroupRole(
		long userId, long groupId, long roleId) {

		UserGroupRole userGroupRole =
			_userGroupRoleLocalService.createUserGroupRole(0);

		userGroupRole.setUserId(userId);
		userGroupRole.setGroupId(groupId);
		userGroupRole.setRoleId(roleId);

		return userGroupRole;
	}

	private List<UserGroupRole> _getUserGroupRoles(
			PortletRequest portletRequest)
		throws PortalException {

		User user = _portal.getSelectedUser(portletRequest);

		if (user == null) {
			return Collections.emptyList();
		}

		Set<UserGroupRole> userGroupRoles = new HashSet<>(
			_userGroupRoleLocalService.getUserGroupRoles(user.getUserId()));

		long userId = _getUserId(user);

		userGroupRoles.addAll(
			_getUserGroupRoles(
				portletRequest, userId, "addDepotGroupRolesGroupIds",
				"addDepotGroupRolesRoleIds"));
		userGroupRoles.removeAll(
			_getUserGroupRoles(
				portletRequest, userId, "deleteDepotGroupRolesGroupIds",
				"deleteDepotGroupRolesRoleIds"));

		return new ArrayList<>(userGroupRoles);
	}

	private Set<UserGroupRole> _getUserGroupRoles(
		PortletRequest portletRequest, long userId, String groupIdsParam,
		String roleIdsParam) {

		long[] groupIds = StringUtil.split(
			ParamUtil.getString(portletRequest, groupIdsParam), 0L);
		long[] roleIds = StringUtil.split(
			ParamUtil.getString(portletRequest, roleIdsParam), 0L);

		if (groupIds.length != roleIds.length) {
			return Collections.emptySet();
		}

		Set<UserGroupRole> userGroupRoles = new HashSet<>();

		for (int i = 0; i < groupIds.length; i++) {
			if ((groupIds[i] != 0) && (roleIds[i] != 0)) {
				userGroupRoles.add(
					_getUserGroupRole(userId, groupIds[i], roleIds[i]));
			}
		}

		return userGroupRoles;
	}

	private long _getUserId(User user) {
		return Optional.of(
			user
		).map(
			User::getUserId
		).orElse(
			0L
		);
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserService _userService;

}