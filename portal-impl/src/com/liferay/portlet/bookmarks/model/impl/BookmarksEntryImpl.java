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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryImpl extends BookmarksEntryBaseImpl {

	public BookmarksEntryImpl() {
	}

	@Override
	public String buildTreePath() throws PortalException, SystemException {
		StringBundler sb = new StringBundler();

		buildTreePath(sb, getFolder());

		return sb.toString();
	}

	@Override
	public BookmarksFolder getFolder() throws PortalException, SystemException {
		if (getFolderId() <= 0) {
			return new BookmarksFolderImpl();
		}

		return BookmarksFolderLocalServiceUtil.getFolder(getFolderId());
	}

	protected void buildTreePath(StringBundler sb, BookmarksFolder folder)
		throws PortalException, SystemException {

		if (folder == null) {
			sb.append(StringPool.SLASH);
		}
		else {
			buildTreePath(sb, folder.getParentFolder());

			sb.append(folder.getFolderId());
			sb.append(StringPool.SLASH);
		}
	}

}