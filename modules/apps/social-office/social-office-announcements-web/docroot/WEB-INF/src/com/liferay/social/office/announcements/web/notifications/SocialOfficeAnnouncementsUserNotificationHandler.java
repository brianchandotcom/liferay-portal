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

package com.liferay.social.office.announcements.web.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceUtil;
import com.liferay.social.office.announcements.web.constants.SocialOfficeAnnouncementsPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Jonathan Lee
 */
public class SocialOfficeAnnouncementsUserNotificationHandler
	extends BaseUserNotificationHandler {

	public SocialOfficeAnnouncementsUserNotificationHandler() {
		setPortletId(
			SocialOfficeAnnouncementsPortletKeys.SOCIAL_OFFICE_ANNOUNCEMENTS);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long announcementEntryId = jsonObject.getLong("classPK");

		AnnouncementsEntry announcementEntry =
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				announcementEntryId);

		if (announcementEntry == null) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		String title = serviceContext.translate(
			"x-sent-a-new-announcement",
			HtmlUtil.escape(
				PortalUtil.getUserName(
					announcementEntry.getUserId(), StringPool.BLANK)));

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				HtmlUtil.escape(
					StringUtil.shorten(announcementEntry.getTitle(), 70)),
				title
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long announcementEntryId = jsonObject.getLong("classPK");

		AnnouncementsEntry announcementEntry =
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				announcementEntryId);

		if (announcementEntry == null) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		User user = themeDisplay.getUser();

		Group group = user.getGroup();

		long portletPlid = PortalUtil.getPlidFromPortletId(
			group.getGroupId(), true,
			SocialOfficeAnnouncementsPortletKeys.SOCIAL_OFFICE_ANNOUNCEMENTS);

		PortletURL portletURL = null;

		if (portletPlid != 0) {
			portletURL = PortletURLFactoryUtil.create(
				serviceContext.getLiferayPortletRequest(),
				SocialOfficeAnnouncementsPortletKeys.
					SOCIAL_OFFICE_ANNOUNCEMENTS, portletPlid,
				PortletRequest.RENDER_PHASE);
		}
		else {
			LiferayPortletResponse liferayPortletResponse =
				serviceContext.getLiferayPortletResponse();

			portletURL = liferayPortletResponse.createRenderURL(
				SocialOfficeAnnouncementsPortletKeys.
					SOCIAL_OFFICE_ANNOUNCEMENTS);

			portletURL.setParameter("mvcPath", "/view.jsp");
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}

		return portletURL.toString();
	}

}