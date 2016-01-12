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
import com.liferay.message.boards.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIconFactory;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconFactory;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

import javax.portlet.PortletRequest;

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
	service = PortletConfigurationIconFactory.class
)
public class ThreadLockPortletConfigurationIconFactory
	extends BasePortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			MBMessageDisplay messageDisplay = ActionUtil.getMessageDisplay(
				portletRequest);

			MBCategory category = messageDisplay.getCategory();

			long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

			if (category != null) {
				categoryId = category.getCategoryId();
			}

			if (MBCategoryPermission.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(), categoryId,
					ActionKeys.LOCK_THREAD)) {

				MBThread thread = messageDisplay.getThread();

				if (thread.isLocked()) {
					return new UnlockThreadPortletConfigurationIcon(
						portletRequest, thread);
				}
				else {
					return new LockThreadPortletConfigurationIcon(
						portletRequest, thread);
				}
			}
		}
		catch (PortalException pe) {
		}

		return null;
	}

	@Override
	public double getWeight() {
		return 101;
	}

}