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
import com.liferay.bookmarks.uad.entity.BookmarksEntryUADEntity;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.aggregator.BaseUADEntityAggregator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY},
	service = UADEntityAggregator.class
)
public class BookmarksEntryUADEntityAggregator extends BaseUADEntityAggregator {

	@Override
	public int count(long userId) {
		return (int)_bookmarksEntryLocalService.dynamicQueryCount(
			_getDynamicQuery(userId));
	}

	@Override
	public List<UADEntity> getUADEntities(long userId, int start, int end) {
		List<BookmarksEntry> bookmarksEntries =
			_bookmarksEntryLocalService.dynamicQuery(
				_getDynamicQuery(userId), start, end);

		List<UADEntity> uadEntities = new ArrayList<>(bookmarksEntries.size());

		for (BookmarksEntry bookmarksEntry : bookmarksEntries) {
			uadEntities.add(
				new BookmarksEntryUADEntity(
					userId, _getUADEntityId(userId, bookmarksEntry),
					bookmarksEntry));
		}

		return uadEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) throws PortalException {
		BookmarksEntry bookmarksEntry =
			_bookmarksEntryLocalService.getBookmarksEntry(
				_getEntryId(uadEntityId));

		return new BookmarksEntryUADEntity(
			_getUserId(uadEntityId), uadEntityId, bookmarksEntry);
	}

	@Override
	public String getUADEntitySetName() {
		return BookmarksUADConstants.UAD_ENTITY_SET_NAME;
	}

	private DynamicQuery _getDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addDynamicQueryCriteria(
			_bookmarksEntryLocalService.dynamicQuery(),
			BookmarksUADConstants.USER_ID_FIELD_NAMES_BOOKMARKS_ENTRY, userId);
	}

	private long _getEntryId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split(StringPool.POUND);

		return Long.parseLong(uadEntityIdParts[0]);
	}

	private String _getUADEntityId(long userId, BookmarksEntry bookmarksEntry) {
		return String.valueOf(bookmarksEntry.getEntryId()) + StringPool.POUND +
			String.valueOf(userId);
	}

	private long _getUserId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split(StringPool.POUND);

		return Long.parseLong(uadEntityIdParts[1]);
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

}