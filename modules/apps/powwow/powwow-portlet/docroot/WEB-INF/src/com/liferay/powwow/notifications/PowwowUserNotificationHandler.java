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

package com.liferay.powwow.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.service.PowwowMeetingLocalServiceUtil;
import com.liferay.powwow.util.PowwowPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Matthew Kong
 */
public class PowwowUserNotificationHandler extends BaseUserNotificationHandler {

	public PowwowUserNotificationHandler() {
		setPortletId(PowwowPortletKeys.POWWOW_MEETINGS);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long powwowMeetingId = jsonObject.getLong("classPK");

		PowwowMeeting powwowMeeting =
			PowwowMeetingLocalServiceUtil.fetchPowwowMeeting(powwowMeetingId);

		if (powwowMeeting == null) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		long userId = jsonObject.getLong("userId");

		User user = UserLocalServiceUtil.fetchUser(userId);

		if (user == null) {
			return null;
		}

		String userFullName = HtmlUtil.escape(
			PortalUtil.getUserName(user.getUserId(), StringPool.BLANK));

		String title = serviceContext.translate(
			"x-invited-you-to-a-meeting", userFullName);

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				HtmlUtil.escape(
					StringUtil.shorten(powwowMeeting.getName(), 50)),
				title
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		User user = themeDisplay.getUser();

		long portletPlid = PortalUtil.getPlidFromPortletId(
			user.getGroupId(), true, PowwowPortletKeys.POWWOW_MEETINGS);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			serviceContext.getLiferayPortletRequest(),
			PowwowPortletKeys.POWWOW_MEETINGS, portletPlid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/meetings/view_meeting.jsp");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long powwowMeetingId = jsonObject.getLong("classPK");

		portletURL.setParameter(
			"powwowMeetingId", String.valueOf(powwowMeetingId));

		return portletURL.toString();
	}

}