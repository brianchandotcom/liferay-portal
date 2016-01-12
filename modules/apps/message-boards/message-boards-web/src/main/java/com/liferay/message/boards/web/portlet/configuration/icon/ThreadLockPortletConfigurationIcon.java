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

package com.liferay.message.boards.web.portlet.configuration.icon;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class ThreadLockPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ThreadLockPortletConfigurationIcon(
		PortletRequest portletRequest, MBThread thread) {

		super(portletRequest);

		_thread = thread;
	}

	@Override
	public String getMessage() {
		if (_thread.isLocked()) {
			return "unlock";
		}

		return "lock";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
			themeDisplay.getPlid(), PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/message_boards/edit_message");

		if (_thread.isLocked()) {
			portletURL.setParameter(Constants.CMD, Constants.UNLOCK);
		}
		else {
			portletURL.setParameter(Constants.CMD, Constants.LOCK);
		}

		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"threadId", String.valueOf(_thread.getThreadId()));

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			return MBCategoryPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), _thread.getCategoryId(),
				ActionKeys.LOCK_THREAD);
		}
		catch (PortalException pe) {
		}

		return false;
	}

	private final MBThread _thread;

}