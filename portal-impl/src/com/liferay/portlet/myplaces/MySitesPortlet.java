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

package com.liferay.portlet.myplaces;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.service.MembershipRequestServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.HashSet;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Eudaldo Alonso
 */
public class MySitesPortlet extends MVCPortlet {

	public void postMembershipRequest(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String comments = ParamUtil.getString(actionRequest, "comments");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		MembershipRequestServiceUtil.addMembershipRequest(
			groupId, comments, serviceContext);

		SessionMessages.add(actionRequest, "membershipRequestSent");
	}

	public void updateGroupUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);

		addUserIds = filterAddUserIds(groupId, addUserIds);

		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		removeUserIds = filterRemoveUserIds(groupId, removeUserIds);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		UserServiceUtil.addGroupUsers(groupId, addUserIds, serviceContext);
		UserServiceUtil.unsetGroupUsers(groupId, removeUserIds, serviceContext);

		LiveUsers.joinGroup(themeDisplay.getCompanyId(), groupId, addUserIds);
		LiveUsers.leaveGroup(
			themeDisplay.getCompanyId(), groupId, removeUserIds);
	}

	protected long[] filterAddUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<>(userIds.length);

		for (long userId : userIds) {
			if (!UserLocalServiceUtil.hasGroupUser(groupId, userId)) {
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
			if (UserLocalServiceUtil.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(
			filteredUserIds.toArray(new Long[filteredUserIds.size()]));
	}

}