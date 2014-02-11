/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Iván Zaera
 */
public class JournalUserNotificationHandler
	extends BaseUserNotificationHandler {

	public JournalUserNotificationHandler() {
		setPortletId(PortletKeys.JOURNAL);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		JournalArticle article = null;

		try {
			article = JournalArticleServiceUtil.getArticle(classPK);
		}
		catch (NoSuchArticleException nsae) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		int notificationType = jsonObject.getInt("notificationType");

		String title = StringPool.BLANK;

		if (notificationType ==
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY) {

			title = "x-added-a-new-web-content";
		}
		else if (notificationType ==
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY) {

			title = "x-updated-a-web-content";
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<div class=\"title\">");
		sb.append(
			serviceContext.translate(
				title,
				HtmlUtil.escape(
					PortalUtil.getUserName(
						article.getUserId(), StringPool.BLANK))));
		sb.append("</div><div class=\"body\">");
		sb.append(
			HtmlUtil.escape(
				StringUtil.shorten(
					article.getTitle(serviceContext.getLocale()), 50)));
		sb.append("</div>");

		return sb.toString();
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		JournalArticle article = null;

		try {
			article = JournalArticleServiceUtil.getArticle(classPK);
		}
		catch (NoSuchArticleException nsae) {
			return null;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		User user = themeDisplay.getUser();

		Group group = user.getGroup();

		long portletPlid = PortalUtil.getPlidFromPortletId(
			group.getGroupId(), true, PortletKeys.JOURNAL);

		PortletURL portletURL = null;

		if (portletPlid != 0) {
			portletURL = PortletURLFactoryUtil.create(
				serviceContext.getLiferayPortletRequest(), PortletKeys.JOURNAL,
				portletPlid, PortletRequest.RENDER_PHASE);

			portletURL.setParameter("struts_action", "/journal/edit_article");
			portletURL.setParameter(
				"groupId", String.valueOf(article.getGroupId()));
			portletURL.setParameter(
				"articleId", String.valueOf(article.getId()));
			portletURL.setParameter(
				"version", String.valueOf(article.getVersion()));
		}
		else {
			LiferayPortletResponse liferayPortletResponse =
				serviceContext.getLiferayPortletResponse();

			portletURL = liferayPortletResponse.createRenderURL(
				PortletKeys.JOURNAL);

			portletURL.setParameter("struts_action", "/journal/edit_article");
			portletURL.setParameter(
				"groupId", String.valueOf(article.getGroupId()));
			portletURL.setParameter(
				"articleId", String.valueOf(article.getArticleId()));
			portletURL.setParameter(
				"version", String.valueOf(article.getVersion()));
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}

		return portletURL.toString();
	}

}