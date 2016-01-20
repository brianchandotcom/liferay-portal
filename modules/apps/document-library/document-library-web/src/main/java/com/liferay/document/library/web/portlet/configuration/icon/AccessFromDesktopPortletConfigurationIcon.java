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

package com.liferay.document.library.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import javax.portlet.PortletRequest;

/**
 * @author Roberto Díaz
 */
public class AccessFromDesktopPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public AccessFromDesktopPortletConfigurationIcon(
		PortletRequest portletRequest, Folder folder) {

		super(portletRequest);

		_folder = folder;
	}

	@Override
	public String getMessage() {
		return "access-from-desktop";
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		try {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			if (DLFolderPermission.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(), _folder.getFolderId(),
					ActionKeys.VIEW) &&
				portletDisplay.isWebDAVEnabled() &&
				(_folder.getRepositoryId() == themeDisplay.getScopeGroupId())) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private final Folder _folder;

}