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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
public class ThreadPermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ThreadPermissionsPortletConfigurationIcon(
		PortletRequest portletRequest, MBMessageDisplay messageDisplay) {

		super(portletRequest);

		_messageDisplay = messageDisplay;
	}

	@Override
	public String getMessage() {
		return "permissions";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			MBMessage rootMessage = null;

			MBMessage message = _messageDisplay.getMessage();

			if (message.isRoot()) {
				rootMessage = message;
			}
			else {
				rootMessage = _mbMessageLocalService.getMessage(
					message.getRootMessageId());
			}

			String modelResource = MBMessage.class.getName();
			String modelResourceDescription = rootMessage.getSubject();

			MBThread thread = _messageDisplay.getThread();

			String resourcePrimKey = String.valueOf(thread.getRootMessageId());

			url = PermissionsURLTag.doTag(
				StringPool.BLANK, modelResource, modelResourceDescription, null,
				resourcePrimKey, LiferayWindowState.POP_UP.toString(), null,
				themeDisplay.getRequest());
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public boolean isShow() {
		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
			return false;
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		try {
			MBThread thread = _messageDisplay.getThread();

			if (thread.isLocked()) {
				return false;
			}

			if (!MBMessagePermission.contains(
					permissionChecker, _messageDisplay.getMessage(),
					ActionKeys.PERMISSIONS)) {

				return false;
			}
		}
		catch (PortalException pe) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	private volatile MBMessageLocalService _mbMessageLocalService;
	private final MBMessageDisplay _messageDisplay;

}