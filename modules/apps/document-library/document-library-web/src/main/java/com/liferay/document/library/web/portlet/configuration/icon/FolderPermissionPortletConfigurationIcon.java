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

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.PortletRequest;

/**
 * @author Roberto Díaz
 */
public class FolderPermissionPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public FolderPermissionPortletConfigurationIcon(
		PortletRequest portletRequest, Folder folder) {

		super(portletRequest);

		_folder = folder;
	}

	@Override
	public String getMessage() {
		return "permissions";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			if (_folder != null) {
				url = PermissionsURLTag.doTag(
					null, DLFolderConstants.getClassName(),
					HtmlUtil.unescape(_folder.getName()), null,
					String.valueOf(_folder.getFolderId()),
					LiferayWindowState.POP_UP.toString(), null,
					themeDisplay.getRequest());
			}
			else {
				url = PermissionsURLTag.doTag(
					null, "com.liferay.portlet.documentlibrary",
					HtmlUtil.unescape(themeDisplay.getScopeGroupName()), null,
					String.valueOf(themeDisplay.getScopeGroupId()),
					LiferayWindowState.POP_UP.toString(), null,
					themeDisplay.getRequest());
			}
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public boolean isShow() {
		if (_folder != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	private final Folder _folder;

}