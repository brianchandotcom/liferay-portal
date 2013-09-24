/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.bookmarks.NoSuchEntryException;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksPermission;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getEntry(HttpServletRequest request) throws Exception {
		long entryId = ParamUtil.getLong(request, "entryId");

		BookmarksEntry entry = null;

		if (entryId > 0) {
			entry = BookmarksEntryServiceUtil.getEntry(entryId);

			if (entry.isInTrash()) {
				throw new NoSuchEntryException();
			}
		}

		request.setAttribute(WebKeys.BOOKMARKS_ENTRY, entry);
	}

	public static void getEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getEntry(request);
	}

	public static void getFolder(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(request, "folderId");

		BookmarksFolder folder = null;

		if ((folderId > 0) &&
			(folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = BookmarksFolderServiceUtil.getFolder(folderId);

			if (folder.isInTrash()) {
				throw new NoSuchFolderException();
			}
		}
		else {
			BookmarksPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		request.setAttribute(WebKeys.BOOKMARKS_FOLDER, folder);
	}

	public static void getFolder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFolder(request);
	}

}