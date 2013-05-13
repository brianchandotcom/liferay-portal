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

package com.liferay.portlet.bookmarks.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryImpl extends BookmarksEntryBaseImpl {

	public BookmarksEntryImpl() {
	}

	@Override
	public BookmarksFolder getFolder() {
		BookmarksFolder folder = null;

		if (getFolderId() > 0) {
			try {
				folder = BookmarksFolderLocalServiceUtil.getFolder(
					getFolderId());
			}
			catch (Exception e) {
				folder = new BookmarksFolderImpl();

				_log.error(e);
			}
		}
		else {
			folder = new BookmarksFolderImpl();
		}

		return folder;
	}

	@Override
	public BookmarksFolder getTrashContainer() {
		BookmarksFolder folder = getFolder();

		if (folder.isInTrash()) {
			return folder;
		}

		return folder.getTrashContainer();
	}

	@Override
	public boolean isInTrashContainer() {
		if (getTrashContainer() != null) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BookmarksEntryImpl.class);

}