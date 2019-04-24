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

package com.liferay.message.boards.web.internal.portlet.action;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.message.boards.exception.MessageBodyException;
import com.liferay.message.boards.exception.MessageSubjectException;
import com.liferay.message.boards.exception.NoSuchThreadException;
import com.liferay.message.boards.exception.RequiredMessageException;
import com.liferay.message.boards.exception.SplitThreadException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadService;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.portal.kernel.portlet.LiferayActionResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.InputStream;

import java.util.Collections;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/split_thread"
	},
	service = MVCActionCommand.class
)
public class SplitThreadMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			splitThread(actionRequest, actionResponse);
		}
		catch (PrincipalException | RequiredMessageException e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/message_boards/error.jsp");
		}
		catch (MessageBodyException | MessageSubjectException |
			   NoSuchThreadException | SplitThreadException e) {

			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	protected void splitThread(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		String splitThreadSubject = ParamUtil.getString(
			actionRequest, "splitThreadSubject");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			MBThread.class.getName(), actionRequest);

		MBMessage message = _mbMessageLocalService.getMessage(messageId);

		MBThread newThread = _mbThreadService.splitThread(
			messageId, splitThreadSubject, serviceContext);

		boolean addExplanationPost = ParamUtil.getBoolean(
			actionRequest, "addExplanationPost");

		if (addExplanationPost) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long oldParentMessageId = message.getParentMessageId();
			String subject = ParamUtil.getString(actionRequest, "subject");
			String body = ParamUtil.getString(actionRequest, "body");

			MBGroupServiceSettings mbGroupServiceSettings =
				MBGroupServiceSettings.getInstance(
					themeDisplay.getScopeGroupId());

			String layoutFullURL = _portal.getLayoutFullURL(themeDisplay);

			String newThreadURL =
				layoutFullURL + "/-/message_boards/view_message/" +
					message.getMessageId();

			body = StringUtil.replace(
				body, MBThreadConstants.NEW_THREAD_URL, newThreadURL);

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			_mbMessageService.addMessage(
				oldParentMessageId, subject, body,
				mbGroupServiceSettings.getMessageFormat(),
				Collections.<ObjectValuePair<String, InputStream>>emptyList(),
				false, MBThreadConstants.PRIORITY_NOT_GIVEN,
				message.isAllowPingbacks(), serviceContext);
		}

		LiferayActionResponse liferayActionResponse =
			(LiferayActionResponse)actionResponse;

		PortletURL portletURL = liferayActionResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_message");
		portletURL.setParameter(
			"messageId", String.valueOf(newThread.getRootMessageId()));

		actionResponse.sendRedirect(portletURL.toString());
	}

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadService _mbThreadService;

	@Reference
	private Portal _portal;

}