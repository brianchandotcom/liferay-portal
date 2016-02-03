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
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.trash.kernel.util.TrashUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class DeleteFolderPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DeleteFolderPortletConfigurationIcon(
		PortletRequest portletRequest, BookmarksFolder folder) {

		super(portletRequest);

		_folder = folder;
	}

	@Override
	public String getMessage() {
		if (isTrashEnabled(themeDisplay.getScopeGroupId())) {
			return "move-to-the-recycle-bin";
		}

		return "delete";
	}

	@Override
	public String getURL() {
		PortletURL deleteURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, BookmarksPortletKeys.BOOKMARKS_ADMIN,
			PortletRequest.ACTION_PHASE);

		deleteURL.setParameter(
			ActionRequest.ACTION_NAME, "/bookmarks/edit_folder");

		String cmd = Constants.DELETE;

		if (isTrashEnabled(themeDisplay.getScopeGroupId())) {
			cmd = Constants.MOVE_TO_TRASH;
		}

		deleteURL.setParameter(Constants.CMD, cmd);

		PortletURL parentFolderURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, BookmarksPortletKeys.BOOKMARKS_ADMIN,
			PortletRequest.RENDER_PHASE);

		long parentFolderId = _folder.getParentFolderId();

		if (parentFolderId ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			parentFolderURL.setParameter(
				"mvcRenderCommandName", "/bookmarks/view");
		}
		else {
			parentFolderURL.setParameter(
				"mvcRenderCommandName", "/bookmarks/view_folder");
			parentFolderURL.setParameter(
				"folderId", String.valueOf(parentFolderId));
		}

		deleteURL.setParameter("redirect", parentFolderURL.toString());

		deleteURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));

		return deleteURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			if (_folder == null) {
				return false;
			}

			if (BookmarksFolderPermissionChecker.contains(
					themeDisplay.getPermissionChecker(), _folder,
					ActionKeys.DELETE)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	protected boolean isTrashEnabled(long groupId) {
		try {
			if (TrashUtil.isTrashEnabled(groupId)) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	private final BookmarksFolder _folder;

}