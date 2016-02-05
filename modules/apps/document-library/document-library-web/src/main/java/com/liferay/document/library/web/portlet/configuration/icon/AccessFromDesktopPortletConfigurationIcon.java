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

import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

/**
 * @author Roberto Díaz
 */
public class AccessFromDesktopPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	public AccessFromDesktopPortletConfigurationIcon(
		ServletContext servletContext, String jspPath,
		PortletRequest portletRequest, Folder folder) {

		super(servletContext, jspPath, portletRequest);

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

			long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			if (_folder != null) {
				folderId = _folder.getFolderId();
			}

			if (DLFolderPermission.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(), folderId,
					ActionKeys.VIEW) &&
				portletDisplay.isWebDAVEnabled() &&
				((_folder == null) ||
				 (_folder.getRepositoryId() ==
					 themeDisplay.getScopeGroupId()))) {

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