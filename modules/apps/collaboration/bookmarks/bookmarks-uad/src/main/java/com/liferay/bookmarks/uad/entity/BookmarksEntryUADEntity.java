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
import com.liferay.user.associated.data.entity.BaseUADEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Noah Sherrill
 */
public class BookmarksEntryUADEntity extends BaseUADEntity {

	public BookmarksEntryUADEntity(
		long userId, String uadEntityId, BookmarksEntry bookmarksEntry) {

		super(
			userId, uadEntityId,
			BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY);

		_bookmarksEntry = bookmarksEntry;
	}

	public BookmarksEntry getBookmarksEntry() {
		return _bookmarksEntry;
	}

	@Override
	public Map<String, Object> getUADEntityNonanonymizableFieldValues() {
		Map<String, Object> uadEntityNonanonymizableFieldValues =
			new HashMap<>();

		uadEntityNonanonymizableFieldValues.put(
			"description", _bookmarksEntry.getDescription());
		uadEntityNonanonymizableFieldValues.put(
			"name", _bookmarksEntry.getName());
		uadEntityNonanonymizableFieldValues.put(
			"url", _bookmarksEntry.getUrl());

		return uadEntityNonanonymizableFieldValues;
	}

	private final BookmarksEntry _bookmarksEntry;

}