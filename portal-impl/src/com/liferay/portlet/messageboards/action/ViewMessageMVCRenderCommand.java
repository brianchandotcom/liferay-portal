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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + PortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/view_message"
	},
	service = MVCRenderCommand.class
)
public class ViewMessageMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long messageId = ParamUtil.getLong(renderRequest, "messageId");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			int status = WorkflowConstants.STATUS_APPROVED;

			if (permissionChecker.isContentReviewer(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId())) {

				status = WorkflowConstants.STATUS_ANY;
			}

			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					renderRequest);

			String threadView = ParamUtil.getString(
				renderRequest, "threadView");

			if (Validator.isNotNull(threadView)) {
				preferences.setValue(
					PortletKeys.MESSAGE_BOARDS, "thread-view", threadView);
			}
			else {
				threadView = preferences.getValue(
					PortletKeys.MESSAGE_BOARDS, "thread-view",
					PropsValues.MESSAGE_BOARDS_THREAD_VIEWS_DEFAULT);
			}

			if (!ArrayUtil.contains(
					PropsValues.MESSAGE_BOARDS_THREAD_VIEWS, threadView)) {

				threadView = PropsValues.MESSAGE_BOARDS_THREAD_VIEWS_DEFAULT;

				preferences.setValue(
					PortletKeys.MESSAGE_BOARDS, "thread-view", threadView);
			}

			boolean includePrevAndNext =
				PropsValues.
					MESSAGE_BOARDS_THREAD_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED;

			MBMessageDisplay messageDisplay =
				MBMessageServiceUtil.getMessageDisplay(
					messageId, status, threadView, includePrevAndNext);

			if (messageDisplay != null) {
				MBMessage message = messageDisplay.getMessage();

				if ((message != null) && message.isInTrash()) {
					throw new NoSuchMessageException(
						"{messageId=" + messageId + "}");
				}
			}

			renderRequest.setAttribute(
				WebKeys.MESSAGE_BOARDS_MESSAGE, messageDisplay);

			return "/html/portlet/message_boards/view_message.jsp";
		}
		catch (NoSuchMessageException | PrincipalException e) {
			SessionErrors.add(renderRequest, e.getClass());

			return "/html/portlet/message_board/error.jsp";
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

}