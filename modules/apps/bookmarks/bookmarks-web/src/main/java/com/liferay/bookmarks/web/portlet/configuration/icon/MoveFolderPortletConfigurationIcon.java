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

package com.liferay.bookmarks.web.portlet.configuration.icon;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.permission.BookmarksFolderPermissionChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class MoveFolderPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public MoveFolderPortletConfigurationIcon(
		PortletRequest portletRequest, BookmarksFolder folder) {

		super(portletRequest);

		_folder = folder;
	}

	@Override
	public String getMessage() {
		return "move";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, BookmarksPortletKeys.BOOKMARKS_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/bookmarks/move_entry");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter(
			"rowIdsBookmarksFolder", String.valueOf(_folder.getFolderId()));

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			if (_folder.getFolderId() ==
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return false;
			}

			if (BookmarksFolderPermissionChecker.contains(
					themeDisplay.getPermissionChecker(), _folder,
					ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (PortalException pe) {
		}

		return false;
	}

	private final BookmarksFolder _folder;

}