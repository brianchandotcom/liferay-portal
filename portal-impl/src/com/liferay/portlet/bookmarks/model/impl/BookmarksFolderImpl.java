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

package com.liferay.portlet.bookmarks.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksFolderImpl extends BookmarksFolderBaseImpl {

	public BookmarksFolderImpl() {
	}

	public List<BookmarksFolder> getAncestors()
		throws PortalException, SystemException {

		List<BookmarksFolder> ancestors = new ArrayList<BookmarksFolder>();

		BookmarksFolder folder = this;

		while (true) {
			if (!folder.isRoot()) {
				folder = folder.getParentFolder();

				ancestors.add(folder);
			}
			else {
				break;
			}
		}

		return ancestors;
	}

	public BookmarksFolder getParentFolder()
		throws PortalException, SystemException {

		if (getParentFolderId() ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return null;
		}

		return BookmarksFolderLocalServiceUtil.getFolder(getParentFolderId());
	}

	public boolean isRoot() {
		if (getParentFolderId() ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return true;
		}
		else {
			return false;
		}
	}

}