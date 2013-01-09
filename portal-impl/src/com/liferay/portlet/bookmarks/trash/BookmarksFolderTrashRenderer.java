/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class BookmarksFolderTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "bookmarks_folder";

	public BookmarksFolderTrashRenderer(BookmarksFolder folder)
		throws PortalException, SystemException {

		_folder = folder;
	}

	public String getClassName() {
		return BookmarksFolder.class.getName();
	}

	public long getClassPK() {
		return _folder.getFolderId();
	}

	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/folder.png";
	}

	public String getPortletId() {
		return PortletKeys.BOOKMARKS;
	}

	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_folder.getDescription());
	}

	public String getTitle(Locale locale) {
		return _folder.getName();
	}

	public String getType() {
		return TYPE;
	}

	private BookmarksFolder _folder;

}