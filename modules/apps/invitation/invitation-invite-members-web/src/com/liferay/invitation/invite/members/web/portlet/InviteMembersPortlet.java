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

package com.liferay.invitation.invite.members.web.portlet;

import com.liferay.invitation.invite.members.service.MemberRequestLocalServiceUtil;
import com.liferay.invitation.invite.members.web.constants.InviteMembersPortletKeys;
import com.liferay.invitation.invite.members.web.util.InviteMembersUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ryan Park
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=so-portlet-invite-members",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.footer-portlet-javascript=/invite_members/js/main.js",
		"com.liferay.portlet.header-portlet-css=/invite_members/css/main.css",
		"com.liferay.portlet.icon=/invite_members/icon.png",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Invite Members",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/invite_members/view.jsp",
		"javax.portlet.name=" + InviteMembersPortletKeys.INVITE_MEMBERS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=invitedMembersCount",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class InviteMembersPortlet extends MVCPortlet {

	public void getAvailableUsers(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		int end = ParamUtil.getInteger(resourceRequest, "end");
		String keywords = ParamUtil.getString(resourceRequest, "keywords");
		int start = ParamUtil.getInteger(resourceRequest, "start");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"count",
			InviteMembersUtil.getAvailableUsersCount(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				keywords));

		JSONObject optionsJSONObject = JSONFactoryUtil.createJSONObject();

		optionsJSONObject.put("end", end);
		optionsJSONObject.put("keywords", keywords);
		optionsJSONObject.put("start", start);

		jsonObject.put("options", optionsJSONObject);

		List<User> users = InviteMembersUtil.getAvailableUsers(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
			keywords, start, end);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User user : users) {
			JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();

			userJSONObject.put(
				"hasPendingMemberRequest",
				MemberRequestLocalServiceUtil.hasPendingMemberRequest(
					themeDisplay.getScopeGroupId(), user.getUserId()));
			userJSONObject.put("userEmailAddress", user.getEmailAddress());
			userJSONObject.put("userFullName", user.getFullName());
			userJSONObject.put("userId", user.getUserId());

			jsonArray.put(userJSONObject);
		}

		jsonObject.put("users", jsonArray);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	public void sendInvites(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doSendInvite(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("getAvailableUsers")) {
				getAvailableUsers(resourceRequest, resourceResponse);
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void updateMemberRequest(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long memberRequestId = ParamUtil.getLong(
			actionRequest, "memberRequestId");
		int status = ParamUtil.getInteger(actionRequest, "status");
		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			MemberRequestLocalServiceUtil.updateMemberRequest(
				themeDisplay.getUserId(), memberRequestId, status);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected void doSendInvite(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long[] receiverUserIds = getLongArray(actionRequest, "receiverUserIds");
		String[] receiverEmailAddresses = getStringArray(
			actionRequest, "receiverEmailAddresses");
		long invitedRoleId = ParamUtil.getLong(actionRequest, "invitedRoleId");
		long invitedTeamId = ParamUtil.getLong(actionRequest, "invitedTeamId");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!UserLocalServiceUtil.hasGroupUser(
				groupId, themeDisplay.getUserId())) {

			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		long plid = themeDisplay.getPlid();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (layout.isPrivateLayout()) {
			Group guestGroup = GroupLocalServiceUtil.getGroup(
				themeDisplay.getCompanyId(), GroupConstants.GUEST);

			plid = guestGroup.getDefaultPublicPlid();
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, PortletKeys.NOTIFICATIONS, plid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/notifications/view.jsp");
		portletURL.setParameter("actionable", StringPool.TRUE);
		portletURL.setWindowState(WindowState.MAXIMIZED);

		serviceContext.setAttribute("redirectURL", portletURL.toString());

		String createAccountURL = getCreateAccountURL(
			PortalUtil.getHttpServletRequest(actionRequest), themeDisplay);

		serviceContext.setAttribute("createAccountURL", createAccountURL);

		serviceContext.setAttribute("loginURL", themeDisplay.getURLSignIn());

		MemberRequestLocalServiceUtil.addMemberRequests(
			themeDisplay.getUserId(), groupId, receiverUserIds, invitedRoleId,
			invitedTeamId, serviceContext);

		MemberRequestLocalServiceUtil.addMemberRequests(
			themeDisplay.getUserId(), groupId, receiverEmailAddresses,
			invitedRoleId, invitedTeamId, serviceContext);
	}

	protected String getCreateAccountURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(themeDisplay.getDefaultUser());

		if (LayoutPermissionUtil.contains(
				permissionChecker, themeDisplay.getLayout(),
				themeDisplay.getControlPanelCategory(), true,
				ActionKeys.VIEW) &&
			LayoutPermissionUtil.contains(
				permissionChecker, themeDisplay.getLayout(), false,
				ActionKeys.VIEW)) {

			return PortalUtil.getCreateAccountURL(request, themeDisplay);
		}

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.GUEST);

		PortletURL createAccountURL = PortletURLFactoryUtil.create(
			request, com.liferay.portal.util.PortletKeys.LOGIN,
			group.getDefaultPublicPlid(), PortletRequest.RENDER_PHASE);

		createAccountURL.setParameter("struts_action", "/login/create_account");
		createAccountURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		createAccountURL.setPortletMode(PortletMode.VIEW);
		createAccountURL.setWindowState(WindowState.MAXIMIZED);

		return createAccountURL.toString();
	}

	protected long[] getLongArray(PortletRequest portletRequest, String name) {
		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value), 0L);
	}

	protected String[] getStringArray(
		PortletRequest portletRequest, String name) {

		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InviteMembersPortlet.class);

}