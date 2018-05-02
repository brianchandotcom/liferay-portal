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

import com.liferay.message.boards.exception.NoSuchMessageException;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageDisplay;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBBanLocalServiceUtil;
import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.message.boards.service.MBMessageServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalServiceUtil;
import com.liferay.message.boards.web.internal.security.permission.MBResourcePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static MBCategory getCategory(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String mvcRenderCommandName = ParamUtil.getString(
			request, "mvcRenderCommandName");

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (mvcRenderCommandName.equals("/message_boards/view_banned_users") &&
			!MBResourcePermission.contains(
				permissionChecker, themeDisplay.getScopeGroupId(),
				ActionKeys.BAN_USER)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, ActionKeys.BAN_USER);
		}

		MBBanLocalServiceUtil.checkBan(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId());

		long categoryId = ParamUtil.getLong(request, "mbCategoryId");

		MBCategory category = null;

		if (categoryId > 0) {
			category = MBCategoryServiceUtil.getCategory(categoryId);
		}
		else {
			MBResourcePermission.check(
				permissionChecker, themeDisplay.getScopeGroupId(),
				ActionKeys.VIEW);
		}

		return category;
	}

	public static MBCategory getCategory(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getCategory(request);
	}

	public static MBMessage getMessage(HttpServletRequest request)
		throws Exception {

		long messageId = ParamUtil.getLong(request, "messageId");

		MBMessage message = null;

		if (messageId > 0) {
			message = MBMessageServiceUtil.getMessage(messageId);
		}

		if ((message != null) && message.isInTrash()) {
			throw new NoSuchMessageException("{messageId=" + messageId + "}");
		}

		return message;
	}

	public static MBMessage getMessage(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getMessage(request);
	}

	public static MBMessageDisplay getMessageDisplay(HttpServletRequest request)
		throws PortalException {

		long messageId = ParamUtil.getLong(request, "messageId");

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		int status = WorkflowConstants.STATUS_APPROVED;

		if (permissionChecker.isContentReviewer(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId())) {

			status = WorkflowConstants.STATUS_ANY;
		}

		MBMessageDisplay messageDisplay =
			MBMessageServiceUtil.getMessageDisplay(messageId, status);

		if (messageDisplay != null) {
			MBMessage message = messageDisplay.getMessage();

			if ((message != null) && message.isInTrash()) {
				throw new NoSuchMessageException(
					"{messageId=" + messageId + "}");
			}
		}

		return messageDisplay;
	}

	public static MBMessageDisplay getMessageDisplay(
			PortletRequest portletRequest)
		throws PortalException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getMessageDisplay(request);
	}

	public static MBMessage getThreadMessage(HttpServletRequest request)
		throws Exception {

		long threadId = ParamUtil.getLong(request, "threadId");

		MBMessage message = null;

		if (threadId > 0) {
			MBThread thread = MBThreadLocalServiceUtil.getThread(threadId);

			message = MBMessageServiceUtil.getMessage(
				thread.getRootMessageId());
		}

		if ((message != null) && message.isInTrash()) {
			throw new NoSuchMessageException("{threadId=" + threadId + "}");
		}

		return message;
	}

	public static MBMessage getThreadMessage(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getThreadMessage(request);
	}

}