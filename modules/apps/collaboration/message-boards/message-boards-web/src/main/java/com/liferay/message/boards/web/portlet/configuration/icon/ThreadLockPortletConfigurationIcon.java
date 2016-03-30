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

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.message.boards.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"path=/message_boards/view_message"
	},
	service = PortletConfigurationIcon.class
)
public class ThreadLockPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		try {
			MBMessage message = ActionUtil.getMessage(portletRequest);

			MBThread thread = message.getThread();

			String key = "lock";

			if (thread.isLocked()) {
				key = "unlock";
			}

			return LanguageUtil.get(
				getResourceBundle(getLocale(portletRequest)), key);
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/message_boards/edit_message");

		try {
			MBMessage message = ActionUtil.getMessage(portletRequest);

			MBThread thread = message.getThread();

			if (thread.isLocked()) {
				portletURL.setParameter(Constants.CMD, Constants.UNLOCK);
			}
			else {
				portletURL.setParameter(Constants.CMD, Constants.LOCK);
			}

			portletURL.setParameter(
				"redirect", PortalUtil.getCurrentURL(portletRequest));
			portletURL.setParameter(
				"threadId", String.valueOf(thread.getThreadId()));
		}
		catch (Exception e) {
			return null;
		}

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 103;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			MBMessage message = ActionUtil.getMessage(portletRequest);

			return MBCategoryPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), message.getCategoryId(),
				ActionKeys.LOCK_THREAD);
		}
		catch (Exception e) {
		}

		return false;
	}

}