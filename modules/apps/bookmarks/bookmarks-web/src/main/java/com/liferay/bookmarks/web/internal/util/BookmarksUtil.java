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

package com.liferay.bookmarks.web.internal.util;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksUtil {

	public static String getAbsolutePath(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return themeDisplay.translate("home");
		}

		BookmarksFolder folder =
			BookmarksFolderLocalServiceUtil.fetchBookmarksFolder(folderId);

		List<BookmarksFolder> folders = folder.getAncestors();

		StringBundler sb = new StringBundler((folders.size() * 3) + 5);

		sb.append(themeDisplay.translate("home"));
		sb.append(StringPool.SPACE);

		Collections.reverse(folders);

		for (BookmarksFolder curFolder : folders) {
			sb.append(StringPool.RAQUO_CHAR);
			sb.append(StringPool.SPACE);
			sb.append(curFolder.getName());
		}

		sb.append(StringPool.RAQUO_CHAR);
		sb.append(StringPool.SPACE);
		sb.append(folder.getName());

		return sb.toString();
	}

	public static String getControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, BookmarksPortletKeys.BOOKMARKS_ADMIN,
			PortletRequest.RENDER_PHASE);

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			portletURL.setParameter("mvcRenderCommandName", "/bookmarks/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/bookmarks/view_folder");
			portletURL.setParameter("folderId", String.valueOf(folderId));
		}

		return portletURL.toString();
	}

}