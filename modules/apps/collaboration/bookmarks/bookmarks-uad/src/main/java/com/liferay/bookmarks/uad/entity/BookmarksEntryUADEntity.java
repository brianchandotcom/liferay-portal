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

package com.liferay.bookmarks.uad.entity;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.entity.BaseUADEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Noah Sherrill
 */
public class BookmarksEntryUADEntity extends BaseUADEntity {

	public BookmarksEntryUADEntity(
		long userId, String uadEntityId, BookmarksEntry bookmarksEntry) {

		super(userId, uadEntityId, BookmarksUADConstants.BOOKMARKS_ENTRY);

		_bookmarksEntry = bookmarksEntry;
	}

	public BookmarksEntry getBookmarksEntry() {
		return _bookmarksEntry;
	}

	@Override
	public Map<String, Object> getEntityNonAnonymizableFieldValues(
			List<String> nonAnonymizableFieldNames)
		throws PortalException {

		return null;
	}

	private final BookmarksEntry _bookmarksEntry;

}