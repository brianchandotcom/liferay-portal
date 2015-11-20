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

package com.liferay.site.memberships.web.portlet;

import com.liferay.portal.MembershipRequestCommentsException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.service.MembershipRequestService;
import com.liferay.portal.service.OrganizationService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupGroupRoleService;
import com.liferay.portal.service.UserGroupRoleService;
import com.liferay.portal.service.UserGroupService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.site.memberships.web.constants.SiteMembershipsPortletKeys;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
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
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SiteMembershipsPortlet extends MVCPortlet {

	public void editGroupOrganizations(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] addOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addOrganizationIds"), 0L);
		long[] removeOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeOrganizationIds"), 0L);

		_organizationService.addGroupOrganizations(
			themeDisplay.getSiteGroupId(), addOrganizationIds);
		_organizationService.unsetGroupOrganizations(
			themeDisplay.getSiteGroupId(), removeOrganizationIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void editGroupUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] addUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserGroupIds"), 0L);
		long[] removeUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserGroupIds"), 0L);

		_userGroupService.addGroupUserGroups(
			themeDisplay.getSiteGroupId(), addUserGroupIds);
		_userGroupService.unsetGroupUserGroups(
			themeDisplay.getSiteGroupId(), removeUserGroupIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void editGroupUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getSiteGroupId();

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);

		addUserIds = filterAddUserIds(groupId, addUserIds);

		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		removeUserIds = filterRemoveUserIds(groupId, removeUserIds);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_userService.addGroupUsers(groupId, addUserIds, serviceContext);
		_userService.unsetGroupUsers(groupId, removeUserIds, serviceContext);

		LiveUsers.joinGroup(themeDisplay.getCompanyId(), groupId, addUserIds);
		LiveUsers.leaveGroup(
			themeDisplay.getCompanyId(), groupId, removeUserIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void editUserGroupGroupRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userGroupId = ParamUtil.getLong(actionRequest, "userGroupId");

		long[] addRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addRoleIds"), 0L);
		long[] removeRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeRoleIds"), 0L);

		_userGroupGroupRoleService.addUserGroupGroupRoles(
			userGroupId, themeDisplay.getSiteGroupId(), addRoleIds);
		_userGroupGroupRoleService.deleteUserGroupGroupRoles(
			userGroupId, themeDisplay.getSiteGroupId(), removeRoleIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void editUserGroupRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		User user = PortalUtil.getSelectedUser(actionRequest, false);

		if (user == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] addRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addRoleIds"), 0L);
		long[] removeRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeRoleIds"), 0L);

		_userGroupRoleService.addUserGroupRoles(
			user.getUserId(), themeDisplay.getSiteGroupId(), addRoleIds);
		_userGroupRoleService.deleteUserGroupRoles(
			user.getUserId(), themeDisplay.getSiteGroupId(), removeRoleIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		if (Validator.isNotNull(redirect)) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}
	}

	public void replyMembershipRequest(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

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
			MembershipRequest membershipRequest =
				_membershipRequestService.getMembershipRequest(
					membershipRequestId);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), membershipRequest.getGroupId(),
				new long[] {membershipRequest.getUserId()});
		}

		SessionMessages.add(actionRequest, "membershipReplySent");

		sendRedirect(actionRequest, actionResponse);
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

	protected long[] filterAddUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<>(userIds.length);

		for (long userId : userIds) {
			if (!_userLocalService.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(
			filteredUserIds.toArray(new Long[filteredUserIds.size()]));
	}

	protected long[] filterRemoveUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<>(userIds.length);

		for (long userId : userIds) {
			if (_userLocalService.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(
			filteredUserIds.toArray(new Long[filteredUserIds.size()]));
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof MembershipPolicyException ||
			cause instanceof MembershipRequestCommentsException ||
			cause instanceof NoSuchGroupException ||
			cause instanceof NoSuchRoleException ||
			cause instanceof PrincipalException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setMembershipRequestService(
		MembershipRequestService membershipRequestService) {

		_membershipRequestService = membershipRequestService;
	}

	@Reference(unbind = "-")
	protected void setOrganizationService(
		OrganizationService organizationService) {

		_organizationService = organizationService;
	}

	@Reference(unbind = "-")
	protected void setUserGroupGroupRoleService(
		UserGroupGroupRoleService userGroupGroupRoleService) {

		_userGroupGroupRoleService = userGroupGroupRoleService;
	}

	@Reference(unbind = "-")
	protected void setUserGroupRoleService(
		UserGroupRoleService userGroupRoleService) {

		_userGroupRoleService = userGroupRoleService;
	}

	@Reference(unbind = "-")
	protected void setUserGroupService(UserGroupService userGroupService) {
		_userGroupService = userGroupService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserService(UserService userService) {
		_userService = userService;
	}

	private volatile MembershipRequestService _membershipRequestService;
	private volatile OrganizationService _organizationService;
	private volatile UserGroupGroupRoleService _userGroupGroupRoleService;
	private volatile UserGroupRoleService _userGroupRoleService;
	private volatile UserGroupService _userGroupService;
	private volatile UserLocalService _userLocalService;
	private volatile UserService _userService;

}