/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.model.impl;

import com.liferay.bookmarks.constants.BookmarksFolderConstants;
import com.liferay.bookmarks.exception.NoSuchFolderException;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksFolderImpl extends BookmarksFolderBaseImpl {

	@Override
	public List<Long> getAncestorFolderIds() throws PortalException {
		List<Long> ancestorFolderIds = new ArrayList<>();

		BookmarksFolder folder = this;

		while (!folder.isRoot()) {
			try {
				folder = folder.getParentFolder();

				ancestorFolderIds.add(folder.getFolderId());
			}
			catch (NoSuchFolderException noSuchFolderException) {
				if (folder.isInTrash()) {
					break;
				}

				throw noSuchFolderException;
			}
		}

		return ancestorFolderIds;
	}

	@Override
	public List<BookmarksFolder> getAncestors() throws PortalException {
		List<BookmarksFolder> ancestors = new ArrayList<>();

		BookmarksFolder folder = this;

		while (!folder.isRoot()) {
			try {
				folder = folder.getParentFolder();

				ancestors.add(folder);
			}
			catch (NoSuchFolderException noSuchFolderException) {
				if (folder.isInTrash()) {
					break;
				}

				throw noSuchFolderException;
			}
		}

		return ancestors;
	}

	@Override
	public BookmarksFolder getParentFolder() throws PortalException {
		if (getParentFolderId() ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return null;
		}

		return BookmarksFolderLocalServiceUtil.getFolder(getParentFolderId());
	}

	@Override
	public boolean isRoot() {
		if (getParentFolderId() ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return true;
		}

		return false;
	}

}