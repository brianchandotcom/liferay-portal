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

package com.liferay.bookmarks.web.internal.portlet.action;

import com.liferay.bookmarks.exception.NoSuchEntryException;
import com.liferay.bookmarks.exception.NoSuchFolderException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.bookmarks.web.internal.security.permission.resource.BookmarksResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static List<BookmarksEntry> getEntries(HttpServletRequest request)
		throws Exception {

		long[] entryIds = ParamUtil.getLongValues(
			request, "rowIdsBookmarksEntry");

		List<BookmarksEntry> entries = new ArrayList<>();

		for (long entryId : entryIds) {
			BookmarksEntry entry = BookmarksEntryServiceUtil.getEntry(entryId);

			entries.add(entry);
		}

		return entries;
	}

	public static List<BookmarksEntry> getEntries(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getEntries(request);
	}

	public static BookmarksEntry getEntry(HttpServletRequest request)
		throws Exception {

		long entryId = ParamUtil.getLong(request, "entryId");

		BookmarksEntry entry = null;

		if (entryId > 0) {
			entry = BookmarksEntryServiceUtil.getEntry(entryId);

			if (entry.isInTrash()) {
				throw new NoSuchEntryException("{entryId=" + entryId + "}");
			}
		}

		return entry;
	}

	public static BookmarksEntry getEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getEntry(request);
	}

	public static BookmarksFolder getFolder(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(request, "folderId");

		BookmarksFolder folder = null;

		if ((folderId > 0) &&
			(folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = BookmarksFolderServiceUtil.getFolder(folderId);

			if (folder.isInTrash()) {
				throw new NoSuchFolderException("{folderId=" + folderId + "}");
			}
		}
		else {
			BookmarksResourcePermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		return folder;
	}

	public static BookmarksFolder getFolder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFolder(request);
	}

	public static List<BookmarksFolder> getFolders(HttpServletRequest request)
		throws Exception {

		long[] folderIds = ParamUtil.getLongValues(
			request, "rowIdsBookmarksFolder");

		List<BookmarksFolder> folders = new ArrayList<>();

		for (long folderId : folderIds) {
			if ((folderId > 0) &&
				(folderId !=
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

				BookmarksFolder folder = BookmarksFolderServiceUtil.getFolder(
					folderId);

				folders.add(folder);
			}
		}

		return folders;
	}

	public static List<BookmarksFolder> getFolders(
			PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFolders(request);
	}

}