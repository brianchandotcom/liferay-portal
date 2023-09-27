/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.security.permission;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.bookmarks.model.BookmarksEntry",
	service = PermissionUpdateHandler.class
)
public class BookmarksEntryPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		BookmarksEntry bookmarksEntry =
			_bookmarksEntryLocalService.fetchBookmarksEntry(
				GetterUtil.getLong(primKey));

		if (bookmarksEntry == null) {
			return;
		}

		bookmarksEntry.setModifiedDate(new Date());

		_bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry);
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

}