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

package com.liferay.message.boards.web.portlet.icon;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.PortletRequest;

/**
 * @author Sergio González
 */
public class PermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PermissionsPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);

		_category = (MBCategory)portletRequest.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);
	}

	@Override
	public String getMessage() {
		return "permissions";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			String modelResource = "com.liferay.portlet.messageboards";
			String modelResourceDescription = themeDisplay.getScopeGroupName();
			String resourcePrimKey = String.valueOf(
				themeDisplay.getScopeGroupId());

			if (_category != null) {
				modelResource = MBCategory.class.getName();
				modelResourceDescription = _category.getName();
				resourcePrimKey = String.valueOf(_category.getCategoryId());
			}

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
			if (!MBPermission.contains(
					permissionChecker, themeDisplay.getScopeGroupId(),
					ActionKeys.PERMISSIONS)) {

				return false;
			}
		}
		catch (Exception e) {
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

	private final MBCategory _category;

}