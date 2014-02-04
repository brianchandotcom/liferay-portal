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

package com.liferay.portlet.documentlibrary.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Roberto Díaz
 */
public class DocumentLibraryUserNotificationHandler
	extends BaseUserNotificationHandler {

	public DocumentLibraryUserNotificationHandler() {
		setPortletId(PortletKeys.DOCUMENT_LIBRARY);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		FileEntry file = null;

		try {
			file = DLAppLocalServiceUtil.getFileEntry(classPK);
		}
		catch (Exception e) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		int notificationType = jsonObject.getInt("notificationType");

		String title = StringPool.BLANK;

		if (notificationType == 0) {
			title = "x-wrote-a-new-file-entry";
		}
		else if (notificationType == 1) {
			title = "x-updated-a-file-entry";
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<div class=\"title\">");
		sb.append(
			serviceContext.translate(
				title,
				HtmlUtil.escape(
					PortalUtil.getUserName(
						file.getUserId(), StringPool.BLANK))));
		sb.append("</div><div class=\"body\">");
		sb.append(HtmlUtil.escape(StringUtil.shorten(file.getTitle(), 50)));
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

		FileEntry file = null;

		try {
			file = DLAppLocalServiceUtil.getFileEntry(classPK);
		}
		catch (Exception e) {
			return null;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		User user = themeDisplay.getUser();

		Group group = user.getGroup();

		long portletPlid = PortalUtil.getPlidFromPortletId(
			group.getGroupId(), true, PortletKeys.DOCUMENT_LIBRARY);

		PortletURL portletURL = null;

		if (portletPlid != 0) {
			portletURL = PortletURLFactoryUtil.create(
				serviceContext.getLiferayPortletRequest(),
				PortletKeys.DOCUMENT_LIBRARY, portletPlid,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"struts_action", "/document_library/view_file_entry");
			portletURL.setParameter(
				"fileEntryId", String.valueOf(file.getFileEntryId()));
		}
		else {
			LiferayPortletResponse liferayPortletResponse =
				serviceContext.getLiferayPortletResponse();

			portletURL = liferayPortletResponse.createRenderURL(
				PortletKeys.DOCUMENT_LIBRARY);

			portletURL.setParameter(
				"struts_action", "/document_library/view_file_entry");
			portletURL.setParameter(
				"fileEntryId", String.valueOf(file.getFileEntryId()));
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}

		return portletURL.toString();
	}

}