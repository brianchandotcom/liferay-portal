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

package com.liferay.bookmarks.uad.aggregator;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.aggregator.DynamicQueryUADAggregator;
import com.liferay.user.associated.data.aggregator.UADAggregator;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY,
	service = UADAggregator.class
)
public class BookmarksEntryUADAggregator
	extends DynamicQueryUADAggregator<BookmarksEntry> {

	@Override
	public BookmarksEntry get(Serializable primaryKey) throws PortalException {
		return _bookmarksEntryLocalService.getBookmarksEntry(
			Long.valueOf(primaryKey.toString()));
	}

	@Override
	public String getApplicationName() {
		return BookmarksUADConstants.APPLICATION_NAME;
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return _bookmarksEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		return _bookmarksEntryLocalService.dynamicQuery();
	}

	@Override
	protected List<BookmarksEntry> doGetRange(
		DynamicQuery dynamicQuery, int start, int end) {

		return _bookmarksEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return BookmarksUADConstants.USER_ID_FIELD_NAMES_BOOKMARKS_ENTRY;
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

}