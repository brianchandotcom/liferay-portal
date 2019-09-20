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

package com.liferay.invitation.invite.members.web.internal.portlet;

import com.liferay.invitation.invite.members.constants.InviteMembersPortletKeys;
import com.liferay.invitation.invite.members.service.MemberRequestLocalService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=so-portlet-invite-members",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.header-portlet-css=/invite_members/css/main.css",
		"com.liferay.portlet.icon=/icons/invite_members.png",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Invite Members",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/invite_members/view.jsp",
		"javax.portlet.name=" + InviteMembersPortletKeys.INVITE_MEMBERS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=invitedMembersCount"
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

		JSONObject jsonObject = JSONUtil.put(
			"count",
			_getAvailableUsersCount(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				keywords));

		JSONObject optionsJSONObject = JSONUtil.put(
			"end", end
		).put(
			"keywords", keywords
		).put(
			"start", start
		);

		jsonObject.put("options", optionsJSONObject);

		List<User> users = _getAvailableUsers(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
			keywords, start, end);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User user : users) {
			JSONObject userJSONObject = JSONUtil.put(
				"hasPendingMemberRequest",
				_memberRequestLocalService.hasPendingMemberRequest(
					themeDisplay.getScopeGroupId(), user.getUserId())
			).put(
				"userEmailAddress", user.getEmailAddress()
			).put(
				"userFullName", user.getFullName()
			).put(
				"userId", user.getUserId()
			);

			jsonArray.put(userJSONObject);
		}

		jsonObject.put("users", jsonArray);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	public void sendInvites(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doSendInvite(actionRequest);
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

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			_memberRequestLocalService.updateMemberRequest(
				themeDisplay.getUserId(), memberRequestId, status);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected void doSendInvite(ActionRequest actionRequest) throws Exception {
		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_userLocalService.hasGroupUser(
				groupId, themeDisplay.getUserId())) {

			return;
		}

		long invitedTeamId = ParamUtil.getLong(actionRequest, "invitedTeamId");
		long[] receiverUserIds = getLongArray(actionRequest, "receiverUserIds");
		long invitedRoleId = ParamUtil.getLong(actionRequest, "invitedRoleId");
		String[] receiverEmailAddresses = getStringArray(
			actionRequest, "receiverEmailAddresses");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, _groupLocalService.getGroup(groupId),
			UserNotificationEvent.class.getName(), PortletProvider.Action.VIEW);

		serviceContext.setAttribute("redirectURL", portletURL.toString());

		String createAccountURL = _portal.getCreateAccountURL(
			_portal.getHttpServletRequest(actionRequest), themeDisplay);

		serviceContext.setAttribute("createAccountURL", createAccountURL);

		serviceContext.setAttribute("loginURL", themeDisplay.getURLSignIn());

		_memberRequestLocalService.addMemberRequests(
			themeDisplay.getUserId(), groupId, receiverUserIds, invitedRoleId,
			invitedTeamId, serviceContext);

		_memberRequestLocalService.addMemberRequests(
			themeDisplay.getUserId(), groupId, receiverEmailAddresses,
			invitedRoleId, invitedTeamId, serviceContext);
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

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.invitation.invite.members.service)(&(release.schema.version>=2.0.0)(!(release.schema.version>=3.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private List<User> _getAvailableUsers(
			long companyId, long groupId, String keywords, int start, int end)
		throws Exception {

		LinkedHashMap usersParams = new LinkedHashMap();

		usersParams.put(
			"usersInvited",
			new CustomSQLParam(
				_customSQL.get(
					getClass(),
					"com.liferay.portal.service.persistence.UserFinder." +
						"filterByUsersGroupsGroupId"),
				groupId));

		return _userLocalService.search(
			companyId, keywords, WorkflowConstants.STATUS_APPROVED, usersParams,
			start, end, new UserFirstNameComparator(true));
	}

	private int _getAvailableUsersCount(
			long companyId, long groupId, String keywords)
		throws Exception {

		LinkedHashMap usersParams = new LinkedHashMap();

		usersParams.put(
			"usersInvited",
			new CustomSQLParam(
				_customSQL.get(
					getClass(),
					"com.liferay.portal.service.persistence.UserFinder." +
						"filterByUsersGroupsGroupId"),
				groupId));

		return _userLocalService.searchCount(
			companyId, keywords, WorkflowConstants.STATUS_APPROVED,
			usersParams);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InviteMembersPortlet.class);

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private MemberRequestLocalService _memberRequestLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}