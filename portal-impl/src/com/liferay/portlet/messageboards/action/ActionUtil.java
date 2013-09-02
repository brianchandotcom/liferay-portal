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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBPermission;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getCategory(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String topLink = ParamUtil.getString(request, "topLink");

		if (topLink.equals("banned-users") &&
			!MBPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.BAN_USER)) {

			throw new PrincipalException();
		}

		MBBanLocalServiceUtil.checkBan(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId());

		long categoryId = ParamUtil.getLong(request, "mbCategoryId");

		MBCategory category = null;

		if ((categoryId > 0) &&
			(categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			category = MBCategoryServiceUtil.getCategory(categoryId);
		}
		else {
			MBPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		request.setAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY, category);
	}

	public static void getCategory(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getCategory(request);
	}

	public static void getMessage(HttpServletRequest request) throws Exception {
		long messageId = ParamUtil.getLong(request, "messageId");

		MBMessage message = null;

		if (messageId > 0) {
			message = MBMessageServiceUtil.getMessage(messageId);
		}

		if ((message != null) && message.isInTrash()) {
			throw new NoSuchMessageException();
		}

		request.setAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE, message);
	}

	public static void getMessage(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getMessage(request);
	}

	public static void getThreadMessage(HttpServletRequest request)
		throws Exception {

		long threadId = ParamUtil.getLong(request, "threadId");

		MBMessage message = null;

		if (threadId > 0) {
			MBThread thread = MBThreadLocalServiceUtil.getThread(threadId);

			message = MBMessageServiceUtil.getMessage(
				thread.getRootMessageId());
		}

		if ((message != null) && message.isInTrash()) {
			throw new NoSuchMessageException();
		}

		request.setAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE, message);
	}

	public static void getThreadMessage(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getThreadMessage(request);
	}

}