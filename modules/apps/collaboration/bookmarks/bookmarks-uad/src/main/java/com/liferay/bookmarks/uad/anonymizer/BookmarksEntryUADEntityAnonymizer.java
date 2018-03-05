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

package com.liferay.bookmarks.uad.anonymizer;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.entity.BookmarksEntryUADEntity;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.BaseUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY},
	service = UADEntityAnonymizer.class
)
public class BookmarksEntryUADEntityAnonymizer extends BaseUADEntityAnonymizer {

	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		BookmarksEntry bookmarksEntry = _getBookmarksEntry(uadEntity);

		_autoAnonymize(bookmarksEntry, uadEntity.getUserId());
	}

	@Override
	public void autoAnonymizeAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			(BookmarksEntry bookmarksEntry) -> _autoAnonymize(
				bookmarksEntry, userId));

		actionableDynamicQuery.performActions();
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		_bookmarksEntryLocalService.deleteEntry(_getBookmarksEntry(uadEntity));
	}

	@Override
	public void deleteAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			(BookmarksEntry bookmarksEntry) ->
				_bookmarksEntryLocalService.deleteEntry(bookmarksEntry));

		actionableDynamicQuery.performActions();
	}

	@Override
	public List<String> getUADEntityNonanonymizableFieldNames() {
		return Arrays.asList("description", "name", "url");
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	private void _autoAnonymize(BookmarksEntry bookmarksEntry, long userId)
		throws PortalException {

		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		if (bookmarksEntry.getStatusByUserId() == userId) {
			bookmarksEntry.setStatusByUserId(anonymousUser.getUserId());
			bookmarksEntry.setStatusByUserName(anonymousUser.getScreenName());
		}

		if (bookmarksEntry.getUserId() == userId) {
			bookmarksEntry.setUserId(anonymousUser.getUserId());
			bookmarksEntry.setUserName(anonymousUser.getFullName());
		}

		_bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry);
	}

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(
			_bookmarksEntryLocalService.getActionableDynamicQuery(),
			BookmarksUADConstants.USER_ID_FIELD_NAMES_BOOKMARKS_ENTRY, userId);
	}

	private BookmarksEntry _getBookmarksEntry(UADEntity uadEntity)
		throws PortalException {

		_validate(uadEntity);

		BookmarksEntryUADEntity bookmarksEntryUADEntity =
			(BookmarksEntryUADEntity)uadEntity;

		return bookmarksEntryUADEntity.getBookmarksEntry();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof BookmarksEntryUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

	@Reference(
		target = "(model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

}