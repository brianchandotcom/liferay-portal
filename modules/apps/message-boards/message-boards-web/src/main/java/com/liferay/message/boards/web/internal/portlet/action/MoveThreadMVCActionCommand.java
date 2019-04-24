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
import com.liferay.message.boards.exception.LockedThreadException;
import com.liferay.message.boards.exception.MessageBodyException;
import com.liferay.message.boards.exception.MessageSubjectException;
import com.liferay.message.boards.exception.NoSuchThreadException;
import com.liferay.message.boards.exception.RequiredMessageException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadLocalService;
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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

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
		"mvc.command.name=/message_boards/move_thread"
	},
	service = MVCActionCommand.class
)
public class MoveThreadMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			moveThread(actionRequest, actionResponse);
		}
		catch (LockedThreadException | PrincipalException |
			   RequiredMessageException e) {

			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/message_boards/error.jsp");
		}
		catch (MessageBodyException | MessageSubjectException |
			   NoSuchThreadException e) {

			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	protected void moveThread(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "mbCategoryId");

		long threadId = ParamUtil.getLong(actionRequest, "threadId");

		MBThread thread = _mbThreadLocalService.getThread(threadId);

		_mbThreadService.moveThread(categoryId, threadId);

		boolean addExplanationPost = ParamUtil.getBoolean(
			actionRequest, "addExplanationPost");

		if (addExplanationPost) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String subject = ParamUtil.getString(actionRequest, "subject");
			String body = ParamUtil.getString(actionRequest, "body");

			MBGroupServiceSettings mbGroupServiceSettings =
				MBGroupServiceSettings.getInstance(
					themeDisplay.getScopeGroupId());

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				MBMessage.class.getName(), actionRequest);

			_mbMessageService.addMessage(
				thread.getRootMessageId(), subject, body,
				mbGroupServiceSettings.getMessageFormat(),
				Collections.emptyList(), false,
				MBThreadConstants.PRIORITY_NOT_GIVEN, false, serviceContext);
		}

		LiferayActionResponse liferayActionResponse =
			(LiferayActionResponse)actionResponse;

		PortletURL portletURL = liferayActionResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_message");
		portletURL.setParameter(
			"messageId", String.valueOf(thread.getRootMessageId()));

		actionResponse.sendRedirect(portletURL.toString());
	}

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private MBThreadService _mbThreadService;

}