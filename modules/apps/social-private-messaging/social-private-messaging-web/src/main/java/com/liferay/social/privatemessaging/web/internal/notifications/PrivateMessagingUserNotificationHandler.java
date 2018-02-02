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

package com.liferay.social.privatemessaging.web.internal.notifications;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.privatemessaging.constants.PrivateMessagingPortletKeys;
import com.liferay.social.privatemessaging.model.UserThread;
import com.liferay.social.privatemessaging.service.UserThreadLocalService;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Lee
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + PrivateMessagingPortletKeys.PRIVATE_MESSAGING},
	service = UserNotificationHandler.class
)
public class PrivateMessagingUserNotificationHandler
	extends BaseUserNotificationHandler {

	public PrivateMessagingUserNotificationHandler() {
		setOpenDialog(true);
		setPortletId(PrivateMessagingPortletKeys.PRIVATE_MESSAGING);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		String body = null;
		long userId = 0;

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		MBMessage mbMessage = _mbMessageLocalService.fetchMBMessage(classPK);

		if (mbMessage == null) {
			body = jsonObject.getString("body");

			if (Validator.isNull(body)) {
				_userNotificationEventLocalService.deleteUserNotificationEvent(
					userNotificationEvent.getUserNotificationEventId());

				return null;
			}

			userId = jsonObject.getLong("userId");
		}
		else {
			UserThread userThread = _userThreadLocalService.fetchUserThread(
				serviceContext.getUserId(), mbMessage.getThreadId());

			if ((userThread == null) || userThread.isDeleted()) {
				_userNotificationEventLocalService.deleteUserNotificationEvent(
					userNotificationEvent.getUserNotificationEventId());

				return null;
			}

			body = mbMessage.getBody();
			userId = mbMessage.getUserId();
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", serviceContext.getLocale(), getClass());

		String title = ResourceBundleUtil.getString(
			resourceBundle, "x-sent-you-a-message",
			HtmlUtil.escape(_portal.getUserName(userId, StringPool.BLANK)));

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				HtmlUtil.escape(StringUtil.shorten(body, 50)), title
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		long mbThreadId = 0;

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		MBMessage mbMessage = _mbMessageLocalService.fetchMBMessage(classPK);

		if (mbMessage == null) {
			MBThread mbThread = _mbThreadLocalService.fetchMBThread(classPK);

			if (mbThread == null) {
				_userNotificationEventLocalService.deleteUserNotificationEvent(
					userNotificationEvent.getUserNotificationEventId());

				return null;
			}

			mbThreadId = classPK;
		}
		else {
			mbThreadId = mbMessage.getThreadId();
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		User user = themeDisplay.getUser();

		Group group = user.getGroup();

		long portletPlid = _portal.getPlidFromPortletId(
			group.getGroupId(), true,
			PrivateMessagingPortletKeys.PRIVATE_MESSAGING);

		PortletURL portletURL = null;

		if (portletPlid != 0) {
			portletURL = PortletURLFactoryUtil.create(
				serviceContext.getLiferayPortletRequest(),
				PrivateMessagingPortletKeys.PRIVATE_MESSAGING, portletPlid,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mbThreadId", String.valueOf(mbThreadId));
		}
		else {
			LiferayPortletResponse liferayPortletResponse =
				serviceContext.getLiferayPortletResponse();

			portletURL = liferayPortletResponse.createRenderURL(
				PrivateMessagingPortletKeys.PRIVATE_MESSAGING);

			portletURL.setParameter("mvcPath", "/view.jsp");
			portletURL.setParameter("mbThreadId", String.valueOf(mbThreadId));
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}

		return portletURL.toString();
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserNotificationEventLocalService(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserThreadLocalService(
		UserThreadLocalService userThreadLocalService) {

		_userThreadLocalService = userThreadLocalService;
	}

	private MBMessageLocalService _mbMessageLocalService;
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private Portal _portal;

	private UserNotificationEventLocalService
		_userNotificationEventLocalService;
	private UserThreadLocalService _userThreadLocalService;

}