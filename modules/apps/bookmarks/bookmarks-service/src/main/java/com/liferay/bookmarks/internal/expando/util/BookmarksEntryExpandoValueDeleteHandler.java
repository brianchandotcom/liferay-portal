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

package com.liferay.bookmarks.internal.expando.util;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.expando.kernel.util.ExpandoValueDeleteHandler;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.bookmarks.model.BookmarksEntry",
	service = ExpandoValueDeleteHandler.class
)
public class BookmarksEntryExpandoValueDeleteHandler
	implements ExpandoValueDeleteHandler {

	@Override
	public void deletedExpandoValue(long classPK) {
		BookmarksEntry entry = _bookmarksEntryLocalService.fetchBookmarksEntry(
			classPK);

		if (entry == null) {
			return;
		}

		entry.setModifiedDate(new Date());

		_bookmarksEntryLocalService.updateBookmarksEntry(entry);
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

}