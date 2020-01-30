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

package com.liferay.site.memberships.web.internal.portlet;

import com.liferay.portal.kernel.exception.MembershipRequestCommentsException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.RequiredUserException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.kernel.service.MembershipRequestService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.site.memberships.constants.SiteMembershipsPortletKeys;
import com.liferay.site.memberships.web.internal.display.context.SiteMembershipsDisplayContext;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-communities",
		"com.liferay.portlet.icon=/icons/site_memberships_admin.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Site Memberships Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class SiteMembershipsPortlet extends MVCPortlet {

	public void addGroupOrganizations(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long[] addOrganizationIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		_organizationService.addGroupOrganizations(
			group.getGroupId(), addOrganizationIds);
	}

	public void addGroupUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long[] addUserGroupIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		_userGroupService.addGroupUserGroups(
			group.getGroupId(), addUserGroupIds);
	}

	public void addGroupUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long groupId = group.getGroupId();

		long[] addUserIds = ParamUtil.getLongValues(actionRequest, "rowIds");

		addUserIds = _filterAddUserIds(groupId, addUserIds);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_userService.addGroupUsers(groupId, addUserIds, serviceContext);

		LiveUsers.joinGroup(group.getCompanyId(), groupId, addUserIds);
	}

	public void addUserGroupGroupRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long userGroupId = ParamUtil.getLong(actionRequest, "userGroupId");

		long[] roleIds = ParamUtil.getLongValues(actionRequest, "rowIds");

		_userGroupGroupRoleService.addUserGroupGroupRoles(
			userGroupId, group.getGroupId(), roleIds);
	}

	public void deleteGroupOrganizations(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long[] removeOrganizationIds = null;

		long removeOrganizationId = ParamUtil.getLong(
			actionRequest, "removeOrganizationId");

		if (removeOrganizationId > 0) {
			removeOrganizationIds = new long[] {removeOrganizationId};
		}
		else {
			removeOrganizationIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		_organizationService.unsetGroupOrganizations(
			group.getGroupId(), removeOrganizationIds);
	}

	public void deleteGroupUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long[] removeUserGroupIds = null;

		long removeUserGroupId = ParamUtil.getLong(
			actionRequest, "removeUserGroupId");

		if (removeUserGroupId > 0) {
			removeUserGroupIds = new long[] {removeUserGroupId};
		}
		else {
			removeUserGroupIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		_userGroupService.unsetGroupUserGroups(
			group.getGroupId(), removeUserGroupIds);
	}

	public void deleteGroupUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long groupId = group.getGroupId();

		long[] removeUserIds = null;

		long removeUserId = ParamUtil.getLong(actionRequest, "removeUserId");

		if (removeUserId > 0) {
			removeUserIds = new long[] {removeUserId};
		}
		else {
			removeUserIds = ParamUtil.getLongValues(actionRequest, "rowIds");
		}

		long[] filteredRemoveUserIds = _filterRemoveUserIds(
			groupId, removeUserIds);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_userService.unsetGroupUsers(
			groupId, filteredRemoveUserIds, serviceContext);

		LiveUsers.leaveGroup(
			group.getCompanyId(), groupId, filteredRemoveUserIds);

		if (removeUserIds.length != filteredRemoveUserIds.length) {
			hideDefaultErrorMessage(actionRequest);

			throw new RequiredUserException();
		}
	}

	public void editUserGroupRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		User user = _portal.getSelectedUser(actionRequest, false);

		if (user == null) {
			return;
		}

		Group group = _getGroup(actionRequest, actionResponse);

		long[] roleIds = ParamUtil.getLongValues(actionRequest, "rowIds");

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRoles(
				user.getUserId(), group.getGroupId());

		List<Long> curRoleIds = ListUtil.toList(
			userGroupRoles, UsersAdmin.USER_GROUP_ROLE_ID_ACCESSOR);

		List<Long> removeRoleIds = new ArrayList<>();

		for (long roleId : curRoleIds) {
			if (!ArrayUtil.contains(roleIds, roleId)) {
				removeRoleIds.add(roleId);
			}
		}

		_userGroupRoleService.updateUserGroupRoles(
			user.getUserId(), group.getGroupId(), roleIds,
			ArrayUtil.toLongArray(removeRoleIds));
	}

	public void editUserGroupsRoles(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long[] userGroupIds = ParamUtil.getLongValues(actionRequest, "rowIds");

		long[] roleIds = ParamUtil.getLongValues(actionRequest, "rowIdsRole");

		for (long roleId : roleIds) {
			_userGroupGroupRoleService.addUserGroupGroupRoles(
				userGroupIds, group.getGroupId(), roleId);
		}
	}

	public void editUsersRoles(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long[] userIds = ParamUtil.getLongValues(actionRequest, "rowIds");

		long[] roleIds = ParamUtil.getLongValues(actionRequest, "rowIdsRole");

		for (long roleId : roleIds) {
			_userGroupRoleService.addUserGroupRoles(
				userIds, group.getGroupId(), roleId);
		}
	}

	public void removeUserGroupRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] userIds = ParamUtil.getLongValues(actionRequest, "rowIds");
		Group group = _getGroup(actionRequest, actionResponse);
		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		_userGroupGroupRoleService.deleteUserGroupGroupRoles(
			userIds, group.getGroupId(), roleId);
	}

	public void removeUserRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] userIds = ParamUtil.getLongValues(actionRequest, "rowIds");
		Group group = _getGroup(actionRequest, actionResponse);
		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		_userGroupRoleService.deleteUserGroupRoles(
			userIds, group.getGroupId(), roleId);
	}

	public void replyMembershipRequest(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long membershipRequestId = ParamUtil.getLong(
			actionRequest, "membershipRequestId");

		long statusId = ParamUtil.getLong(actionRequest, "statusId");
		String replyComments = ParamUtil.getString(
			actionRequest, "replyComments");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_membershipRequestService.updateStatus(
			membershipRequestId, replyComments, statusId, serviceContext);

		if (statusId == MembershipRequestConstants.STATUS_APPROVED) {
			Group group = _getGroup(actionRequest, actionResponse);
			MembershipRequest membershipRequest =
				_membershipRequestService.getMembershipRequest(
					membershipRequestId);

			LiveUsers.joinGroup(
				group.getCompanyId(), membershipRequest.getGroupId(),
				new long[] {membershipRequest.getUserId()});
		}

		SessionMessages.add(actionRequest, "membershipReplySent");

		sendRedirect(actionRequest, actionResponse);
	}

	public void unassignUserGroupGroupRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Group group = _getGroup(actionRequest, actionResponse);

		long userGroupId = ParamUtil.getLong(actionRequest, "userGroupId");

		long[] roleIds = ParamUtil.getLongValues(actionRequest, "rowIds");

		_userGroupGroupRoleService.deleteUserGroupGroupRoles(
			userGroupId, group.getGroupId(), roleIds);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchRoleException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof MembershipPolicyException ||
			cause instanceof MembershipRequestCommentsException ||
			cause instanceof NoSuchGroupException ||
			cause instanceof NoSuchRoleException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredUserException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

	private long[] _filterAddUserIds(long groupId, long[] userIds) {
		Set<Long> filteredUserIds = new HashSet<>();

		for (long userId : userIds) {
			if (!_userLocalService.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(filteredUserIds.toArray(new Long[0]));
	}

	private long[] _filterRemoveUserIds(long groupId, long[] userIds) {
		Set<Long> filteredUserIds = new HashSet<>();

		for (long userId : userIds) {
			if (_userLocalService.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(filteredUserIds.toArray(new Long[0]));
	}

	private Group _getGroup(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		SiteMembershipsDisplayContext siteMembershipsDisplayContext =
			new SiteMembershipsDisplayContext(
				_portal.getHttpServletRequest(portletRequest),
				_portal.getLiferayPortletResponse(portletResponse));

		return siteMembershipsDisplayContext.getGroup();
	}

	@Reference
	private MembershipRequestService _membershipRequestService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private UserGroupGroupRoleService _userGroupGroupRoleService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserGroupRoleService _userGroupRoleService;

	@Reference
	private UserGroupService _userGroupService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}