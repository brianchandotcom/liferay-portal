/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.model.impl;

import com.liferay.bookmarks.constants.BookmarksFolderConstants;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.view.count.ViewCountManagerUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryImpl extends BookmarksEntryBaseImpl {

	@Override
	public String buildTreePath() throws PortalException {
		if (getFolderId() ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return StringPool.SLASH;
		}

		BookmarksFolder folder = getFolder();

		return folder.buildTreePath();
	}

	@Override
	public BookmarksFolder getFolder() throws PortalException {
		if (getFolderId() <= 0) {
			return new BookmarksFolderImpl();
		}

		return BookmarksFolderLocalServiceUtil.getFolder(getFolderId());
	}

	@Override
	public long getVisits() {
		return ViewCountManagerUtil.getViewCount(
			getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(BookmarksEntry.class),
			getPrimaryKey());
	}

}