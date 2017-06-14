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

package com.liferay.social.networking.web.internal.summary.portlet;

import com.liferay.blogs.service.BlogsStatsUserLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.service.SocialRelationLocalService;
import com.liferay.social.kernel.service.SocialRequestLocalService;
import com.liferay.social.networking.constants.SocialNetworkingPortletKeys;
import com.liferay.social.networking.friends.social.FriendsRequestKeys;
import com.liferay.social.networking.members.social.MembersRequestKeys;
import com.liferay.social.networking.service.MeetupsEntryLocalService;
import com.liferay.social.networking.service.MeetupsRegistrationLocalService;
import com.liferay.social.networking.service.WallEntryLocalService;
import com.liferay.social.networking.web.internal.constants.SocialNetworkingWebKeys;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.List;

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
	property = {
		"com.liferay.portlet.css-class-wrapper=social-networking-portlet-summary",
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.header-portlet-css=/summary/css/main.css",
		"javax.portlet.display-name=Summary",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Summary",
		"javax.portlet.info.short-title=Summary",
		"javax.portlet.info.title=Summary",
		"javax.portlet.init-param.clear-request-parameters=true",
		"javax.portlet.init-param.view-template=/summary/view.jsp",
		"javax.portlet.name=" + SocialNetworkingPortletKeys.SUMMARY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SummaryPortlet extends MVCPortlet {

	public void addFriend(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		User user = _userLocalService.getUserById(group.getClassPK());

		JSONObject extraDataJSONObject = getExtraDataJSONObject(actionRequest);

		String addFriendMessage = ParamUtil.getString(
			actionRequest, "addFriendMessage");

		extraDataJSONObject.put("addFriendMessage", addFriendMessage);

		_socialRequestLocalService.addRequest(
			themeDisplay.getUserId(), 0, User.class.getName(),
			themeDisplay.getUserId(), FriendsRequestKeys.ADD_FRIEND,
			extraDataJSONObject.toString(), user.getUserId());
	}

	public void deleteFriend(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		User user = _userLocalService.getUserById(group.getClassPK());

		_socialRelationLocalService.deleteRelation(
			themeDisplay.getUserId(), user.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);
	}

	public void joinGroup(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		if (group.getType() == GroupConstants.TYPE_SITE_OPEN) {
			_userLocalService.addGroupUsers(
				group.getGroupId(), new long[] {themeDisplay.getUserId()});

			return;
		}

		Role siteAdminRole = _roleLocalService.getRole(
			themeDisplay.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		userParams.put(
			"userGroupRole",
			new Long[] {group.getGroupId(), siteAdminRole.getRoleId()});

		List<User> users = _userLocalService.search(
			themeDisplay.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, userParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, (OrderByComparator<User>)null);

		if (users.isEmpty()) {
			Role adminRole = _roleLocalService.getRole(
				themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR);

			userParams.clear();

			userParams.put("usersRoles", adminRole.getRoleId());

			users = _userLocalService.search(
				themeDisplay.getCompanyId(), null,
				WorkflowConstants.STATUS_APPROVED, userParams,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				(OrderByComparator<User>)null);
		}

		JSONObject extraDataJSONObject = getExtraDataJSONObject(actionRequest);

		for (User user : users) {
			_socialRequestLocalService.addRequest(
				themeDisplay.getUserId(), 0, Group.class.getName(),
				group.getGroupId(), MembersRequestKeys.ADD_MEMBER,
				extraDataJSONObject.toString(), user.getUserId());
		}
	}

	public void joinOrganization(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		Organization organization = _organizationLocalService.getOrganization(
			group.getClassPK());

		Role role = _roleLocalService.getRole(
			themeDisplay.getCompanyId(), "Organization Administrator");

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		userParams.put(
			"userGroupRole", new Long[] {group.getGroupId(), role.getRoleId()});

		List<User> users = _userLocalService.search(
			themeDisplay.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, userParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, (OrderByComparator<User>)null);

		if (users.isEmpty()) {
			Role adminRole = _roleLocalService.getRole(
				themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR);

			userParams.clear();

			userParams.put("usersRoles", adminRole.getRoleId());

			users = _userLocalService.search(
				themeDisplay.getCompanyId(), null,
				WorkflowConstants.STATUS_APPROVED, userParams,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				(OrderByComparator<User>)null);
		}

		JSONObject extraDataJSONObject = getExtraDataJSONObject(actionRequest);

		for (User user : users) {
			_socialRequestLocalService.addRequest(
				themeDisplay.getUserId(), 0, Organization.class.getName(),
				organization.getOrganizationId(), MembersRequestKeys.ADD_MEMBER,
				extraDataJSONObject.toString(), user.getUserId());
		}
	}

	public void leaveGroup(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_userLocalService.unsetGroupUsers(
			themeDisplay.getScopeGroupId(),
			new long[] {themeDisplay.getUserId()}, serviceContext);
	}

	public void leaveOrganization(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		_userLocalService.unsetOrganizationUsers(
			group.getClassPK(), new long[] {themeDisplay.getUserId()});
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			SocialNetworkingWebKeys.BLOGS_STATS_USER_LOCAL_SERVICE,
			_blogsStatsUserLocalService);

		super.render(renderRequest, renderResponse);
	}

	public void updateSummary(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		User user = null;

		if (group.isUser()) {
			user = _userLocalService.getUserById(group.getClassPK());
		}
		else {
			return;
		}

		if (!UserPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), user.getUserId(),
				ActionKeys.UPDATE)) {

			return;
		}

		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");

		_userLocalService.updateJobTitle(user.getUserId(), jobTitle);

		String aboutMe = ParamUtil.getString(actionRequest, "aboutMe");

		_expandoValueLocalService.addValue(
			themeDisplay.getCompanyId(), User.class.getName(), "SN", "aboutMe",
			user.getUserId(), aboutMe);
	}

	protected JSONObject getExtraDataJSONObject(ActionRequest actionRequest) {
		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		String portletId = _portal.getPortletId(actionRequest);

		extraDataJSONObject.put(
			"portletId", PortletIdCodec.decodePortletName(portletId));

		return extraDataJSONObject;
	}

	@Reference(unbind = "-")
	protected void setBlogsStatsUserLocalService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {

		_expandoValueLocalService = expandoValueLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setMeetupsEntryLocalService(
		MeetupsEntryLocalService meetupsEntryLocalService) {
	}

	@Reference(unbind = "-")
	protected void setMeetupsRegistrationLocalService(
		MeetupsRegistrationLocalService meetupsRegistrationLocalService) {
	}

	@Reference(unbind = "-")
	protected void setOrganizationLocalService(
		OrganizationLocalService organizationLocalService) {

		_organizationLocalService = organizationLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setSocialRelationLocalService(
		SocialRelationLocalService socialRelationLocalService) {

		_socialRelationLocalService = socialRelationLocalService;
	}

	@Reference(unbind = "-")
	protected void setSocialRequestLocalService(
		SocialRequestLocalService socialRequestLocalService) {

		_socialRequestLocalService = socialRequestLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setWallEntryLocalService(
		WallEntryLocalService wallEntryLocalService) {
	}

	private BlogsStatsUserLocalService _blogsStatsUserLocalService;
	private ExpandoValueLocalService _expandoValueLocalService;
	private GroupLocalService _groupLocalService;
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

	private RoleLocalService _roleLocalService;
	private SocialRelationLocalService _socialRelationLocalService;
	private SocialRequestLocalService _socialRequestLocalService;
	private UserLocalService _userLocalService;

}